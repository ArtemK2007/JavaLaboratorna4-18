package org.example;

import java.util.Objects;

/**
 * Клас, що описує сорочку або футболку (похідний від Clothes).
 */
public class Shirts extends Clothes {
    private boolean longSleeve;

    /**
     * Конструктор для ініціалізації сорочки/футболки.
     *
     * @param type       тип одягу
     * @param size       розмір одягу
     * @param price      ціна
     * @param brand      бренд
     * @param longSleeve чи є довгий рукав (true/false)
     */
    public Shirts(String type, ClothesSize size, double price, String brand, boolean longSleeve) {
        super(type, size, price, brand);
        this.longSleeve = longSleeve;
    }

    /**
     * Перевіряє наявність довгого рукава.
     *
     * @return true, якщо рукав довгий, інакше false
     */
    public boolean isLongSleeve() {
        return longSleeve;
    }

    /**
     * Встановлює наявність довгого рукава.
     *
     * @param longSleeve стан рукава
     */
    public void setLongSleeve(boolean longSleeve) {
        this.longSleeve = longSleeve;
    }

    @Override
    public String toString() {
        return "Shirts{" +
                "type='" + getType() + '\'' +
                ", size=" + getSize() +
                ", price=" + getPrice() +
                ", brand='" + getBrand() + '\'' +
                ", longSleeve=" + longSleeve +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Shirts shirts = (Shirts) o;
        return longSleeve == shirts.longSleeve;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), longSleeve);
    }
}