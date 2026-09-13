class ReferenceAccessChecker {
    static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) return "ALLOWED";
        if ("private".equals(fieldModifier)) return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        if ("default".equals(fieldModifier)) return ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) ? "ALLOWED" : "DENIED";
        if ("protected".equals(fieldModifier)) {
            if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) return "ALLOWED";
            return "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }
        return "DENIED";
    }
    static String describeContext(String context) {
        String[] words=context.split("_"); StringBuilder out=new StringBuilder();
        for(String w:words){ if(w.isEmpty()) continue; if(out.length()>0) out.append(' '); out.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1).toLowerCase()); }
        return out.toString();
    }
}
public class Problem2 {
    public static void main(String[] args) {
        System.out.println(ReferenceAccessChecker.classifyAccess("protected","SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(ReferenceAccessChecker.classifyAccess("protected","SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(ReferenceAccessChecker.describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
    }
}
