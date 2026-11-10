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
            throw new InvalidFieldValueException("Кількість має бути більшою за 0.");
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
     * Завдання 1 (ЛР №17-18): Оновлення об'єкта в колекції зі збереженням оригінального UUID.
     */
    public boolean update(StoreItem existingItem, StoreItem newItem) {
        int index = inventory.indexOf(existingItem);
        if (index == -1) {
            throw new ObjectNotFoundException("Товар для оновлення не знайдено на складі.");
        }
        if (newItem == null) {
            throw new InvalidFieldValueException("Новий об'єкт товару не може бути null.");
        }
        newItem.getItem().setUuid(existingItem.getItem().getUuid());
        inventory.set(index, newItem);
        return true;
    }

    /**
     * Допоміжний метод оновлення за UUID із використанням кастомних винятків.
     */
    public boolean updateByUuid(String uuidStr, Clothes newClothes, int newQuantity) {
        if (newQuantity <= 0) {
            throw new InvalidFieldValueException("Кількість має бути більшою за 0.");
        }
        StoreItem existing = searchByUuid(uuidStr);
        if (existing == null) {
            throw new ObjectNotFoundException("Товар із таким UUID (" + uuidStr + ") не знайдено.");
        }
        newClothes.setUuid(existing.getItem().getUuid());
        int index = inventory.indexOf(existing);
        inventory.set(index, new StoreItem(newClothes, newQuantity));
        return true;
    }

    /**
     * Завдання 2 (ЛР №17-18): Видалення об'єкта StoreItem з колекції.
     */
    public boolean delete(StoreItem storeItem) {
        boolean removed = inventory.remove(storeItem);
        if (!removed) {
            throw new ObjectNotFoundException("Нічого видаляти: вказаний об'єкт не знайдено у колекції.");
        }
        return true;
    }

    /**
     * Зручний метод видалення за UUID з викиданням ObjectNotFoundException.
     */
    public boolean deleteByUuid(String uuidStr) {
        StoreItem existing = searchByUuid(uuidStr);
        if (existing == null) {
            throw new ObjectNotFoundException("Нічого видаляти: товар із UUID (" + uuidStr + ") не знайдено.");
        }
        return inventory.remove(existing);
    }
}