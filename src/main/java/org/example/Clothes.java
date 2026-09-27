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
     */
    public Clothes(String type, String size, double price, String brand) {
        this.type = type;
        this.size = size;
        this.price = price;
        this.brand = brand;
    }

    /**
     * Отримує тип одягу.
     * @return тип одягу
     */
    public String getType() {
        return type;
    }

    /**
     * Встановлює тип одягу.
     * @param type тип одягу
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Отримує розмір одягу.
     * @return розмір одягу
     */
    public String getSize() {
        return size;
    }

    /**
     * Встановлює розмір одягу.
     * @param size розмір одягу
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Отримує ціну одягу.
     * @return ціна одягу
     */
    public double getPrice() {
        return price;
    }

    /**
     * Встановлює ціну одягу.
     * @param price ціна одягу
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Отримує бренд одягу.
     * @return бренд одягу
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Встановлює бренд одягу.
     * @param brand бренд одягу
     */
    public void setBrand(String brand) {
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