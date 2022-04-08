package cn.com.sun.algorithm.leetcode;

/**
 * @Author : mockingbird
 * @Date : 2022/3/20 22:41
 * @Description : 相同的树
 */
public class Test100 {

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

    public boolean isSameTree(TreeNode p, TreeNode q) {
        // 遍历两个树并比较节点的值
        return isSame(p, q);
    }

    public boolean isSame(TreeNode p, TreeNode q) {
        if (p == null && q != null)
            return false;
        else if (p != null && q == null)
            return false;
        else if (p == null && q == null)
            return true;
        boolean same = p.val == q.val;
        boolean leftSame = isSame(p.left, q.left);
        boolean rightSame = isSame(p.right, q.right);
        if (same && leftSame && rightSame)
            return true;
        else
            return false;
    }
}
