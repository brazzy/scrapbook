package avltree;

abstract class NodeOneChild extends InnerNode implements TreeParent{
	protected TreeNode child;

	public NodeOneChild(TreeParent parent, TreeNode child, long val) {
		super(parent, val);
		this.child = child;
	}

	@Override
	public void removeChild(TreeNode child) {
		LeafNode n = new LeafNode(this.getParent(), this.getValue());
		getParent().replaceChild(this, n);
	}

	@Override
	public void replaceChild(TreeNode child, TreeNode replacement) {
		if(this.child != child){
			throw new IllegalStateException();
		} else {
			this.child = replacement;
		}
	}
	
	@Override
    void check(TreeParent parent){
		super.check(parent);
		child.check(this);
    }
}
