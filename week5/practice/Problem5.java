class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;
    public LoanReceipt(String memberId,String[] bookIds){
        if(bookIds==null) throw new IllegalArgumentException("bookIds required");
        for(String id:bookIds) if(!valid(id)) throw new IllegalArgumentException("Invalid book id");
        this.memberId=memberId; this.bookIds=bookIds.clone();
    }
    private static boolean valid(String id){
        if(id==null || id.length()!=6 || !id.startsWith("BK-")) return false;
        return Character.isDigit(id.charAt(3))&&Character.isDigit(id.charAt(4))&&Character.isDigit(id.charAt(5));
    }
    public String[] getBookIds(){ return bookIds.clone(); }
    public LoanReceipt withCorrectedBookId(int index,String newId){
        if(index<0||index>=bookIds.length||!valid(newId)) throw new IllegalArgumentException("Invalid correction");
        String[] copy=bookIds.clone(); copy[index]=newId; return new LoanReceipt(memberId,copy);
    }
}
class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;
    public ReferenceOnlyLoanReceipt(String memberId,String[] bookIds,String roomNumber){ super(memberId,bookIds); this.roomNumber=roomNumber; }
}
public class Problem5 {
    static String processNightlyCirculation(LoanReceipt[] receipts){
        int processed=0,nulls=0,reference=0,regular=0;
        if(receipts!=null) for(LoanReceipt r:receipts){
            if(r==null){nulls++;continue;} processed++;
            if(r instanceof ReferenceOnlyLoanReceipt) reference++; else regular++;
        }
        return processed+" processed | "+nulls+" null skipped | "+reference+" reference-only | "+regular+" regular";
    }
    public static void main(String[] args){
        LoanReceipt r=new LoanReceipt("LIB-8841",new String[]{"BK-100","BK-101"});
        String[] ids=r.getBookIds(); ids[0]="CHANGED"; System.out.println(r.getBookIds()[0]);
        System.out.println(processNightlyCirculation(new LoanReceipt[]{new ReferenceOnlyLoanReceipt("LIB-001",new String[]{"BK-200"},"Reading Room 3"),null,new LoanReceipt("LIB-002",new String[]{"BK-201"})}));
    }
}
