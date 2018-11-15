package com.takmer.fileOps.model;

public class Movie {
  String title;
  double rating;
  boolean starred;

  // constructor
  public Movie() {
  }

  // constructor
  public Movie(String movieTitle) {
    title = movieTitle;
  }

  // constructor
  public Movie(String MovieTitle, double Rating) {
    title = MovieTitle;
    rating = Rating;
  }

  // constructor
  public Movie(String MovieTitle, double Rating, boolean Starred) {
    title = MovieTitle;
    rating = Rating;
    starred = Starred;
  }

  @Override
  public String toString() {
    return "Movie{" +
        "starred=" + starred +
        ", title='" + title + '\'' +
        ", rating=" + rating +

        '}';
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) { this.title = title;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public boolean getStarred() {
    return starred;
  }

  public boolean isStarred() {
    return starred;
  }

  public void setStarred(boolean starred) {
    this.starred = starred;
  }
}
