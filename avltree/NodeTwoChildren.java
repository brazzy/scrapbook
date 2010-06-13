package avltree;

class NodeTwoChildren extends InnerNode{
	private TreeNode left;
	private TreeNode right;
	
	public NodeTwoChildren(TreeParent parent, TreeNode right, TreeNode left, long val) {
		super(parent, val);
		this.right = right;
		this.left = left;
	}

	@Override
    public boolean contains(long val){
        if(val==getValue()){
        	return true;
        } else if(val < getValue()){
        	return left.contains(val);
        } else {
        	return right.contains(val);
        }
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
            long preVal = pre.getValue();
            setValue(preVal);
            pre.remove(preVal);
            return true;
    	} else if (val < this.getValue()) {
    		return left.remove(val);
    	} else {
    		return right.remove(val);
    	}
	}

	@Override
	public void removeChild(TreeNode child) {
		if(child == left){
			NodeRightChild n = new NodeRightChild(getParent(), this.right, getValue());
			this.right.setParent(n);
			getParent().replaceChild(this, n);
		} else if(child == right) {
			NodeLeftChild n = new NodeLeftChild(getParent(), this.left, getValue());
			this.left.setParent(n);
			getParent().replaceChild(this, n);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void replaceChild(TreeNode child, TreeNode replacement) {
		if(child == left){
			left = replacement;
		} else if(child == right) {
			right = replacement;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	protected void toString(String prefix, StringBuilder buf) {
		super.toString(prefix, buf);
        right.toString(prefix+"  |", buf);
        left.toString(prefix+"   ", buf);
	}

	@Override
    void check(TreeParent parent){
		super.check(parent);
		assert getValue() < right.getValue();
		assert getValue() > left.getValue();
		right.check(this);
		left.check(this);
    }

	@Override
	public TreeNode rightmost() {
		return right.rightmost();
	}
}
