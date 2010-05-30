package avltree;

import java.util.Iterator;

/**
 * @author Michael Borgwardt
 *
 */
public class AvlTree implements Iterable<Long>
{
    private static class TreeIterator implements Iterator<Long>
    {
        private static enum Direction{ LEFT, RIGHT, UP};

        private TreeIterator(AvlTree node)
        {
            this.node = node;
            this.dir = Direction.LEFT;
            next();
        }

        private AvlTree node;
        private Direction dir;
        private boolean finished;

        @Override
        public boolean hasNext()
        {
            return node.value != null && dir != null;
        }
        @Override
        public Long next()
        {
            Long result = node.value;
            switch(dir){
            case LEFT:
                traverseLeft();
                if(node.right != null){
                    dir = Direction.RIGHT;
                } else {
                    dir = Direction.UP;
                }
                break;
            case RIGHT:
                if(node.right!=null){
                    node = node.right;
                    traverseLeft();
                    dir = Direction.RIGHT;
                } else {
                    AvlTree previous;
                    do{
                        previous = node;
                        node = node.parent;
                    } while (node.parent != null && node.right == previous);
                    if(node.parent == null && node.right == previous)
                    {
                        dir = null;
                    }else {
                        dir = Direction.RIGHT;
                    }
                }
                break;
            case UP:
                node = node.parent;
                dir = Direction.LEFT;
                break;
            }
            return result;
        }

        private void traverseLeft(){
            while(node.left != null){
                node = node.left;
            }
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }

    }

    private Long value;
    private AvlTree left;
    private AvlTree right;
    private AvlTree parent;

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

    public boolean isLeaf(){
        return left == null && right == null;
    }

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

    public boolean contains(long val){
        return node(val) != null;
    }

    private AvlTree node(long val){
        if(value == null){
            return null;
        } else if(this.value == val){
            return this;
        } else if (val < this.value) {
            return left == null ? null : left.node(val);
        } else {
            return right == null ? null : right.node(val);
        }
    }

    private void pullUp(AvlTree child){
        assert child == left || child == right;
        value = child.value;
        left = child.left;
        right = child.right;
    }

    public boolean remove(long val)
    {
        if(this.value == null){
            return false;
        }

        if(this.value == val){
            if(left == null && right != null){
                pullUp(right);
            } else if(right == null && left != null){
                pullUp(left);
            } else if(right == null && left == null){
                value = null;
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
            } else if(left.isLeaf() && left.value == val){
                left = null;
                return true;
            } else{
                return left.remove(val);
            }
        } else {
            if(right == null){
                return false;
            } else if(right.isLeaf() && right.value == val){
                right = null;
                return true;
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
    public long getValue()
    {
        return value;
    }
}
