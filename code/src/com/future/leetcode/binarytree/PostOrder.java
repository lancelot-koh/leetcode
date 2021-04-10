package com.future.leetcode.binarytree;

import java.util.*;

public class PostOrder {



    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(1);

        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);

        tree.root.right.left = new TreeNode(6);
        tree.root.right.right = new TreeNode(7);

        postOrderTraversal(tree.root);
        postOrderTraversal2(tree.root);
    }

    private static List<Integer> postOrderTraversal(TreeNode root) {
//        left -> right -> root
        if (root == null) {
            return null;
        }

        LinkedList<Integer>  res = new LinkedList<>();
//        Stack<TreeNode> stack = new Stack<>();
        Deque<TreeNode> stack = new ArrayDeque<>();

//        TreeNode curr = root;
        stack.push(root);

        while (!stack.isEmpty()) {
            root = stack.pop();
            res.addFirst(root.value);
            if (root.left != null) {
                stack.push(root.left);
            }
            if (root.right != null) {
                stack.push((root.right));
            }
        }

        return res;

    }

    private static List<Integer> postOrderTraversal2(TreeNode root) {

        if (root == null) {
            return null;
        }

        List<Integer> res = new LinkedList<>();
        helper(root, res);
        return res;
    }

    private static void helper(TreeNode node, List<Integer> res) {
        if(node == null) {
            return;
        }
        helper(node.left, res);
        helper(node.right,res);
        res.add(node.value);
    }
}
