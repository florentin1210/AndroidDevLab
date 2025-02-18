public class CustomStringSorting {
    public static String sortString(String input) {
        StringBuilder lowerCase = new StringBuilder();
        StringBuilder upperCase = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                lowerCase.append(ch);
            } else if (Character.isUpperCase(ch)) {
                upperCase.append(ch);
            }
        }
        return lowerCase.append(upperCase).toString();
    }

    public static void main(String[] args) {
        String input = "hElLoWoRLd";
        String result = sortString(input);
        System.out.println("Input: " + input);
        System.out.println("Output: " + result);
    }
}