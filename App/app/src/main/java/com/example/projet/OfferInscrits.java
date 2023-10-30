package com.example.projet;

import java.io.Serializable;

public class OfferInscrits implements Serializable {
    private String id; // new property for the ID
    private String title;
    private String description;
    private String name;
    private String salary; // new property for the salary
    private String period; // new property for the period
    private String job; // new property for the job
    private String category; // new property for the category

    public OfferInscrits() {
        // empty constructor needed for Firebase
    }

    public OfferInscrits(String id, String title, String description, String name, String salary, String period, String job, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.name = name;
        this.salary = salary;
        this.period = period;
        this.job = job;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
