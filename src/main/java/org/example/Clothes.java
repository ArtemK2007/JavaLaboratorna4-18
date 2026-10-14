package org.example;

import java.util.Objects;

/**
 * Клас, що описує предмет одягу.
 * Містить інформацію про тип, розмір, ціну та бренд.
 */
public class Clothes {
    private String type;
    private ClothesSize size;
    private double price;
    private String brand;

    /**
     * Основний конструктор для ініціалізації об'єкта одягу.
     *
     * @param type  тип одягу (наприклад, Футболка)
     * @param size  розмір одягу (об'єкт перерахування ClothesSize)
     * @param price ціна одягу
     * @param brand бренд одягу
     * @throws IllegalArgumentException якщо передані некоректні дані
     */
    public Clothes(String type, ClothesSize size, double price, String brand) {
        setType(type);
        setSize(size);
        setPrice(price);
        setBrand(brand);
    }

    /**
     * Конструктор копіювання. Створює новий об'єкт на основі існуючого.
     *
     * @param other об'єкт класу Clothes, який потрібно скопіювати
     * @throws IllegalArgumentException якщо переданий об'єкт для копіювання є null
     */
    public Clothes(Clothes other) {
        if (other == null) {
            throw new IllegalArgumentException("Об'єкт для копіювання не може бути null.");
        }
        this.type = other.type;
        this.size = other.size;
        this.price = other.price;
        this.brand = other.brand;
    }

    /**
     * Отримує тип одягу.
     *
     * @return тип одягу
     */
    public String getType() {
        return type;
    }

    /**
     * Встановлює тип одягу.
     *
     * @param type тип одягу
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип одягу не може бути порожнім.");
        }
        this.type = type;
    }

    /**
     * Отримує розмір одягу.
     *
     * @return розмір одягу (ClothesSize)
     */
    public ClothesSize getSize() {
        return size;
    }

    /**
     * Встановлює розмір одягу.
     *
     * @param size розмір одягу
     * @throws IllegalArgumentException якщо розмір дорівнює null
     */
    public void setSize(ClothesSize size) {
        if (size == null) {
            throw new IllegalArgumentException("Розмір одягу не може бути null.");
        }
        this.size = size;
    }

    /**
     * Отримує ціну одягу.
     *
     * @return ціна одягу
     */
    public double getPrice() {
        return price;
    }

    /**
     * Встановлює ціну одягу.
     *
     * @param price ціна одягу
     * @throws IllegalArgumentException якщо ціна від'ємна
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною.");
        }
        this.price = price;
    }

    /**
     * Отримує бренд одягу.
     *
     * @return бренд одягу
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Встановлює бренд одягу.
     *
     * @param brand бренд одягу
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Бренд одягу не може бути порожнім.");
        }
        this.brand = brand;
    }

    /**
     * Повертає рядкове представлення об'єкта одягу.
     *
     * @return рядок з інформацією про одяг
     */
    @Override
    public String toString() {
        return "Clothes{" +
                "type='" + type + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                '}';
    }

    /**
     * Порівнює поточний об'єкт з іншим на рівність.
     *
     * @param o інший об'єкт для порівняння
     * @return true, якщо об'єкти рівні, інакше false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
                Objects.equals(type, clothes.type) &&
                size == clothes.size &&
                Objects.equals(brand, clothes.brand);
    }
}