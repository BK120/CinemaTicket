package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/19.
 * 需要购买的电影
 */

public class BuyFilm {
    //电影ID  唯一标识
    private String movieid;
    //影片名称
    private String title;
    //题材
    private String genres;
    //语言
    private String language;
    //影片评分
    private String rating;
    //影片拍摄国家
    private String country;
    //导演们
    private String directors;
    //演员
    private String actors;
    //剧情
    private String plot_simple;
    //海报
    private String poster;

    public String getMovieid() {
        return movieid;
    }
    //构造器
    public BuyFilm(String id,String title,String genres,String language){
        this.movieid=id;
        this.title=title;
        this.genres=genres;
        this.language=language;
    }
    public void setMovieid(String movieid) {
        this.movieid = movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot_simple() {
        return plot_simple;
    }

    public void setPlot_simple(String plot_simple) {
        this.plot_simple = plot_simple;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
