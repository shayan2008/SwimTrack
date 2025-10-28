package com.example.gui_optionalassignment1_doroudianishayan.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class SwimResult {
    private final StringProperty swimmerId = new SimpleStringProperty();
    private final ObjectProperty<SwimEvent> event = new SimpleObjectProperty<>();
    private final DoubleProperty timeSeconds = new SimpleDoubleProperty(); // canonical numeric
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final StringProperty meet = new SimpleStringProperty();

    public SwimResult(String swimmerId, SwimEvent event, double timeSeconds, LocalDate date, String meet) {
        this.swimmerId.set(swimmerId);
        this.event.set(event);
        this.timeSeconds.set(timeSeconds);
        this.date.set(date);
        this.meet.set(meet);
    }

    public String getSwimmerId() { return swimmerId.get(); }
    public StringProperty swimmerIdProperty() { return swimmerId; }

    public SwimEvent getEvent() { return event.get(); }
    public ObjectProperty<SwimEvent> eventProperty() { return event; }

    public double getTimeSeconds() { return timeSeconds.get(); }
    public void setTimeSeconds(double v) { timeSeconds.set(v); }
    public DoubleProperty timeSecondsProperty() { return timeSeconds; }

    public LocalDate getDate() { return date.get(); }
    public ObjectProperty<LocalDate> dateProperty() { return date; }

    public String getMeet() { return meet.get(); }
    public StringProperty meetProperty() { return meet; }
}
