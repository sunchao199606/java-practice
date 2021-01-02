package cn.com.sun;

import java.io.*;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Export {

    // 导出目录前缀ab4x
    public static final String MARKET_PATH_PREFIX_AB4X = "E:\\工作文件\\A青海\\青海AB4.2平台源码\\ab4x";
    // 导出目录前缀adore2
    public static final String MARKET_PATH_PREFIX_ADORE2 = "E:\\工作文件\\A青海\\青海AB4.2平台源码\\adore2";
    // 导出路径
    public static final String EXPORT_PATH = "E:\\qinghai\\abcExport";
    // 压缩路径
    public static final String ZIP_PATH = "E:\\qinghai\\zipExport.zip";
    // 市场目录
    public static final String MARKET_PATH = MARKET_PATH_PREFIX_AB4X + "\\" + "QingHaiNongXin";

    // 需要导出的源码目录
    private static final String[] AB_SOURCE = new String[]{MARKET_PATH_PREFIX_AB4X + "\\" + "AB_Client",
            MARKET_PATH_PREFIX_AB4X + "\\" + "AB_Public", MARKET_PATH_PREFIX_AB4X + "\\" + "AB_Server",
            MARKET_PATH_PREFIX_AB4X + "\\" + "Dep", MARKET_PATH_PREFIX_AB4X + "\\" + "EVS", MARKET_PATH_PREFIX_ADORE2 + "\\" + "adore", MARKET_PATH};

    // 导出目录
    public static final File DESTIMATION = new File(EXPORT_PATH);
    // 导出后的压缩路径
    public static final File ZIP_DESTINATION_FILE = new File(ZIP_PATH);
    // 不复制的文件
    public static final String[] FILES_NOT_COPY = new String[]{"classes", ".product", ".gitignore", ".launch"};

    public static void main(String[] args) {
        DESTIMATION.mkdirs();
        for (String s : AB_SOURCE) {
            File src = new File(s);
            getDestinationDir(src);
        }
        try {
            zipCompress(DESTIMATION.getAbsolutePath(), ZIP_DESTINATION_FILE.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getDestinationDir(File file) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory()) {
                String[] fileName = getFileName(f.getPath());
                String destinationPath = DESTIMATION.getAbsolutePath();
                if (Arrays.asList(fileName).contains(".project")) {
                    String destinationination = f.getPath().substring(f.getPath().lastIndexOf("\\") + 1,
                            f.getPath().length());
                    copyDir(f.getPath(), destinationPath + File.separator + destinationination);
                } else {
                    getDestinationDir(f);
                }

            }
        }
    }

    private static String[] getFileName(String path) {
        File file = new File(path);
        String[] fileName = file.list();
        return fileName;
    }

    public static void copyDir(String src, String destination) {
        File file = new File(src);
        String[] filePath = file.list();

        if (!(new File(destination)).exists()) {
            (new File(destination)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            if (Arrays.asList(FILES_NOT_COPY).contains(filePath[i])) {
                continue;
            }
            if ((new File(src + file.separator + filePath[i])).isDirectory()) {
                copyDir(src + file.separator + filePath[i], destination + file.separator + filePath[i]);
            }
            if (new File(src + file.separator + filePath[i]).isFile()) {
                copyFile(src + file.separator + filePath[i], destination + file.separator + filePath[i]);
            }

        }
    }

    public static void copyFile(String src, String destination) {
        byte[] buff = new byte[1024];
        int length = 0;
        try (FileInputStream fis = new FileInputStream(src); FileOutputStream fos = new FileOutputStream(destination)) {
            while ((length = fis.read(buff)) > 0) {
                fos.write(buff, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 压缩 */
    public static void zipCompress(String inputFile, String outputFile) throws Exception {
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFile));
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            File input = new File(inputFile);
            compress(out, bos, input, null);
        }
    }

    public static void compress(ZipOutputStream out, BufferedOutputStream bos, File input, String name)
            throws IOException {
        if (name == null) {
            name = input.getName();
        }
        if (input.isDirectory()) {
            File[] flist = input.listFiles();

            if (flist.length == 0) {
                out.putNextEntry(new ZipEntry(name + "/"));
            } else {
                for (int i = 0; i < flist.length; i++) {
                    compress(out, bos, flist[i], name + "/" + flist[i].getName());
                }
            }
        } else {
            out.putNextEntry(new ZipEntry(name));
            int len = -1;
            byte[] buf = new byte[1024];
            try (FileInputStream fos = new FileInputStream(input);
                 BufferedInputStream bis = new BufferedInputStream(fos)) {
                while ((len = bis.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

