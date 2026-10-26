package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static void main() {
        Store store = new Store("Магазин Одягу WinWin");

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
            System.out.println("4. Вивести відсортовану інформацію про всі товари (за ціною)");
            System.out.println("5. Завершити роботу програми (із збереженням)");
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
                    if (store.getInventory().isEmpty()) {
                        System.out.println("Склад порожній. Немає що сортувати.");
                    } else {
                        boolean sortingMenu = true;
                        while (sortingMenu) {
                            System.out.println("\n--- Оберіть критерій сортування ---");
                            System.out.println("1. Сортувати за ціною (зростанням)");
                            System.out.println("2. Сортувати за брендом (алфавітом)");
                            System.out.println("3. Сортувати за типом товару");
                            System.out.println("0. Повернутися в головне меню");
                            System.out.print("Ваш вибір: ");

                            String sortChoice = scanner.nextLine().trim();
                            ArrayList<StoreItem> sortedList = new ArrayList<>(store.getInventory());

                            switch (sortChoice) {
                                case "1":
                                    sortedList.sort((o1, o2) -> Double.compare(o1.getItem().getPrice(), o2.getItem().getPrice()));
                                    printList(sortedList, "За ціною");
                                    break;

                                case "2":
                                    sortedList.sort((o1, o2) -> o1.getItem().getBrand().compareTo(o2.getItem().getBrand()));
                                    printList(sortedList, "За брендом");
                                    break;

                                case "3":
                                    sortedList.sort((o1, o2) -> o1.getItem().getType().compareTo(o2.getItem().getType()));
                                    printList(sortedList, "За типом");
                                    break;

                                case "0":
                                    sortingMenu = false;
                                    break;

                                default:
                                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
                            }
                        }
                    }
                    break;
                case "5":
                    FileStorage.saveToTxt(store, "input.txt");
                    FileStorage.saveToJson(store, "input.json");
                    System.out.println("Дані успішно збережено. Завершення роботи програми...");
                    running = false;
                    break;
                default:
                    System.out.println("Некоректний вибір. Будь ласка, введіть число від 1 до 5.");
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
        System.out.println("1. Штани (Pants)");
        System.out.println("2. Сорочка / Футболка (Shirts)");
        System.out.println("3. Джинси (Jeans)");
        System.out.println("4. Сорочка-поло (PoloShirt)");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");

        String typeChoice = scanner.nextLine().trim();
        if (typeChoice.equals("0")) return;

        try {
            Clothes newItem;
            switch (typeChoice) {
                case "1":
                    String[] baseP = readBaseClothesData(scanner, "Штани");
                    newItem = new Pants(baseP[0], ClothesSize.valueOf(baseP[1]), Double.parseDouble(baseP[2]), baseP[3], readWaistSize(scanner));
                    break;
                case "2":
                    String[] baseS = readBaseClothesData(scanner, "Сорочка");
                    newItem = new Shirts(baseS[0], ClothesSize.valueOf(baseS[1]), Double.parseDouble(baseS[2]), baseS[3], readLongSleeve(scanner));
                    break;
                case "3":
                    String[] baseJ = readBaseClothesData(scanner, "Джинси");
                    int waistJ = readWaistSize(scanner);
                    System.out.print("Введіть тип крою джинсів (Slim, Regular, Wide): ");
                    newItem = new Jeans(baseJ[0], ClothesSize.valueOf(baseJ[1]), Double.parseDouble(baseJ[2]), baseJ[3], waistJ, scanner.nextLine());
                    break;
                case "4":
                    String[] basePo = readBaseClothesData(scanner, "Поло");
                    boolean longSleevePo = readLongSleeve(scanner);
                    System.out.print("Введіть кількість ґудзиків на комірці: ");
                    newItem = new PoloShirt(basePo[0], ClothesSize.valueOf(basePo[1]), Double.parseDouble(basePo[2]), basePo[3], longSleevePo, Integer.parseInt(scanner.nextLine().trim()));
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

    private static String[] readBaseClothesData(Scanner scanner, String defaultType) {
        System.out.print("Назва / тип виробу (Enter для '" + defaultType + "'): ");
        String type = scanner.nextLine().trim();
        if (type.isEmpty()) type = defaultType;

        System.out.print("Розмір (XS, S, M, L, XL, XXL): ");
        String size = scanner.nextLine().trim().toUpperCase();

        System.out.print("Ціна: ");
        String price = scanner.nextLine().trim();

        System.out.print("Бренд: ");
        String brand = scanner.nextLine().trim();

        return new String[]{type, size, price, brand};
    }

    private static int readWaistSize(Scanner scanner) {
        System.out.print("Введіть обхват талії (в см): ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private static boolean readLongSleeve(Scanner scanner) {
        System.out.print("Чи є довгий рукав? (true/false): ");
        return Boolean.parseBoolean(scanner.nextLine().trim());
    }

    private static void printList(ArrayList<StoreItem> list, String title) {
        System.out.println("\n=== ВІДСОРТОВАНИЙ ІНВЕНТАР (" + title + ") ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }
}