package avltree;

class LeafNode extends TreeNode{
    /** Creates empty tree */
    public LeafNode(InnerNode parent, long val){
    	super(parent, val);
    }

    @Override
    public boolean insert(long val){
    	InnerNode replacement;
    	if(this.getValue() == val) {
    		return true;
    	} else if (val < this.getValue()) {
    		replacement = new NodeLeftChild(this.getParent(), this, this.getValue());
    		this.setValue(val);
    	} else {
    		replacement = new NodeRightChild(this.getParent(), this, this.getValue());
    		this.setValue(val);		
    	}
    	getParent().replaceChild(this, replacement);
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
    protected void toString(String prefix, StringBuilder buf){
        // TODO
    }

	@Override
	public void removeChild(TreeNode child) {
        throw new UnsupportedOperationException();
	}

    @Override
    protected void replaceChild(TreeNode child, TreeNode replacement){
        throw new UnsupportedOperationException();
    }

	@Override
	public TreeNode rightmost() {
		return this;
	}
}
