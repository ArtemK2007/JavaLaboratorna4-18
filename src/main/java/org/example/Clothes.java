package org.example;

public abstract class Clothes implements Comparable<Clothes> {
    private final String type;
    private final ClothesSize size;
    private final double price;
    private final String brand;

    public Clothes(String type, ClothesSize size, double price, String brand) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною!");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип одягу не може бути порожнім!");
        }
        this.type = type;
        this.size = size;
        this.price = price;
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public ClothesSize getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public int compareTo(Clothes other) {
        return Double.compare(this.price, other.price);
    }

    @Override
    public String toString() {
        return type + "{size=" + size + ", price=" + price + ", brand='" + brand + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Clothes clothes = (Clothes) obj;
        return Double.compare(clothes.price, price) == 0 &&
                type.equals(clothes.type) &&
                size == clothes.size &&
                brand.equals(clothes.brand);
    }
}