package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static void main() {
        System.out.println("==================================================");
        System.out.println("Практична робота №10. Пошук у колекціях");
        System.out.println("Виконав: студент групи ІН-41/2 Крячко Артем (Варіант 5 / 1)");
        System.out.println("==================================================\n");

        ArrayList<Clothes> clothesList = FileStorage.loadFromJson("input.json");
        if (clothesList.isEmpty()) {
            clothesList = FileStorage.loadFromTxt("input.txt");
        }

        if (!clothesList.isEmpty()) {
            System.out.println("Успішно завантажено " + clothesList.size() + " об'єктів з файлу збереження.");
        } else {
            System.out.println("Файли збереження порожні або відсутні. Створено нову колекцію.");
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== ГОЛОВНЕ МЕНЮ ===");
            System.out.println("1. Пошук об'єкта");
            System.out.println("2. Створити новий об'єкт");
            System.out.println("3. Вивести інформацію про всі об'єкти");
            System.out.println("4. Завершити роботу програми (із збереженням)");
            System.out.print("Оберіть пункт меню: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleSearchMenu(scanner, clothesList);
                    break;

                case "2":
                    handleCreateMenu(scanner, clothesList);
                    break;

                case "3":
                    if (clothesList.isEmpty()) {
                        System.out.println("Колекція порожня. Ще не створено жодного об'єкта.");
                    } else {
                        System.out.println("\n=== СПИСОК УСІХ ОБ'ЄКТІВ ===");
                        for (int i = 0; i < clothesList.size(); i++) {
                            System.out.println((i + 1) + ". " + clothesList.get(i).toString());
                        }
                    }
                    break;

                case "4":
                    FileStorage.saveToTxt(clothesList, "input.txt");
                    FileStorage.saveToJson(clothesList, "input.json");
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

    /**
     * Підменю для пошуку об'єктів за критеріями.
     */
    private static void handleSearchMenu(Scanner scanner, ArrayList<Clothes> list) {
        if (list.isEmpty()) {
            System.out.println("Колекція порожня. Немає чого шукати.");
            return;
        }

        System.out.println("\n--- Оберіть критерій пошуку ---");
        System.out.println("1. За брендом");
        System.out.println("2. За розміром");
        System.out.println("3. За діапазоном ціни");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");

        String choice = scanner.nextLine().trim();
        ArrayList<Clothes> results;

        switch (choice) {
            case "1":
                System.out.print("Введіть назву бренду для пошуку: ");
                String brand = scanner.nextLine().trim();
                results = searchByBrand(list, brand);
                break;
            case "2":
                System.out.print("Введіть розмір (XS, S, M, L, XL, XXL): ");
                try {
                    ClothesSize size = ClothesSize.valueOf(scanner.nextLine().trim().toUpperCase());
                    results = searchBySize(list, size);
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
                    results = searchByPriceRange(list, min, max);
                } catch (NumberFormatException e) {
                    System.out.println("Помилка: ціна має бути числом!");
                    return;
                }
                break;
            case "0":
                System.out.println("Повернення до головного меню...");
                return;
            default:
                System.out.println("Невідомий критерій. Повернення до головного меню.");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("За вказаним критерієм жодного об'єкта не знайдено.");
        } else {
            System.out.println("\n--- Результати пошуку (" + results.size() + " шт.) ---");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).toString());
            }
        }
    }

    //МЕТОДИ ПОШУКУ

    static ArrayList<Clothes> searchByBrand(ArrayList<Clothes> list, String brand) {
        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes item : list) {
            if (item.getBrand().equalsIgnoreCase(brand)) {
                result.add(item);
            }
        }
        return result;
    }

    static ArrayList<Clothes> searchBySize(ArrayList<Clothes> list, ClothesSize size) {
        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes item : list) {
            if (item.getSize() == size) {
                result.add(item);
            }
        }
        return result;
    }

    static ArrayList<Clothes> searchByPriceRange(ArrayList<Clothes> list, double min, double max) {
        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes item : list) {
            if (item.getPrice() >= min && item.getPrice() <= max) {
                result.add(item);
            }
        }
        return result;
    }


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
                case "1":
                    clothesList.add(readBaseClothesData(scanner));
                    System.out.println("Об'єкт Clothes успішно додано!");
                    break;
                case "2":
                    Clothes baseP = readBaseClothesData(scanner);
                    clothesList.add(new Pants(baseP.getType(), baseP.getSize(), baseP.getPrice(), baseP.getBrand(), readWaistSize(scanner)));
                    System.out.println("Об'єкт Pants успішно додано!");
                    break;
                case "3":
                    Clothes baseS = readBaseClothesData(scanner);
                    clothesList.add(new Shirts(baseS.getType(), baseS.getSize(), baseS.getPrice(), baseS.getBrand(), readLongSleeve(scanner)));
                    System.out.println("Об'єкт Shirts успішно додано!");
                    break;
                case "4":
                    Clothes baseJ = readBaseClothesData(scanner);
                    int waistJ = readWaistSize(scanner);
                    System.out.print("Введіть тип крою джинсів (наприклад, Slim, Regular, Wide): ");
                    String fitType = scanner.nextLine();
                    clothesList.add(new Jeans(baseJ.getType(), baseJ.getSize(), baseJ.getPrice(), baseJ.getBrand(), waistJ, fitType));
                    System.out.println("Об'єкт Jeans успішно додано!");
                    break;
                case "5":
                    Clothes basePo = readBaseClothesData(scanner);
                    boolean longSleevePo = readLongSleeve(scanner);
                    System.out.print("Введіть кількість ґудзиків на комірці: ");
                    int buttonCount = Integer.parseInt(scanner.nextLine().trim());
                    clothesList.add(new PoloShirt(basePo.getType(), basePo.getSize(), basePo.getPrice(), basePo.getBrand(), longSleevePo, buttonCount));
                    System.out.println("Об'єкт PoloShirt успішно додано!");
                    break;
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

    private static int readWaistSize(Scanner scanner) {
        System.out.print("Введіть обхват талії (в см): ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private static boolean readLongSleeve(Scanner scanner) {
        System.out.print("Чи є довгий рукав? (true/false): ");
        return Boolean.parseBoolean(scanner.nextLine().trim());
    }
}