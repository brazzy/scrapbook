package avltree;

class LeafNode extends TreeNode{
    /** Creates empty tree */
    public LeafNode(TreeParent parent, long val){
    	super(parent, val);
    }

    @Override
    public boolean insert(long val){
    	InnerNode replacement;
    	if(this.getValue() == val) {
    		return true;
    	} else if (val < this.getValue()) {
    		replacement = new NodeLeftChild(this.getParent(), this, this.getValue());
    	} else {
    		replacement = new NodeRightChild(this.getParent(), this, this.getValue());
    	}
		setValue(val);		
    	getParent().replaceChild(this, replacement);
		setParent(replacement);
        return false;
    }

    @Override
    public boolean remove(long val){
    	if(val==this.getValue()){
    		getParent().removeChild(this);
    		return true;            	
    	}else {
    		return false;
    	}
    }

	@Override
	public TreeNode rightmost() {
		return this;
	}
}
