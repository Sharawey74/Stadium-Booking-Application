import javafx.animation.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.*;
import javafx.util.Duration;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.*;

public class StadiumBookingSystemFX extends Application {
    private static final List<StadiumFacility> facilities = new ArrayList<>();
    private static final List<Booking> bookings = new ArrayList<>();
    private Label statusLabel;
    private AnimationPane contentPane;
    private Timeline animationTimeline;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stadium Booking System");
        primaryStage.setMaximized(true);

        // Create the main layout
        BorderPane root = new BorderPane();

        // Create header panel
        Pane headerPanel = createHeaderPanel();
        root.setTop(headerPanel);

        // Create navigation panel with image
        VBox navPanel = createNavigationPanel(primaryStage);
        root.setLeft(navPanel);

        // Create content panel with animation
        contentPane = new AnimationPane();
        root.setCenter(contentPane);

        // Create status bar
        Pane statusBar = createStatusBar();
        root.setBottom(statusBar);

        // Start animation
        startAnimation();

        // Set scene and show stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane createHeaderPanel() {
        Pane headerPanel = new Pane();

        // Create gradient background
        Stop[] stops = new Stop[]{new Stop(0, Color.rgb(5, 119, 250)), new Stop(1, Color.rgb(15, 32, 60))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        headerPanel.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add white border
        headerPanel.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(6))));

        // Add header label
        Label headerLabel = new Label("Stadium Booking Management System");
        headerLabel.setFont(Font.font("Serif", FontWeight.BOLD, FontPosture.ITALIC, 44));
        headerLabel.setTextFill(Color.WHITE);
        headerLabel.setPadding(new Insets(10));
        headerLabel.setLayoutX(150);
        headerLabel.setLayoutY(10);

        headerPanel.getChildren().add(headerLabel);
        headerPanel.setPrefHeight(80);

        return headerPanel;
    }

    private VBox createNavigationPanel(Stage primaryStage) {
        VBox navPanel = new VBox(10);
        navPanel.setPrefWidth(250);
        navPanel.setPadding(new Insets(10));

        // Create gradient background
        Stop[] stops = new Stop[]{new Stop(0, Color.rgb(5, 119, 250)), new Stop(1, Color.rgb(15, 32, 60))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        navPanel.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add image at the top
        try {
            Image image = new Image("file:stadium.jpg"); // Update this path to match your image location
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(230);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);

            navPanel.getChildren().add(imageView);
        } catch (Exception e) {
            System.out.println("Image not found: " + e.getMessage());
            // Add a placeholder if image isn't found
            Pane placeholder = new Pane();
            placeholder.setStyle("-fx-background-color: #0077cc; -fx-min-height: 150px;");
            navPanel.getChildren().add(placeholder);
        }

        // Create navigation buttons
        Button addFacilityButton = createStyledButton("Add Facility", "Add a new facility to the system.");
        Button viewFacilitiesButton = createStyledButton("View Facilities", "View all available facilities.");
        Button makeBookingButton = createStyledButton("Make Booking", "Make a new booking.");
        Button viewBookingsButton = createStyledButton("View Bookings", "View all current bookings.");
        Button cancelBookingButton = createStyledButton("Cancel Booking", "Cancel an existing booking.");
        Button exitButton = createStyledButton("Exit", "Exit the application.");

        // Add buttons to the navigation panel
        navPanel.getChildren().addAll(
                addFacilityButton,
                viewFacilitiesButton,
                makeBookingButton,
                viewBookingsButton,
                cancelBookingButton,
                exitButton
        );

        // Set button actions
        addFacilityButton.setOnAction(e -> showAddFacilityDialog(primaryStage));
        viewFacilitiesButton.setOnAction(e -> showFacilitiesTable(primaryStage));
        makeBookingButton.setOnAction(e -> showBookingDialog(primaryStage));
        viewBookingsButton.setOnAction(e -> showBookingsTable(primaryStage));
        cancelBookingButton.setOnAction(e -> showCancelBookingDialog(primaryStage));
        exitButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Exit Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                Platform.exit();
            }
        });

        return navPanel;
    }

    private Button createStyledButton(String text, String tooltip) {
        Button button = new Button(text);
        button.setTooltip(new Tooltip(tooltip));
        button.setStyle(
                "-fx-background-color: #004D7C; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: white; " +
                        "-fx-border-width: 3px; " +
                        "-fx-min-width: 230px; " +
                        "-fx-min-height: 40px;"
        );
        button.setCursor(Cursor.HAND);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #00305A; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: white; " +
                        "-fx-border-width: 3px; " +
                        "-fx-min-width: 230px; " +
                        "-fx-min-height: 40px;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #004D7C; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: white; " +
                        "-fx-border-width: 3px; " +
                        "-fx-min-width: 230px; " +
                        "-fx-min-height: 40px;"
        ));

        return button;
    }

    private Pane createStatusBar() {
        Pane statusBar = new Pane();

        // Create gradient background
        Stop[] stops = new Stop[]{new Stop(0, Color.rgb(5, 119, 250)), new Stop(1, Color.rgb(15, 32, 60))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        statusBar.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add status label
        statusLabel = new Label("Ready");
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setFont(Font.font("Arial", 22));
        statusLabel.setLayoutX(10);
        statusLabel.setLayoutY(10);

        statusBar.getChildren().add(statusLabel);
        statusBar.setPrefHeight(50);

        return statusBar;
    }

    private void startAnimation() {
        animationTimeline = new Timeline(
                new KeyFrame(Duration.millis(30), event -> contentPane.updateAnimation())
        );
        animationTimeline.setCycleCount(Animation.INDEFINITE);
        animationTimeline.play();
    }

    private void showAddFacilityDialog(Stage owner) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Add Facility");
        dialog.setWidth(650);
        dialog.setHeight(450);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Create input fields
        Label nameLabel = new Label("Facility Name:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        TextField nameField = new TextField();

        Label capacityLabel = new Label("Capacity:");
        capacityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        TextField capacityField = new TextField();

        Label typeLabel = new Label("Type:");
        typeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        ComboBox<String> typeComboBox = new ComboBox<>(
                FXCollections.observableArrayList("Seating Section", "Conference Room")
        );
        typeComboBox.setValue("Seating Section");

        Label seatTypeLabel = new Label("Seat Type (for Seating Section):");
        seatTypeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        ComboBox<String> seatTypeComboBox = new ComboBox<>(
                FXCollections.observableArrayList("VIP", "Regular")
        );
        seatTypeComboBox.setValue("Regular");

        Label projectorLabel = new Label("Has Projector (Conference Room):");
        projectorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        CheckBox projectorCheckBox = new CheckBox();

        // Initially hide Conference Room options
        projectorLabel.setVisible(false);
        projectorCheckBox.setVisible(false);

        // Add type change listener
        typeComboBox.setOnAction(e -> {
            boolean isSeatingSection = "Seating Section".equals(typeComboBox.getValue());
            seatTypeLabel.setVisible(isSeatingSection);
            seatTypeComboBox.setVisible(isSeatingSection);
            projectorLabel.setVisible(!isSeatingSection);
            projectorCheckBox.setVisible(!isSeatingSection);
        });

        Button saveButton = new Button("Save");
        saveButton.setStyle(
                "-fx-background-color: #01B040; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-min-width: 100px; " +
                        "-fx-min-height: 40px;"
        );

        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String capacityStr = capacityField.getText().trim();

            if (name.isEmpty() || capacityStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
                return;
            }

            try {
                int capacity = Integer.parseInt(capacityStr);
                if (capacity <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid Capacity Number");
                    return;
                }

                String type = typeComboBox.getValue();

                if ("Seating Section".equals(type)) {
                    String seatType = seatTypeComboBox.getValue();
                    facilities.add(new StadiumFacility(name, capacity, type, seatType, false));
                } else {
                    boolean hasProjector = projectorCheckBox.isSelected();
                    facilities.add(new StadiumFacility(name, capacity, type, "", hasProjector));
                }

                statusLabel.setText("Facility added successfully.");
                showAlert(Alert.AlertType.INFORMATION, "Success", "Facility added successfully!");
                dialog.close();

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Capacity must be a valid number.");
            }
        });

        // Add components to grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(capacityLabel, 0, 1);
        grid.add(capacityField, 1, 1);
        grid.add(typeLabel, 0, 2);
        grid.add(typeComboBox, 1, 2);
        grid.add(seatTypeLabel, 0, 3);
        grid.add(seatTypeComboBox, 1, 3);
        grid.add(projectorLabel, 0, 4);
        grid.add(projectorCheckBox, 1, 4);
        grid.add(saveButton, 1, 5);

        Scene scene = new Scene(grid);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showFacilitiesTable(Stage owner) {
        if (facilities.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "No facilities available.");
            return;
        }

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("View Facilities");
        dialog.setWidth(900);
        dialog.setHeight(600);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        // Create table
        TableView<FacilityTableData> table = new TableView<>();

        // Define columns
        TableColumn<FacilityTableData, String> nameCol = new TableColumn<>("Facility Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<FacilityTableData, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setPrefWidth(150);

        TableColumn<FacilityTableData, Integer> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capacityCol.setPrefWidth(100);

        TableColumn<FacilityTableData, String> infoCol = new TableColumn<>("Additional Info");
        infoCol.setCellValueFactory(new PropertyValueFactory<>("additionalInfo"));
        infoCol.setPrefWidth(200);

        TableColumn<FacilityTableData, Integer> bookingsCol = new TableColumn<>("Current Bookings");
        bookingsCol.setCellValueFactory(new PropertyValueFactory<>("currentBookings"));
        bookingsCol.setPrefWidth(150);

        TableColumn<FacilityTableData, Integer> availabilityCol = new TableColumn<>("Availability");
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        availabilityCol.setPrefWidth(100);

        table.getColumns().addAll(nameCol, typeCol, capacityCol, infoCol, bookingsCol, availabilityCol);

        // Populate table data
        ObservableList<FacilityTableData> data = FXCollections.observableArrayList();
        for (StadiumFacility facility : facilities) {
            int currentBookings = getCurrentBookings(facility.getName());
            int availability = facility.getCapacity() - currentBookings;

            String additionalInfo;
            if (facility.getType().equals("Seating Section")) {
                additionalInfo = "Seat Type: " + facility.getSeatType();
            } else {
                additionalInfo = "Projector: " + (facility.hasProjector() ? "Yes" : "No");
            }

            data.add(new FacilityTableData(
                    facility.getName(),
                    facility.getType(),
                    facility.getCapacity(),
                    additionalInfo,
                    currentBookings,
                    availability
            ));
        }

        table.setItems(data);

        // Set row height and font
        table.setStyle("-fx-font-size: 16px;");

        borderPane.setCenter(table);

        Scene scene = new Scene(borderPane);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showBookingDialog(Stage owner) {
        if (facilities.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No facilities available to book.");
            return;
        }

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Make Booking");
        dialog.setWidth(600);
        dialog.setHeight(400);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Create input fields
        Label facilityLabel = new Label("Select Facility:");
        facilityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        ComboBox<String> facilityComboBox = new ComboBox<>();
        ObservableList<String> facilityNames = FXCollections.observableArrayList(
                facilities.stream().map(StadiumFacility::getName).collect(Collectors.toList())
        );
        facilityComboBox.setItems(facilityNames);
        if (!facilityNames.isEmpty()) {
            facilityComboBox.setValue(facilityNames.get(0));
        }

        Label dateLabel = new Label("Date (yyyy-MM-dd):");
        dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        TextField dateField = new TextField();

        Label timeLabel = new Label("Time (HH:mm):");
        timeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        TextField timeField = new TextField();

        Label bookedUnitsLabel = new Label("Number of Booked Units:");
        bookedUnitsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        TextField bookedUnitsField = new TextField();

        Button bookButton = new Button("Book");
        bookButton.setStyle(
                "-fx-background-color: #01B040; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-min-width: 100px; " +
                        "-fx-min-height: 40px;"
        );

        bookButton.setOnAction(e -> {
            String facilityName = facilityComboBox.getValue();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();
            String bookedUnitsStr = bookedUnitsField.getText().trim();

            // Validate fields
            if (facilityName == null || facilityName.isEmpty() || date.isEmpty() || time.isEmpty() || bookedUnitsStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
                return;
            }

            // Validate date format
            if (!date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            // Validate that the date is not in the past
            try {
                LocalDate today = LocalDate.now();
                LocalDate enteredDate = LocalDate.parse(date);
                if (enteredDate.isBefore(today)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Cannot book for a past date. Please select a valid date.");
                    return;
                }
            } catch (DateTimeParseException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid date entered. Please use a valid yyyy-MM-dd format.");
                return;
            }

            // Validate time format
            if (!time.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid time format. Please use HH:mm.");
                return;
            }

            // Validate booked units
            int bookedUnits;
            try {
                bookedUnits = Integer.parseInt(bookedUnitsStr);
                if (bookedUnits <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid number of booked units.");
                return;
            }

            // Check availability
            int currentAvailability = getAvailability(facilityName);
            if (bookedUnits > currentAvailability) {
                showAlert(Alert.AlertType.ERROR, "Error", "Insufficient capacity. Available units: " + currentAvailability);
                return;
            }

            // Check for duplicate bookings
            boolean duplicateExists = bookings.stream()
                    .anyMatch(b -> b.getFacilityName().equals(facilityName) &&
                            b.getDate().equals(date) &&
                            b.getTime().equals(time));

            if (duplicateExists) {
                showAlert(Alert.AlertType.ERROR, "Error", "A booking already exists for this facility at the specified date and time.");
                return;
            }

            // Add booking
            bookings.add(new Booking(facilityName, date, time, bookedUnits));
            statusLabel.setText("Booking added successfully.");
            showAlert(Alert.AlertType.INFORMATION, "Success", "Booking made successfully!");
            dialog.close();
        });

        // Add components to grid
        grid.add(facilityLabel, 0, 0);
        grid.add(facilityComboBox, 1, 0);
        grid.add(dateLabel, 0, 1);
        grid.add(dateField, 1, 1);
        grid.add(timeLabel, 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(bookedUnitsLabel, 0, 3);
        grid.add(bookedUnitsField, 1, 3);
        grid.add(bookButton, 1, 4);

        Scene scene = new Scene(grid);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showBookingsTable(Stage owner) {
        if (bookings.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "No bookings available.");
            return;
        }

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("View Bookings");
        dialog.setWidth(800);
        dialog.setHeight(600);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        // Create table
        TableView<Booking> table = new TableView<>();

        // Define columns
        TableColumn<Booking, String> facilityCol = new TableColumn<>("Facility Name");
        facilityCol.setCellValueFactory(new PropertyValueFactory<>("facilityName"));
        facilityCol.setPrefWidth(200);

        TableColumn<Booking, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(150);

        TableColumn<Booking, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeCol.setPrefWidth(150);

        TableColumn<Booking, Integer> unitsCol = new TableColumn<>("Booked Units");
        unitsCol.setCellValueFactory(new PropertyValueFactory<>("bookedUnits"));
        unitsCol.setPrefWidth(150);

        table.getColumns().addAll(facilityCol, dateCol, timeCol, unitsCol);

        // Populate table data
        ObservableList<Booking> data = FXCollections.observableArrayList(bookings);
        table.setItems(data);

        // Set row height and font
        table.setStyle("-fx-font-size: 16px;");

        borderPane.setCenter(table);

        Scene scene = new Scene(borderPane);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showCancelBookingDialog(Stage owner) {
        if (bookings.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "No bookings available to cancel.");
            return;
        }

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Cancel Booking");
        dialog.setWidth(600);
        dialog.setHeight(400);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Create input fields
        Label facilityLabel = new Label("Select Facility:");
        facilityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Get distinct facility names from bookings
        List<String> distinctFacilities = bookings.stream()
                .map(Booking::getFacilityName)
                .distinct()
                .collect(Collectors.toList());

        ComboBox<String> facilityComboBox = new ComboBox<>(FXCollections.observableArrayList(distinctFacilities));
        if (!distinctFacilities.isEmpty()) {
            facilityComboBox.setValue(distinctFacilities.get(0));
        }

        Label dateLabel = new Label("Date (yyyy-MM-dd):");
        dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        TextField dateField = new TextField();

        Label timeLabel = new Label("Time (HH:mm):");
        timeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        TextField timeField = new TextField();

        Button cancelButton = new Button("Cancel Booking");
        cancelButton.setStyle(
                "-fx-background-color: #B70000; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-min-width: 150px; " +
                        "-fx-min-height: 40px;"
        );

        cancelButton.setOnAction(e -> {
            String facilityName = facilityComboBox.getValue();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();

            if (facilityName == null || facilityName.isEmpty() || date.isEmpty() || time.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
                return;
            }

            // Validate date format
            if (!date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            // Validate time format
            if (!time.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid time format. Please use HH:mm.");
                return;
            }

            // Find and remove matching booking
            boolean removed = bookings.removeIf(b ->
                    b.getFacilityName().equals(facilityName) &&
                            b.getDate().equals(date) &&
                            b.getTime().equals(time)
            );

            if (removed) {
                statusLabel.setText("Booking canceled successfully.");
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking canceled successfully!");
                dialog.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No matching booking found.");
            }
        });

        // Add components to grid
        grid.add(facilityLabel, 0, 0);
        grid.add(facilityComboBox, 1, 0);
        grid.add(dateLabel, 0, 1);
        grid.add(dateField, 1, 1);
        grid.add(timeLabel, 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(cancelButton, 1, 3);

        Scene scene = new Scene(grid);
        dialog.setScene(scene);
        dialog.show();
    }

    private int getCurrentBookings(String facilityName) {
        return bookings.stream()
                .filter(b -> b.getFacilityName().equals(facilityName))
                .mapToInt(Booking::getBookedUnits)
                .sum();
    }

    private int getAvailability(String facilityName) {
        StadiumFacility facility = facilities.stream()
                .filter(f -> f.getName().equals(facilityName))
                .findFirst()
                .orElse(null);

        if (facility == null) {
            return 0;
        }

        int totalCapacity = facility.getCapacity();
        int currentBookings = getCurrentBookings(facilityName);

        return totalCapacity - currentBookings;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        if (animationTimeline != null) {
            animationTimeline.stop();
        }
    }

    // Inner class for facility table data
    public static class FacilityTableData {
        private final String name;
        private final String type;
        private final int capacity;
        private final String additionalInfo;
        private final int currentBookings;
        private final int availability;

        public FacilityTableData(String name, String type, int capacity,
                                 String additionalInfo, int currentBookings, int availability) {
            this.name = name;
            this.type = type;
            this.capacity = capacity;
            this.additionalInfo = additionalInfo;
            this.currentBookings = currentBookings;
            this.availability = availability;
        }

        public String getName() { return name; }
        public String getType() { return type; }
        public int getCapacity() { return capacity; }
        public String getAdditionalInfo() { return additionalInfo; }
        public int getCurrentBookings() { return currentBookings; }
        public int getAvailability() { return availability; }
    }

    // Stadium Facility class
    public static class StadiumFacility {
        private final String name;
        private final int capacity;
        private final String type;
        private final String seatType;
        private final boolean hasProjector;

        public StadiumFacility(String name, int capacity, String type, String seatType, boolean hasProjector) {
            this.name = name;
            this.capacity = capacity;
            this.type = type;
            this.seatType = seatType;
            this.hasProjector = hasProjector;
        }

        public String getName() { return name; }
        public int getCapacity() { return capacity; }
        public String getType() { return type; }
        public String getSeatType() { return seatType; }
        public boolean hasProjector() { return hasProjector; }
    }

    // Booking class
    public static class Booking {
        private final String facilityName;
        private final String date;
        private final String time;
        private final int bookedUnits;

        public Booking(String facilityName, String date, String time, int bookedUnits) {
            this.facilityName = facilityName;
            this.date = date;
            this.time = time;
            this.bookedUnits = bookedUnits;
        }

        public String getFacilityName() { return facilityName; }
        public String getDate() { return date; }
        public String getTime() { return time; }
        public int getBookedUnits() { return bookedUnits; }
    }

    // AnimationPane class for animated background
    private static class AnimationPane extends Pane {
        private final Canvas canvas;
        private final GraphicsContext gc;
        private final List<Ball> balls = new ArrayList<>();
        private final Random random = new Random();

        public AnimationPane() {
            canvas = new Canvas(800, 600);
            gc = canvas.getGraphicsContext2D();

            // Create background
            Stop[] stops = new Stop[]{new Stop(0, Color.rgb(240, 248, 255)), new Stop(1, Color.rgb(176, 224, 230))};
            LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
            setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

            // Create balls
            for (int i = 0; i < 20; i++) {
                balls.add(new Ball(
                        random.nextInt(500),
                        random.nextInt(400),
                        random.nextInt(5) + 1,
                        random.nextInt(5) + 1,
                        10 + random.nextInt(20),
                        Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255), 0.7)
                ));
            }

            widthProperty().addListener((obs, oldVal, newVal) -> {
                canvas.setWidth(newVal.doubleValue());
            });

            heightProperty().addListener((obs, oldVal, newVal) -> {
                canvas.setHeight(newVal.doubleValue());
            });

            getChildren().add(canvas);
        }

        public void updateAnimation() {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            for (Ball ball : balls) {
                ball.update(canvas.getWidth(), canvas.getHeight());
                ball.draw(gc);
            }
        }

        private static class Ball {
            private double x;
            private double y;
            private double dx;
            private double dy;
            private final double radius;
            private final Color color;

            public Ball(double x, double y, double dx, double dy, double radius, Color color) {
                this.x = x;
                this.y = y;
                this.dx = dx;
                this.dy = dy;
                this.radius = radius;
                this.color = color;
            }

            public void update(double width, double height) {
                x += dx;
                y += dy;

                if (x < radius || x > width - radius) {
                    dx = -dx;
                    x = Math.max(radius, Math.min(x, width - radius));
                }

                if (y < radius || y > height - radius) {
                    dy = -dy;
                    y = Math.max(radius, Math.min(y, height - radius));
                }
            }

            public void draw(GraphicsContext gc) {
                gc.setFill(color);
                gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            }
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}