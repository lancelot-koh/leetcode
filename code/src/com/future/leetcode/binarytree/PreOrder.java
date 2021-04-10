package com.future.leetcode.binarytree;

import apple.laf.JRSUIUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PreOrder {
    public static List<Integer> preOrderTraversal(TreeNode root) {
//        top -> bottom
//        root -> left -> right

        List<Integer> res = new LinkedList<>();
        if (root == null) {
            return res;
        }

//        LinkedList<TreeNode> stack = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
//        stack.add(root);


        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.value);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }

            //pollLast
//            TreeNode node = stack.pollLast();
//            res.add(node.value);
//            if (node.right != null) {
//                stack.add(node.right);
//            }
//            if (node.left != null) {
//                stack.add(node.left);
//            }
        }// [1,2,4,5,3,6,7]
        return res;

    }

    public static List<Integer> preOrderTraversal2(TreeNode root) {
        if (root == null) {
            return null;
        }

        List<Integer> res = new LinkedList<>();
        helper(root, res);
        return res;
    }

    public static void helper(TreeNode node, List<Integer> res){
        if (node == null) {
            return;
        }
        res.add(node.value);
        helper(node.left, res);
        helper(node.right, res);
    }


    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(1);

        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);

        tree.root.right.left = new TreeNode(6);
        tree.root.right.right = new TreeNode(7);
        preOrderTraversal(tree.root);
        preOrderTraversal2(tree.root);
    }
}
