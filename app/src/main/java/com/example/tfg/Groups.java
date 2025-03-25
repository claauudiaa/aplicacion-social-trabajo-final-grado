package com.example.tfg;

public class Groups {
    private String zona;
    private String fecha;
    private String horario;
    private String name_activity;

    public Groups(String zona, String fecha, String horario, String name_activity) {
        this.zona = zona;
        this.fecha = fecha;
        this.horario = horario;
        this.name_activity = name_activity;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getName_activity() {
        return name_activity;
    }

    public void setName_activity(String name_activity) {
        this.name_activity = name_activity;
    }
}
