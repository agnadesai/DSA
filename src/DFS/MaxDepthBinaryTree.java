package DFS;

import binarySearchTree.TreeNode;

public class MaxDepthBinaryTree {

    // DFS function to find maximum depth of a binary tree
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0; // If the tree is empty, the depth is 0
        }

        // Recursively find the depth of the left and right subtrees
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        // The depth of the current node is 1 + the maximum of left and right subtrees' depth
        return 1 + Math.max(leftDepth, rightDepth);
    }

    public static void main(String[] args) {
        // Example Binary Tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);

        // Find the maximum depth
        System.out.println("Maximum Depth of the Binary Tree: " + maxDepth(root));
    }
}