package cn.com.sun.agree;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class GrmFileCompare {

    private final static String SPLIT = ":";
    private final static String HTTP = "http://";
    private final static String HEAD = "HEAD";
    private final static String MAP = "MAP";
    private final static String RESULT = "RESULT";/*比对结果的文件名*/
    private final static String SUB_URL = "/servlets/updateGRM/.grm/";
    private static String version = null;

    public static void main(String[] args) throws IOException {
        //读取jar包同级目录下的grmcomp.properties文件
        File config = new File("grmcomp.properties");
        if (!config.exists()) {
            System.err.println("配置文件[" + config.getAbsolutePath() + "]不存在");
        }

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(config)) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (props == null) {
            System.err.println("获取配置失败,退出程序!");
            return;
        }
        String names = props.getProperty("names");
        String newnames = names.trim();
        String serverName[] = newnames.split(",");
        int len = serverName.length;
        String[] ips = new String[len];
        String[] ports = new String[len];
        for (int i = 0; i < len; i++) {
            String ipKey = serverName[i] + ".ip";
            String portKey = serverName[i] + ".port";
            ips[i] = props.getProperty(ipKey);
            ports[i] = props.getProperty(portKey);
        }
        //定义写出流
        String resultDir = props.getProperty("resultDirPath");
        File f = new File(resultDir);
        String absoluteDir = f.getAbsolutePath();
        //检验文件目录是否存在
        File f2 = new File(absoluteDir);
        if (!f2.exists())
            f2.mkdirs();
        String resultPath = absoluteDir + "\\" + RESULT;
        System.out.println(resultPath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resultPath))) {
            for (int k = 1; k < len; k++) {
                Map<String, String> map = convertDLFileToMap(ips[0], ports[0]);
                Map<String, String> map2 = convertDLFileToMap(ips[k], ports[k]);

                //多余部分写进文件，并返回两个key相同，value不同的Map对象
                Map<String, String> tempMap1 = mapObjCompare(map, map2, bw, ips[0], ports[0], ips[k], ports[k]);
                Map<String, String> tempMap2 = mapObjCompare(map2, map, bw, ips[k], ports[k], ips[0], ports[0]);

                //将不同部分写进文件
                writeValueDiff(tempMap1, tempMap2, bw, ips[0], ports[0], ips[k], ports[k]);
            }
        }

    }

    //将服务器文件下载并将内容转换为Map数据
    private static Map<String, String> convertDLFileToMap(String ip, String port) {
        String headUrl = HTTP + ip + SPLIT + port + SUB_URL + HEAD;
        // System.out.println(headUrl);

        try (InputStream is = new URL(headUrl).openStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            version = br.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(version);
        if (version == null) {
            //找不到HEAD文件
            System.err.println("无法获取到HEAD文件，程序退出");
            return null;
        }

        String mapUrl = HTTP + ip + SPLIT + port + SUB_URL + MAP + version;
        System.out.println(mapUrl);
        Map<String, String> map = null;
        try (InputStream mapis = new URL(mapUrl).openStream()) {
            map = readMap(mapis);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    //读入输入流，将数据转换为Map数据
    private static Map<String, String> readMap(InputStream mapInputStream) {
        Map<String, String> result = new HashMap<String, String>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                mapInputStream, "utf8"))) {

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                String[] segs = line.split(SPLIT);
                // 之所以做这个判断，是因为服务端如果存在一个文件大小为0的文件,解析到这里时stamp没有值,获取不到,报错,就会导致GRM下发不下来
                if (segs.length == 2) {
                    String path = segs[0];
                    String stamp = segs[1];
                    result.put(path, stamp);
                } else {
                    // 记录日志,方便排查问题
                    System.err.println("MAP文件解析错误, 条目数据为: " + line);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //比较两个文件不同的键值，将多余的内容写进文件。key相同，value不同的返回出来
    private static <k, v> Map<k, v> mapObjCompare(Map<k, v> newMap, Map<k, v> oldMap, BufferedWriter bw, String ip1, String port1, String ip2, String port2) throws IOException {
        Map<k, v> tempmap = new HashMap<k, v>();
        Map<k, v> tempmap2 = new HashMap<k, v>();
        tempmap.putAll(newMap);
        newMap.forEach((k, v) -> {
            Object oldValue = oldMap.get(k);
            if (Objects.equals(v, oldValue)) {
                tempmap.remove(k, oldValue);
            }
        });
        bw.write("MAP文件[IP:" + ip1 + " Port:" + port1 + "]    比MAP文件[IP:" + ip2 + " Port:" + port2 + "] 多出的部分为:" + "\n");

        for (Iterator it = tempmap.keySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            bw.write(key + SPLIT + tempmap.get(key) + "\n");
        }
        bw.write("\n");

        tempmap2.putAll(tempmap);

        //比较来得到key相同，value不同的部分
        for (Iterator it = tempmap.keySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            int count = 0;
            for (Iterator itold = oldMap.keySet().iterator(); itold.hasNext(); ) {
                Object oldkey = itold.next();
                if (!key.equals(oldkey))
                    count++;
                else
                    break;
            }
            if (count == oldMap.size())
                tempmap2.remove(key, tempmap.get(key));
        }
        return tempmap2;


    }

    public static <k, v> void writeValueDiff(Map<k, v> newtempMap, Map<k, v> oldtempMap, BufferedWriter bw, String ip1, String port1, String ip2, String port2) throws IOException {
        bw.write("MAP文件[IP:" + ip1 + " Port:" + port1 + "]    与MAP文件[IP:" + ip2 + " Port:" + port2 + "] 不同的部分为:" + "\n");
        for (Iterator it = newtempMap.keySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            bw.write(key + SPLIT + newtempMap.get(key) + "\n");
            bw.write(key + SPLIT + oldtempMap.get(key) + "\n");
            bw.write("-------------------------******-------------------------");
        }
    }
}
