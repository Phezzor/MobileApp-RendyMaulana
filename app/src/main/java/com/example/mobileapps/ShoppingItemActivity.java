package com.example.mobileapps;

public class ShoppingItemActivity {
    private int id;
    private String name;
    private boolean isBought;

    public ShoppingItemActivity(int id, String name, boolean isBought) {
        this.id = id;
        this.name = name;
        this.isBought = isBought;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public boolean isBought() { return isBought; }

    public void setBought(boolean bought) { isBought = bought; }
}
