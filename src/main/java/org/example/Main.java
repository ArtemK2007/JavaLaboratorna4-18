package org.example;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Драйвер-клас програми для демонстрації розширеної ієрархії класів (5 класів),
 * поліморфізму та роботи з колекцією ArrayList.
 */
public class Main {

    /**
     * Головний метод програми, що реалізує дворівневе інтерактивне консольне меню.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("Практична робота №8. Розширення ієрархії класів");
        System.out.println("==================================================\n");

        Scanner scanner = new Scanner(System.in);

        ArrayList<Clothes> clothesList = new ArrayList<>();

        boolean running = true;

        while (running) {
            System.out.println("\n=== ГОЛОВНЕ МЕНЮ ===");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Завершити роботу програми");
            System.out.print("Оберіть пункт меню: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleCreateMenu(scanner, clothesList);
                    break;

                case "2":
                    if (clothesList.isEmpty()) {
                        System.out.println("Колекція порожня. Ще не створено жодного об'єкта.");
                    } else {
                        System.out.println("\n=== СПИСОК УСІХ ОБ'ЄКТІВ (Поліморфний вивід) ===");
                        for (int i = 0; i < clothesList.size(); i++) {
                            System.out.println((i + 1) + ". " + clothesList.get(i).toString());
                        }
                    }
                    break;

                case "3":
                    running = false;
                    System.out.println("Завершення роботи програми...");
                    break;

                default:
                    System.out.println("Некоректний вибір. Будь ласка, введіть число від 1 до 3.");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Підменю для вибору типу об'єкта з 5 класів ієрархії або повернення назад.
     *
     * @param scanner     об'єкт Scanner для зчитування даних
     * @param clothesList спільна колекція ArrayList для збереження об'єктів
     */
    private static void handleCreateMenu(Scanner scanner, ArrayList<Clothes> clothesList) {
        System.out.println("\n--- Оберіть тип об'єкта для створення ---");
        System.out.println("1. Базовий одяг (Clothes)");
        System.out.println("2. Штани (Pants)");
        System.out.println("3. Сорочка / Футболка (Shirts)");
        System.out.println("4. Джинси (Jeans)");
        System.out.println("5. Сорочка-поло (PoloShirt)");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine().trim();

        if (typeChoice.equals("0")) {
            System.out.println("Повернення до головного меню...");
            return;
        }

        try {
            switch (typeChoice) {
                case "1": {
                    Clothes base = readBaseClothesData(scanner);
                    clothesList.add(base);
                    System.out.println("Об'єкт Clothes успішно додано!");
                    break;
                }
                case "2": {
                    Clothes base = readBaseClothesData(scanner);
                    int waistSize = readWaistSize(scanner);
                    Pants pants = new Pants(base.getType(), base.getSize(), base.getPrice(), base.getBrand(), waistSize);
                    clothesList.add(pants);
                    System.out.println("Об'єкт Pants успішно додано!");
                    break;
                }
                case "3": {
                    Clothes base = readBaseClothesData(scanner);
                    boolean longSleeve = readLongSleeve(scanner);
                    Shirts shirt = new Shirts(base.getType(), base.getSize(), base.getPrice(), base.getBrand(), longSleeve);
                    clothesList.add(shirt);
                    System.out.println("Об'єкт Shirts успішно додано!");
                    break;
                }
                case "4": {
                    Clothes base = readBaseClothesData(scanner);
                    int waistSize = readWaistSize(scanner);
                    System.out.print("Введіть тип крою джинсів (наприклад, Slim, Regular, Wide): ");
                    String fitType = scanner.nextLine();
                    Jeans jeans = new Jeans(base.getType(), base.getSize(), base.getPrice(), base.getBrand(), waistSize, fitType);
                    clothesList.add(jeans);
                    System.out.println("Об'єкт Jeans успішно додано!");
                    break;
                }
                case "5": {
                    Clothes base = readBaseClothesData(scanner);
                    boolean longSleeve = readLongSleeve(scanner);
                    System.out.print("Введіть кількість ґудзиків на комірці: ");
                    int buttonCount = Integer.parseInt(scanner.nextLine().trim());
                    PoloShirt polo = new PoloShirt(base.getType(), base.getSize(), base.getPrice(), base.getBrand(), longSleeve, buttonCount);
                    clothesList.add(polo);
                    System.out.println("Об'єкт PoloShirt успішно додано!");
                    break;
                }
                default:
                    System.out.println("Невідомий тип об'єкта. Повернення до головного меню.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Помилка: Числові дані введені некоректно!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        }
    }

    /**
     * Зчитує базові параметри одягу з консолі.
     *
     * @param scanner об'єкт сканера
     * @return тимчасовий об'єкт Clothes із базовими полями
     */
    private static Clothes readBaseClothesData(Scanner scanner) {
        System.out.print("Назва / тип виробу: ");
        String type = scanner.nextLine();

        System.out.print("Розмір (XS, S, M, L, XL, XXL): ");
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

    /**
     * Зчитує обхват талії для штанів та джинсів.
     *
     * @param scanner об'єкт сканера
     * @return обхват талії в см
     */
    private static int readWaistSize(Scanner scanner) {
        System.out.print("Введіть обхват талії (в см): ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    /**
     * Зчитує параметр довжини рукава для сорочок та поло.
     *
     * @param scanner об'єкт сканера
     * @return true, якщо рукав довгий, інакше false
     */
    private static boolean readLongSleeve(Scanner scanner) {
        System.out.print("Чи є довгий рукав? (true/false): ");
        return Boolean.parseBoolean(scanner.nextLine().trim());
    }
}