package com.future.leetcode.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrder {

    public static List<List<Integer>> LevelOrderTraversal(TreeNode root) {
        List<List<Integer>>  res = new ArrayList<>();
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;

        while (!queue.isEmpty()) {
            res.add(new ArrayList<>());

            int level_length = queue.size();
            for(int i = 0; i < level_length; i++) {
                TreeNode node = queue.remove();
                res.get(level).add(node.value);

                if(node.left != null) {
                    queue.add(node.left);
                }

                if(node.right != null) {
                    queue.add(node.right);
                }
            }
            level++;
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

        LevelOrderTraversal(tree.root);
    }
}
