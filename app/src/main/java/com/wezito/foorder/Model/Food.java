package com.wezito.foorder.Model;

public class Food {
    private String category;
    private String image;
    private String name;
    private String description;
    private String fid;
    private int price;
    private int quantity;
    private int status;

    public Food() {
    }

    public Food(String category, String image, String name, String description, String fid, int price, int quantity, int status) {
        this.category = category;
        this.image = image;
        this.name = name;
        this.description = description;
        this.fid = fid;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
