package org.example;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Утилітний клас для збереження та завантаження колекції об'єктів з файлів (TXT та JSON).
 */
public class FileStorage {

    //РОБОТА З TXT

    public static void saveToTxt(ArrayList<Clothes> list, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Clothes item : list) {
                if (item instanceof PoloShirt) {
                    PoloShirt p = (PoloShirt) item;
                    writer.println("PoloShirt;" + p.getType() + ";" + p.getSize() + ";" + p.getPrice() + ";" + p.getBrand() + ";" + p.isLongSleeve() + ";" + p.getButtonCount());
                } else if (item instanceof Jeans) {
                    Jeans j = (Jeans) item;
                    writer.println("Jeans;" + j.getType() + ";" + j.getSize() + ";" + j.getPrice() + ";" + j.getBrand() + ";" + j.getWaistSize() + ";" + j.getFitType());
                } else if (item instanceof Shirts) {
                    Shirts s = (Shirts) item;
                    writer.println("Shirts;" + s.getType() + ";" + s.getSize() + ";" + s.getPrice() + ";" + s.getBrand() + ";" + s.isLongSleeve());
                } else if (item instanceof Pants) {
                    Pants p = (Pants) item;
                    writer.println("Pants;" + p.getType() + ";" + p.getSize() + ";" + p.getPrice() + ";" + p.getBrand() + ";" + p.getWaistSize());
                } else {
                    writer.println("Clothes;" + item.getType() + ";" + item.getSize() + ";" + item.getPrice() + ";" + item.getBrand());
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка запису в TXT: " + e.getMessage());
        }
    }

    public static ArrayList<Clothes> loadFromTxt(String fileName) {
        ArrayList<Clothes> list = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) return list;

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

                    switch (classType) {
                        case "PoloShirt":
                            list.add(new PoloShirt(type, size, price, brand, Boolean.parseBoolean(p[5]), Integer.parseInt(p[6])));
                            break;
                        case "Jeans":
                            list.add(new Jeans(type, size, price, brand, Integer.parseInt(p[5]), p[6]));
                            break;
                        case "Shirts":
                            list.add(new Shirts(type, size, price, brand, Boolean.parseBoolean(p[5])));
                            break;
                        case "Pants":
                            list.add(new Pants(type, size, price, brand, Integer.parseInt(p[5])));
                            break;
                        case "Clothes":
                            list.add(new Clothes(type, size, price, brand));
                            break;
                        default:
                            System.out.println("Невідомий тип у TXT: " + classType);
                    }
                } catch (Exception e) {
                    System.out.println("Помилка обробки рядка TXT (пропущено): " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка читання TXT: " + e.getMessage());
        }
        return list;
    }

    //РОБОТА З JSON (Додатковий бал)

    public static void saveToJson(ArrayList<Clothes> list, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.out.println("Помилка запису в JSON: " + e.getMessage());
        }
    }

    public static ArrayList<Clothes> loadFromJson(String fileName) {
        ArrayList<Clothes> list = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) return list;

        try (FileReader reader = new FileReader(file)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            Gson gson = new Gson();

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                if (obj.has("classType")) {
                    String classType = obj.get("classType").getAsString();
                    try {
                        switch (classType) {
                            case "PoloShirt": list.add(gson.fromJson(element, PoloShirt.class)); break;
                            case "Jeans":     list.add(gson.fromJson(element, Jeans.class)); break;
                            case "Shirts":    list.add(gson.fromJson(element, Shirts.class)); break;
                            case "Pants":     list.add(gson.fromJson(element, Pants.class)); break;
                            case "Clothes":   list.add(gson.fromJson(element, Clothes.class)); break;
                        }
                    } catch (Exception e) {
                        System.out.println("Помилка відновлення об'єкта JSON: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Помилка читання JSON: " + e.getMessage());
        }
        return list;
    }
}