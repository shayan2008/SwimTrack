package com.example.gui_optionalassignment1_doroudianishayan.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.UUID;

public class Swimmer {
    private final StringProperty id = new SimpleStringProperty(UUID.randomUUID().toString());
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty team = new SimpleStringProperty();
    private final StringProperty gender = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dob = new SimpleObjectProperty<>();

    public Swimmer(String name, String team, String gender, LocalDate dob) {
        this.name.set(name);
        this.team.set(team);
        this.gender.set(gender);
        this.dob.set(dob);
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public void setName(String val) { name.set(val); }
    public StringProperty nameProperty() { return name; }

    public String getTeam() { return team.get(); }
    public void setTeam(String val) { team.set(val); }
    public StringProperty teamProperty() { return team; }

    public String getGender() { return gender.get(); }
    public void setGender(String val) { gender.set(val); }
    public StringProperty genderProperty() { return gender; }

    public LocalDate getDob() { return dob.get(); }
    public void setDob(LocalDate val) { dob.set(val); }
    public ObjectProperty<LocalDate> dobProperty() { return dob; }

    @Override public String toString() { return getName(); }
}
