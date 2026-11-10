package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingNonExistingObject() {
        Store store = new Store("Test Store");
        Clothes shirt = new Shirts("Shirt", ClothesSize.M, 100.0, "Brand", true);
        StoreItem storeItem = new StoreItem(shirt, 5);

        assertThrows(ObjectNotFoundException.class, () -> {
            store.delete(storeItem);
        });
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenQuantityIsInvalid() {
        Store store = new Store("Test Store");
        Clothes shirt = new Shirts("Shirt", ClothesSize.M, 100.0, "Brand", true);

        assertThrows(InvalidFieldValueException.class, () -> {
            store.addNewClothes(shirt, 0);
        });
    }
}