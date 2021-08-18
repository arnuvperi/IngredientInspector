package com.example.ingredientinspector;

public class Ingredient {

    //Fields
    private String name;
    private Label label;

    //Constructor
    public Ingredient(String name) {
        this.name = name;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }



}
