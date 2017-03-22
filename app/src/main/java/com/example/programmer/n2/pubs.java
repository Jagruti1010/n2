package com.example.programmer.n2;

/**
 * Created by Programmer on 10/4/2016.
 */

public class pubs {
    private String title , image , desc;
    public pubs()
    {

    }
    public pubs(String title,String image,String desc)
    {
            this.title=title;
            this.image=image;
            this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
