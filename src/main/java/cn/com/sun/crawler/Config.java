package cn.com.sun.crawler;

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

    public static final String HOME_PAGE = "http://www.91porn.com/index.php";

    public static final String TOP_HOT = "http://www.91porn.com/v.php?category=top&viewtype=basic";

    public static final String TOP_STORE = "http://www.91porn.com/v.php?category=tf&viewtype=basic";

    public static final String HOT_CURRENT = "http://www.91porn.com/v.php?category=hot&viewtype=basic";

    public static final String TOP_HOT_LAST_MONTH = "http://www.91porn.com/v.php?category=top&m=-1&viewtype=basic";

    public static final String[] PORN_PAGES = new String[]{"http://www.91porn.com/v.php?category=rf&viewtype=basic&page=1", "http://www.91porn.com/v.php?category=rf&viewtype=basic&page=4", "http://www.91porn.com/v.php?category=rf&viewtype=basic&page=3", "http://www.91porn.com/v.php?category=rf&viewtype=basic&page=2"};
    //,HOME_PAGE, TOP_HOT, TOP_STORE, HOT_CURRENT, TOP_HOT_LAST_MONTH

    public static final String[] PORNY_PAGES = new String[]{
            //"https://91porny.com/",
            "https://91porny.com/video/category/recent-favorite",
//            "https://91porny.com/video/category/recent-favorite/2",
//            "https://91porny.com/video/category/recent-favorite/3",
//            "https://91porny.com/video/category/recent-favorite/4",
            "https://91porny.com/video/category/hot-list",
            "https://91porny.com/video/category/month-discuss",
            "https://91porny.com/video/category/top-favorite",
            "https://91porny.com/video/category/top-list",
            "https://91porny.com/video/category/top-last"
    };

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
