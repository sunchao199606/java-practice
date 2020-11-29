package cn.com.sun.algorithm;

/**
 * @Description :
 * 给出两个非空的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * @Author :sunchao
 * @Date: 2020-04-04 01:30
 */

class Node {
    int value;

    Node next;

    private Node(int v, Node next) {
        this.value = v;
        this.next = next;
    }
}

/**
 * 链表转化为数字 相加 再反序
 */
public class AddTwoNumbers {

    public int addTwoNumbers(Node first, Node second) {
        while (first.next != null) {

        }
        return 0;
    }

}
