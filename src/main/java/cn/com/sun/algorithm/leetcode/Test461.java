package cn.com.sun.algorithm.leetcode;

/**
 * @Author : mockingbird
 * @Date : 2022/3/21 15:05
 * @Description : 汉明距离
 */
public class Test461 {
    //    两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
//    给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
//
//    示例 1：
//    输入：x = 1, y = 4
//    输出：2
//    解释：
//            1   (0 0 0 1)
//            4   (0 1 0 0)
//            ↑   ↑
//    上面的箭头指出了对应二进制位不同的位置。
//    示例 2：
//
//    输入：x = 3, y = 1
//    输出：1
//
//    提示：
//    0 <=x, y <= 2~31 - 1
    public static void main(String[] args) {
        System.out.println(hammingDistance(3, 1));
    }

    public static int hammingDistance(int x, int y) {
        String xs = Integer.toBinaryString(x);
        String ys = Integer.toBinaryString(y);
        //补0

        if (ys.length() > xs.length()) {
            // 填充
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < ys.length() - xs.length(); j++) {
                stringBuilder.append('0');
            }
            xs = stringBuilder.append(xs).toString();
        } else if (ys.length() < xs.length()) {
            // 填充
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < xs.length() - ys.length(); j++) {
                stringBuilder.append('0');
            }
            ys = stringBuilder.append(ys).toString();
        }
        int distance = 0;
        for (int i = 0; i < ys.length(); i++) {
            if (ys.charAt(i) != xs.charAt(i))
                distance++;
        }
        return distance;
    }
}
