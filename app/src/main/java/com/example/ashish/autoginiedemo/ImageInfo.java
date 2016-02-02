package com.example.ashish.autoginiedemo;

/**
 * Created by Ashish on 2/1/2016.
 */
public class ImageInfo {
    String title,url;
    public ImageInfo(String title,String url){
        this.title=title;
        this.url=url;
    }
    public String getTitle(){
        return title;
    }

    public String getUrl() {
        return url;
    }
}
