package com.example.medicinereminderapplication;

import java.util.List;

public class Medicine {
    private int id;
    private String name;
    private String dosage;
    private String startDate;
    private String endDate;
    private String notes;
    private String username;
    private List<String> days;
    private List<String> times;

    //Constructor for existing medicine with id
    public Medicine(int id, String name, String dosage, String startDate, String endDate, List<String> days, List<String> times, String notes, String username) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.times = times;
        this.notes = notes;
        this.username = username;
    }

    //Contructor for new medicine (before inserting and id is not assigned)
    public Medicine(String name, String dosage, String startDate, String endDate, List<String> days, List<String> times, String notes, String username) {
        this.name = name;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.times = times;
        this.notes = notes;
        this.username = username;
    }

    //Getters
    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getNotes() {
        return notes;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getDays() {
        return days;
    }

    public List<String> getTimes() {
        return times;
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}