package org.example;

public class StoreItem {
    private Clothes item;
    private int quantity;

    public StoreItem(Clothes item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Clothes getItem() {
        return item;
    }

    public void setItem(Clothes item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    @Override
    public String toString() {
        return item.toString() + " | Кількість на складі: " + quantity + " шт.";
    }
}