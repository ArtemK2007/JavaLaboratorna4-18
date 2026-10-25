package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static void main() {
        Store store = new Store("Магазин Одягу Clothes");

        FileStorage.loadFromJson(store, "input.json");
        if (store.getInventory().isEmpty()) {
            FileStorage.loadFromTxt(store, "input.txt");
        }

        if (!store.getInventory().isEmpty()) {
            System.out.println("Успішно завантажено інвентар. Унікальних позицій: " + store.getInventory().size());
        } else {
            System.out.println("Файли збереження порожні або відсутні. Створено порожній склад.");
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== " + store.getStoreName().toUpperCase() + " ===");
            System.out.println("1. Пошук товару");
            System.out.println("2. Додати новий товар (надходження на склад)");
            System.out.println("3. Вивести весь інвентар");
            System.out.println("4. Завершити роботу програми (із збереженням)");
            System.out.print("Оберіть пункт меню: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleSearchMenu(scanner, store);
                    break;

                case "2":
                    handleCreateMenu(scanner, store);
                    break;

                case "3":
                    if (store.getInventory().isEmpty()) {
                        System.out.println("Склад порожній.");
                    } else {
                        System.out.println("\n=== ІНВЕНТАР СКЛАДУ ===");
                        for (int i = 0; i < store.getInventory().size(); i++) {
                            System.out.println((i + 1) + ". " + store.getInventory().get(i).toString());
                        }
                    }
                    break;

                case "4":
                    FileStorage.saveToTxt(store, "input.txt");
                    FileStorage.saveToJson(store, "input.json");
                    System.out.println("Дані успішно збережено. Завершення роботи програми...");
                    running = false;
                    break;

                default:
                    System.out.println("Некоректний вибір. Будь ласка, введіть число від 1 до 4.");
                    break;
            }
        }
        scanner.close();
    }

    private static void handleSearchMenu(Scanner scanner, Store store) {
        if (store.getInventory().isEmpty()) {
            System.out.println("Склад порожній. Немає чого шукати.");
            return;
        }

        System.out.println("\n--- Оберіть критерій пошуку ---");
        System.out.println("1. За брендом");
        System.out.println("2. За розміром");
        System.out.println("3. За діапазоном ціни");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");

        String choice = scanner.nextLine().trim();
        ArrayList<StoreItem> results;

        switch (choice) {
            case "1":
                System.out.print("Введіть назву бренду для пошуку: ");
                results = store.searchByBrand(scanner.nextLine().trim());
                break;
            case "2":
                System.out.print("Введіть розмір (XS, S, M, L, XL, XXL): ");
                try {
                    results = store.searchBySize(ClothesSize.valueOf(scanner.nextLine().trim().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Помилка: введено некоректний розмір!");
                    return;
                }
                break;
            case "3":
                try {
                    System.out.print("Введіть мінімальну ціну: ");
                    double min = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Введіть максимальну ціну: ");
                    double max = Double.parseDouble(scanner.nextLine().trim());
                    results = store.searchByPriceRange(min, max);
                } catch (NumberFormatException e) {
                    System.out.println("Помилка: ціна має бути числом!");
                    return;
                }
                break;
            case "0":
                return;
            default:
                System.out.println("Невідомий критерій.");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("За вказаним критерієм товарів не знайдено.");
        } else {
            System.out.println("\n--- Результати пошуку (" + results.size() + " шт.) ---");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).toString());
            }
        }
    }

    private static void handleCreateMenu(Scanner scanner, Store store) {
        System.out.println("\n--- Оберіть тип об'єкта для створення ---");
        System.out.println("1. Базовий одяг (Clothes)");
        System.out.println("2. Штани (Pants)");
        System.out.println("3. Сорочка / Футболка (Shirts)");
        System.out.println("4. Джинси (Jeans)");
        System.out.println("5. Сорочка-поло (PoloShirt)");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine().trim();
        if (typeChoice.equals("0")) return;

        try {
            Clothes newItem;
            switch (typeChoice) {
                case "1":
                    newItem = readBaseClothesData(scanner);
                    break;
                case "2":
                    Clothes baseP = readBaseClothesData(scanner);
                    newItem = new Pants(baseP.getType(), baseP.getSize(), baseP.getPrice(), baseP.getBrand(), readWaistSize(scanner));
                    break;
                case "3":
                    Clothes baseS = readBaseClothesData(scanner);
                    newItem = new Shirts(baseS.getType(), baseS.getSize(), baseS.getPrice(), baseS.getBrand(), readLongSleeve(scanner));
                    break;
                case "4":
                    Clothes baseJ = readBaseClothesData(scanner);
                    int waistJ = readWaistSize(scanner);
                    System.out.print("Введіть тип крою джинсів (Slim, Regular, Wide): ");
                    newItem = new Jeans(baseJ.getType(), baseJ.getSize(), baseJ.getPrice(), baseJ.getBrand(), waistJ, scanner.nextLine());
                    break;
                case "5":
                    Clothes basePo = readBaseClothesData(scanner);
                    boolean longSleevePo = readLongSleeve(scanner);
                    System.out.print("Введіть кількість ґудзиків на комірці: ");
                    newItem = new PoloShirt(basePo.getType(), basePo.getSize(), basePo.getPrice(), basePo.getBrand(), longSleevePo, Integer.parseInt(scanner.nextLine().trim()));
                    break;
                default:
                    System.out.println("Невідомий тип об'єкта.");
                    return;
            }

            System.out.print("Введіть кількість одиниць для додавання на склад: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            store.addNewClothes(newItem, quantity);
            System.out.println("Товар успішно додано / оновлено на складі!");

        } catch (NumberFormatException e) {
            System.out.println("Помилка: Числові дані введені некоректно!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        }
    }

    private static Clothes readBaseClothesData(Scanner scanner) {
        System.out.print("Назва / тип виробу: ");
        String type = scanner.nextLine();
        System.out.print("Розмір (XS, S, M, L, XL, XXL): ");
        ClothesSize size = ClothesSize.valueOf(scanner.nextLine().trim().toUpperCase());
        System.out.print("Бренд: ");
        String brand = scanner.nextLine();
        System.out.print("Ціна: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        return new Clothes(type, size, price, brand);
    }

    private static int readWaistSize(Scanner scanner) {
        System.out.print("Введіть обхват талії (в см): ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private static boolean readLongSleeve(Scanner scanner) {
        System.out.print("Чи є довгий рукав? (true/false): ");
        return Boolean.parseBoolean(scanner.nextLine().trim());
    }
}