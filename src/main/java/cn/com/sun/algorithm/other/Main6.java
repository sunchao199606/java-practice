package cn.com.sun.algorithm.other;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/13 16:01
 * @Description : 反转链表
 */
public class Main6 {
//    给定一个单链表的头结点pHead(该头节点是有值的，比如在下图，它的val是1)，长度为n，反转该链表后，返回新链表的表头。
//
//    数据范围： 0≤n≤1000
//    要求：空间复杂度 O(1)O(1) ，时间复杂度 O(n)O(n) 。
//
//    如当输入链表{1,2,3}时，
//    经反转后，原链表变为{3,2,1}，所以对应的输出为{3,2,1}。
//    示例1
//    输入：
//    {1,2,3}
//    返回值：
//    {3,2,1}
//    示例2
//    输入：
//    {}
//    返回值：
//    {}
//    说明：
//    空链表则输出空

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.substring(1, input.length() - 1).length() == 0) {
            System.out.println("{}");
            return;
        }
        int[] list = Arrays.stream(input.substring(1, input.length() - 1).split(",")).mapToInt(Integer::parseInt).toArray();
        StringBuilder builder = new StringBuilder("{");
        for (int index = list.length - 1; index >= 0; index--) {
            builder.append(list[index]).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(builder + "}");
    }
}
