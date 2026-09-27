package org.example;

import java.util.Objects;

/**
 * Клас, що описує предмет одягу.
 * Містить інформацію про тип, розмір, ціну та бренд.
 */
public class Clothes {
    private String type;
    private String size;
    private double price;
    private String brand;

    /**
     * Конструктор для ініціалізації об'єкта одягу.
     *
     * @param type  тип одягу (наприклад, Футболка)
     * @param size  розмір одягу (наприклад, L)
     * @param price ціна одягу
     * @param brand бренд одягу
     * @throws IllegalArgumentException якщо передані некоректні дані
     */
    public Clothes(String type, String size, double price, String brand) {
        setType(type);
        setSize(size);
        setPrice(price);
        setBrand(brand);
    }

    public String getType() {
        return type;
    }

    /**
     * Встановлює тип одягу.
     * @param type тип одягу
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип одягу не може бути порожнім.");
        }
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    /**
     * Встановлює розмір одягу.
     * @param size розмір одягу
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Розмір одягу не може бути порожнім.");
        }
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Встановлює ціну одягу.
     * @param price ціна одягу
     * @throws IllegalArgumentException якщо ціна від'ємна
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною.");
        }
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    /**
     * Встановлює бренд одягу.
     * @param brand бренд одягу
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Бренд одягу не може бути порожнім.");
        }
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
                Objects.equals(type, clothes.type) &&
                Objects.equals(size, clothes.size) &&
                Objects.equals(brand, clothes.brand);
    }
}