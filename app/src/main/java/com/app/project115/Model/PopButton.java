package com.app.project115.Model;

import android.widget.Button;

public class PopButton {
    private String title;
    private String url;
    private String backGroundImage;

    public PopButton() {
    }

    public PopButton(String title, String url, String backGroundImage) {
        this.title = title;
        this.url = url;
        this.backGroundImage = backGroundImage;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getBackGroundImage() {
        return backGroundImage;
    }
}
