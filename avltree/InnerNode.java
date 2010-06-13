package avltree;

abstract class InnerNode extends TreeNode implements TreeParent{
	public InnerNode(TreeParent parent, long val) {
		super(parent, val);
	}

}
