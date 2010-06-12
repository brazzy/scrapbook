package avltree;

public class NodeTwoChildren extends InnerNode{
	TreeNode left;
	TreeNode right;
	
	public NodeTwoChildren(InnerNode parent, long val) {
		super(parent, val);
	}

	@Override
	public boolean insert(long val) {
    	if(this.value == val) {
    		return true;
    	} else if (val < this.value) {
    		return left.insert(val);
    	} else {
    		return right.insert(val);
    	}
	}

	@Override
	public boolean remove(long val) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void removeChild(TreeNode child) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void replaceChild(TreeNode child, TreeNode replacement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void toString(String prefix, StringBuilder buf) {
		// TODO Auto-generated method stub
		
	}

}
