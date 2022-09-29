package com.sheikh.exe_apk.Model;

public class ListItem {
    private String title;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private String description;
    private String timeAgo;
    private String link;

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

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    private String image_link;

    public ListItem(String image_link, String title, String description, String timeAgo, String link) {
        this.title = title;
        this.description = description;
        this.timeAgo = timeAgo;
        this.image_link = image_link;
        this.link = link;
    }
}
