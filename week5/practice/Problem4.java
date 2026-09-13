class LibraryMember {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String storedAnswer;
    public LibraryMember() { this(null, null); }
    public LibraryMember(String name) { this(null, name); }
    public LibraryMember(String membershipId, String name) { this.membershipId=membershipId; this.name=name; }
    public String getMembershipId(){ return membershipId; }
    public void setMembershipId(String id){ if(membershipId==null) membershipId=id; }
    public String getName(){ return name; }
    public void setName(String name){ this.name=name; }
    public boolean isPremiumMember(){ return premiumMember; }
    public void setPremiumMember(boolean premium){ premiumMember=premium; }
    public void setSecurityAnswer(String answer){ storedAnswer=answer==null?null:Integer.toHexString(answer.hashCode()); }
}
public class Problem4 {
    public static void main(String[] args){
        LibraryMember m=new LibraryMember();
        m.setMembershipId("LIB-8841"); m.setMembershipId("FAKE-0000");
        System.out.println(m.getMembershipId());
        System.out.println(new LibraryMember("Priya Nair").getMembershipId());
        System.out.println(new LibraryMember("LIB-8841","Priya Nair").getMembershipId());
    }
}
