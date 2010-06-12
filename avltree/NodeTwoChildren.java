package avltree;

class NodeTwoChildren extends InnerNode{
	private TreeNode left;
	private TreeNode right;
	
	public NodeTwoChildren(InnerNode parent, TreeNode right, TreeNode left, long val) {
		super(parent, val);
		this.right = right;
		this.left = left;
	}

	@Override
	public boolean insert(long val) {
    	if(this.getValue() == val) {
    		return true;
    	} else if (val < this.getValue()) {
    		return left.insert(val);
    	} else {
    		return right.insert(val);
    	}
	}

	@Override
	public boolean remove(long val) {
    	if(this.getValue() == val) {
            TreeNode pre = left.rightmost();
            Long preVal = pre.getValue();
            pre.remove(preVal);
            setValue(preVal);
            return true;
    	} else if (val < this.getValue()) {
    		return left.remove(val);
    	} else {
    		return right.remove(val);
    	}
	}

	@Override
	protected void removeChild(TreeNode child) {
		if(child == left){
			NodeRightChild n = new NodeRightChild(getParent(), this.right, getValue());
			getParent().replaceChild(this, n);
		} else if(child == right) {
			NodeLeftChild n = new NodeLeftChild(getParent(), this.left, getValue());
			getParent().replaceChild(this, n);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	protected void replaceChild(TreeNode child, TreeNode replacement) {
		if(child == left){
			left = child;
		} else if(child == right) {
			right = child;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	protected void toString(String prefix, StringBuilder buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TreeNode rightmost() {
		return right.rightmost();
	}
}
