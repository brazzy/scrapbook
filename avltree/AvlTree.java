package avltree;

import java.util.Iterator;

/**
 * Implementation of an AVL tree (balanced binary search tree)
 * NOTE: so far, the AVL auto-balancing is not yet implemented!!
 *
 * @author Michael Borgwardt
 */
public class AvlTree implements Iterable<Long>, TreeParent{

    private TreeNode root;
    private int size;

    @Override
    public Iterator<Long> iterator(){
        return new TreeIterator(root);
    }

    public int size() {
        return size;
    }

    public void clear(){
        root = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Adds value to tree
     *
     * @param val to add
     * @return true if value was already present
     */
    public boolean insert(long val){
    	if(root == null){
    		root = new LeafNode(this, val);
    		size++;
    		return false;
    	} else {
    		boolean wasPresent = root.insert(val);
    		if(!wasPresent){
    		    size++;
    		}
    		return wasPresent;    		
    	}
    }

    /**
     * @return true if val is present in tree
     */
    public boolean contains(long val){
    	if(root==null){
    		return false;
    	}
    	return root.contains(val);
    }

    /**
     * Removes value from tree
     * @param val to remove
     * @return true if val was present in tree
     */
    public boolean remove(long val){
    	if(root==null){
    		return false;
    	}
		boolean wasPresent = root.remove(val);
		if(wasPresent){
		    size--;
		}
		return wasPresent;    
    }

    @Override
    public String toString() {
    	if(root==null){
    		return "Empty";
    	}
		StringBuilder b = new StringBuilder();
		root.toString("", b);
		return b.toString();
    }

    void check(){
    	if(!isEmpty()){
    		root.check(this);    		
    	}
    }
	
    /**
     * Iterates over the tree's contents
     *
     * @author Michael Borgwardt
     */
    private static class TreeIterator implements Iterator<Long>, TreeWalk{
        private TreeIterator(TreeNode root){
        	if(root != null){
                this.current = root.leftmost();
            	this.previous = this.current.getParent();
        	}
        }

        /** Current node, contains value that will be returned next. */
        private TreeNode current;

        /** Previous node, used for naviation */
        private Object previous;

        @Override
        public boolean hasNext(){
            return current != null;
        }
        @Override
        public Long next(){
        	long result = current.getValue();
        	current.next(this);
        	return result;
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
		public Object getPrevious() {
			return previous;
		}
		public void setPrevious(Object previous) {
			this.previous = previous;
		}
		public void setCurrent(TreeNode current) {
			this.current = current;
		}
    }

	@Override
	public void removeChild(TreeNode child) {
		if(root != child){
			throw new IllegalArgumentException();			
		}
		root = null;
	}

	@Override
	public void replaceChild(TreeNode child, TreeNode replacement) {
		if(root != child){
			throw new IllegalArgumentException();			
		}
		root = replacement;
	}

}
