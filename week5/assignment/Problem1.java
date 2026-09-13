class AccessChecker {
    static String classifyAccess(String m,String c){
        if("public".equals(m)) return "ALLOWED";
        if("private".equals(m)) return "SAME_CLASS".equals(c)?"ALLOWED":"DENIED";
        if("default".equals(m)||"protected".equals(m)) return ("SAME_CLASS".equals(c)||"SAME_PACKAGE".equals(c))?"ALLOWED":"DENIED";
        return "DENIED";
    }
    static String summarizeByModifier(String[][] a){
        String[] ms={"private","default","protected","public"}; StringBuilder out=new StringBuilder();
        for(String m:ms){int yes=0,no=0; for(String[] x:a) if(m.equals(x[0])){if("ALLOWED".equals(classifyAccess(x[0],x[1])))yes++;else no++;} if(out.length()>0)out.append(" | "); out.append(m).append(": ").append(yes).append(" allowed / ").append(no).append(" denied");} return out.toString();
    }
}
class LibraryMemberRecord { private String membershipId; String branchCode; double finesOwed; public String displayName;
    public LibraryMemberRecord(String id,String branch,double fines,String name){if(id==null||id.trim().length()<4)throw new IllegalArgumentException("Invalid membershipId");membershipId=id;branchCode=branch;finesOwed=fines;displayName=name;}
}
public class Problem1{public static void main(String[]x){System.out.println(AccessChecker.classifyAccess("private","SAME_CLASS"));System.out.println(AccessChecker.classifyAccess("protected","DIFFERENT_PACKAGE"));System.out.println(AccessChecker.summarizeByModifier(new String[][]{{"private","SAME_CLASS"},{"private","SAME_PACKAGE"},{"default","SAME_PACKAGE"},{"default","DIFFERENT_PACKAGE"},{"protected","SAME_PACKAGE"},{"protected","SAME_CLASS"},{"public","DIFFERENT_PACKAGE"}}));}}
