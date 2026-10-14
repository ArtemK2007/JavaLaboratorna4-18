package org.example;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Драйвер-клас програми для демонстрації успадкування, поліморфізму та колекцій ArrayList.
 */
public class Main {

    /**
     * Головний метод програми, що реалізує інтерактивне консольне меню.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("Практична робота №7. Наслідування та поліморфізм");
        System.out.println("==================================================\n");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Clothes> clothesList = new ArrayList<>();

        boolean running = true;

        while (running) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Створити та додати базовий предмет одягу (Clothes)");
            System.out.println("2. Створити та додати штани (Pants)");
            System.out.println("3. Створити та додати сорочку/футболку (Shirts)");
            System.out.println("4. Вивести весь список одягу (демонстрація поліморфізму)");
            System.out.println("5. Завершити роботу");
            System.out.print("Оберіть пункт меню: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    try {
                        Clothes baseClothes = readClothesData(scanner);
                        clothesList.add(baseClothes);
                        System.out.println("Базовий предмет одягу успішно додано до колекції!");
                    } catch (Exception e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        Clothes base = readClothesData(scanner);
                        System.out.print("Введіть обхват талії (в см): ");
                        int waistSize = Integer.parseInt(scanner.nextLine().trim());

                        Pants pants = new Pants(base.getType(), base.getSize(), base.getPrice(), base.getBrand(), waistSize);
                        clothesList.add(pants);
                        System.out.println("Штани успішно додано до колекції!");
                    } catch (NumberFormatException e) {
                        System.out.println("Помилка: Числові дані введені некоректно!");
                    } catch (Exception e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                    break;

                case "3":
                    try {
                        Clothes base = readClothesData(scanner);
                        System.out.print("Чи є довгий рукав? (true/false): ");
                        boolean longSleeve = Boolean.parseBoolean(scanner.nextLine().trim());

                        Shirts shirt = new Shirts(base.getType(), base.getSize(), base.getPrice(), base.getBrand(), longSleeve);
                        clothesList.add(shirt);
                        System.out.println("Успішно додано до колекції!");
                    } catch (Exception e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                    break;

                case "4":
                    if (clothesList.isEmpty()) {
                        System.out.println("Колекція одягу порожня.");
                    } else {
                        System.out.println("\n=== СПИСОК ОДЯГУ (Поліморфний вивід) ===");
                        for (int i = 0; i < clothesList.size(); i++) {
                            System.out.println((i + 1) + ". " + clothesList.get(i).toString());
                        }
                    }
                    break;

                case "5":
                    running = false;
                    System.out.println("Завершення роботи програми...");
                    break;

                default:
                    System.out.println("Некоректний вибір. Будь ласка, введіть число від 1 до 5.");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Допоміжний метод для зчитування базових параметрів одягу з консолі.
     * @ об'єкт сканера
     * @return тимчасовий об'єкт Clothes з базовими полями
     */
    private static Clothes readClothesData(Scanner scanner) {
        System.out.print("Тип (наприклад, Джинси / Сорочка): ");
        String type = scanner.nextLine();

        System.out.print("Розмір (доступні: XS, S, M, L, XL, XXL): ");
        String sizeInput = scanner.nextLine().trim().toUpperCase();
        ClothesSize size;
        try {
            size = ClothesSize.valueOf(sizeInput);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Некоректний розмір! Допустимі значення: XS, S, M, L, XL, XXL.");
        }

        System.out.print("Бренд: ");
        String brand = scanner.nextLine();

        System.out.print("Ціна: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        return new Clothes(type, size, price, brand);
    }
}