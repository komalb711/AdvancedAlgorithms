public class LowestCommonAncestor {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
            left = null;
            right = null;
        }
    }

    TreeNode lcaNode;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        traverseTree(root, p, q);
        return this.lcaNode;
    }

    public boolean traverseTree(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return false;
        }
        boolean left = traverseTree(node.left, p, q);
        boolean right = traverseTree(node.right, p, q);
        boolean current = (node == p || node == q);

        if ((left && right) || (left && current) || (right && current)) {
            this.lcaNode = node;
        }
        return (left || right || current);
    }

}
