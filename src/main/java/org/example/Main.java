package org.example;

import java.util.Scanner;

public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        Clothes[] wardrobe = null;
        int count = 0;

        while (wardrobe == null) {
            System.out.print("Введіть максимальну кількість елементів одягу: ");
            try {
                count = Integer.parseInt(scanner.nextLine());
                if (count <= 0) {
                    System.out.println("Помилка: Кількість має бути більшою за 0.");
                } else {
                    wardrobe = new Clothes[count];
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введено не ціле число. Спробуйте ще раз.");
            }
        }

        int currentIndex = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Створити новий об'єкт (одяг)");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Завершити роботу");
            System.out.print("Оберіть пункт меню: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (currentIndex >= count) {
                        System.out.println("Масив заповнений! Неможливо додати більше об'єктів.");
                        break;
                    }

                    try {
                        System.out.print("Тип (наприклад, Футболка): ");
                        String type = scanner.nextLine();

                        System.out.print("Розмір (наприклад, L): ");
                        String size = scanner.nextLine();

                        System.out.print("Бренд: ");
                        String brand = scanner.nextLine();

                        System.out.print("Ціна: ");
                        double price = Double.parseDouble(scanner.nextLine());

                        wardrobe[currentIndex] = new Clothes(type, size, price, brand);
                        currentIndex++;
                        System.out.println("Об'єкт успішно додано!");

                    } catch (NumberFormatException e) {
                        System.out.println("Помилка: Ціна має бути числом!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка створення об'єкта: " + e.getMessage());
                    }
                    break;

                case "2":
                    if (currentIndex == 0) {
                        System.out.println("Список порожній.");
                    } else {
                        System.out.println("\n=== Список створеного одягу ===");
                        for (int i = 0; i < currentIndex; i++) {
                            System.out.println(wardrobe[i].toString());
                        }
                    }
                    break;

                case "3":
                    running = false;
                    System.out.println("Завершення роботи програми...");
                    break;

                default:
                    System.out.println("Некоректний вибір. Будь ласка, введіть 1, 2 або 3.");
                    break;
            }
        }
        scanner.close();
    }
}