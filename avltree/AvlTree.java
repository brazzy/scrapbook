package avltree;

import java.util.Iterator;

/**
 * Implementation of an AVL tree (balanced binary search tree)
 * NOTE: so far, the AVL auto-balancing is not yet implemented!!
 *
 * @author Michael Borgwardt
 */
public class AvlTree implements Iterable<Long>{

    TreeNode root = new TreeNode(null);

    @Override
    public Iterator<Long> iterator(){
        return root.iterator();
    }

    public void clear(){
        root = null;
    }

    public boolean isEmpty(){
        return root.isEmpty();
    }

    /**
     * Adds value to tree
     *
     * @param val to add
     * @return true if value was already present
     */
    public boolean insert(long val){
	return root.insert(val);
    }

    /**
     * @return true if val is present in tree
     */
    public boolean contains(long val){
	return root.contains(val);
    }

    /**
     * Removes value from tree
     * @param val to remove
     * @return true if val was present in tree
     */
    public boolean remove(long val){
	return root.remove(val);
    }

    @Override
    public String toString() {
	StringBuilder b = new StringBuilder();
	root.toString("", b);
	return b.toString();
    }

    /**
     * Basic (empty) node
     *
     * @author Michael Borgwardt
     */
    private class TreeNode{
        /** Parent node */
        protected TreeNode parent;

        /** Creates empty tree */
        public TreeNode(TreeNode parent){
            this.parent = parent;
        }

        public boolean isEmpty(){
            return true;
        }

        public boolean isRoot(){
            return parent == null;
        }

        public Iterator<Long> iterator(){
            return new Iterator<Long>(){
		@Override
		public boolean hasNext() {
		    return false;
		}
		@Override
		public Long next() {
		    throw new UnsupportedOperationException();
		}
		@Override
		public void remove() {
		    throw new UnsupportedOperationException();
		}
            };
        }

        protected void replace(TreeNode child, TreeNode replacement){
            throw new UnsupportedOperationException();
        }

        public boolean insert(long val){
            ContentNode newNode = new ContentNode(parent, val);
            if(isRoot()){
        	AvlTree.this.root = newNode;
            } else{
        	parent.replace(this, newNode);
            }
            return false;
        }

        public boolean contains(long val){
            return false;
        }

        public boolean remove(long val){
            return false;
        }

        protected void toString(String prefix, StringBuilder buf){
            throw new UnsupportedOperationException();
        }

        public long getValue(){
            throw new UnsupportedOperationException();
        }

        protected TreeNode rightmost(){
            return this.parent;
        }
    }

    /**
     * Node with content.
     *
     * @author Michael Borgwardt
     */
    private class ContentNode extends TreeNode{
        /**
         * Value contained in this node.
         */
        private Long value;

        /** Left subtree */
        private TreeNode left;

        /** Right subtree */
        private TreeNode right;

        private ContentNode(TreeNode parent, Long value){
            super(parent);
            this.value = value;
            this.left = new TreeNode(this);
            this.right = new TreeNode(this);
        }

        @Override
        public boolean isEmpty(){
            return value == null;
        }

        @Override
        public Iterator<Long> iterator(){
            return new TreeIterator(this);
        }

        private boolean isLeaf(){
            return left.isEmpty() && right.isEmpty();
        }

        @Override
        public boolean insert(long val){
            if(value == null){
                this.value = val;
                return false;
            } else if(this.value == val){
                return true;
            } else if(val < this.value){
                return left.insert(val);
            } else {
                return right.insert(val);
            }
        }

        @Override
        public boolean contains(long val){
            if(value == null){
                return false;
            } else if(this.value == val){
                return true;
            } else if (val < this.value) {
                return left.contains(val);
            } else {
                return right.contains(val);
            }
        }

        @Override
	protected void replace(TreeNode child, TreeNode replacement){
            replacement.parent = this;
            if(child == left){
        	left = replacement;
            } else {
        	right = replacement;
            }
        }

        @Override
        public boolean remove(long val){
            if(this.value == null){
                return false;
            }

            if(this.value == val){
                if(isLeaf()){
                    TreeNode empty = new TreeNode(null);
                    if(isRoot()){
                	AvlTree.this.root = empty;
                    } else {
                        parent.replace(this, empty);
                    }
                } else if(left.isEmpty()){
                    parent.replace(this, right);
                } else if(right.isEmpty()){
                    parent.replace(this, left);
                } else{
                    TreeNode pre = left.rightmost();
                    Long preVal = pre.getValue();
                    pre.remove(preVal);
                    this.value = preVal;
                }
                return true;
            } else if (val < this.value) {
                return left.remove(val);
            } else {
                return right.remove(val);
            }
        }


        @Override
	public long getValue(){
            return this.value;
        }

        @Override
	protected TreeNode rightmost(){
            return right.rightmost();
        }

        @Override
	protected void toString(String prefix, StringBuilder buf){
            buf.append('(');
            buf.append(value);
            buf.append(')');
            buf.append('\n');
            if(!right.isEmpty())
            {
                buf.append(prefix);
                buf.append("|-");
                if(left.isEmpty())
                {
                    right.toString(prefix+"   ", buf);
                } else {
                    right.toString(prefix+"|  ", buf);
                }
            }
            if(!left.isEmpty())
            {
                buf.append(prefix);
                buf.append("|-");
                left.toString(prefix+"   ", buf);
            }
        }
    }

    /**
     * Iterates over the tree's contents
     *
     * @author Michael Borgwardt
     */
    private static class TreeIterator implements Iterator<Long>{
        /** Direction in which to iterate next */
        private static enum Direction{ LEFT, RIGHT, UP};

        private TreeIterator(ContentNode node){
            this.node = node;
            this.dir = Direction.LEFT;
            next();
        }

        /** Current node, contains value that will be returned next. */
        private ContentNode node;

        /** Direction in which to iterate next */
        private Direction dir;

        @Override
        public boolean hasNext(){
            return node.value != null && dir != null;
        }
        @Override
        public Long next(){
            Long result = node.value;
            switch(dir){
            case LEFT:
                traverseLeft();
                break;
            case RIGHT:
                node = (ContentNode)node.right;
                traverseLeft();
                break;
            case UP:
                ContentNode previous;
                do{
                    previous = node;
                    node = (ContentNode)node.parent;
                } while (!node.isRoot() &&
                         node.right == previous);
                if(node.isRoot() &&
                   node.right == previous)
                {
                    dir = null;
                }else {
                    dir = Direction.RIGHT;
                }
                break;
            }
            return result;
        }

        private void traverseLeft(){
            while(!node.left.isEmpty()){
                node = (ContentNode)node.left;
            }
            if(!node.right.isEmpty()){
                dir = Direction.RIGHT;
            } else {
                dir = Direction.UP;
            }
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
}
