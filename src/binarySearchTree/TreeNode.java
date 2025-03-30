package binarySearchTree;

import com.sun.source.tree.Tree;

public class TreeNode {
    public TreeNode left;
    public TreeNode right;
    int val;

    public TreeNode(int val){
        this.val = val;
    }

    TreeNode(TreeNode left, TreeNode right, int val) {
        this.left = left;
        this.right = right;
        this.val = val;
    }
}
