package org.example;

import java.util.Scanner;

/**
 * Драйвер-клас для тестування масиву об'єктів Clothes.
 */
public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість елементів одягу: ");
        int count;

        if (scanner.hasNextInt()) {
            count = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Помилка: введено не число.");
            scanner.close();
            return;
        }

        if (count <= 0) {
            System.out.println("Кількість елементів має бути більшою за 0.");
            scanner.close();
            return;
        }

        Clothes[] wardrobe = new Clothes[count];

        for (int i = 0; i < count; i++) {
            System.out.println("\nВведіть дані для одягу #" + (i + 1) + ":");

            System.out.print("Тип (наприклад, Футболка): ");
            String type = scanner.nextLine();

            System.out.print("Розмір (наприклад, L): ");
            String size = scanner.nextLine();

            System.out.print("Ціна: ");
            double price = 0;
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                scanner.nextLine();

                if (price < 0) {
                    System.out.println("Помилка вводу ціни. Ціна не може бути від'ємною. Встановлено 0.0");
                    price = 0.0;
                }
            } else {
                System.out.println("Помилка вводу ціни. Встановлено 0.0");
                scanner.nextLine();
            }

            wardrobe[i] = new Clothes(type, size, price);
        }

        System.out.println("\n=== Список створеного одягу ===");
        for (Clothes item : wardrobe) {
            System.out.println(item.toString());
        }

        scanner.close();
    }
}