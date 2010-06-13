package avltree;

abstract class TreeNode {
    private TreeParent parent;
    private long value;

	public TreeNode(TreeParent parent, long value) {
		this.parent = parent;
		this.value = value;
	}
	
    public boolean isRoot(){
        return parent == null;
    }
    
    public boolean contains(long val){
        return this.value == val;
    }

    abstract public TreeNode rightmost();

    abstract public boolean insert(long val);

    abstract public boolean remove(long val);

    void check(TreeParent parent){
    	assert this.parent == parent : "Wrong parent for "+getValue();
    }

	protected TreeParent getParent() {
		return parent;
	}

	public long getValue() {
		return value;
	}

	protected void setParent(TreeParent parent) {
		this.parent = parent;
	}

	protected void setValue(long value) {
		this.value = value;
	}

    public String toString(){
    	StringBuilder b = new StringBuilder();
    	toString("", b);
    	return b.toString();
    }

    protected void toString(String prefix, StringBuilder buf){
        buf.append(prefix);
        buf.append("-(");
        buf.append(getValue());
        buf.append(")\n");
    }
}
