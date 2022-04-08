package cn.com.sun.algorithm;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @Author : mockingbird
 * @Date : 2022/3/18 11:29
 * @Description :
 */
public class Main3 {
    /*
      二叉树也可以用数组来存储
      给定一个数组
      树的根节点的值储存在下标1
      对于储存在下标n的节点，
      他的左子节点和右子节点分别储存在下标2*n和2*n+1
      并且我们用-1代表一个节点为空
      给定一个数组存储的二叉树
      试求从根节点到最小的叶子节点的路径
      路径由节点的值组成

      输入描述
      输入一行为数组的内容
      数组的每个元素都是正整数，元素间用空格分割
      注意第一个元素即为根节点的值
      即数组的第n元素对应下标n
      下标0在树的表示中没有使用
      所以我们省略了
      输入的树最多为7层

      输出描述
       输出从根节点到最小叶子节点的路径上各个节点的值
       由空格分割
       用例保证最小叶子节点只有一个

       例子
        输入
        3 5 7 -1 -1 2 4
        输出
         3 7 2

        例子
         输入
        5 9 8 -1 -1 7 -1 -1 -1 -1 -1 6
         输出
        5 8 7 6
       */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] nodes = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        //使用TreeMap或者LinkedHashMap
        Map<Integer, Integer> indexToValueMap = new TreeMap<>();
        for (int index = 0; index < nodes.length; index++) {
            indexToValueMap.put(index + 1, nodes[index]);
        }
        // 遍历每一个
        int min = Integer.MAX_VALUE;
        Map.Entry<Integer, Integer> minEntry = null;
        int size = indexToValueMap.size();
        for (Map.Entry<Integer, Integer> entry : indexToValueMap.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (value == -1) continue;
            // 如果判断出当前节点有子节点，那么说明当前节点并不是叶子节点，有子节点的情况：左节点有值或右节点有值
            // 1.左节点在集合范围内，且不为-1
            // 2.右节点在集合范围内，且不为-1
            int leftKey = 2 * key;
            if (leftKey <= size && indexToValueMap.get(leftKey).intValue() != -1)
                continue;
            int rightKey = 2 * key + 1;
            if (rightKey <= size && indexToValueMap.get(rightKey).intValue() != -1)
                continue;
            // 存储最小值
            if (value < min) {
                min = value;
                minEntry = entry;
            }
        }
        // 反向求路径
        int index = minEntry.getKey();
        StringBuilder builder = new StringBuilder();
        while (index > 0) {
            // 左节点为双 右节点为单
            int yushu = index % 2;
            builder.append(indexToValueMap.get(index)).append(" ");
            if (yushu == 1) {
                // 右节点
                index = (index - 1) / 2;
            } else if (yushu == 0) {
                // 左节点
                index = index / 2;
            }
        }
        System.out.println(builder.reverse().toString().trim());
    }
}
