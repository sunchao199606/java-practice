package cn.com.sun.crawler.interfaces;

import java.util.List;

/**
 * @Description : 网络视频爬虫接口
 * @Author : Mockingbird
 * @Date : 2020-08-16 10:22
 */
public interface IVideoCrawler<T> {

    /**
     * 从页面解析基础信息
     *
     * @param pageUrl
     * @return
     */
    List<T> parseVideoBaseInfo(String pageUrl);

    /**
     * 从基础信息解析下载信息
     *
     * @param t
     * @return 是否解析成功
     */
    boolean parseVideoDownloadInfo(T t);
}
