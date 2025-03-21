package binarySearchTree;

class Node {
    int data;
    Node left, right;

    Node(int val) {
        data = val;
        left = null;
        right = null;
    }
}

class MaximumDepthBinaryTree {
    // Returns height which is the number of edges
// along the longest path from the root node down
// to the farthest leaf node.
    static int height(Node root) {
        if (root == null)
            return -1;

        // compute the height of left and right subtrees
        int lHeight = height(root.left);
        int rHeight = height(root.right);

        return Math.max(lHeight, rHeight) + 1;
    }


    public static void main(String[] args) {

        // Representation of the input tree:
        //     12
        //    /  \
        //   8   18
        //  / \
        // 5   11
        Node root = new Node(12);
        root.left = new Node(8);
//        root.right = new Node(18);
//        root.left.left = new Node(5);
//        root.left.right = new Node(11);

        System.out.println(height(root));
    }
}
