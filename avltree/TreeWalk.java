package avltree;

interface TreeWalk {
	void setCurrent(TreeNode current);
	void setPrevious(Object previous);
	Object getPrevious();
}
