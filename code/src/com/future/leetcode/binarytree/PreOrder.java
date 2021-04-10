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


    public static List<Integer> MorrisTraversal(TreeNode root) {
        /**
         * Morris traversal rule
         * Current node - curr
         *
         * 1 curr has no left child, curr move to its right: curr = curr.right
         * 2 curr has left child, find the right most node(rightMostNode) of the current sub trees.
         *  2.1 if rightMostNode.right point to null, then  rightMostNode.right = curr,
         *      and curr move left: curr = curr.left.
         *  2.2 if rightMostNode.right point to curr, then rightMostNode.right = null,
         *      and curr move right: curr = curr.right
         */
        LinkedList<Integer> res = new LinkedList<>();

        TreeNode node = root;
        while (node != null) {
            // rule 1
            if (node.left == null) {
                res.add(node.value);
                node = node.right;
            } else {
                TreeNode predecessor = node.left;
                // find the right most node
                while (predecessor.right != null && predecessor.right != node) {
                    predecessor = predecessor.right;
                }

//               rule 2.1
                if(predecessor.right == null) {
                    res.add(node.value);
                    predecessor.right = node;
                    node = node.left;
                } else {
                    predecessor.right = null;
                    node = node.right;
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(1);

        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);

        tree.root.right.left = new TreeNode(6);
        tree.root.right.right = new TreeNode(7);
//        preOrderTraversal(tree.root);
        List<Integer> res = preOrderTraversal2(tree.root);
        for (Integer t: res) {
            System.out.println(t);
        }

        MorrisTraversal(tree.root);
    }
}
