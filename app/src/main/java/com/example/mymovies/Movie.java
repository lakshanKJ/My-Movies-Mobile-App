package com.example.mymovies;

public class Movie {

    private int mId;
    private String mTitle;
    private int mYear;
    private String mDirector;
    private String mActors;
    private int mRating;
    private String mReview;
    private Boolean isFavourite;

    public Movie() {
    }

    public Movie(String mTitle) {
        this.mTitle = mTitle;
    }

    public Movie(String mTitle, int mYear, String mDirector, String mActors, int mRating, String mReview, Boolean isFavourite) {

        this.mTitle = mTitle;
        this.mYear = mYear;
        this.mDirector = mDirector;
        this.mActors = mActors;
        this.mRating = mRating;
        this.mReview = mReview;
        this.isFavourite = isFavourite;
    }

    public Movie(int mId, String mTitle, int mYear, String mDirector, String mActors, int mRating, String mReview, Boolean isFavourite) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mYear = mYear;
        this.mDirector = mDirector;
        this.mActors = mActors;
        this.mRating = mRating;
        this.mReview = mReview;
        this.isFavourite = isFavourite;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public String getmDirector() {
        return mDirector;
    }

    public void setmDirector(String mDirector) {
        this.mDirector = mDirector;
    }

    public String getmActors() {
        return mActors;
    }

    public void setmActors(String mActors) {
        this.mActors = mActors;
    }

    public int getmRating() {
        return mRating;
    }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }

    public String getmReview() {
        return mReview;
    }

    public void setmReview(String mReview) {
        this.mReview = mReview;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mYear=" + mYear +
                ", mDirector='" + mDirector + '\'' +
                ", mActors='" + mActors + '\'' +
                ", mRating=" + mRating +
                ", mReview='" + mReview + '\'' +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
