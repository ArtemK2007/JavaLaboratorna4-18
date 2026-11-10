package org.example;

import java.util.ArrayList;
import java.util.UUID; // ДОДАНО: імпорт для роботи з UUID

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
}