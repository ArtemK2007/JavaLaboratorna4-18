package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Клас модульного тестування для перевірки 5 класів ієрархії:
 * Clothes, Pants, Shirts, Jeans та PoloShirt.
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
        Pants pants = new Pants("Штани", ClothesSize.L, 1200.0, "Levi's", 32);

        assertEquals(32, pants.getWaistSize());
        assertEquals("Штани", pants.getType());

        assertThrows(IllegalArgumentException.class, () -> {
            new Pants("Шорти", ClothesSize.M, 400.0, "Zara", 0);
        });
    }

    /**
     * Перевіряє коректність створення та роботу полів у похідному класі Shirts.
     */
    @Test
    void shouldCreateShirtsAndCheckSleeve() {
        Shirts shirt = new Shirts("Сорочка", ClothesSize.M, 850.0, "H&M", true);

        assertTrue(shirt.isLongSleeve());
        shirt.setLongSleeve(false);
        assertFalse(shirt.isLongSleeve());
    }

    /**
     * Перевіряє створення та валідацію класу Jeans (3-й рівень ієрархії).
     */
    @Test
    void shouldCreateJeansAndValidateFitType() {
        Jeans jeans = new Jeans("Джинси", ClothesSize.M, 1800.0, "Diesel", 32, "Slim");

        assertEquals("Slim", jeans.getFitType());
        assertEquals(32, jeans.getWaistSize());
        assertTrue(jeans.toString().contains("fitType='Slim'"));

        assertThrows(IllegalArgumentException.class, () -> {
            new Jeans("Джинси", ClothesSize.M, 1800.0, "Diesel", 32, "   ");
        });
    }

    /**
     * Перевіряє створення та валідацію класу PoloShirt (3-й рівень ієрархії).
     */
    @Test
    void shouldCreatePoloShirtAndValidateButtonCount() {
        PoloShirt polo = new PoloShirt("Поло", ClothesSize.L, 1100.0, "Lacoste", false, 3);

        assertEquals(3, polo.getButtonCount());
        assertFalse(polo.isLongSleeve());
        assertTrue(polo.toString().contains("buttonCount=3"));

        assertThrows(IllegalArgumentException.class, () -> {
            polo.setButtonCount(0);
        });
    }

    /**
     * Перевіряє поліморфне збереження всіх 5 класів ієрархії в одній колекції ArrayList.
     */
    @Test
    void shouldStoreAllFiveHierarchyClassesInSingleArrayList() {
        ArrayList<Clothes> list = new ArrayList<>();
        list.add(new Clothes("Куртка", ClothesSize.XL, 2500.0, "Puma"));
        list.add(new Pants("Чиноси", ClothesSize.M, 900.0, "Zara", 31));
        list.add(new Shirts("Футболка", ClothesSize.S, 450.0, "Nike", false));
        list.add(new Jeans("Джинси", ClothesSize.L, 1600.0, "Levi's", 34, "Regular"));
        list.add(new PoloShirt("Поло", ClothesSize.M, 1300.0, "Ralph Lauren", false, 2));

        assertEquals(5, list.size());
    }
}