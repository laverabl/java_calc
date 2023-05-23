import java.util.Scanner;

public class Main {public static String calc(String input) throws Exception {
    String[] elements = input.split(" ");

    if (elements.length != 3)
        throw new Exception("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
    String firstElement = elements[0];
    String operation = elements[1];
    String secondElement = elements[2];
    if (!(isNumber(firstElement) && isNumber(secondElement)) &&
            !(isRomanNumber(firstElement) && isRomanNumber(secondElement)))
        throw new Exception("Exception //т.к. используются одновременно разные системы счисления");
    int a = 0, b = 0;
    if (isNumber(firstElement)) {
        a = Integer.parseInt(firstElement);
        if (a < 1 || a > 10)
            throw new Exception("Только арабские цифры в диапазоне от 1 до 10 включительно");
    } else {
        a = RomanToArabic(firstElement);
        if (a < 1 || a > 10)
            throw new Exception("Только числа от 1 до 10 в десятичной римской системе.");
    }
    if (isNumber(secondElement)) {
        b = Integer.parseInt(secondElement);
        if (b < 1 || b > 10)
            throw new Exception("Только арабские цифры в диапазоне от 1 до 10 включительно");
    } else {
        b = RomanToArabic(secondElement);
        if (b < 1 || b > 10)
            throw new Exception("Только числа от 1 до 10 в десятичной римской системе.");
    }

    int result = 0;
    switch (operation) {
        case "+":
            result = a + b;
            break;
        case "-":
            result = a - b;
            break;
        case "*":
            result = a * b;
            break;
        case "/":
            if (b == 0)
                throw new Exception("Делитель не может быть равен нулю.");
            result = a / b;
            break;
        default:
            throw new Exception("Неизвестная арифметическая операция.");
    }
    if (isNumber(firstElement) && isNumber(secondElement)) {
        return Integer.toString(result);
    }
    else {
        if (result <= 0)
            throw new Exception("throws Exception //т.к. в римской системе нет отрицательных чисел");
        return ArabicToRoman(result);
    }
}
    private static boolean isNumber(String s) {
        return s.matches("\\d+");
    }
    private static boolean isRomanNumber(String s) {
        return s.matches("[IVX]+");
    }

    private static String[] romanDigit = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

    private static int RomanToArabic(String input) throws Exception {
        input = input.toUpperCase();
        int result = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'I') {
                if (i + 1 < input.length()) {
                    if (input.charAt(i + 1) == 'V') {
                        result += 4;
                        i++;
                    } else if (input.charAt(i + 1) == 'X') {
                        result += 9;
                        i++;
                    } else {
                        result += 1;
                    }
                } else {
                    result += 1;
                }
            } else if (input.charAt(i) == 'V') {
                result += 5;
            } else if (input.charAt(i) == 'X') {
                if (i + 1 < input.length()) {
                    if (input.charAt(i + 1) == 'L') {
                        result += 40;
                        i++;
                    } else if (input.charAt(i + 1) == 'C') {
                        result += 90;
                        i++;
                    } else {
                        result += 10;
                    }
                } else {
                    result += 10;
                }
            } else if (input.charAt(i) == 'L') {
                result += 50;
            } else if (input.charAt(i) == 'C') {
                if (i + 1 < input.length()) {
                    if (input.charAt(i + 1) == 'D') {
                        result += 400;
                        i++;
                    } else if (input.charAt(i + 1) == 'M') {
                        result += 900;
                        i++;
                    } else {
                        result += 100;
                    }
                } else {
                    result += 100;
                }
            } else if (input.charAt(i) == 'D') {
                result += 500;
            } else if (input.charAt(i) == 'M') {
                result += 1000;
            } else {
                throw new Exception("Неверный формат римского числа");
            }
        }
        return result;
    }

    private static String ArabicToRoman(int number) {
        if (number < 0) {
            return "-" + ArabicToRoman(-number);
        }
        return romanDigit[number / 10] + romanDigit[number % 10];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String input = scanner.nextLine();
            System.out.println(calc(input));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
