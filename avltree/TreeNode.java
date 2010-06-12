package avltree;

abstract class TreeNode {
    private InnerNode parent;
    private long value;

	public TreeNode(InnerNode parent, long value) {
		this.parent = parent;
		this.value = value;
	}
	
    public boolean isRoot(){
        return parent == null;
    }
    
    public boolean contains(long val){
        return this.value == val;
    }

    abstract protected void replaceChild(TreeNode child, TreeNode replacement);

    abstract public TreeNode rightmost();

    abstract public boolean insert(long val);

    abstract public boolean remove(long val);

    abstract protected void removeChild(TreeNode child);

    abstract protected void toString(String prefix, StringBuilder buf);

	protected InnerNode getParent() {
		return parent;
	}

	public long getValue() {
		return value;
	}

	protected void setParent(InnerNode parent) {
		this.parent = parent;
	}

	protected void setValue(long value) {
		this.value = value;
	}
}
