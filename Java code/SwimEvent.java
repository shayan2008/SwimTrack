package com.example.gui_optionalassignment1_doroudianishayan.model;

public class SwimEvent {
    public enum Stroke { FREESTYLE, BACKSTROKE, BREASTSTROKE, BUTTERFLY, IM }

    private final int distance; // meters
    private final Stroke stroke;

    public SwimEvent(int distance, Stroke stroke) {
        this.distance = distance;
        this.stroke = stroke;
    }

    public int getDistance() { return distance; }
    public Stroke getStroke() { return stroke; }

    @Override
    public String toString() {
        return distance + " " + switch (stroke) {
            case FREESTYLE -> "Free";
            case BACKSTROKE -> "Back";
            case BREASTSTROKE -> "Breast";
            case BUTTERFLY -> "Fly";
            case IM -> "IM";
        };
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof SwimEvent)) return false;
        SwimEvent e = (SwimEvent) o;
        return e.distance == distance && e.stroke == stroke;
    }
    @Override public int hashCode() { return distance * 31 + stroke.ordinal(); }
}
