package binarySearchTree;

import java.util.*;

public class VerticalOrderTraversal {
    public static List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();

        // TreeMap to store nodes at different horizontal distances
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        Queue<Pair> queue = new LinkedList<>();

        queue.add(new Pair(root, 0));

        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            TreeNode node = p.node;
            int hd = p.hd;

            map.putIfAbsent(hd, new ArrayList<>());
            map.get(hd).add(node.val);

            if (node.left != null) queue.add(new Pair(node.left, hd - 1));
            if (node.right != null) queue.add(new Pair(node.right, hd + 1));
        }

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        List<List<Integer>> result = verticalOrder(root);
        System.out.println(result);
    }
}
