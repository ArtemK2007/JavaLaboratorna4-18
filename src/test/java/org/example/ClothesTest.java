package org.example;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Клас модульного тестування для перевірки 5 класів ієрархії
 * та роботи з файлами (збереження і читання TXT/JSON).
 */
class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () -> new Clothes("Футболка", ClothesSize.M, -150.0, "Nike"));
        assertThrows(IllegalArgumentException.class, () -> new Clothes("Штани", null, 500.0, "Adidas"));
        assertThrows(IllegalArgumentException.class, () -> new Clothes("Штани", ClothesSize.L, 500.0, ""));
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
    void shouldStoreAllFiveHierarchyClassesInSingleArrayList() {
        ArrayList<Clothes> list = new ArrayList<>();
        list.add(new Clothes("Куртка", ClothesSize.XL, 2500.0, "Puma"));
        list.add(new Pants("Чиноси", ClothesSize.M, 900.0, "Zara", 31));
        list.add(new Shirts("Футболка", ClothesSize.S, 450.0, "Nike", false));
        list.add(new Jeans("Джинси", ClothesSize.L, 1600.0, "Levi's", 34, "Regular"));
        list.add(new PoloShirt("Поло", ClothesSize.M, 1300.0, "Ralph Lauren", false, 2));
        assertEquals(5, list.size());
    }


    @Test
    void shouldSaveAndLoadFromTxtFile() {
        String testFileName = "test_input.txt";
        ArrayList<Clothes> originalList = createTestList();

        FileStorage.saveToTxt(originalList, testFileName);

        ArrayList<Clothes> loadedList = FileStorage.loadFromTxt(testFileName);

        assertEquals(originalList.size(), loadedList.size());
        for (int i = 0; i < originalList.size(); i++) {
            assertEquals(originalList.get(i), loadedList.get(i));
        }

        new File(testFileName).delete();
    }

    @Test
    void shouldSaveAndLoadFromJsonFile() {
        String testFileName = "test_input.json";
        ArrayList<Clothes> originalList = createTestList();

        FileStorage.saveToJson(originalList, testFileName);

        ArrayList<Clothes> loadedList = FileStorage.loadFromJson(testFileName);

        assertEquals(originalList.size(), loadedList.size());
        for (int i = 0; i < originalList.size(); i++) {
            assertEquals(originalList.get(i), loadedList.get(i));
        }

        new File(testFileName).delete();
    }

    //ТЕСТИ ДЛЯ ПОШУКУ (ЛАБА 10)

    @Test
    void shouldSearchByBrand() {
        ArrayList<Clothes> list = createTestList();
        ArrayList<Clothes> result = Main.searchByBrand(list, "Nike");
        assertEquals(1, result.size());
        assertEquals("Nike", result.get(0).getBrand());
    }

    @Test
    void shouldSearchBySize() {
        ArrayList<Clothes> list = createTestList();
        ArrayList<Clothes> result = Main.searchBySize(list, ClothesSize.M);
        assertEquals(2, result.size());
        assertEquals(ClothesSize.M, result.get(0).getSize());
        assertEquals(ClothesSize.M, result.get(1).getSize());
    }

    @Test
    void shouldSearchByPriceRange() {
        ArrayList<Clothes> list = createTestList();
        ArrayList<Clothes> result = Main.searchByPriceRange(list, 1000.0, 2000.0);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getPrice() >= 1000.0 && result.get(0).getPrice() <= 2000.0);
        assertTrue(result.get(1).getPrice() >= 1000.0 && result.get(1).getPrice() <= 2000.0);
    }

    private ArrayList<Clothes> createTestList() {
        ArrayList<Clothes> list = new ArrayList<>();
        list.add(new Clothes("Куртка", ClothesSize.XL, 2500.0, "Puma"));
        list.add(new Pants("Чиноси", ClothesSize.M, 900.0, "Zara", 31));
        list.add(new Shirts("Футболка", ClothesSize.S, 450.0, "Nike", false));
        list.add(new Jeans("Джинси", ClothesSize.L, 1600.0, "Levi's", 34, "Regular"));
        list.add(new PoloShirt("Поло", ClothesSize.M, 1300.0, "Ralph Lauren", false, 2));
        return list;
    }
}