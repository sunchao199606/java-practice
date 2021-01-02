package cn.com.sun.crawler;

import cn.com.sun.crawler.impl.PornCrawler;
import cn.com.sun.crawler.impl.PornyCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CrawlerConfig {
    public static final String EXT = ".mp4";
    public static final String HTTP_PROXY_HOSTNAME = "localhost";
    public static final String FILE_SAVE_PATH = "F://Download//crawler//";
    public static final String AUTHOR_PATH = "F://Download//crawler//author//";
    public static final int CONNECT_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 5000;
    public static final int READ_FILE_TIMEOUT = 60000;
    /**
     * 请求失败重新尝试请求的次数
     */
    public static final int REQUEST_RETRY_COUNT = 8;
    /**
     * 本机代理端口，若为-1，则不走代理
     */
    public static final int HTTP_PROXY_PORT = 1080;
    private static final Logger logger = LoggerFactory.getLogger(CrawlerConfig.class);
    public static String domain;
    public static String homePage;
    public static String hot;
    public static String recentHighlight;
    public static String monthHot;
    public static String monthStore;
    public static String monthDiscuss;
    public static String lastMonthHot;

    /**
     * crawler类型
     */
    public static Class crawler = PornyCrawler.class;
    public static String[] pages;
    public static String[] daily;
    public static String[] allLastMonthHot = new String[5];
    public static String[] allMonthHot = new String[5];
    public static String[] topTenMonthStore = new String[10];
    public static String[] author = new String[3];
    public static String authorName = "";
    public static File workspace;

    static {
        initPages();
        pages = daily;
        //pages = new String[]{"http://0728.91p50.com/uvideos.php?UID=b41cJANU7wyUhcQnmUc3AveGBv3rT35NOuM8Qj6x449R6vj1&type=public&page=3"};
        initWorkspace();
    }

    static void initPages() {
        if (crawler == PornCrawler.class) {
            //http://91.91p07.com
            //http://0728.91p50.com
            domain = "http://91.91p07.com";
            homePage = domain + "/index.php";
            hot = domain + "/v.php?category=hot&viewtype=basic";
            recentHighlight = domain + "/v.php?category=rf&viewtype=basic";
            monthHot = domain + "/v.php?category=top&viewtype=basic";
            monthStore = domain + "/v.php?category=tf&viewtype=basic";
            monthDiscuss = domain + "/v.php?category=md&viewtype=basic";
            lastMonthHot = domain + "/v.php?category=top&m=-1&viewtype=basic";
            daily = new String[]{homePage, hot, recentHighlight, monthHot, monthStore, monthDiscuss, lastMonthHot};
        } else if (crawler == PornyCrawler.class) {
            domain = "https://91porny.com";
            hot = domain + "/video/category/hot-list";
            recentHighlight = domain + "/video/category/recent-favorite";
            monthHot = domain + "/video/category/top-list";
            monthStore = domain + "/video/category/top-favorite";
            monthDiscuss = domain + "/video/category/month-discuss";
            lastMonthHot = domain + "/video/category/top-last";
            daily = new String[]{hot, recentHighlight, monthHot, monthStore, monthDiscuss, lastMonthHot};
        }
        String query = "&page=";
        if (crawler == PornyCrawler.class) {
            query = "/";
        }
        for (int i = 0; i < allMonthHot.length; i++) {
            allMonthHot[i] = String.format("%s%s%s", monthHot, query, i + 1);
        }
        for (int i = 0; i < allLastMonthHot.length; i++) {
            allLastMonthHot[i] = String.format("%s%s%s", lastMonthHot, query, i + 1);
        }
        for (int i = 0; i < topTenMonthStore.length; i++) {
            topTenMonthStore[i] = String.format("%s%s%s", monthStore, query, i + 1);
        }
        if (!authorName.isEmpty()) {
            try {
                for (int i = 0; i < author.length; i++)
                    //AUTHOR[i] = String.format("%s/search?keywords=%s&page=%s", DOMAIN, URLEncoder.encode(authorName, "UTF-8"), i + 1);
                    author[i] = String.format("%s/author?keywords=%s&page=%s", domain, URLEncoder.encode(authorName, "UTF-8"), i + 1);
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    static void initWorkspace() {
        // 存储目录信息
        if (Arrays.stream(pages).allMatch(p -> p.contains("/author"))) {
            workspace = new File(CrawlerConfig.AUTHOR_PATH + CrawlerConfig.authorName);
        } else {
            // 创建对应日期的文件夹
            String date = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
            workspace = new File(CrawlerConfig.FILE_SAVE_PATH + date);
        }
    }
}
