package com.kosmo.jsonparser32_04;

public class ShoppingItem {
    private String title;
    private String image;
    private String hprice;
    private String lprice;
    private String maker;

    public ShoppingItem(String title, String image, String hprice, String lprice, String maker) {
        this.title = title;
        this.image = image;
        this.hprice = hprice;
        this.lprice = lprice;
        this.maker = maker;
    }

    public String getTitle() {
        return title;
    }
    public String getImage() {
        return image;
    }
    public String getHprice() {
        return hprice;
    }
    public String getLprice() {
        return lprice;
    }
    public String getMaker() {
        return maker;
    }



}
