package org.example;

import java.util.Objects;

public class Clothes {
    private String classType;

    private String type;
    private ClothesSize size;
    private double price;
    private String brand;

    public Clothes(String type, ClothesSize size, double price, String brand) {
        this.classType = this.getClass().getSimpleName();
        setType(type);
        setSize(size);
        setPrice(price);
        setBrand(brand);
    }

    public Clothes(Clothes other) {
        if (other == null) {
            throw new IllegalArgumentException("Об'єкт для копіювання не може бути null.");
        }
        this.classType = other.classType;
        this.type = other.type;
        this.size = other.size;
        this.price = other.price;
        this.brand = other.brand;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип одягу не може бути порожнім.");
        }
        this.type = type;
    }

    public ClothesSize getSize() {
        return size;
    }

    public void setSize(ClothesSize size) {
        if (size == null) {
            throw new IllegalArgumentException("Розмір одягу не може бути null.");
        }
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною.");
        }
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Бренд одягу не може бути порожнім.");
        }
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "classType='" + classType + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
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
                size == clothes.size &&
                Objects.equals(brand, clothes.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, size, price, brand);
    }
}