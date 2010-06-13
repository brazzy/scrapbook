package avltree;

interface TreeParent {
    void removeChild(TreeNode child);
    void replaceChild(TreeNode child, TreeNode replacement);
}
