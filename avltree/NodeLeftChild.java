package avltree;

class NodeLeftChild extends NodeOneChild {

	public NodeLeftChild(TreeParent parent, TreeNode child, long val) {
		super(parent, child, val);
	}

	@Override
	public boolean insert(long val) {
		if(this.getValue() == val){
			return true;
		} else if(val < this.getValue()) {
			return child.insert(val);
		} else {
			LeafNode leaf = new LeafNode(null, val);
			NodeTwoChildren n = new NodeTwoChildren(getParent(), leaf, child, getValue());
			child.setParent(n);
			leaf.setParent(n);
			getParent().replaceChild(this, n);
			return false;
		}
	}

	@Override
    public boolean contains(long val){
        if(val==getValue()){
        	return true;
        } else if(val < getValue()){
        	return child.contains(val);
        } else {
        	return false;
        }
    }
	
	@Override
	public boolean remove(long val) {
		if(this.getValue() == val){
			getParent().replaceChild(this, child);
			return true;
		} else if(val < this.getValue()) {
			return child.remove(val);
		} else {
			return false;
		}
	}

	@Override
	protected void toString(String prefix, StringBuilder buf) {
		super.toString(prefix, buf);
        buf.append(prefix+"  |-\n");
        child.toString(prefix+"   ", buf);
	}

	@Override
    void check(TreeParent parent){
		assert getValue() > child.getValue();
		super.check(parent);
    }
	
	@Override
	public TreeNode rightmost() {
		return this;
	}
}
