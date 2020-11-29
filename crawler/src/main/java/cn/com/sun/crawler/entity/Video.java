package cn.com.sun.crawler.entity;

import java.util.Objects;

/**
 * @Description : 一条视频的元数据
 * @Author : Mockingbird
 * @Date: 2020-07-18 18:47
 */
public class Video {

    private String id = "";

    private String downloadUrl = "";

    private String coverUrl = "";

    private String title = "null";

    private int duration = 0;

    private String author = "";

    private int storeNum = 0;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    private String pageUrl = "";

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public int getWatchNum() {
        return watchNum;
    }

    public void setWatchNum(int watchNum) {
        this.watchNum = watchNum;
    }

    private int watchNum = 0;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int durationSecond) {
        this.duration = durationSecond;
    }

    @Override
    public String toString() {
        return id + "|" + title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(id, video.id) &&
                Objects.equals(title, video.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
