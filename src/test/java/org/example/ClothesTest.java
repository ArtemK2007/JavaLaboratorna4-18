package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Клас модульного тестування для перевірки класів Clothes та Wardrobe.
 */
class ClothesTest {

    /**
     * Перевіряє кидання винятку при передачі некоректних параметрів у конструктор.
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
     * Перевіряє кидання винятку при передачі некоректних значень у сетери.
     */
    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes clothes = new Clothes("Куртка", ClothesSize.XL, 1200.0, "Puma");

        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setPrice(-10.0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setType("   ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setSize(null);
        });
    }

    /**
     * Перевіряє роботу конструктора копіювання та статичного лічильника.
     */
    @Test
    void shouldCopyObjectAndIncrementStaticCounter() {
        int countBefore = Clothes.getCreatedCount();

        Clothes original = new Clothes("Худі", ClothesSize.L, 950.0, "Reebok");
        Clothes copy = new Clothes(original);

        assertEquals(original, copy);
        assertEquals(countBefore + 2, Clothes.getCreatedCount());

        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes(null);
        });
    }

    /**
     * Перевіряє базову роботу класу агрегації Wardrobe.
     */
    @Test
    void shouldAddClothesToWardrobeAndCheckCapacity() {
        Wardrobe wardrobe = new Wardrobe(1);
        Clothes item = new Clothes("Кепка", ClothesSize.S, 300.0, "New Era");

        wardrobe.addClothes(item);
        assertEquals(1, wardrobe.getCurrentCount());
        assertEquals(item, wardrobe.getClothes(0));

        // Перевірка переповнення гардеробу
        assertThrows(IllegalStateException.class, () -> {
            wardrobe.addClothes(new Clothes(item));
        });
    }
}