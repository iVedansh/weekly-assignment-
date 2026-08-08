import java.util.*;

public class uniqueletter {

    static char findFirstNonRepeatingChar(String text) {

        int[] freq = new int[256];

        // Count characters
        for (int i = 0; i < text.length(); i++) {
            freq[text.charAt(i)]++;
        }

        // Find first character with frequency 1
        for (int i = 0; i < text.length(); i++) {

            if (freq[text.charAt(i)] == 1) {
                return text.charAt(i);
            }
        }

        return '\0';
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        char result = findFirstNonRepeatingChar(text);

        if (result == '\0')
            System.out.println("No Non-Repeating Character Found");
        else
            System.out.println(
                "First Non-Repeating Character: '" + result + "'"
            );

        sc.close();
    }
}
