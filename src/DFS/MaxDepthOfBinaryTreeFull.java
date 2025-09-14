package DFS;

public class MaxDepthOfBinaryTreeFull {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);

        int maximumDepthOfBinaryTree = findMaximumDepthOfBinaryTree(root);
        System.out.println("mx" + maximumDepthOfBinaryTree);
    }

    public static int findMaximumDepthOfBinaryTree(TreeNode node) {

        if(node == null){
            return 0;
        }

        int left = findMaximumDepthOfBinaryTree(node.left);
        int right = findMaximumDepthOfBinaryTree(node.right);

        return 1+Math.max(left,right);

    }
}

class TreeNode {
    TreeNode left;
    TreeNode right;
    int value;

    TreeNode(int val) {
        this.value = val;
    }

    TreeNode(TreeNode left, TreeNode right, int val) {
        this.left = left;
        this.right = right;
        this.value = val;
    }
}