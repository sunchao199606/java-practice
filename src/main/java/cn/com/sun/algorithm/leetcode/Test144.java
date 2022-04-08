package cn.com.sun.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : mockingbird
 * @Date : 2022/3/20 22:31
 * @Description : 二叉树的前序遍历
 */
public class Test144 {

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

    public List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        preOrderTravel(root, list);
        return list;
    }

    public void preOrderTravel(TreeNode node, List<Integer> list) {
        if (node == null) return;
        list.add(node.val);
        preOrderTravel(node.left, list);
        preOrderTravel(node.right, list);
    }
}
