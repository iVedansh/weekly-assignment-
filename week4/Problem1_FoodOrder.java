public class Problem1_FoodOrder {
    static class FoodOrder {
        private String studentName;
        private String dishName;
        private boolean delivered;

        FoodOrder(String studentName, String dishName) {
            if (studentName == null || studentName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid studentName");
            }
            if (dishName == null || dishName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid dishName");
            }
            this.studentName = studentName;
            this.dishName = dishName;
        }

        void markDelivered() {
            if (!delivered) {
                delivered = true;
                System.out.println("Order marked delivered.");
            } else {
                System.out.println("Order was already delivered.");
            }
        }
    }

    static void processBatch(String[][] rawOrders) {
        int valid = 0;
        int rejected = 0;

        for (String[] raw : rawOrders) {
            try {
                if (raw == null || raw.length < 2) {
                    throw new IllegalArgumentException();
                }
                new FoodOrder(raw[0], raw[1]);
                valid++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };
        processBatch(rawOrders);
    }
}
