package cn.com.sun.crawler;

import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Description : 常量
 * @Author : Mockingbird
 * @Date: 2020-07-18 00:43
 */
public class Config {

    public static final String HTTP_PROXY_HOSTNAME = "localhost";

    /**
     * 本机代理端口，若为-1，则不走代理
     */
    public static int HTTP_PROXY_PORT = 1080;

    /**
     * 站点：91porn.com或者91porny.com
     */
    public static final String SITE = "porny";

    public static final String[] PORN_PAGES = new String[]{
            //HOME_PAGE, TOP_HOT, TOP_STORE, HOT_CURRENT, TOP_HOT_LAST_MONTH
    };

    public static final String EXT = ".mp4";
    public static final String DOMAIN = "https://91porny.com";
    public static final String HOT = "/video/category/hot-list";
    public static final String RECENT_HIGHLIGHT = "/video/category/recent-favorite";
    public static final String MONTH_HOT = "/video/category/top-list";
    public static final String MONTH_STORE = "/video/category/top-favorite";
    public static final String MONTH_DISCUSS = "/video/category/month-discuss";
    public static final String LAST_MONTH_HOT = "/video/category/top-last";


    public static String[] DAILY;
    public static String[] ALL_LAST_MONTH_HOT = new String[5];
    public static String[] ALL_MONTH_HOT = new String[5];
    public static String[] TOP_TEN_MONTH_STORE = new String[10];
    public static String[] AUTHOR = new String[10];
    public static String authorName = "tfboss58";

    static {
        DAILY = new String[]{
                DOMAIN + HOT,
                DOMAIN + RECENT_HIGHLIGHT,
                DOMAIN + MONTH_HOT,
                DOMAIN + MONTH_STORE,
                DOMAIN + MONTH_DISCUSS,
                DOMAIN + LAST_MONTH_HOT
        };
        for (int i = 0; i < ALL_MONTH_HOT.length; i++) {
            ALL_MONTH_HOT[i] = String.format("%s%s/%s", DOMAIN, MONTH_HOT, i + 1);
        }
        for (int i = 0; i < ALL_LAST_MONTH_HOT.length; i++) {
            ALL_LAST_MONTH_HOT[i] = String.format("%s%s/%s", DOMAIN, LAST_MONTH_HOT, i + 1);
        }
        for (int i = 0; i < TOP_TEN_MONTH_STORE.length; i++) {
            TOP_TEN_MONTH_STORE[i] = String.format("%s%s/%s", DOMAIN, MONTH_STORE, i + 1);
        }
        if (!authorName.isEmpty()) {
            try {
                for (int i = 0; i < AUTHOR.length; i++)
                    AUTHOR[i] = String.format("%s/author?keywords=%s&page=%s", DOMAIN, URLEncoder.encode(authorName, "UTF-8"), i + 1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> PORNY_PAGES = Lists.newArrayList(AUTHOR);

    public static final int CONNECT_TIMEOUT = 10000;

    public static final int READ_TIMEOUT = 5000;

    public static final int READ_FILE_TIMEOUT = 60000;

    public static String FILE_SAVE_PATH = "F://Download//crawler//";

    public static String AUTHOR_PATH = FILE_SAVE_PATH + "author//";


    /**
     * 请求失败重新尝试请求的次数
     */
    public static final int REQUEST_RETRY_COUNT = 8;

}
