package avltree;

class NodeLeftChild extends NodeOneChild {

	public NodeLeftChild(InnerNode parent, TreeNode child, long val) {
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
			leaf.setParent(n);
			getParent().replaceChild(this, n);
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
	public TreeNode rightmost() {
		return this;
	}
}
