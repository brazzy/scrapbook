package avltree;

import java.util.Iterator;

/**
 * Implementation of an AVL tree (balanced binary search tree)
 * NOTE: so far, the AVL auto-balancing is not yet implemented!!
 *
 * @author Michael Borgwardt
 */
public class AvlTree implements Iterable<Long>{

    private TreeNode root;
    private int size;

    @Override
    public Iterator<Long> iterator(){
        return root.iterator();
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
		boolean wasPresent = root.insert(val);
		if(!wasPresent){
		    size++;
		}
		return wasPresent;
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
		boolean wasPresent = root.remove(val);
		if(wasPresent){
		    size--;
		}
		return wasPresent;    
    }

    @Override
    public String toString() {
		StringBuilder b = new StringBuilder();
		root.toString("", b);
		return b.toString();
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
