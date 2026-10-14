package org.example;

import java.util.Objects;

/**
 * Клас, що описує джинси (похідний від Pants).
 * Демонструє багаторівневе успадкування (Clothes -> Pants -> Jeans).
 */
public class Jeans extends Pants {
    private String fitType;

    /**
     * Конструктор для ініціалізації джинсів.
     *
     * @param type      тип одягу
     * @param size      розмір одягу
     * @param price     ціна
     * @param brand     бренд
     * @param waistSize обхват талії в сантиметрах
     * @param fitType   тип крою (наприклад, Slim, Regular, Baggy)
     * @throws IllegalArgumentException якщо параметри некоректні
     */
    public Jeans(String type, ClothesSize size, double price, String brand, int waistSize, String fitType) {
        super(type, size, price, brand, waistSize);
        setFitType(fitType);
    }

    /**
     * Повертає тип крою джинсів.
     *
     * @return тип крою
     */
    public String getFitType() {
        return fitType;
    }

    /**
     * Встановлює тип крою джинсів із валідацією.
     *
     * @param fitType тип крою
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setFitType(String fitType) {
        if (fitType == null || fitType.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип крою джинсів не може бути порожнім.");
        }
        this.fitType = fitType;
    }

    @Override
    public String toString() {
        return "Jeans{" +
                "type='" + getType() + '\'' +
                ", size=" + getSize() +
                ", price=" + getPrice() +
                ", brand='" + getBrand() + '\'' +
                ", waistSize=" + getWaistSize() +
                ", fitType='" + fitType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Jeans jeans = (Jeans) o;
        return Objects.equals(fitType, jeans.fitType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fitType);
    }
}