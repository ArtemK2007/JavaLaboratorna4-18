package org.example;

import java.util.Objects;

/**
 * Клас, що описує штани (похідний від Clothes).
 */
public class Pants extends Clothes {
    private int waistSize;

    /**
     * Конструктор для ініціалізації штанів.
     *
     * @param type      тип одягу
     * @param size      розмір одягу
     * @param price     ціна
     * @param brand     бренд
     * @param waistSize обхват талії в сантиметрах
     * @throws IllegalArgumentException якщо обхват талії некоректний
     */
    public Pants(String type, ClothesSize size, double price, String brand, int waistSize) {
        super(type, size, price, brand);
        setWaistSize(waistSize);
    }

    /**
     * Повертає обхват талії.
     *
     * @return обхват талії
     */
    public int getWaistSize() {
        return waistSize;
    }

    /**
     * Встановлює обхват талії з перевіркою.
     *
     * @param waistSize обхват талії
     * @throws IllegalArgumentException якщо значення менше або дорівнює 0
     */
    public void setWaistSize(int waistSize) {
        if (waistSize <= 0) {
            throw new IllegalArgumentException("Обхват талії має бути більшим за 0.");
        }
        this.waistSize = waistSize;
    }

    @Override
    public String toString() {
        return "Pants{" +
                "type='" + getType() + '\'' +
                ", size=" + getSize() +
                ", price=" + getPrice() +
                ", brand='" + getBrand() + '\'' +
                ", waistSize=" + waistSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pants pants = (Pants) o;
        return waistSize == pants.waistSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), waistSize);
    }
}