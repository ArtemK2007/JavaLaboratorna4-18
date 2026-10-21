package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Завершити роботу програми (із збереженням)");
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
                        System.out.println("\n=== СПИСОК УСІХ ОБ'ЄКТІВ ===");
                        for (int i = 0; i < clothesList.size(); i++) {
                            System.out.println((i + 1) + ". " + clothesList.get(i).toString());
                        }
                    }
                    break;

                case "3":
                    FileStorage.saveToTxt(clothesList, "input.txt");
                    FileStorage.saveToJson(clothesList, "input.json");
                    System.out.println("Дані успішно збережено у файли input.txt та input.json.");

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