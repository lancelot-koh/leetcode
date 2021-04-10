package com.future.leetcode.bst;

import com.future.leetcode.bst.TreeNode;

public class BinarySearchTree {
    BinarySearchTree() {
    }

    public TreeNode insert(TreeNode root, int value) {
        if(root == null) {
            return new TreeNode(value);
        }

        if (root.value < value) {
            root.right = insert(root.right, value);
        } else {
            root.left = insert(root.left, value);
        }
        return root;
    }
}
