package com.c323FinalProject.ctaddeuc;

public class Favorite {
    String reviewId;
    String reviewListId;
    String reviewName;
    String reviewImage;

    public Favorite(String reviewId, String reviewListId, String reviewName, String reviewImage) {
        this.reviewId = reviewId;
        this.reviewListId = reviewListId;
        this.reviewName = reviewName;
        this.reviewImage = reviewImage;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewListId() {
        return reviewListId;
    }

    public void setReviewListId(String reviewListId) {
        this.reviewListId = reviewListId;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }
}
