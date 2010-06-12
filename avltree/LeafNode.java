package avltree;

public class LeafNode extends TreeNode{
    /** Creates empty tree */
    public LeafNode(InnerNode parent, long val){
    	super(parent, val);
    }

    @Override
    public boolean insert(long val){
    	InnerNode replacement;
    	if(this.value == val) {
    		return true;
    	} else if (val < this.value) {
    		replacement = new NodeLeftChild(this.parent, this, this.value);
    		this.value = val;
    	} else {
    		replacement = new NodeRightChild(this.parent, this, this.value);
    		this.value = val;		
    	}
        if(isRoot()){
        	//TODO;
        } else{
        	parent.replaceChild(this, replacement);
        }
        return false;
    }

    @Override
    public boolean remove(long val){
    	if(val==this.value){
            if(isRoot()){
            	//TODO;            	
            } else {
        		parent.removeChild(this);
            }
    		return true;            	
    	}else {
    		return false;
    	}
    }

	@Override
    protected void toString(String prefix, StringBuilder buf){
        throw new UnsupportedOperationException();
    }

	@Override
	public void removeChild(TreeNode child) {
        throw new UnsupportedOperationException();
	}

    @Override
    protected void replaceChild(TreeNode child, TreeNode replacement){
        throw new UnsupportedOperationException();
    }
}
