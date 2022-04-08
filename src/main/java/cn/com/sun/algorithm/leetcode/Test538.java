package cn.com.sun.algorithm.leetcode;

/**
 * @Author : mockingbird
 * @Date : 2022/3/20 22:08
 * @Description : 二叉搜索树转换为累加树
 */
public class Test538 {

    public static class TreeNode {
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

    public int pre = 0;

    /**
     * 按照右中左的顺序遍历出来 并记录之前计算的值
     *
     * @param root
     * @return
     */
    public TreeNode convertBST(TreeNode root) {
        if (root == null)
            return null;
        //遍历右子树
        convertBST(root.right);
        //遍历顶点 并将该节点赋值
        root.val = root.val + pre;
        //记录当前节点的值
        pre = root.val;
        //遍历左子树
        convertBST(root.left);
        return root;
    }
}
