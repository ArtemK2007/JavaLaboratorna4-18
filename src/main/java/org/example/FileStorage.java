package org.example;

import com.google.gson.*;
import java.io.*;

public class FileStorage {

    //РОБОТА З TXT
    public static void saveToTxt(Store store, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (StoreItem storeItem : store.getInventory()) {
                Clothes item = storeItem.getItem();
                int qty = storeItem.getQuantity();

                if (item instanceof PoloShirt) {
                    PoloShirt p = (PoloShirt) item;
                    writer.println("PoloShirt;" + p.getType() + ";" + p.getSize() + ";" + p.getPrice() + ";" + p.getBrand() + ";" + p.isLongSleeve() + ";" + p.getButtonCount() + ";" + qty);
                } else if (item instanceof Jeans) {
                    Jeans j = (Jeans) item;
                    writer.println("Jeans;" + j.getType() + ";" + j.getSize() + ";" + j.getPrice() + ";" + j.getBrand() + ";" + j.getWaistSize() + ";" + j.getFitType() + ";" + qty);
                } else if (item instanceof Shirts) {
                    Shirts s = (Shirts) item;
                    writer.println("Shirts;" + s.getType() + ";" + s.getSize() + ";" + s.getPrice() + ";" + s.getBrand() + ";" + s.isLongSleeve() + ";" + qty);
                } else if (item instanceof Pants) {
                    Pants p = (Pants) item;
                    writer.println("Pants;" + p.getType() + ";" + p.getSize() + ";" + p.getPrice() + ";" + p.getBrand() + ";" + p.getWaistSize() + ";" + qty);
                } else {
                    writer.println("Clothes;" + item.getType() + ";" + item.getSize() + ";" + item.getPrice() + ";" + item.getBrand() + ";" + qty);
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка запису в TXT: " + e.getMessage());
        }
    }

    public static void loadFromTxt(Store store, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] p = line.split(";");
                    String classType = p[0];
                    String type = p[1];
                    ClothesSize size = ClothesSize.valueOf(p[2]);
                    double price = Double.parseDouble(p[3]);
                    String brand = p[4];

                    Clothes clothes = null;
                    int qty = 1;

                    switch (classType) {
                        case "PoloShirt":
                            clothes = new PoloShirt(type, size, price, brand, Boolean.parseBoolean(p[5]), Integer.parseInt(p[6]));
                            qty = Integer.parseInt(p[7]);
                            break;
                        case "Jeans":
                            clothes = new Jeans(type, size, price, brand, Integer.parseInt(p[5]), p[6]);
                            qty = Integer.parseInt(p[7]);
                            break;
                        case "Shirts":
                            clothes = new Shirts(type, size, price, brand, Boolean.parseBoolean(p[5]));
                            qty = Integer.parseInt(p[6]);
                            break;
                        case "Pants":
                            clothes = new Pants(type, size, price, brand, Integer.parseInt(p[5]));
                            qty = Integer.parseInt(p[6]);
                            break;
                    }
                    if (clothes != null) {
                        store.addNewClothes(clothes, qty);
                    }
                } catch (Exception e) {
                    System.out.println("Помилка обробки рядка TXT (пропущено): " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка читання TXT: " + e.getMessage());
        }
    }

    //РОБОТА З JSON
    public static void saveToJson(Store store, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(store.getInventory(), writer);
        } catch (IOException e) {
            System.out.println("Помилка запису в JSON: " + e.getMessage());
        }
    }

    public static void loadFromJson(Store store, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            Gson gson = new Gson();

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                int qty = obj.has("quantity") ? obj.get("quantity").getAsInt() : 1;

                JsonObject itemObj = obj.has("item") ? obj.getAsJsonObject("item") : null;
                if (itemObj != null) {
                    Clothes clothes = null;
                    try {
                        if (itemObj.has("classType")) {
                            String classType = itemObj.get("classType").getAsString();
                            switch (classType) {
                                case "PoloShirt": clothes = gson.fromJson(itemObj, PoloShirt.class); break;
                                case "Jeans":     clothes = gson.fromJson(itemObj, Jeans.class); break;
                                case "Shirts":    clothes = gson.fromJson(itemObj, Shirts.class); break;
                                case "Pants":     clothes = gson.fromJson(itemObj, Pants.class); break;
                                case "Clothes":   clothes = gson.fromJson(itemObj, Shirts.class); break;
                            }
                        } else if (itemObj.has("buttonCount")) {
                            clothes = gson.fromJson(itemObj, PoloShirt.class);
                        } else if (itemObj.has("fitType")) {
                            clothes = gson.fromJson(itemObj, Jeans.class);
                        } else if (itemObj.has("waistSize")) {
                            clothes = gson.fromJson(itemObj, Pants.class);
                        } else if (itemObj.has("longSleeve") || itemObj.has("isLongSleeve")) {
                            clothes = gson.fromJson(itemObj, Shirts.class);
                        } else {
                            clothes = gson.fromJson(itemObj, Shirts.class);
                        }

                        if (clothes != null) {
                            store.addNewClothes(clothes, qty);
                        }
                    } catch (Exception e) {
                        System.out.println("Помилка відновлення об'єкта JSON: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Помилка читання JSON: " + e.getMessage());
        }
    }
}