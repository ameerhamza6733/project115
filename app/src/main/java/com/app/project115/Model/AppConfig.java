package com.app.project115.Model;

import com.app.project115.Model.PopButton;

import java.util.List;

public class AppConfig {
    private int AppCodeNumber;
    private String webViewUrl;
    private boolean showFixBanner;
    private String fixBannerImageUrl;
    private String fixBannerUrl;
    private boolean bannerOpenNewBrowser;
    private String popBackGroundUrl;
    private String pop2BackGroundUrl;
    private boolean enablePop31;
    private boolean enablePop32;
    private int popX;
    private  int popY;

    private List<PopButton> popButtonList;
    private List<PopButton> popButtonList2;

    public AppConfig() {

    }

    public String getFixBannerImageUrl() {
        return fixBannerImageUrl;
    }

    public void setFixBannerImageUrl(String fixBannerImageUrl) {
        this.fixBannerImageUrl = fixBannerImageUrl;
    }

    public int getPopX() {
        return popX;
    }

    public void setPop2BackGroundUrl(String pop2BackGroundUrl) {
        this.pop2BackGroundUrl = pop2BackGroundUrl;
    }

    public String getPop2BackGroundUrl() {
        return pop2BackGroundUrl;
    }

    public List<PopButton> getPopButtonList2() {
        return popButtonList2;
    }

    public void setPopButtonList2(List<PopButton> popButtonList2) {
        this.popButtonList2 = popButtonList2;
    }

    public void setPopX(int popX) {
        this.popX = popX;
    }

    public int getPopY() {
        return popY;
    }

    public void setPopY(int popY) {
        this.popY = popY;
    }

    public void setEnablePop31(boolean enablePop31) {
        this.enablePop31 = enablePop31;
    }

    public boolean isEnablePop31() {
        return enablePop31;
    }

    public String getPopBackGroundUrl() {
        return popBackGroundUrl;
    }

    public void setPopBackGroundUrl(String popBackGroundUrl) {
        this.popBackGroundUrl = popBackGroundUrl;
    }

    public List<PopButton> getPopButtonList() {
        return popButtonList;
    }

    public void setPopButtonList(List<PopButton> popButtonList) {
        this.popButtonList = popButtonList;
    }


    public boolean isEnablePop32() {
        return enablePop32;
    }

    public void setEnablePop32(boolean enablePop32) {
        this.enablePop32 = enablePop32;
    }

    public boolean isShowFixBanner() {
        return showFixBanner;
    }

    public void setShowFixBanner(boolean showFixBanner) {
        this.showFixBanner = showFixBanner;
    }

    public String getFixBannerUrl() {
        return fixBannerUrl;
    }

    public void setBannerOpenNewBrowser(boolean bannerOpenNewBrowser) {
        this.bannerOpenNewBrowser = bannerOpenNewBrowser;
    }

    public boolean isBannerOpenNewBrowser() {
        return bannerOpenNewBrowser;
    }

    public void setFixBannerUrl(String fixBannerUrl) {
        this.fixBannerUrl = fixBannerUrl;
    }

    public String getWebViewUrl() {
        return webViewUrl;
    }

    public void setWebViewUrl(String webViewUrl) {
        this.webViewUrl = webViewUrl;
    }

    public int getAppCodeNumber() {
        return AppCodeNumber;
    }

    public void setAppCodeNumber(int appCodeNumber) {
        AppCodeNumber = appCodeNumber;
    }
}
