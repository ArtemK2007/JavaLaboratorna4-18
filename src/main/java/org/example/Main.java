package org.example;

import java.util.Scanner;

/**
 * Драйвер-клас програми для демонстрації роботи з класами Clothes, Wardrobe та перерахуванням ClothesSize.
 */
public class Main {

    /**
     * Головний метод програми, що реалізує інтерактивне консольне меню.
     */
    static void main() {
        System.out.println("Практична робота №6. Статичні члени, агрегація, enum");
        System.out.println("==================================================\n");

        Scanner scanner = new Scanner(System.in);
        Wardrobe wardrobe = null;

        while (wardrobe == null) {
            System.out.print("Введіть максимальну місткість гардеробу: ");
            try {
                int capacity = Integer.parseInt(scanner.nextLine().trim());
                wardrobe = new Wardrobe(capacity);
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введено не ціле число. Спробуйте ще раз.");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        boolean running = true;

        while (running) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Створити новий предмет одягу та додати у гардероб");
            System.out.println("2. Скопіювати існуючий предмет одягу (конструктор копіювання)");
            System.out.println("3. Вивести вміст гардеробу");
            System.out.println("4. Вивести загальну кількість створених об'єктів (статичний метод)");
            System.out.println("5. Завершити роботу");
            System.out.print("Оберіть пункт меню: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (wardrobe.getCurrentCount() >= wardrobe.getCapacity()) {
                        System.out.println("Гардероб заповнений! Неможливо додати більше речей.");
                        break;
                    }

                    try {
                        System.out.print("Тип (наприклад, Футболка): ");
                        String type = scanner.nextLine();

                        System.out.print("Розмір (доступні: XS, S, M, L, XL, XXL): ");
                        String sizeInput = scanner.nextLine().trim().toUpperCase();
                        ClothesSize size;
                        try {
                            size = ClothesSize.valueOf(sizeInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Помилка: Некоректний розмір! Допустимі значення: XS, S, M, L, XL, XXL.");
                            break;
                        }

                        System.out.print("Бренд: ");
                        String brand = scanner.nextLine();

                        System.out.print("Ціна: ");
                        double price = Double.parseDouble(scanner.nextLine().trim());

                        Clothes newClothes = new Clothes(type, size, price, brand);
                        wardrobe.addClothes(newClothes);
                        System.out.println("Предмет одягу успішно створено та додано у гардероб!");

                    } catch (NumberFormatException e) {
                        System.out.println("Помилка: Ціна має бути числом!");
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                    break;

                case "2":
                    if (wardrobe.getCurrentCount() == 0) {
                        System.out.println("Гардероб порожній, немає чого копіювати.");
                        break;
                    }
                    if (wardrobe.getCurrentCount() >= wardrobe.getCapacity()) {
                        System.out.println("Гардероб заповнений! Немає місця для копії.");
                        break;
                    }

                    wardrobe.printAllClothes();
                    System.out.print("Введіть номер предмета для копіювання (1-" + wardrobe.getCurrentCount() + "): ");
                    try {
                        int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        Clothes original = wardrobe.getClothes(index);
                        Clothes copy = new Clothes(original);
                        wardrobe.addClothes(copy);
                        System.out.println("Копію успішно створено та додано у гардероб: " + copy);
                    } catch (NumberFormatException e) {
                        System.out.println("Помилка: Номер має бути цілим числом!");
                    } catch (IndexOutOfBoundsException | IllegalArgumentException | IllegalStateException e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                    break;

                case "3":
                    wardrobe.printAllClothes();
                    break;

                case "4":
                    System.out.println("Загальна кількість створених об'єктів Clothes за час роботи програми: "
                            + Clothes.getCreatedCount());
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
}