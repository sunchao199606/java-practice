package cn.com.sunchao.algorithm;

/**
 * @Description :
 * @Author :sunchao
 * @Date: 2020-03-22 23:21
 */

public class SetTest {

    public static void main(String[] args) {

        String str[] = { "A", "B", "C", "D", "E" };

        int nCnt = str.length;

        int nBit = (0xFFFFFFFF >>> (32 - nCnt));

        for (int i = 1; i <= nBit; i++) {
            for (int j = 0; j < nCnt; j++) {
                if ((i << (31 - j)) >> 31 == -1) {
                    System.out.print(str[j]);
                }
            }
            System.out.println("");
        }

    }
}
