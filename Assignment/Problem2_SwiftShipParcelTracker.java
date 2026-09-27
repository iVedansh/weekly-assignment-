import java.util.*;

enum ParcelStatus{BOOKED,PICKED_UP,IN_TRANSIT,OUT_FOR_DELIVERY,DELIVERED,CANCELLED}
interface ShippingType{double calculateCharge(double weightKg);String getName();}
class StandardShipping implements ShippingType{public double calculateCharge(double w){return 40+10*w;}public String getName(){return "Standard";}}
class ExpressShipping implements ShippingType{public double calculateCharge(double w){return 80+15*w;}public String getName(){return "Express";}}
class FragileShipping implements ShippingType{private final ShippingType standard=new StandardShipping();public double calculateCharge(double w){return standard.calculateCharge(w)+50;}public String getName(){return "Fragile";}}
interface NotificationChannel{void notify(String parcelId,ParcelStatus status);}
class SmsChannel implements NotificationChannel{public void notify(String id,ParcelStatus s){System.out.println("[SMS] "+id+" is now "+s+".");}}
class EmailChannel implements NotificationChannel{public void notify(String id,ParcelStatus s){System.out.println("[Email] "+id+" is now "+s+".");}}
class Customer{private final String name;public Customer(String name){this.name=name;}}
class Parcel{
    private final String id;private final Customer customer;private final double weightKg;private final ShippingType shippingType;private final List<NotificationChannel> channels=new ArrayList<>();private ParcelStatus status;
    public Parcel(String id,Customer customer,double weightKg,ShippingType shippingType){if(weightKg<=0)throw new IllegalArgumentException("Weight must be positive");this.id=id;this.customer=customer;this.weightKg=weightKg;this.shippingType=shippingType;status=ParcelStatus.BOOKED;}
    public String getId(){return id;}public ParcelStatus getStatus(){return status;}public double getCharge(){return shippingType.calculateCharge(weightKg);}
    public void subscribe(NotificationChannel c){channels.add(c);}private void notifyChannels(){for(NotificationChannel c:channels)c.notify(id,status);}public void notifyBooked(){notifyChannels();}
    public void advanceTo(ParcelStatus next){if(next.ordinal()!=status.ordinal()+1)throw new IllegalStateException(status+" → "+next+" is not allowed");status=next;notifyChannels();}
    public void cancel(){if(status!=ParcelStatus.BOOKED)throw new IllegalStateException(id+" can be cancelled only while BOOKED");status=ParcelStatus.CANCELLED;}
}
class ParcelService{
    public Parcel book(String id,Customer customer,double weightKg,ShippingType type,NotificationChannel... channels){Parcel p=new Parcel(id,customer,weightKg,type);for(NotificationChannel c:channels)p.subscribe(c);System.out.printf("Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f.%n",id,type.getName(),weightKg,p.getCharge());p.notifyBooked();return p;}
}
public class Problem2_SwiftShipParcelTracker{
    public static void main(String[] args){
        ParcelService service=new ParcelService();Parcel p=service.book("P101",new Customer("Asha"),2,new ExpressShipping(),new SmsChannel(),new EmailChannel());
        p.advanceTo(ParcelStatus.PICKED_UP);
        try{p.cancel();}catch(Exception e){System.out.println("Cancellation failed: "+e.getMessage()+".");}
        p.advanceTo(ParcelStatus.IN_TRANSIT);
        try{p.advanceTo(ParcelStatus.DELIVERED);}catch(Exception e){System.out.println("Invalid transition: "+e.getMessage()+".");}
    }
}