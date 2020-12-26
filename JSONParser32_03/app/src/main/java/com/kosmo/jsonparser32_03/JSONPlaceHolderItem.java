package com.kosmo.jsonparser32_03;

public class JSONPlaceHolderItem {
    private String id;
    private String title;
    private String thumbnailUrl;

    public JSONPlaceHolderItem(String id, String title, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
