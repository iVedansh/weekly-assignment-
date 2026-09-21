public class Problem3_CanteenRanking {
    static class Canteen {
        private String canteenCode;
        private String canteenName;
        private int trustScore;

        Canteen(String canteenCode, String canteenName, int trustScore) {
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, 3);
        }

        int compareTo(Canteen other) {
            if (trustScore != other.trustScore) {
                return Integer.compare(other.trustScore, trustScore);
            }

            int codeCompare = canteenCode.compareToIgnoreCase(other.canteenCode);
            if (codeCompare != 0) {
                return codeCompare;
            }

            return Integer.compare(canteenName.length(), other.canteenName.length());
        }
    }

    static Canteen[] rankCanteens(Canteen[] canteens) {
        Canteen[] result = canteens.clone();

        // O(n^2) selection-style sort; highest trust score comes first.
        for (int i = 0; i < result.length - 1; i++) {
            int best = i;
            for (int j = i + 1; j < result.length; j++) {
                if (result[j].compareTo(result[best]) < 0) {
                    best = j;
                }
            }
            Canteen temp = result[i];
            result[i] = result[best];
            result[best] = temp;
        }
        return result;
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        for (Canteen canteen : rankCanteens(canteens)) {
            System.out.println(canteen.canteenCode);
        }
    }
}
