package cn.com.sun.crawler.config;

import cn.com.sun.crawler.impl.PornCrawler;
import cn.com.sun.crawler.impl.PornyCrawler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CrawlerConfig {
    /**
     * crawler类型
     */
    public static Class crawler = PornCrawler.class;

    /**
     * 页面
     */
    public static String[] pages;

    public static String[] daily;
    public static final String EXT = ".mp4";
    public static final String DOMAIN = "https://91porny.com";
    public static final String HOT = "/video/category/hot-list";
    public static final String RECENT_HIGHLIGHT = "/video/category/recent-favorite";
    public static final String MONTH_HOT = "/video/category/top-list";
    public static final String MONTH_STORE = "/video/category/top-favorite";
    public static final String MONTH_DISCUSS = "/video/category/month-discuss";
    public static final String LAST_MONTH_HOT = "/video/category/top-last";
    public static String[] allLastMonthHot = new String[5];
    public static String[] allMonthHot = new String[5];
    public static String[] topTenMonthStore = new String[20];
    public static String[] author = new String[3];
    public static String authorName = "";
    public static String FILE_SAVE_PATH = "F://Download//crawler//";

    public static String AUTHOR_PATH = FILE_SAVE_PATH + "author//";

    static {
        daily = new String[]{
                DOMAIN + HOT,
                //DOMAIN + HOT + "/2",
                DOMAIN + RECENT_HIGHLIGHT,
                DOMAIN + MONTH_HOT,
                DOMAIN + MONTH_STORE,
                DOMAIN + MONTH_DISCUSS,
                DOMAIN + LAST_MONTH_HOT
        };
        for (int i = 0; i < allMonthHot.length; i++) {
            allMonthHot[i] = String.format("%s%s/%s", DOMAIN, MONTH_HOT, i + 1);
        }
        for (int i = 0; i < allLastMonthHot.length; i++) {
            allLastMonthHot[i] = String.format("%s%s/%s", DOMAIN, LAST_MONTH_HOT, i + 1);
        }
        for (int i = 0; i < topTenMonthStore.length; i++) {
            topTenMonthStore[i] = String.format("%s%s/%s", DOMAIN, MONTH_STORE, i + 1);
        }
        if (!authorName.isEmpty()) {
            try {
                for (int i = 0; i < author.length; i++)
                    //AUTHOR[i] = String.format("%s/search?keywords=%s&page=%s", DOMAIN, URLEncoder.encode(authorName, "UTF-8"), i + 1);
                    author[i] = String.format("%s/author?keywords=%s&page=%s", DOMAIN, URLEncoder.encode(authorName, "UTF-8"), i + 1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] pornyPages = daily;
    public static final String PORN_DOMAIN = "http://0728.91p50.com";
    public static String[] pornPages = new String[]{
            PORN_DOMAIN + "/index.php",
            PORN_DOMAIN + "/v.php?category=hot&viewtype=basic", //当前最热
            PORN_DOMAIN + "/v.php?category=rf&viewtype=basic", //最近加精
            PORN_DOMAIN + "/v.php?category=top&viewtype=basic",//本月最热
            PORN_DOMAIN + "/v.php?category=tf&viewtype=basic",//本月收藏
            PORN_DOMAIN + "/v.php?category=top&m=-1&viewtype=basic",//上月最热
            PORN_DOMAIN + "/v.php?category=md&viewtype=basic"//本月讨论
    };
    public static File workspace;

    static void initPages() {
        if (crawler == PornCrawler.class) {
            pages = pornPages;
        } else if (crawler == PornyCrawler.class) {
            pages = pornyPages;
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

    static {
        initPages();
        initWorkspace();
    }

    public static final String HTTP_PROXY_HOSTNAME = "localhost";

    /**
     * 本机代理端口，若为-1，则不走代理
     */
    public static int HTTP_PROXY_PORT = 1080;

    /**
     * 站点：91porn.com或者91porny.com
     */
    public static final String SITE = "porny";

    //"http://www.91porn.com/v.php?category=hot&viewtype=basic"

    public static final int CONNECT_TIMEOUT = 10000;

    public static final int READ_TIMEOUT = 5000;

    public static final int READ_FILE_TIMEOUT = 60000;


    /**
     * 请求失败重新尝试请求的次数
     */
    public static final int REQUEST_RETRY_COUNT = 8;
}
