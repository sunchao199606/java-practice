package cn.com.sun.crawler;

public interface VideoCrawler {
    VideoCrawler parseVideo();

    VideoCrawler parseDownloadInfo();

    void download();
}
