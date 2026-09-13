class ReferenceDeskAccessChecker {
    static String classifyAccess(String m,String c){
        if("public".equals(m))return "ALLOWED";
        if("private".equals(m))return "SAME_CLASS".equals(c)?"ALLOWED":"DENIED";
        if("default".equals(m))return ("SAME_CLASS".equals(c)||"SAME_PACKAGE".equals(c))?"ALLOWED":"DENIED";
        if("protected".equals(m))return ("SAME_CLASS".equals(c)||"SAME_PACKAGE".equals(c)||"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(c))?"ALLOWED":"DENIED";
        return "DENIED";
    }
    static String describeContext(String c){StringBuilder s=new StringBuilder();for(String p:c.split("_")){if(s.length()>0)s.append(' ');s.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1).toLowerCase());}return s.toString();}
}
public class Problem2{public static void main(String[]x){System.out.println(ReferenceDeskAccessChecker.classifyAccess("protected","SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));System.out.println(ReferenceDeskAccessChecker.classifyAccess("protected","SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));System.out.println(ReferenceDeskAccessChecker.describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));}}
