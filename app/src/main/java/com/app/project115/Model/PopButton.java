package com.app.project115.Model;

import android.widget.Button;

public class PopButton {
    private String title;
    private String url;
    private String backGroundColorCode;

    public PopButton() {
    }

    public PopButton(String title, String url, String backGroundColorCode) {
        this.title = title;
        this.url = url;
        this.backGroundColorCode = backGroundColorCode;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getBackGroundColorCode() {
        return backGroundColorCode;
    }
}
