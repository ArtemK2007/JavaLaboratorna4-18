package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Клас модульного тестування для перевірки базового класу Clothes та його нащадків Pants і Shirts.
 */
class ClothesTest {

    /**
     * Перевіряє кидання винятку при передачі некоректних параметрів у базовий конструктор.
     */
    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Футболка", ClothesSize.M, -150.0, "Nike");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Штани", null, 500.0, "Adidas");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Штани", ClothesSize.L, 500.0, "");
        });
    }

    /**
     * Перевіряє коректність створення та валідацію в похідному класі Pants.
     */
    @Test
    void shouldCreatePantsAndValidateWaistSize() {
        Pants pants = new Pants("Джинси", ClothesSize.L, 1200.0, "Levi's", 32);

        assertEquals(32, pants.getWaistSize());
        assertEquals("Джинси", pants.getType());
        assertEquals(ClothesSize.L, pants.getSize());

        assertThrows(IllegalArgumentException.class, () -> {
            new Pants("Шорти", ClothesSize.M, 400.0, "Zara", 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            pants.setWaistSize(-5);
        });
    }

    /**
     * Перевіряє коректність створення та роботу полів у похідному класі Shirts.
     */
    @Test
    void shouldCreateShirtsAndCheckSleeve() {
        Shirts shirt = new Shirts("Сорочка", ClothesSize.M, 850.0, "H&M", true);

        assertTrue(shirt.isLongSleeve());
        assertEquals("Сорочка", shirt.getType());
        assertEquals("H&M", shirt.getBrand());

        shirt.setLongSleeve(false);
        assertFalse(shirt.isLongSleeve());
    }

    /**
     * Перевіряє роботу методу equals та hashCode для похідних класів.
     */
    @Test
    void shouldCheckEqualityForDerivedClasses() {
        Pants pants1 = new Pants("Класичні", ClothesSize.XL, 1500.0, "Boss", 36);
        Pants pants2 = new Pants("Класичні", ClothesSize.XL, 1500.0, "Boss", 36);
        Pants pants3 = new Pants("Класичні", ClothesSize.XL, 1500.0, "Boss", 34);

        assertEquals(pants1, pants2);
        assertNotEquals(pants1, pants3);
        assertEquals(pants1.hashCode(), pants2.hashCode());
    }
}