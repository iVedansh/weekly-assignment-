import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problem1 {
    static class Order {
        private final List<LineItem> items = new ArrayList<>();

        // Static nested class: a line item does not need an Order object.
        static class LineItem {
            String productName;
            int quantity;
            double unitPrice;

            LineItem(String productName, int quantity, double unitPrice) {
                this.productName = productName;
                this.quantity = quantity;
                this.unitPrice = unitPrice;
            }

            double total() {
                return quantity * unitPrice;
            }
        }

        void addItem(String name, int quantity, double unitPrice) {
            items.add(new LineItem(name, quantity, unitPrice));
        }

        // Member inner class: it can directly access this Order's private item list.
        class Cursor {
            private int index = 0;

            boolean hasNext() {
                return index < items.size();
            }

            LineItem next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more line items");
                }
                return items.get(index++);
            }

            void reset() {
                index = 0;
            }
        }

        Cursor cursor() {
            return new Cursor();
        }
    }

    private static String format(double value) {
        return String.format(java.util.Locale.US, "%.2f", value)
                .replaceAll("\\.?0+$", "");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Order order = new Order();

        for (int i = 0; i < n; i++) {
            String product = sc.next();
            int quantity = sc.nextInt();
            double price = sc.nextDouble();
            order.addItem(product, quantity, price);
        }

        double grandTotal = 0;
        Order.Cursor cursor = order.cursor();

        while (cursor.hasNext()) {
            Order.LineItem item = cursor.next();
            double lineTotal = item.total();
            System.out.println(item.productName + " " + format(lineTotal));
            grandTotal += lineTotal;
        }

        System.out.println("Total " + format(grandTotal));
        System.out.println("cursor: inner (reads the order's array)");
        System.out.println("line item: static nested (needs no order)");
        sc.close();
    }
}
