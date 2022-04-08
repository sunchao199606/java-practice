package cn.com.sun.algorithm.leetcode;

/**
 * @Author : mockingbird
 * @Date : 2022/3/20 16:56
 * @Description : 二叉树的直径
 */
public class Test543 {
    /*
    给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
    示例 :
    给定二叉树

          1
         / \
        2   3
       / \
      4   5
     返回3, 它的长度是路径 [4,2,1,3] 或者[5,2,1,3]。
     注意：两结点之间的路径长度是以它们之间边的数目表示。
     */
    public static void main(String[] args) {

    }

    public int diameterOfBinaryTree(TreeNode root) {
        // 求左右子树深度之和
        if (root == null)
            return 0;
        // 左子树的最大深度+右子树的最大深度
        int left = root.left == null ? 0 : diameterOfBinaryTree(root.left) + 1;
        int right = root.right == null ? 0 : diameterOfBinaryTree(root.right) + 1;
        int total = left + right;
        return Math.max(left, right);
    }


    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
