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
	
	@Override
    void next(TreeWalk walk){		
		if(walk.getPrevious() == getParent()){
			TreeNode n = child.leftmost();
			walk.setCurrent(n);
			walk.setPrevious(n.getParent());
		} else if(walk.getPrevious() == child || walk.getPrevious() == this){
            super.next(walk);				
		} else if(getParent() instanceof TreeNode){
			throw new IllegalStateException();
		} else {
			walk.setCurrent(null);				
		}
	}
}
