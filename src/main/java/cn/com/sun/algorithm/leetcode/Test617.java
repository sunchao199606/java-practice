package cn.com.sun.algorithm.leetcode;

/**
 * @Author : mockingbird
 * @Date : 2022/3/19 23:50
 * @Description : 合并二叉树
 */
public class Test617 {
    /*
    给你两棵二叉树：root1和root2。想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）你需要将这两棵树合并成一棵新二叉树。
    合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；否则，不为 null 的节点将直接作为新二叉树的节点。返回合并后的二叉树。
    注意: 合并过程必须从两个树的根节点开始。
    示例 1：
    输入：root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
    输出：[3,4,5,5,4,null,7]

    示例 2：
    输入：root1 = [1], root2 = [1,2]
    输出：[2,2]
    * */
    //此函数返回的结果为两个节点合并后的node
    private static TreeNode mergeTrees(TreeNode node1, TreeNode node2) {
        if (node1 == null)
            return node2;
        if (node2 == null)
            return node1;
        TreeNode mergeNode = new TreeNode(node1.val + node2.val);
        mergeNode.left = mergeTrees(node1.left, node2.left);
        mergeNode.right = mergeTrees(node1.right, node2.right);
        return mergeNode;
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
