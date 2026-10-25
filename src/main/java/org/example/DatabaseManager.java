package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String propertiesPath) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            props.load(fis);
            this.connection = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
            System.out.println("Успішне підключення до бази даних PostgreSQL!");
            initializeDatabase();
        } catch (IOException e) {
            System.out.println("Помилка читання конфігураційного файлу: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Помилка підключення до БД: " + e.getMessage());
        }
    }

    private void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS clothes_inventory (" +
                "id SERIAL PRIMARY KEY, " +
                "type VARCHAR(50) NOT NULL, " +
                "size VARCHAR(10) NOT NULL, " +
                "price NUMERIC(10, 2) NOT NULL, " +
                "brand VARCHAR(100) NOT NULL, " +
                "waist_size INT, " +
                "is_long_sleeve BOOLEAN, " +
                "fit_type VARCHAR(50), " +
                "button_count INT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Помилка ініціалізації таблиці: " + e.getMessage());
        }
    }

    public void saveClothes(Clothes item) {
        if (connection == null) return;

        //noinspection SqlNoDataSourceInspection,SqlResolve
        String sql = "INSERT INTO clothes_inventory (type, size, price, brand, waist_size, is_long_sleeve, fit_type, button_count) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getClass().getSimpleName());
            pstmt.setString(2, item.getSize().name());
            pstmt.setDouble(3, item.getPrice());
            pstmt.setString(4, item.getBrand());

            pstmt.setNull(5, Types.INTEGER);
            pstmt.setNull(6, Types.BOOLEAN);
            pstmt.setNull(7, Types.VARCHAR);
            pstmt.setNull(8, Types.INTEGER);

            if (item instanceof Pants) {
                pstmt.setInt(5, ((Pants) item).getWaistSize());
            }
            if (item instanceof Shirts) {
                pstmt.setBoolean(6, ((Shirts) item).isLongSleeve());
            }
            if (item instanceof Jeans) {
                pstmt.setInt(5, ((Jeans) item).getWaistSize());
                pstmt.setString(7, ((Jeans) item).getFitType());
            }
            if (item instanceof PoloShirt) {
                pstmt.setBoolean(6, ((PoloShirt) item).isLongSleeve());
                pstmt.setInt(8, ((PoloShirt) item).getButtonCount());
            }

            pstmt.executeUpdate();
            System.out.println("-> Об'єкт успішно збережено в базу даних!");
        } catch (SQLException e) {
            System.out.println("Помилка збереження об'єкта в БД: " + e.getMessage());
        }
    }

    public void loadClothes(Store store) {
        if (connection == null) return;

        //noinspection SqlNoDataSourceInspection,SqlResolve
        String sql = "SELECT * FROM clothes_inventory";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String classType = rs.getString("type");
                ClothesSize size = ClothesSize.valueOf(rs.getString("size"));
                double price = rs.getDouble("price");
                String brand = rs.getString("brand");

                Clothes item = null;

                switch (classType) {
                    case "Pants":
                        item = new Pants("Штани", size, price, brand, rs.getInt("waist_size"));
                        break;
                    case "Shirts":
                        item = new Shirts("Сорочка", size, price, brand, rs.getBoolean("is_long_sleeve"));
                        break;
                    case "Jeans":
                        item = new Jeans("Джинси", size, price, brand, rs.getInt("waist_size"), rs.getString("fit_type"));
                        break;
                    case "PoloShirt":
                        item = new PoloShirt("Поло", size, price, brand, rs.getBoolean("is_long_sleeve"), rs.getInt("button_count"));
                        break;
                    case "Clothes":
                        item = new Clothes("Одяг", size, price, brand);
                        break;
                }

                if (item != null) {
                    store.addNewClothes(item, 1);
                }
            }
            System.out.println("-> Дані успішно завантажено з бази даних PostgreSQL!");

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Помилка завантаження даних з БД: " + e.getMessage());
        }
    }
}