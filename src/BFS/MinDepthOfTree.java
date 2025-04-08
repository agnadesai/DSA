package BFS;

import binarySearchTree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // create queue - add all nodes in it
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int depth = 1;

        while (q.isEmpty() == false) {
            int qSize = q.size();

            while (qSize > 0) {
                qSize--;
                // remove the top node
                TreeNode node = q.remove();

                if (node == null) {
                    continue;
                }
                //if it's a leaf node, then return depth
                if (node.left == null && node.right == null) {
                    return depth;
                }
                // if not leaf node add left and right to the queue
                q.add(node.left);
                q.add(node.right);
            }
            //increase depth
            depth++;
        }
        return -1;
    }
}