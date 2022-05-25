package com.c323FinalProject.ctaddeuc;

public class Review {
    String reviewListId;
    String categoryId;
    String reviewDetails;
    String reviewImage;

    public Review(String reviewListId, String categoryId, String reviewDetails, String reviewImage) {
        this.reviewListId = reviewListId;
        this.categoryId = categoryId;
        this.reviewDetails = reviewDetails;
        this.reviewImage = reviewImage;
    }

    public String getReviewListId() {
        return reviewListId;
    }

    public void setReviewListId(String reviewListId) {
        this.reviewListId = reviewListId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getReviewDetails() {
        return reviewDetails;
    }

    public void setReviewDetails(String reviewDetails) {
        this.reviewDetails = reviewDetails;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }
}
