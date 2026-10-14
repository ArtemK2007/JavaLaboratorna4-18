package org.example;

import java.util.Objects;

/**
 * Клас, що описує сорочку-поло (похідний від Shirts).
 * Демонструє багаторівневе успадкування (Clothes -> Shirts -> PoloShirt).
 */
public class PoloShirt extends Shirts {
    private int buttonCount;

    /**
     * Конструктор для ініціалізації сорочки-поло.
     *
     * @param type        тип одягу
     * @param size        розмір одягу
     * @param price       ціна
     * @param brand       бренд
     * @param longSleeve  чи є довгий рукав (true/false)
     * @param buttonCount кількість ґудзиків на комірці
     * @throws IllegalArgumentException якщо кількість ґудзиків некоректна
     */
    public PoloShirt(String type, ClothesSize size, double price, String brand, boolean longSleeve, int buttonCount) {
        super(type, size, price, brand, longSleeve);
        setButtonCount(buttonCount);
    }

    /**
     * Повертає кількість ґудзиків на комірці.
     *
     * @return кількість ґудзиків
     */
    public int getButtonCount() {
        return buttonCount;
    }

    /**
     * Встановлює кількість ґудзиків із валідацією.
     *
     * @param buttonCount кількість ґудзиків (має бути > 0)
     * @throws IllegalArgumentException якщо кількість менша або дорівнює 0
     */
    public void setButtonCount(int buttonCount) {
        if (buttonCount <= 0) {
            throw new IllegalArgumentException("Кількість ґудзиків на поло має бути більшою за 0.");
        }
        this.buttonCount = buttonCount;
    }

    @Override
    public String toString() {
        return "PoloShirt{" +
                "type='" + getType() + '\'' +
                ", size=" + getSize() +
                ", price=" + getPrice() +
                ", brand='" + getBrand() + '\'' +
                ", longSleeve=" + isLongSleeve() +
                ", buttonCount=" + buttonCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PoloShirt poloShirt = (PoloShirt) o;
        return buttonCount == poloShirt.buttonCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), buttonCount);
    }
}