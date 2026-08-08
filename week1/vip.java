import java.util.*;

public class vip {

    static String validateCustomerId(String customerId) {

        if (customerId.startsWith("VIP-"))
            return "VIP Customer";
        else
            return "Regular Customer";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        String id = sc.nextLine();

        System.out.println(validateCustomerId(id));

        sc.close();
    }
}