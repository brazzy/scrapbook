package avltree;

abstract class NodeOneChild  extends InnerNode{
	protected TreeNode child;

	public NodeOneChild(InnerNode parent, TreeNode child, long val) {
		super(parent, val);
		this.child = child;
	}

	@Override
	protected void removeChild(TreeNode child) {
		LeafNode n = new LeafNode(this.getParent(), this.getValue());
		getParent().replaceChild(this, n);
	}

	@Override
	protected void replaceChild(TreeNode child, TreeNode replacement) {
		if(this.child != child){
			throw new IllegalStateException();
		} else {
			this.child = replacement;
		}
	}

	@Override
	protected void toString(String prefix, StringBuilder buf) {
		// TODO Auto-generated method stub
		
	}

}
