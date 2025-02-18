public class FriendlyNumbers {

    public static int sumDiv(int num) {
        int sum = 1;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                if (i == num / i) {
                    sum += i;
                } else {
                    sum += i + (num / i);
                }
            }
        }
        return sum;
    }

    public static boolean areFriendly(int num1, int num2) {
        int sum1 = sumDiv(num1);
        int sum2 = sumDiv(num2);
        return sum1 == num2 && sum2 == num1;
    }

    public static void main(String[] args) {
        int num1 = 220;
        int num2 = 284;

        if (areFriendly(num1, num2)) {
            System.out.println(num1 + " and " + num2 + " are friendly numbers.");
        } else {
            System.out.println(num1 + " and " + num2 + " are not friendly numbers.");
        }
    }
}
