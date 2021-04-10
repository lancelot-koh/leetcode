package com.future.leetcode.binarytree;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class InOrder {

    public static List<Integer> InOrderTraversal(TreeNode root) {
        // left -> root -> right
        if (root == null) {
            return  null;
        }

        List<Integer> res = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            while(curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();
            res.add(curr.value);
            curr = curr.right;
        }
        return res;

    }

    public static List<Integer> InOrderTraversal2(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<Integer> res = new LinkedList<>();
        helper(root, res);
        return res;

    }

    public static void helper(TreeNode node, List<Integer> res) {
        if(node != null) {
            helper(node.left, res);
            res.add(node.value);
            helper(node.right, res);
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(1);

        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);

        tree.root.right.left = new TreeNode(6);
        tree.root.right.right = new TreeNode(7);
        InOrderTraversal(tree.root);
        InOrderTraversal2(tree.root);
    }
}
