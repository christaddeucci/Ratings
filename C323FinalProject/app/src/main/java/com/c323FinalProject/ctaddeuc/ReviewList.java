package com.c323FinalProject.ctaddeuc;

public class ReviewList {
    String categoryId;
    String reviewListName;
    String reviewListImage;

    public ReviewList(String categoryId, String reviewListName, String reviewListImage){
        this.categoryId = categoryId;
        this.reviewListImage = reviewListImage;
        this.reviewListName = reviewListName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getReviewListName() {
        return reviewListName;
    }

    public void setReviewListName(String reviewListName) {
        this.reviewListName = reviewListName;
    }

    public String getReviewListImage() {
        return reviewListImage;
    }

    public void setReviewListImage(String reviewListImage) {
        this.reviewListImage = reviewListImage;
    }
}
