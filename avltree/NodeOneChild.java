package avltree;

public abstract class NodeOneChild  extends InnerNode{
	TreeNode child;

	public NodeOneChild(InnerNode parent, TreeNode child, long val) {
		super(parent, val);
		this.child = child;
	}

	@Override
	protected void removeChild(TreeNode child) {
		// TODO Auto-generated method stub
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
