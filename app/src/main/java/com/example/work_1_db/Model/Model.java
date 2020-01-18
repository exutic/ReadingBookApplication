package com.example.work_1_db.Model;

import android.media.Image;
import android.view.Display;

public class Model {
    private String id;
    private String title;
    private String content;
    private String imageName;
    private String fave;


    public Model(String id, String title, String content, String imgName, String fave)
    {
        setId(id);
        setTitle(title);
        setContent(content);
        setImageName(imgName);
        setFave(fave);
    }

    public Model() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFave() {
        return fave;
    }
    public void setFave(String fave) {
        this.fave = fave;
    }
}
