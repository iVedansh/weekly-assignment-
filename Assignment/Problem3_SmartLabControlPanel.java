import java.util.*;

interface Capability{String getName();void apply(Device device,double value);}
class PowerCapability implements Capability{
    private boolean on;public String getName(){return "Power";}
    public void apply(Device d,double v){if(v!=0&&v!=1)throw new IllegalArgumentException("Power must be 0 (OFF) or 1 (ON)");on=v==1;System.out.println(d.getName()+": "+(on?"ON":"OFF")+".");}
}
class BrightnessCapability implements Capability{
    private double brightness;public String getName(){return "Brightness";}
    public void apply(Device d,double v){if(v<0||v>100)throw new IllegalArgumentException(d.getName()+" brightness must be between 0% and 100%");brightness=v;System.out.println(d.getName()+": brightness set to "+trim(v)+"%.");}
    private String trim(double v){return v==(long)v?String.valueOf((long)v):String.valueOf(v);}
}
class TemperatureCapability implements Capability{
    private double temperature;public String getName(){return "Temperature";}
    public void apply(Device d,double v){if(v<16||v>30)throw new IllegalArgumentException(d.getName()+" temperature must be between 16°C and 30°C");temperature=v;System.out.println(d.getName()+": temperature set to "+trim(v)+"°C.");}
    private String trim(double v){return v==(long)v?String.valueOf((long)v):String.valueOf(v);}
}
class Device{
    private final String name;private final Map<String,Capability> capabilities=new LinkedHashMap<>();
    public Device(String name){this.name=name;}public String getName(){return name;}
    public void addCapability(Capability c){capabilities.put(c.getName(),c);System.out.println(name+": "+c.getName()+" capability added.");}
    public boolean supports(String n){return capabilities.containsKey(n);}
    public void apply(String n,double v){Capability c=capabilities.get(n);if(c!=null)c.apply(this,v);}
}
class SceneStep{
    private final String capabilityName;private final double value;
    public SceneStep(String n,double v){capabilityName=n;value=v;}
    public boolean applyTo(Device d){if(!d.supports(capabilityName))return false;d.apply(capabilityName,value);return true;}
}
class Scene{
    private final String name;private final List<SceneStep> steps;
    public Scene(String name,List<SceneStep> steps){this.name=name;this.steps=steps;}
    public void execute(List<Device> devices){System.out.println("Scene '"+name+"' started.");int actions=0;for(SceneStep s:steps)for(Device d:devices)if(s.applyTo(d))actions++;System.out.println("Scene '"+name+"' completed: "+actions+" actions applied.");}
}
public class Problem3_SmartLabControlPanel{
    public static void main(String[] args){
        Device ac=new Device("Lab AC");ac.addCapability(new PowerCapability());ac.addCapability(new TemperatureCapability());
        Device lights=new Device("Ceiling Lights");lights.addCapability(new PowerCapability());lights.addCapability(new BrightnessCapability());
        Device projector=new Device("Projector");projector.addCapability(new PowerCapability());
        List<Device> devices=Arrays.asList(ac,lights,projector);
        Scene lectureMode=new Scene("Lecture Mode",Arrays.asList(new SceneStep("Power",1),new SceneStep("Brightness",40),new SceneStep("Temperature",24)));
        lectureMode.execute(devices);
        try{ac.apply("Temperature",12);}catch(Exception e){System.out.println("Rejected: "+e.getMessage()+".");}
        projector.addCapability(new BrightnessCapability());projector.apply("Brightness",70);
    }
}