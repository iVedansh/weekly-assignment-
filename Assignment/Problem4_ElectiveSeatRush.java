import java.util.*;

interface CreditPolicy{int getLimit();String getName();}
class RegularPolicy implements CreditPolicy{public int getLimit(){return 24;}public String getName(){return "Regular";}}
class HonorsPolicy implements CreditPolicy{public int getLimit(){return 28;}public String getName(){return "Honors";}}
class ExchangePolicy implements CreditPolicy{public int getLimit(){return 20;}public String getName(){return "Exchange";}}
class ElectiveStudent{
    private final String name;private final CreditPolicy policy;private int currentCredits;
    public ElectiveStudent(String name,CreditPolicy policy,int currentCredits){this.name=name;this.policy=policy;this.currentCredits=currentCredits;}
    public String getName(){return name;}public int getCredits(){return currentCredits;}public int getLimit(){return policy.getLimit();}public String getType(){return policy.getName();}
    public boolean canAdd(int credits){return currentCredits+credits<=getLimit();}public void addCredits(int credits){currentCredits+=credits;}public void removeCredits(int credits){currentCredits-=credits;}
}
class Elective{
    private final String name;private final int credits;private final int capacity;private final List<ElectiveStudent> enrolled=new ArrayList<>();private final Queue<ElectiveStudent> waitlist=new ArrayDeque<>();
    public Elective(String name,int credits,int capacity){this.name=name;this.credits=credits;this.capacity=capacity;}public String getName(){return name;}public int getCredits(){return credits;}
    public boolean isEnrolled(ElectiveStudent s){return enrolled.contains(s);}public boolean isWaiting(ElectiveStudent s){return waitlist.contains(s);}public void enroll(ElectiveStudent s){enrolled.add(s);s.addCredits(credits);}public void addToWaitlist(ElectiveStudent s){waitlist.offer(s);}
    public ElectiveStudent drop(ElectiveStudent s){if(!enrolled.remove(s))return null;s.removeCredits(credits);return s;}public ElectiveStudent nextWaitlisted(){return waitlist.peek();}public ElectiveStudent removeNextWaitlisted(){return waitlist.poll();}public boolean hasSeat(){return enrolled.size()<capacity;}
}
class EnrollmentService{
    public void enroll(ElectiveStudent s,Elective e){
        if(e.isEnrolled(s)||e.isWaiting(s)){System.out.println("Enrollment failed: "+s.getName()+" already enrolled or waitlisted.");return;}
        if(!s.canAdd(e.getCredits())){System.out.println("Enrollment failed: "+s.getName()+" would exceed the "+s.getType()+" credit limit ("+(s.getCredits()+e.getCredits())+"/"+s.getLimit()+").");return;}
        if(e.hasSeat()){e.enroll(s);System.out.println(s.getName()+" enrolled in "+e.getName()+" (credits: "+s.getCredits()+"/"+s.getLimit()+").");}
        else{e.addToWaitlist(s);System.out.println(e.getName()+" is full.\n"+s.getName()+" added to waitlist (position 1).");}
    }
    public void drop(ElectiveStudent s,Elective e){
        if(!e.isEnrolled(s)){System.out.println("Drop failed: "+s.getName()+" is not enrolled.");return;}
        e.drop(s);System.out.println(s.getName()+" dropped "+e.getName()+" (credits: "+s.getCredits()+"/"+s.getLimit()+").");
        ElectiveStudent next=e.nextWaitlisted();
        if(next!=null&&next.canAdd(e.getCredits())){e.removeNextWaitlisted();e.enroll(next);System.out.println(next.getName()+" promoted from waitlist and enrolled in "+e.getName()+" (credits: "+next.getCredits()+"/"+next.getLimit()+").");}
        else if(next!=null)System.out.println(next.getName()+" remains on waitlist because the credit limit is exceeded.");
    }
}
public class Problem4_ElectiveSeatRush{
    public static void main(String[] args){
        Elective cloud=new Elective("Cloud Computing",4,2);EnrollmentService service=new EnrollmentService();
        ElectiveStudent asha=new ElectiveStudent("Asha",new RegularPolicy(),20),ravi=new ElectiveStudent("Ravi",new HonorsPolicy(),22),neha=new ElectiveStudent("Neha",new ExchangePolicy(),12),kiran=new ElectiveStudent("Kiran",new RegularPolicy(),22);
        service.enroll(asha,cloud);service.enroll(ravi,cloud);service.enroll(neha,cloud);service.enroll(kiran,cloud);service.drop(asha,cloud);
    }
}