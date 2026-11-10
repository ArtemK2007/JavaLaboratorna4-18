package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;

public class MainApp extends Application {

    private final Store store = new Store("Магазин Одягу WinWin");

    private TextArea inventoryArea;
    private TextField brandField;
    private TextField priceField;
    private TextField uuidSearchField;
    private TextArea searchResultArea;
    private ComboBox<String> typeComboBox;
    private ComboBox<ClothesSize> sizeComboBox;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("WinWin Store - UUID & JavaFX Manager");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label addLabel = new Label("Додати новий товар:");
        addLabel.setStyle("-fx-font-weight: bold;");

        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Штани", "Сорочка", "Джинси", "Поло");
        typeComboBox.setValue("Штани");

        brandField = new PromptTextField("Бренд");
        priceField = new PromptTextField("Ціна");

        sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(ClothesSize.values());
        sizeComboBox.setValue(ClothesSize.M);

        Button addButton = new Button("Додати товар");
        addButton.setOnAction(e -> handleAddClothes());

        Label listLabel = new Label("Короткий список інвентарю (Об'єкт + UUID):");
        listLabel.setStyle("-fx-font-weight: bold;");

        inventoryArea = new TextArea();
        inventoryArea.setEditable(false);
        inventoryArea.setPrefHeight(150);

        Label searchLabel = new Label("Пошук товару за UUID:");
        searchLabel.setStyle("-fx-font-weight: bold;");

        uuidSearchField = new TextField();
        uuidSearchField.setPromptText("Введіть UUID (напр. дефіси обов'язкові)");

        Button searchButton = new Button("Знайти за UUID");
        searchButton.setOnAction(e -> handleSearchUuid());

        searchResultArea = new TextArea();
        searchResultArea.setEditable(false);
        searchResultArea.setPrefHeight(80);
        searchResultArea.setPromptText("Результат пошуку з'явиться тут...");

        root.getChildren().addAll(
                addLabel, typeComboBox, brandField, priceField, sizeComboBox, addButton,
                new Separator(),
                listLabel, inventoryArea,
                new Separator(),
                searchLabel, uuidSearchField, searchButton, searchResultArea
        );

        updateInventoryView();

        Scene scene = new Scene(root, 500, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleAddClothes() {
        try {
            String type = typeComboBox.getValue();
            String brand = brandField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            ClothesSize size = sizeComboBox.getValue();

            if (brand.isEmpty()) {
                showAlert("Помилка", "Введіть бренд товару!");
                return;
            }

            Clothes newItem = switch (type) {
                case "Штани" -> new Pants(type, size, price, brand, 40);
                case "Сорочка" -> new Shirts(type, size, price, brand, true);
                case "Джинси" -> new Jeans(type, size, price, brand, 42, "Slim");
                case "Поло" -> new PoloShirt(type, size, price, brand, true, 3);
                default -> new Shirts(type, size, price, brand, false);
            };

            store.addNewClothes(newItem, 1);
            updateInventoryView();

            brandField.clear();
            priceField.clear();
            showAlert("Успіх", "Товар успішно додано! UUID створено автоматично.");

        } catch (NumberFormatException e) {
            showAlert("Помилка вводу", "Ціна має бути числом!");
        } catch (Exception e) {
            showAlert("Помилка", e.getMessage());
        }
    }

    private void updateInventoryView() {
        StringBuilder sb = new StringBuilder();
        for (StoreItem item : store.getInventory()) {
            sb.append("Тип: ").append(item.getItem().getType())
                    .append(" | Бренд: ").append(item.getItem().getBrand())
                    .append(" | Кількість: ").append(item.getQuantity())
                    .append(" | UUID: ").append(item.getItem().getUuid())
                    .append("\n");
        }
        inventoryArea.setText(sb.toString());
    }

    private void handleSearchUuid() {
        String query = uuidSearchField.getText();
        StoreItem found = store.searchByUuid(query);

        if (found != null) {
            searchResultArea.setText("ЗНАЙДЕНО УСПІШНО:\n" + found);
        } else {
            try {
                UUID.fromString(query.trim());
                searchResultArea.setText("Товар з таким UUID не знайдено на складі.");
            } catch (IllegalArgumentException ignored) {
                searchResultArea.setText("ПОМИЛКА: Некоректний формат UUID! Перевірте правильність вводу.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class PromptTextField extends TextField {
        public PromptTextField(String prompt) {
            setPromptText(prompt);
        }
    }

    static void main(String[] args) {
        launch(args);
    }
}