class PracticeAccessRuleEngine {
    static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) return "ALLOWED";
        if ("private".equals(fieldModifier)) return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        if ("default".equals(fieldModifier)) return ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) ? "ALLOWED" : "DENIED";
        if ("protected".equals(fieldModifier)) {
            if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) return "ALLOWED";
            if ("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)) return "ALLOWED";
            return "DENIED";
        }
        return "DENIED";
    }
    static String describeContext(String context) {
        String[] parts = context.split("_");
        StringBuilder s = new StringBuilder();
        for (String p : parts) { if (p.isEmpty()) continue; if (s.length()>0) s.append(' '); s.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1).toLowerCase()); }
        return s.toString();
    }
}
class PracticePatientRecord {
    private final String patientId; String wardCode; protected double vitalsScore; public String facilityName;
    PracticePatientRecord(String patientId,String wardCode,double vitalsScore,String facilityName) {
        if (patientId == null || patientId.trim().length() < 4) throw new IllegalArgumentException("Invalid patientId");
        this.patientId=patientId; this.wardCode=wardCode; this.vitalsScore=vitalsScore; this.facilityName=facilityName;
    }
}
public class Problem1 {
    public static void main(String[] args) {
        System.out.println(PracticeAccessRuleEngine.classifyAccess("protected","SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(PracticeAccessRuleEngine.classifyAccess("protected","SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(PracticeAccessRuleEngine.describeContext("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
    }
}
