package com.intro.fancyvideomaker.utils.listdecorators;
import androidx.annotation.Keep;
@Keep
public class Bucket {

    private String id;
    private String name;
    private String path;

    public Bucket(String id, String name, String path) {
        this.name = name;
        this.path = path;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

}
