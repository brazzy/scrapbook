package avltree;

public abstract class TreeNode {
    InnerNode parent;
    long value;

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

    abstract public boolean insert(long val);

    abstract public boolean remove(long val);

    abstract protected void removeChild(TreeNode child);

    abstract protected void toString(String prefix, StringBuilder buf);
}
