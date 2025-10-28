package com.example.gui_optionalassignment1_doroudianishayan;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainController {

    // ---- Navigation / pages
    @FXML private StackPane contentStack;
    @FXML private AnchorPane dashboardView;
    @FXML private AnchorPane swimmersView;
    @FXML private AnchorPane analyticsView;

    // ---- Top bar actions
    @FXML private TextField globalSearchField;
    @FXML private ComboBox<String> themePicker; // theme switcher

    // ---- Left filters
    @FXML private ComboBox<com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent> eventCombo;
    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;

    // ---- Swimmers page
    @FXML private TableView<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer> swimmersTable;
    @FXML private TableColumn<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer, String> colName;
    @FXML private TableColumn<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer, String> colTeam;
    @FXML private TableColumn<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer, String> colGender;
    @FXML private TableColumn<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer, String> colDob;
    @FXML private Pagination resultsPagination;

    @FXML private TableView<com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult> resultsTable;
    @FXML private TableColumn<com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult, String> colEvent;
    @FXML private TableColumn<com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult, String> colTime;
    @FXML private TableColumn<com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult, String> colDate;
    @FXML private TableColumn<com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult, String> colMeet;

    // ---- Analytics page
    @FXML private BarChart<String, Number> topBarChart;
    @FXML private LineChart<String, Number> trendLineChart;
    @FXML private PieChart eventPieChart;
    @FXML private ScatterChart<Number, Number> scatterChart;

    @FXML private TreeTableView<String> treeTable;
    @FXML private TreeTableColumn<String, String> treeColGroup;
    @FXML private TreeTableColumn<String, String> treeColValue;

    // ---- Data store
    private final com.example.gui_optionalassignment1_doroudianishayan.store.DataStore store =
            new com.example.gui_optionalassignment1_doroudianishayan.store.DataStore();

    // Events
    private final ObservableList<com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent> events =
            FXCollections.observableArrayList(
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(50,  com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.FREESTYLE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(100, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.FREESTYLE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(200, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.FREESTYLE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(400, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.FREESTYLE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(800, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.FREESTYLE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(1500, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.FREESTYLE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(50,  com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BACKSTROKE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(100, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BACKSTROKE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(200, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BACKSTROKE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(50,  com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BREASTSTROKE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(100, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BREASTSTROKE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(200, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BREASTSTROKE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(50,  com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BUTTERFLY),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(100, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BUTTERFLY),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(200, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BUTTERFLY),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(200, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.IM),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(400, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.IM)
            );

    private final FilteredList<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer> filteredSwimmers =
            new FilteredList<>(store.getSwimmers(), s -> true);
    private final FilteredList<com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult> filteredResults =
            new FilteredList<>(store.getResults(), r -> true);

    private static final int PAGE_SIZE = 12;

    @FXML
    public void initialize() {
        seed();

        // Theme picker (add High-Contrast)
        themePicker.setItems(FXCollections.observableArrayList("Ocean HC", "Ocean", "Sunset", "Mono"));
        Platform.runLater(() -> { themePicker.getSelectionModel().select("Ocean HC"); applyTheme("Ocean HC"); });

        themePicker.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> applyTheme(nv));

        // Filters + event picker
        eventCombo.setItems(events);
        eventCombo.getSelectionModel().selectFirst();
        Platform.runLater(() -> updateAccentForStroke(eventCombo.getValue().getStroke()));
        eventCombo.valueProperty().addListener((obs, o, v) -> {
            updateResultsFilter(); updatePagination(); updateAnalytics();
            if (v != null) updateAccentForStroke(v.getStroke());
        });

        // ---- Swimmers table (lambda cell value factories)
        colName.setCellValueFactory(cd -> new SimpleStringProperty(
                Optional.ofNullable(cd.getValue()).map(com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer::getName).orElse("")));
        colTeam.setCellValueFactory(cd -> new SimpleStringProperty(
                Optional.ofNullable(cd.getValue().getTeam()).orElse("")));
        colGender.setCellValueFactory(cd -> new SimpleStringProperty(
                Optional.ofNullable(cd.getValue().getGender()).orElse("")));
        colDob.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getDob() == null ? "" : cd.getValue().getDob().toString()));

        // Name as hyperlink (accent)
        colName.setCellFactory(col -> new TableCell<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer, String>() {
            @Override protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setGraphic(null); return; }
                Hyperlink link = new Hyperlink(item);
                link.getStyleClass().add("name-link");
                link.setOnAction(ev -> {
                    var s = getTableView().getItems().get(getIndex());
                    swimmersTable.getSelectionModel().select(s);
                    goAnalytics();
                });
                setText(null); setGraphic(link);
            }
        });

        SortedList<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer> sortedSwimmers =
                new SortedList<>(filteredSwimmers,
                        Comparator.comparing(
                                com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer::getName,
                                String.CASE_INSENSITIVE_ORDER));
        swimmersTable.setItems(sortedSwimmers);

        // Context menu
        swimmersTable.setRowFactory(tv -> {
            TableRow<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer> row = new TableRow<>();
            ContextMenu ctx = new ContextMenu();
            MenuItem addRes = new MenuItem("Add Result");
            addRes.setOnAction(e -> { var s = row.getItem(); if (s != null) showAddResultDialog(s); });
            MenuItem del = new MenuItem("Delete Swimmer");
            del.setOnAction(e -> { var s = row.getItem(); if (s != null) store.removeSwimmer(s); refreshAll(); });
            ctx.getItems().addAll(addRes, del);
            row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu)null).otherwise(ctx));
            row.setOnMouseClicked(me -> {
                if (me.getButton() == MouseButton.PRIMARY && me.getClickCount() == 2 && !row.isEmpty()) showAddResultDialog(row.getItem());
            });
            return row;
        });

        // ---- Results table
        colEvent.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getEvent().toString()));
        colTime.setCellValueFactory(cd -> new SimpleStringProperty(
                com.example.gui_optionalassignment1_doroudianishayan.util.TimeUtil.formatMins(cd.getValue().getTimeSeconds())));
        colDate.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getDate() == null ? "" : cd.getValue().getDate().toString()));
        colMeet.setCellValueFactory(cd -> new SimpleStringProperty(
                Optional.ofNullable(cd.getValue().getMeet()).orElse("")));

        // PB highlight
        colTime.setCellFactory(col -> new TableCell<com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult, String>() {
            @Override protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().remove("pb-cell");
                if (empty || item == null) { setText(null); setTooltip(null); return; }
                setText(item);
                var r = getTableView().getItems().get(getIndex());
                double best = store.getResults().stream()
                        .filter(x -> x.getSwimmerId().equals(r.getSwimmerId()) && x.getEvent().equals(r.getEvent()))
                        .mapToDouble(com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult::getTimeSeconds)
                        .min().orElse(Double.NaN);
                if (!Double.isNaN(best) && Math.abs(best - r.getTimeSeconds()) < 1e-6) {
                    getStyleClass().add("pb-cell"); setTooltip(new Tooltip("Personal Best"));
                } else setTooltip(null);
            }
        });

        // Link selection to results/analytics
        swimmersTable.getSelectionModel().selectedItemProperty().addListener((obs, o, sel) -> {
            updateResultsFilter(); updatePagination(); updateAnalytics();
        });

        // Filters
        globalSearchField.textProperty().addListener((obs, o, t) -> { updateSwimmerFilter(); updateResultsFilter(); updatePagination(); updateAnalytics(); });
        fromDate.valueProperty().addListener((obs, o, v) -> { updateResultsFilter(); updatePagination(); updateAnalytics(); });
        toDate.valueProperty().addListener((obs, o, v) -> { updateResultsFilter(); updatePagination(); updateAnalytics(); });

        // Pagination
        resultsPagination.setPageFactory(this::createResultsPage);

        // TreeTable
        treeColGroup.setCellValueFactory(p -> {
            String raw = p.getValue().getValue();
            int ix = raw.indexOf('|');
            return new SimpleStringProperty(ix >= 0 ? raw.substring(0, ix) : raw);
        });
        treeColValue.setCellValueFactory(p -> {
            String raw = p.getValue().getValue();
            int ix = raw.indexOf('|');
            String right = (ix >= 0 && p.getValue().getChildren().isEmpty()) ? raw.substring(ix + 1) : "";
            return new SimpleStringProperty(right);
        });

        // Default page
        switchTo(dashboardView);
        updatePagination();
        updateAnalytics();
    }

    // Theme handling
    private void applyTheme(String theme) {
        if (contentStack.getScene() == null) return;
        Node root = contentStack.getScene().getRoot();
        var classes = root.getStyleClass();
        classes.removeAll("theme-ocean-hc","theme-ocean","theme-sunset","theme-mono");
        if (theme == null) theme = "Ocean HC";
        switch (theme.toLowerCase()) {
            case "ocean hc" -> classes.add("theme-ocean-hc");
            case "ocean" -> classes.add("theme-ocean");
            case "sunset" -> classes.add("theme-sunset");
            case "mono" -> classes.add("theme-mono");
            default -> classes.add("theme-ocean-hc");
        }
        var ev = eventCombo.getValue();
        if (ev != null) updateAccentForStroke(ev.getStroke());
    }

    private void updateAccentForStroke(com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke stroke) {
        if (contentStack.getScene() == null || stroke == null) return;
        Node root = contentStack.getScene().getRoot();
        String a1, a2;
        switch (stroke) {
            case FREESTYLE -> { a1 = "#64b5ff"; a2 = "#b3d8ff"; }     // brighter blues
            case BACKSTROKE -> { a1 = "#8c9eff"; a2 = "#c5cae9"; }
            case BREASTSTROKE -> { a1 = "#66d17a"; a2 = "#b6f2c1"; }
            case BUTTERFLY -> { a1 = "#ffa658"; a2 = "#ffd1a6"; }
            case IM -> { a1 = "#c084fc"; a2 = "#e0b7ff"; }
            default -> { a1 = "#64b5ff"; a2 = "#b3d8ff"; }
        }
        root.setStyle(String.format("-app-accent: %s; -app-accent-2: %s;", a1, a2));
    }

    // ---- NAVIGATION ----
    @FXML private void goDashboard() { switchTo(dashboardView); }
    @FXML private void goSwimmers()  { switchTo(swimmersView); }
    @FXML private void goAnalytics() { switchTo(analyticsView); }

    private void switchTo(Node pane) {
        for (Node child : contentStack.getChildren()) child.setVisible(false);
        pane.setVisible(true);
        pane.toFront();
    }

    // ---- ACTIONS ----
    @FXML
    private void addSwimmer(ActionEvent e) {
        TextInputDialog d = new TextInputDialog();
        d.setHeaderText("Add Swimmer");
        d.setContentText("Name (e.g., Shayan Doroudiani):");
        d.showAndWait().ifPresent(name -> {
            if (name.trim().isEmpty()) return;
            var s = new com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer(name.trim(), "NYAC", "M", LocalDate.of(2009,1,1));
            store.addSwimmer(s);
            swimmersTable.getSelectionModel().select(s);
            refreshAll();
        });
    }

    @FXML
    private void importCsv(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Import Results CSV");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File f = fc.showOpenDialog(contentStack.getScene().getWindow());
        if (f == null) return;
        try {
            java.util.List<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer> created = new ArrayList<>();
            store.importResultsCsv(f, created, events);
            info("Import complete", "Imported " + store.getResults().size() + " results.\nNew swimmers: " + created.size());
            refreshAll();
        } catch (Exception ex) {
            error("Import failed", ex.getMessage());
        }
    }

    @FXML
    private void exportCsv(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Export Results CSV");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        fc.setInitialFileName("swim_results.csv");
        File f = fc.showSaveDialog(contentStack.getScene().getWindow());
        if (f == null) return;
        try {
            store.exportResultsCsv(f);
            info("Exported", "Saved to " + f.getAbsolutePath());
        } catch (Exception ex) {
            error("Export failed", ex.getMessage());
        }
    }

    // ---- FILTERS ----
    private void updateSwimmerFilter() {
        String q = globalSearchField.getText() == null ? "" : globalSearchField.getText().trim().toLowerCase();
        filteredSwimmers.setPredicate(s -> q.isEmpty()
                || s.getName().toLowerCase().contains(q)
                || (s.getTeam() != null && s.getTeam().toLowerCase().contains(q)));
    }

    private void updateResultsFilter() {
        var sel = swimmersTable.getSelectionModel().getSelectedItem();
        var ev = eventCombo.getValue();
        LocalDate from = fromDate.getValue();
        LocalDate to = toDate.getValue();
        String q = globalSearchField.getText() == null ? "" : globalSearchField.getText().trim().toLowerCase();

        Predicate<com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult> p =
                r -> (sel == null || r.getSwimmerId().equals(sel.getId()));
        if (ev != null) p = p.and(r -> r.getEvent().equals(ev));
        if (from != null) p = p.and(r -> r.getDate() == null || !r.getDate().isBefore(from));
        if (to != null) p = p.and(r -> r.getDate() == null || !r.getDate().isAfter(to));
        if (!q.isEmpty()) {
            p = p.and(r -> {
                Optional<com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer> sw =
                        store.getSwimmers().stream().filter(s -> s.getId().equals(r.getSwimmerId())).findFirst();
                String name = sw.map(com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer::getName).orElse("");
                String meet = r.getMeet() == null ? "" : r.getMeet();
                return name.toLowerCase().contains(q) || meet.toLowerCase().contains(q);
            });
        }
        filteredResults.setPredicate(p);
    }

    // ---- PAGINATION ----
    private Node createResultsPage(Integer pageIndex) {
        int from = pageIndex * PAGE_SIZE;
        int to = Math.min(from + PAGE_SIZE, filteredResults.size());
        if (from > to) {
            resultsTable.setItems(FXCollections.observableArrayList());
        } else {
            resultsTable.setItems(FXCollections.observableArrayList(filteredResults.subList(from, to)));
        }
        return resultsTable;
    }

    private void updatePagination() {
        int pages = (int)Math.ceil(Math.max(1, filteredResults.size()) / (double) PAGE_SIZE);
        resultsPagination.setPageCount(Math.max(1, pages));
        resultsPagination.setCurrentPageIndex(0);
        createResultsPage(0);
    }

    // ---- ANALYTICS / CHARTS ----
    private void updateAnalytics() {
        updateTopBarChart();
        updateTrendLineChart();
        updateEventPie();
        updateScatter();
        updateTreeTable();
    }

    private void updateTopBarChart() {
        topBarChart.getData().clear();
        var ev = eventCombo.getValue();
        if (ev == null) return;

        var list = store.topByEvent(ev, 5);
        XYChart.Series<String, Number> s = new XYChart.Series<>();
        s.setName(ev.toString() + " — Top 5 PB");
        list.forEach(e -> s.getData().add(new XYChart.Data<>(e.getKey().getName(), e.getValue())));
        topBarChart.getData().add(s);

        for (XYChart.Data<String, Number> d : s.getData()) {
            String text = d.getXValue() + ": " +
                    com.example.gui_optionalassignment1_doroudianishayan.util.TimeUtil.formatMins(d.getYValue().doubleValue());
            d.nodeProperty().addListener((ob, ov, nv) -> {
                if (nv != null) Tooltip.install(nv, new Tooltip(text));
            });
        }
    }

    private void updateTrendLineChart() {
        trendLineChart.getData().clear();
        var sel = swimmersTable.getSelectionModel().getSelectedItem();
        var ev = eventCombo.getValue();
        if (sel == null || ev == null) return;

        var chron = store.getResults().stream()
                .filter(r -> r.getSwimmerId().equals(sel.getId()) && r.getEvent().equals(ev))
                .sorted(Comparator.comparing(r -> r.getDate() == null ? LocalDate.MIN : r.getDate()))
                .collect(Collectors.toList());

        XYChart.Series<String, Number> s = new XYChart.Series<>();
        s.setName(sel.getName() + " — " + ev);
        for (var r : chron) {
            String x = r.getDate() == null ? "?" : r.getDate().toString();
            s.getData().add(new XYChart.Data<>(x, r.getTimeSeconds()));
        }
        trendLineChart.getData().add(s);
    }

    private void updateEventPie() {
        eventPieChart.getData().clear();
        Map<String, Long> counts = filteredResults.stream()
                .collect(Collectors.groupingBy(r -> r.getEvent().toString(), Collectors.counting()));
        counts.forEach((k,v) -> eventPieChart.getData().add(new PieChart.Data(k, v)));
    }

    private void updateScatter() {
        scatterChart.getData().clear();
        var sel = swimmersTable.getSelectionModel().getSelectedItem();
        var ev = eventCombo.getValue();
        if (sel == null || ev == null) return;
        var seq = store.getResults().stream()
                .filter(r -> r.getSwimmerId().equals(sel.getId()) && r.getEvent().equals(ev))
                .sorted(Comparator.comparing(r -> r.getDate() == null ? LocalDate.MIN : r.getDate()))
                .toList();

        XYChart.Series<Number, Number> s = new XYChart.Series<>();
        s.setName("Improvement path (index vs time)");
        int i = 1;
        for (var r : seq) s.getData().add(new XYChart.Data<>(i++, r.getTimeSeconds()));
        scatterChart.getData().add(s);
    }

    private void updateTreeTable() {
        TreeItem<String> root = new TreeItem<>("PB by Stroke");
        root.setExpanded(true);

        Map<com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke,
                java.util.List<com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent>> groups =
                events.stream().collect(Collectors.groupingBy(
                        com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent::getStroke));

        var sel = swimmersTable.getSelectionModel().getSelectedItem();
        for (var entry : groups.entrySet()) {
            TreeItem<String> strokeNode = new TreeItem<>(entry.getKey().name());
            for (var ev : entry.getValue().stream()
                    .sorted(Comparator.comparingInt(com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent::getDistance))
                    .toList()) {
                double best = store.getResults().stream()
                        .filter(r -> (sel == null || r.getSwimmerId().equals(sel.getId())) && r.getEvent().equals(ev))
                        .mapToDouble(com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult::getTimeSeconds)
                        .min().orElse(Double.NaN);
                String label = ev + "|" + (Double.isNaN(best)
                        ? "—"
                        : com.example.gui_optionalassignment1_doroudianishayan.util.TimeUtil.formatMins(best));
                strokeNode.getChildren().add(new TreeItem<>(label));
            }
            root.getChildren().add(strokeNode);
        }
        treeTable.setRoot(root);
        treeTable.setShowRoot(true);
    }

    // ---- Dialog for adding a result
    private void showAddResultDialog(com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer s) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Result for " + s.getName());
        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        ComboBox<com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent> ev = new ComboBox<>(events);
        ev.getSelectionModel().selectFirst();
        DatePicker dp = new DatePicker(LocalDate.now());
        TextField meet = new TextField();
        meet.setPromptText("Meet name");
        TextField time = new TextField();
        time.setPromptText("M:SS.ss or SS.ss");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.addRow(0, new Label("Event:"), ev);
        grid.addRow(1, new Label("Date:"), dp);
        grid.addRow(2, new Label("Meet:"), meet);
        grid.addRow(3, new Label("Time:"), time);
        pane.setContent(grid);

        dialog.setResultConverter(bt -> bt);
        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    double secs = com.example.gui_optionalassignment1_doroudianishayan.util.TimeUtil.parseTimeFlexible(time.getText());
                    store.addResult(new com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult(
                            s.getId(), ev.getValue(), secs, dp.getValue(), meet.getText()
                    ));
                    refreshAll();
                } catch (Exception ex) {
                    error("Invalid time", "Enter SS.ss or M:SS.ss (e.g., 26.75 or 1:02.33).");
                }
            }
        });
    }

    private void refreshAll() {
        swimmersTable.refresh();
        updateSwimmerFilter();
        updateResultsFilter();
        updatePagination();
        updateAnalytics();
    }

    // ---- Seed demo data
    private void seed() {
        var a = new com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer("Shayan Doroudiani", "NYAC", "M", LocalDate.of(2009,8,1));
        var b = new com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer("A. Smith", "NYAC", "M", LocalDate.of(2009,2,10));
        var c = new com.example.gui_optionalassignment1_doroudianishayan.model.Swimmer("J. Lee", "NYAC", "F", LocalDate.of(2010,5,12));
        store.addSwimmer(a); store.addSwimmer(b); store.addSwimmer(c);

        var rnd = new Random(42);
        for (var s : java.util.List.of(a,b,c)) {
            for (var ev : java.util.List.of(
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(50,  com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.FREESTYLE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(100, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.FREESTYLE),
                    new com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent(200, com.example.gui_optionalassignment1_doroudianishayan.model.SwimEvent.Stroke.BACKSTROKE))) {
                LocalDate d = LocalDate.of(2025,1,1);
                double base = switch (ev.getDistance()) {
                    case 50 -> 28 + rnd.nextDouble()*5;
                    case 100 -> 60 + rnd.nextDouble()*15;
                    case 200 -> 130 + rnd.nextDouble()*20;
                    default -> 90;
                };
                for (int i=0;i<6;i++) {
                    store.addResult(new com.example.gui_optionalassignment1_doroudianishayan.model.SwimResult(
                            s.getId(), ev, base - i*rnd.nextDouble(), d.plusWeeks(i*3L), "Season " + (i+1)
                    ));
                }
            }
        }
    }

    // Alerts
    private void error(String header, String content) { Alert a = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK); a.setHeaderText(header); a.showAndWait(); }
    private void info (String header, String content) { Alert a = new Alert(Alert.AlertType.INFORMATION, content, ButtonType.OK); a.setHeaderText(header); a.showAndWait(); }
}
