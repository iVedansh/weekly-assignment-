import java.util.*;

interface PricingPlan{double price(double basePrice);String getName();}
class DayScholarPlan implements PricingPlan{public double price(double p){return p;}public String getName(){return "Day Scholar";}}
class HostellerPlan implements PricingPlan{public double price(double p){return p*.90;}public String getName(){return "Hosteller";}}
class StaffPlan implements PricingPlan{public double price(double p){return p*.80;}public String getName(){return "Staff";}}
class Transaction{
    private final String description;private final double amount;
    public Transaction(String description,double amount){this.description=description;this.amount=amount;}public double getAmount(){return amount;}
    public String toString(){return (amount>=0?"+":"")+String.format("%.2f",amount);}
}
class Purchase{
    private final String item;private final double chargedAmount;private boolean refunded;
    public Purchase(String item,double chargedAmount){this.item=item;this.chargedAmount=chargedAmount;}
    public String getItem(){return item;}public double getChargedAmount(){return chargedAmount;}public boolean isRefunded(){return refunded;}public void markRefunded(){refunded=true;}
}
class SmartCard{
    private final String cardId;private final PricingPlan plan;private double balance;private boolean blocked;private final List<Transaction> transactions=new ArrayList<>();
    public SmartCard(String cardId,PricingPlan plan){this.cardId=cardId;this.plan=plan;}
    public void topUp(double amount){
        if(blocked)throw new IllegalStateException("Card is blocked");if(amount<100)throw new IllegalArgumentException("Top-up must be at least ₹100.00");if(balance+amount>5000)throw new IllegalArgumentException("Maximum balance is ₹5000.00");
        record("Top-up",amount);System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n",cardId,amount,balance);
    }
    public Purchase purchase(String item,double basePrice){
        if(blocked)throw new IllegalStateException("Card is blocked");double charged=plan.price(basePrice);
        if(charged>balance)throw new IllegalStateException(String.format("Insufficient balance (required ₹%.2f, available ₹%.2f)",charged,balance));
        record(item,-charged);Purchase p=new Purchase(item,charged);System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.%n",item,charged,balance);return p;
    }
    public void refund(Purchase p){if(p.isRefunded())throw new IllegalStateException(p.getItem()+" has already been refunded");record("Refund of "+p.getItem(),p.getChargedAmount());p.markRefunded();System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",p.getChargedAmount(),p.getItem(),balance);}
    public void block(){blocked=true;}public void unblock(){blocked=false;}
    private void record(String description,double amount){transactions.add(new Transaction(description,amount));balance+=amount;if(balance<0)throw new IllegalStateException("Balance cannot be negative");}
    public void printStatement(){StringBuilder sb=new StringBuilder();double sum=0;for(Transaction t:transactions){if(sb.length()>0)sb.append(", ");sb.append(t);sum+=t.getAmount();}System.out.printf("Mini-statement for %s: %s = ₹%.2f.%n",cardId,sb,sum);if(Math.abs(sum-balance)>0.000001)throw new IllegalStateException("Balance invariant violated");}
}
public class Problem5_CampusCanteenSmartCard{
    public static void main(String[] args){
        SmartCard card=new SmartCard("C-2045",new HostellerPlan());card.topUp(500);Purchase veg=card.purchase("Veg Thali",120);card.purchase("Cold Coffee",60);
        try{card.purchase("Snack Combo",400);}catch(Exception e){System.out.println("Purchase failed: "+e.getMessage()+".");}
        card.refund(veg);try{card.refund(veg);}catch(Exception e){System.out.println("Refund rejected: "+e.getMessage()+".");}card.printStatement();
    }
}