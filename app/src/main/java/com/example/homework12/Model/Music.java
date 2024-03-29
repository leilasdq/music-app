package com.example.homework12.Model;

import java.io.Serializable;

public class Music implements Serializable {
    private Long id;
    private String title;
    private String album;
    private String singer;
    private String picPath;

    public Music(String title, String album, String singer) {
        this.title = title;
        this.album = album;
        this.singer = singer;
    }

    public Music(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
