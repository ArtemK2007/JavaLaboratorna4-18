package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () -> new Clothes("Футболка", "M", -150.0, "Nike"));

        assertThrows(IllegalArgumentException.class, () -> new Clothes("Штани", "L", 500.0, ""));
    }

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes clothes = new Clothes("Куртка", "XL", 1200.0, "Puma");

        assertThrows(IllegalArgumentException.class, () -> clothes.setPrice(-10.0));

        assertThrows(IllegalArgumentException.class, () -> clothes.setType("   "));
    }
}