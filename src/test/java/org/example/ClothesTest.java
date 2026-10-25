package org.example;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () -> new Shirts("Футболка", ClothesSize.M, -150.0, "Nike", false));
        assertThrows(IllegalArgumentException.class, () -> new Pants("Штани", null, 500.0, "Adidas", 32));
        assertThrows(IllegalArgumentException.class, () -> new Pants("Штани", ClothesSize.L, 500.0, "", 32));
    }

    @Test
    void shouldCreatePantsAndValidateWaistSize() {
        Pants pants = new Pants("Штани", ClothesSize.L, 1200.0, "Levi's", 32);
        assertEquals(32, pants.getWaistSize());
        assertEquals("Штани", pants.getType());
        assertThrows(IllegalArgumentException.class, () -> new Pants("Шорти", ClothesSize.M, 400.0, "Zara", 0));
    }

    @Test
    void shouldCreateShirtsAndCheckSleeve() {
        Shirts shirt = new Shirts("Сорочка", ClothesSize.M, 850.0, "H&M", true);
        assertTrue(shirt.isLongSleeve());
        shirt.setLongSleeve(false);
        assertFalse(shirt.isLongSleeve());
    }

    @Test
    void shouldCreateJeansAndValidateFitType() {
        Jeans jeans = new Jeans("Джинси", ClothesSize.M, 1800.0, "Diesel", 32, "Slim");
        assertEquals("Slim", jeans.getFitType());
        assertThrows(IllegalArgumentException.class, () -> new Jeans("Джинси", ClothesSize.M, 1800.0, "Diesel", 32, "   "));
    }

    @Test
    void shouldCreatePoloShirtAndValidateButtonCount() {
        PoloShirt polo = new PoloShirt("Поло", ClothesSize.L, 1100.0, "Lacoste", false, 3);
        assertEquals(3, polo.getButtonCount());
        assertThrows(IllegalArgumentException.class, () -> polo.setButtonCount(0));
    }

    @Test
    void shouldAddItemsToStoreAndIncreaseQuantityForDuplicates() {
        Store store = new Store("Тестовий магазин");
        Clothes shirt = new Shirts("Футболка", ClothesSize.S, 450.0, "Nike", false);

        store.addNewClothes(shirt, 5);
        assertEquals(1, store.getInventory().size());
        assertEquals(5, store.getInventory().getFirst().getQuantity());

        store.addNewClothes(new Shirts("Футболка", ClothesSize.S, 450.0, "Nike", false), 3);

        assertEquals(1, store.getInventory().size());
        assertEquals(8, store.getInventory().getFirst().getQuantity());
    }

    @Test
    void shouldSearchByBrandInStore() {
        Store store = createTestStore();
        ArrayList<StoreItem> result = store.searchByBrand("Nike");
        assertEquals(1, result.size());
        assertEquals("Nike", result.getFirst().getItem().getBrand());
    }

    @Test
    void shouldSearchBySizeInStore() {
        Store store = createTestStore();
        ArrayList<StoreItem> result = store.searchBySize(ClothesSize.M);
        assertEquals(2, result.size());
    }

    @Test
    void shouldSearchByPriceRangeInStore() {
        Store store = createTestStore();
        ArrayList<StoreItem> result = store.searchByPriceRange(1000.0, 2000.0);
        assertEquals(2, result.size());
    }


    @Test
    void shouldSaveAndLoadFromTxtFileWithStore() {
        String testFileName = "test_store_input.txt";
        Store originalStore = createTestStore();

        FileStorage.saveToTxt(originalStore, testFileName);

        Store loadedStore = new Store("Завантажений TXT");
        FileStorage.loadFromTxt(loadedStore, testFileName);

        assertEquals(originalStore.getInventory().size(), loadedStore.getInventory().size());
        new File(testFileName).delete();
    }

    @Test
    void shouldSaveAndLoadFromJsonFileWithStore() {
        String testFileName = "test_store_input.json";
        Store originalStore = createTestStore();

        FileStorage.saveToJson(originalStore, testFileName);

        Store loadedStore = new Store("Завантажений JSON");
        FileStorage.loadFromJson(loadedStore, testFileName);

        assertEquals(originalStore.getInventory().size(), loadedStore.getInventory().size());
        new File(testFileName).delete();
    }

    private Store createTestStore() {
        Store store = new Store("Test Store");
        store.addNewClothes(new Shirts("Куртка", ClothesSize.XL, 2500.0, "Puma", true), 2);
        store.addNewClothes(new Pants("Чиноси", ClothesSize.M, 900.0, "Zara", 31), 4);
        store.addNewClothes(new Shirts("Футболка", ClothesSize.S, 450.0, "Nike", false), 10);
        store.addNewClothes(new Jeans("Джинси", ClothesSize.L, 1600.0, "Levi's", 34, "Regular"), 5);
        store.addNewClothes(new PoloShirt("Поло", ClothesSize.M, 1300.0, "Ralph Lauren", false, 2), 3);
        return store;
    }
}