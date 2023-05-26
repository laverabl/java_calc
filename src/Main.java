import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main{
    public static class RomantoArabicConverter {
        private HashMap<Character, Integer> romanDict = new HashMap<>();
        public RomantoArabicConverter() {
            romanDict.put('I', 1);
            romanDict.put('V', 5);
            romanDict.put('X', 10);
        }

        public int convert(String str) throws Exception {
            int result = 0;

            for (int i = 0; i < str.length(); i++) {
                char current = str.charAt(i);
                if (!romanDict.containsKey(current)) {
                    throw new Exception("Некорректное римское число: " + str); /// oшибка
                }
                if (i == str.length() - 1 || romanDict.get(current) >= romanDict.get(str.charAt(i + 1))) {
                    result += romanDict.get(current);
                } else {
                    result -= romanDict.get(current);
                }
            }
            return result;
        }
        public static String ArabicToRoman(int number) {
            LinkedHashMap<Integer, String> arabicDict = new LinkedHashMap<>();
            arabicDict.put(100, "C");
            arabicDict.put(90, "XC");
            arabicDict.put(50, "L");
            arabicDict.put(40, "XL");
            arabicDict.put(10, "X");
            arabicDict.put(9, "IX");
            arabicDict.put(5, "V");
            arabicDict.put(4, "IV");
            arabicDict.put(1, "I");
            StringBuilder result = new StringBuilder();
            for (Map.Entry<Integer, String> entry : arabicDict.entrySet()) {
                int current = entry.getKey();
                String roman = entry.getValue();
                while (number >= current) {
                    result.append(roman);
                    number -= current;
                }
            }
            return result.toString();
        }


        private static boolean isNumber(String s) throws  Exception{
                return s.matches("\\d+");
        }
        private static boolean isRomanNumber(String s) {
            return s.matches("[IVX]+");
        }
        public static String calc(String input) throws Exception {
            RomantoArabicConverter converter = new RomantoArabicConverter();
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
                a = converter.convert(firstElement);
                if (a < 1 || a > 10)
                    throw new Exception("Только числа от 1 до 10 в десятичной римской системе.");
            }
            if (isNumber(secondElement)) {
                b = Integer.parseInt(secondElement);
                if (b < 1 || b > 10)
                    throw new Exception("Только арабские цифры в диапазоне от 1 до 10 включительно");
            } else {
                b = converter.convert(secondElement);
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
}
