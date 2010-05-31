package avltree;

import java.util.Iterator;

/**
 * Implementation of an AVL tree (balanced binary search tree)
 * NOTE: so far, the AVL auto-balancing is not yet implemented!!
 *
 * @author Michael Borgwardt
 */
public class AvlTree implements Iterable<Long>{
    /**
     * Iterates over the tree's contents
     *
     * @author Michael Borgwardt
     */
    private static class TreeIterator implements Iterator<Long>{
        /** Direction in which to iterate next */
        private static enum Direction{ LEFT, RIGHT, UP};

        private TreeIterator(AvlTree node){
            this.node = node;
            this.dir = Direction.LEFT;
            next();
        }

        /** Current node, contains value that will be returned next. */
        private AvlTree node;

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
                node = node.right;
                traverseLeft();
                break;
            case UP:
                AvlTree previous;
                do{
                    previous = node;
                    node = node.parent;
                } while (node.parent != null &&
                         node.right == previous);
                if(node.parent == null &&
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
            while(node.left != null){
                node = node.left;
            }
            if(node.right != null){
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

    /**
     * Value contained in this node. Can be null, but only at
     * the root of the empty tree.
     */
    private Long value;

    /** Left subtree */
    private AvlTree left;

    /** Right subtree */
    private AvlTree right;

    /** Parent node */
    private AvlTree parent;

    /** Creates empty tree */
    public AvlTree(){
        this(null, null);
    }
    private AvlTree(AvlTree parent, Long value){
        this.parent = parent;
        this.value = value;
    }

    private void ensureLeft(){
        if(this.left == null){
            this.left = new AvlTree(this, null);
        }
    }

    private void ensureRight(){
        if(this.right == null){
            this.right = new AvlTree(this, null);
        }
    }

    public boolean isEmpty(){
        return value == null;
    }

    public void clear(){
        value = null;
        left = null;
        right = null;
    }

    public Iterator<Long> iterator(){
        return new TreeIterator(this);
    }

    private boolean isLeaf(){
        return left == null && right == null;
    }

    /**
     * Adds value to tree
     *
     * @param val to add
     * @return true if value was already present
     */
    public boolean insert(long val){
        if(value == null){
            this.value = val;
            return false;
        } else if(this.value == val){
            return true;
        } else if(val < this.value){
            ensureLeft();
            return left.insert(val);
        } else {
            ensureRight();
            return right.insert(val);
        }
    }

    /**
     * @return true if val is present in tree
     */
    public boolean contains(long val){
        if(value == null){
            return false;
        } else if(this.value == val){
            return true;
        } else if (val < this.value) {
            return left == null ? false : left.contains(val);
        } else {
            return right == null ? false : right.contains(val);
        }
    }

    private void pullUp(AvlTree child){
        assert child == left || child == right;
        value = child.value;
        left = child.left;
        right = child.right;
    }

    /**
     * Removes value from tree
     * @param val to remove
     * @return true if val was present in tree
     */
    public boolean remove(long val){
        if(this.value == null){
            return false;
        }

        if(this.value == val){
            if(isLeaf()){
                if(parent == null){
                    value = null;
                } else if(parent.left == this){
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else if(left == null){
                pullUp(right);
            } else if(right == null){
                pullUp(left);
            } else{
                AvlTree pre = predecessor();
                Long preVal = pre.value;
                remove(pre.value);
                this.value = preVal;
            }
            return true;
        } else if (val < this.value) {
            if(left == null){
                return false;
            } else{
                return left.remove(val);
            }
        } else {
            if(right == null){
                return false;
            } else{
                return right.remove(val);
            }
        }
    }

    @Override
    public String toString(){
        return toString("");
    }

    private String toString(String prefix){
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        buf.append(value);
        buf.append(')');
        buf.append('\n');
        if(right != null)
        {
            buf.append(prefix);
            buf.append("|-");
            if(left == null)
            {
                buf.append(right.toString(prefix+"   "));
            } else {
                buf.append(right.toString(prefix+"|  "));
            }
        }
        if(left != null)
        {
            buf.append(prefix);
            buf.append("|-");
            buf.append(left.toString(prefix+"   "));
        }
        return buf.toString();
    }

    private AvlTree predecessor(){
        AvlTree result = left;
        while(result.right != null){
            result = result.right;
        }
        return result;
    }

    public long getValue(){
        return value;
    }
}
