package cn.com.sun.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : mockingbird
 * @Date : 2022/3/20 22:15
 * @Description : 二叉树中序遍历
 */
public class Test94 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inOrderTravel(root, list);
        return list;
    }

    public void inOrderTravel(TreeNode node, List<Integer> list) {
        if (node == null) return;
        inOrderTravel(node.left, list);
        list.add(node.val);
        inOrderTravel(node.right, list);
    }
}
