package cn.com.sun.algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Author : mockingbird
 * @Date : 2022/3/21 22:19
 * @Description : 二叉树的层序遍历
 */
public class Test102 {
    // 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
//    输入：root = [3,9,20,null,null,15,7]
//    输出：[[3],[9,20],[15,7]]
//    示例 2：
//
//    输入：root = [1]
//    输出：[[1]]
//    示例 3：
//
//    输入：root = []
//    输出：[]


    // for a binary tree node.
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

    /**
     * 二叉树的层序遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levelList = new ArrayList<>();
        if (root == null)
            return levelList;
        Queue<TreeNode> queue = new LinkedList<>();
        // 需要计算层数
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 记录一下queue的原状态
            int size = queue.size();
            List<Integer> list = new ArrayList<>(size);
            while (size > 0) {
                TreeNode node = queue.poll();
                if (node.left != null)
                    queue.offer(node.left);
                if (node.right != null)
                    queue.offer(node.right);
                list.add(node.val);
                size--;
            }
            levelList.add(list);
        }
        return levelList;
    }
}
