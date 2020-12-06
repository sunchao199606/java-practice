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

    public static final String EXT = ".mp4";

    public static final String CURRENT_HOT_1 = "https://91porny.com/video/category/hot-list/1";
    public static final String CURRENT_HOT_2 = "https://91porny.com/video/category/hot-list/2";
    public static final String CURRENT_HOT_3 = "https://91porny.com/video/category/hot-list/3";
    public static final String RECENT_HIGHLIGHT_1 = "https://91porny.com/video/category/recent-favorite/1";
    public static final String RECENT_HIGHLIGHT_2 = "https://91porny.com/video/category/recent-favorite/2";
    public static final String MONTH_HOT = "https://91porny.com/video/category/top-list";
    public static final String MONTH_STORE = "https://91porny.com/video/category/top-favorite";
    public static final String MONTH_DISCUSS = "https://91porny.com/video/category/month-discuss";
    public static final String LAST_MONTH_HOT = "https://91porny.com/video/category/top-last";


    public static final String[] PORN_PAGES = new String[]{
            //HOME_PAGE, TOP_HOT, TOP_STORE, HOT_CURRENT, TOP_HOT_LAST_MONTH
    };
    public static final String[] ALL_LAST_MONTH_HOT = new String[]{
            "https://91porny.com/video/category/top-last/1",
            "https://91porny.com/video/category/top-last/2",
            "https://91porny.com/video/category/top-last/3",
            "https://91porny.com/video/category/top-last/4",
            "https://91porny.com/video/category/top-last/5"

    };
    public static final String[] ALL_MONTH_HOT = new String[]{
            "https://91porny.com/video/category/top-list/1",
            "https://91porny.com/video/category/top-list/2",
            "https://91porny.com/video/category/top-list/3",
            "https://91porny.com/video/category/top-list/4",
            "https://91porny.com/video/category/top-list/5",
    };

    public static final String[] TOP_TEN_MONTH_STORE = new String[]{
            "https://91porny.com/video/category/top-favorite/1",
            "https://91porny.com/video/category/top-favorite/2",
            "https://91porny.com/video/category/top-favorite/3",
            "https://91porny.com/video/category/top-favorite/4",
            "https://91porny.com/video/category/top-favorite/5",
            "https://91porny.com/video/category/top-favorite/6",
            "https://91porny.com/video/category/top-favorite/7",
            "https://91porny.com/video/category/top-favorite/8",
            "https://91porny.com/video/category/top-favorite/9",
            "https://91porny.com/video/category/top-favorite/10"
    };
    public static final String[] DAILY = new String[]{
            CURRENT_HOT_1,
//            RECENT_HIGHLIGHT_1,
//            MONTH_HOT,
//            MONTH_STORE,
//            MONTH_DISCUSS,
//            LAST_MONTH_HOT
    };
    public static String[] AUTHOR = null;
    public static String authorName = "91太宰治";

    static {
        try {
            AUTHOR = new String[]{
                    String.format("https://91porny.com/author?keywords=%s&page=1", URLEncoder.encode(authorName,"UTF-8")),
                    String.format("https://91porny.com/author?keywords=%s&page=2", URLEncoder.encode(authorName,"UTF-8")),
                    String.format("https://91porny.com/author?keywords=%s&page=3", URLEncoder.encode(authorName,"UTF-8")),
                    String.format("https://91porny.com/author?keywords=%s&page=4", URLEncoder.encode(authorName,"UTF-8")),
                    String.format("https://91porny.com/author?keywords=%s&page=5", URLEncoder.encode(authorName,"UTF-8")),
            };
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static final List<String> PORNY_PAGES = Lists.newArrayList(AUTHOR);

    //public static final String[] PAGES = new String[] {"http://0728.91p50.com/v.php?category=tf&viewtype=basic&page=2","http://www.91porn.com/v.php?category=top&m=-1&viewtype=basic&page=3","http://www.91porn.com/v.php?category=top&m=-1&viewtype=basic&page=4","http://www.91porn.com/v.php?category=top&m=-1&viewtype=basic&page=5"};
    public static final int CONNECT_TIMEOUT = 10000;

    public static final int READ_TIMEOUT = 5000;

    public static final int READ_FILE_TIMEOUT = 60000;

    public static final String FILE_SAVE_PATH = "F://Download//crawler//";

    public static final String DOWNLOADED_FILE_PATH = FILE_SAVE_PATH + "downloaded";

    /**
     * 请求失败重新尝试请求的次数
     */
    public static final int REQUEST_RETRY_COUNT = 8;

}
