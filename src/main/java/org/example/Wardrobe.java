package org.example;

/**
 * Клас, що представляє гардероб (шафу з одягом).
 * Демонструє принцип агрегації, зберігаючи масив об'єктів класу Clothes.
 */
public class Wardrobe {
    private Clothes[] items;
    private int currentCount;

    /**
     * Конструктор для створення гардеробу заданої місткості.
     *
     * @param capacity максимальна кількість речей у гардеробі
     * @throws IllegalArgumentException якщо місткість менша або дорівнює 0
     */
    public Wardrobe(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Місткість гардеробу має бути більшою за 0.");
        }
        this.items = new Clothes[capacity];
        this.currentCount = 0;
    }

    /**
     * Додає новий предмет одягу до гардеробу.
     *
     * @param clothes об'єкт одягу для додавання
     * @throws IllegalArgumentException якщо переданий об'єкт є null
     * @throws IllegalStateException    якщо гардероб вже заповнений
     */
    public void addClothes(Clothes clothes) {
        if (clothes == null) {
            throw new IllegalArgumentException("Неможливо додати порожній об'єкт (null) у гардероб.");
        }
        if (currentCount >= items.length) {
            throw new IllegalStateException("Гардероб заповнений! Неможливо додати більше речей.");
        }
        items[currentCount] = clothes;
        currentCount++;
    }

    /**
     * Повертає предмет одягу за його індексом у гардеробі.
     *
     * @param index індекс предмета (від 0 до currentCount - 1)
     * @return об'єкт класу Clothes
     * @throws IndexOutOfBoundsException якщо індекс виходить за межі заповненої частини
     */
    public Clothes getClothes(int index) {
        if (index < 0 || index >= currentCount) {
            throw new IndexOutOfBoundsException("Некоректний індекс предмета одягу.");
        }
        return items[index];
    }

    /**
     * Повертає поточну кількість речей у гардеробі.
     *
     * @return поточна кількість речей
     */
    public int getCurrentCount() {
        return currentCount;
    }

    /**
     * Повертає максимальну місткість гардеробу.
     *
     * @return максимальна кількість місць у гардеробі
     */
    public int getCapacity() {
        return items.length;
    }

    /**
     * Виводить у консоль список усіх речей у гардеробі.
     */
    public void printAllClothes() {
        if (currentCount == 0) {
            System.out.println("Гардероб порожній.");
            return;
        }
        System.out.println("\n=== Речі у гардеробі (" + currentCount + "/" + items.length + ") ===");
        for (int i = 0; i < currentCount; i++) {
            System.out.println((i + 1) + ". " + items[i].toString());
        }
    }
}