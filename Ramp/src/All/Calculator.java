package All;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение (например, 3 + 4):");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }

    public static String calc(String input) {
        String[] args = input.split(" ");

        if (args.length != 3) {
            throw new IllegalArgumentException("Неверное количество аргументов!");
        }

        try {
            int num1 = parseNumber(args[0]);
            int num2 = parseNumber(args[2]);
            char operator = args[1].charAt(0);

            if (num1 > 10 || num2 > 10) {
                throw new IllegalArgumentException("Число на входе не должно быть больше 10");
            }

            int result;

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        throw new IllegalArgumentException("Деление на ноль!");
                    }
                    else if (num1 % num2 != 0) {
                        throw new IllegalArgumentException("Результат деления римских чисел не является целым числом!");
                    }
                    result = num1 / num2;
                    break;

                default:
                    throw new IllegalArgumentException("Неверная операция!");
            }

            if ((isRoman(args[0]) && !isRoman(args[2])) || (!isRoman(args[0]) && isRoman(args[2]))) {
                throw new IllegalArgumentException("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно!");
            }

            if (isRoman(args[0]) && isRoman(args[2])) {
                if (result <= 0) {
                    throw new IllegalArgumentException("Результат с римскими числами не может быть меньше 1!");
                }
                return toRoman(result);
            } else {
                return Integer.toString(result);
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа!");
        }
    }

    private static int parseNumber(String token) {
        if (isRoman(token)) {
            return romanToDecimal(token);
        } else {
            int number = Integer.parseInt(token);
            if (number > 10 || number > 10) {
                throw new IllegalArgumentException("Число на входе не должно быть больше 10");
            }
            return number;
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("[IVX]+");
    }

    private static int romanToDecimal(String roman) {
        Map<Character, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanNumerals.get(roman.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    private static String convertResult(int num1, int num2, int result) {
        if (isRoman(Integer.toString(num1)) && isRoman(Integer.toString(num2))) {
            if (result <= 0) {
                throw new IllegalArgumentException("Результат с римскими числами не может быть меньше 1!");
            }
            return toRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static String toRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Римские числа должны быть положительными!");
        }

        StringBuilder result = new StringBuilder();
        Map<Integer, String> romanNumerals = new HashMap<>();
        romanNumerals.put(1, "I");
        romanNumerals.put(2, "II");
        romanNumerals.put(3, "III");
        romanNumerals.put(4, "IV");
        romanNumerals.put(5, "V");
        romanNumerals.put(6, "VI");
        romanNumerals.put(7, "VII");
        romanNumerals.put(8, "VIII");
        romanNumerals.put(9, "IX");
        romanNumerals.put(10, "X");

        for (int value : new int[]{10, 9, 8, 7, 6, 5, 4, 3 , 2 , 1}) {
            while (number >= value) {
                result.append(romanNumerals.get(value));
                number = number - value;
            }
        }

        return result.toString();
    }
}