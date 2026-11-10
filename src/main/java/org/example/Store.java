package org.example;

import java.util.ArrayList;
import java.util.UUID;

public class Store {
    private final String storeName;
    private final ArrayList<StoreItem> inventory;

    public Store(String storeName) {
        this.storeName = storeName;
        this.inventory = new ArrayList<>();
    }

    public String getStoreName() {
        return storeName;
    }

    public ArrayList<StoreItem> getInventory() {
        return inventory;
    }

    /**
     * Додає новий товар. Якщо такий товар вже є — збільшує його кількість.
     */
    public void addNewClothes(Clothes cl, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути більшою за 0.");
        }

        for (StoreItem storeItem : inventory) {
            if (storeItem.getItem().equals(cl)) {
                storeItem.addQuantity(quantity);
                return;
            }
        }

        inventory.add(new StoreItem(cl, quantity));
    }

    // МЕТОДИ ПОШУКУ

    public ArrayList<StoreItem> searchByBrand(String brand) {
        ArrayList<StoreItem> results = new ArrayList<>();
        for (StoreItem storeItem : inventory) {
            if (storeItem.getItem().getBrand().equalsIgnoreCase(brand)) {
                results.add(storeItem);
            }
        }
        return results;
    }

    public ArrayList<StoreItem> searchBySize(ClothesSize size) {
        ArrayList<StoreItem> results = new ArrayList<>();
        for (StoreItem storeItem : inventory) {
            if (storeItem.getItem().getSize() == size) {
                results.add(storeItem);
            }
        }
        return results;
    }

    public ArrayList<StoreItem> searchByPriceRange(double min, double max) {
        ArrayList<StoreItem> results = new ArrayList<>();
        for (StoreItem storeItem : inventory) {
            double price = storeItem.getItem().getPrice();
            if (price >= min && price <= max) {
                results.add(storeItem);
            }
        }
        return results;
    }

    public StoreItem searchByUuid(String uuidStr) {
        try {
            UUID targetUuid = UUID.fromString(uuidStr.trim());
            for (StoreItem storeItem : inventory) {
                if (storeItem.getItem().getUuid().equals(targetUuid)) {
                    return storeItem;
                }
            }
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Завдання 1 (ЛР №17): Оновлення об'єкта в колекції зі збереженням оригінального UUID.
     */
    public boolean update(StoreItem existingItem, StoreItem newItem) {
        int index = inventory.indexOf(existingItem);
        if (index != -1 && newItem != null) {
            newItem.getItem().setUuid(existingItem.getItem().getUuid());
            inventory.set(index, newItem);
            return true;
        }
        return false;
    }

    /**
     * Допоміжний метод оновлення за UUID (зручно для консолі).
     */
    public boolean updateByUuid(String uuidStr, Clothes newClothes, int newQuantity) {
        StoreItem existing = searchByUuid(uuidStr);
        if (existing == null) {
            return false;
        }
        newClothes.setUuid(existing.getItem().getUuid());
        int index = inventory.indexOf(existing);
        inventory.set(index, new StoreItem(newClothes, newQuantity));
        return true;
    }

    /**
     * Завдання 2 (ЛР №17): Видалення об'єкта StoreItem з колекції.
     */
    public boolean delete(StoreItem storeItem) {
        return inventory.remove(storeItem);
    }

    /**
     * Зручний метод видалення за UUID (для консольного меню).
     */
    public boolean deleteByUuid(String uuidStr) {
        try {
            UUID targetUuid = UUID.fromString(uuidStr.trim());
            return inventory.removeIf(storeItem -> storeItem.getItem().getUuid().equals(targetUuid));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}