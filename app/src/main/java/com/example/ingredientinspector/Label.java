package com.example.ingredientinspector;

public class Label {

    //Fields

    private String name;
    private String color;

    //Constructor
    public Label(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Label(){};

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}


