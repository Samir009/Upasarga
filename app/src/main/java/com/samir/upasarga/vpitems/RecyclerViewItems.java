package com.samir.upasarga.vpitems;

public class RecyclerViewItems {
    String title;
    String description;
    String seen_unseen;
    String date;

    public RecyclerViewItems(String title, String description, String seen_unseen, String date) {
        this.title = title;
        this.description = description;
        this.seen_unseen = seen_unseen;
        this.date = date;
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

    public String getSeen_unseen() {
        return seen_unseen;
    }

    public void setSeen_unseen(String seen_unseen) {
        this.seen_unseen = seen_unseen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
