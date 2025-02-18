public class HexDecimalConvertor {

    public static int hexToDecimal(String hex) {
        int decimal = 0;
        int base = 1;

        for (int i = hex.length() - 1; i >= 0; i--) {
            char ch = hex.charAt(i);

            if (ch >= '0' && ch <= '9') {
                decimal += (ch - '0') * base;
            } else if (ch >= 'A' && ch <= 'F') {
                decimal += (ch - 'A' + 10) * base;
            } else if (ch >= 'a' && ch <= 'f') {
                decimal += (ch - 'a' + 10) * base;
            } else {
                throw new IllegalArgumentException("Invalid hexadecimal character: " + ch);
            }

            base *= 16;
        }

        return decimal;
    }

    public static void main(String[] args) {
        String hex = "1B3";
        int decimal = hexToDecimal(hex);
        System.out.println("Input (Hex): " + hex);
        System.out.println("Output (Decimal): " + decimal);
    }
}
