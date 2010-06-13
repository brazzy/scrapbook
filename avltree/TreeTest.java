package avltree;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael Borgwardt
 *
 */
public class TreeTest
{
    AvlTree tree;

    @Before
    public void setUp(){
        tree = new AvlTree();
    }

    @Test
    public void testAdd(){
    	tree.check();
        assertFalse(tree.insert(42));
    	tree.check();
        assertTrue(tree.contains(42));
        assertFalse(tree.contains(66));
        assertFalse(tree.isEmpty());
        assertTrue(tree.insert(42));
        assertTrue(tree.contains(42));
        assertFalse(tree.isEmpty());
    }

    @Test
    public void testAddMultiple(){
    	tree.check();
        assertFalse(tree.insert(42));
    	tree.check();
        assertFalse(tree.insert(5));
    	tree.check();
        assertFalse(tree.insert(17));
    	tree.check();
        assertFalse(tree.insert(-100));
    	tree.check();
        assertFalse(tree.insert(8));
    	tree.check();
        assertFalse(tree.insert(6));
    	tree.check();
        assertFalse(tree.insert(-20));
    	tree.check();
        assertFalse(tree.insert(100));
    	tree.check();

        assertTrue(tree.insert(17));
    	tree.check();
        assertTrue(tree.insert(-100));
    	tree.check();

        assertTrue(tree.contains(42));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(17));
        assertTrue(tree.contains(-100));
        assertTrue(tree.contains(8));
        assertTrue(tree.contains(6));
        assertTrue(tree.contains(-20));
        assertTrue(tree.contains(100));
    }

    @Test
    public void testRemove(){
        tree.insert(42);
    	tree.check();
        assertTrue(tree.remove(42));
    	tree.check();
        assertTrue(tree.isEmpty());
        assertFalse(tree.remove(42));
    	tree.check();
        assertFalse(tree.contains(42));
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testRemoveMultiple(){
        tree.insert(42);
        tree.insert(-1);
        tree.insert(100);
        tree.insert(0);
        tree.insert(-17);
        tree.insert(38);
    	tree.check();

        assertTrue(tree.remove(42));
    	tree.check();
        assertFalse(tree.contains(42));
        assertFalse(tree.remove(42));
        assertTrue(tree.contains(-1));

        assertTrue(tree.remove(-1));
    	tree.check();
        assertFalse(tree.contains(-1));
        assertFalse(tree.remove(-1));
        assertTrue(tree.contains(100));

        assertTrue(tree.remove(100));
    	tree.check();
        assertFalse(tree.contains(100));
        assertFalse(tree.remove(100));
        assertTrue(tree.contains(0));

        assertTrue(tree.remove(0));
    	tree.check();
        assertFalse(tree.contains(0));
        assertFalse(tree.remove(0));
        assertTrue(tree.contains(-17));

        assertTrue(tree.remove(-17));
    	tree.check();
        assertFalse(tree.contains(-17));
        assertFalse(tree.remove(-17));
        assertTrue(tree.contains(38));

        assertTrue(tree.remove(38));
    	tree.check();
        assertFalse(tree.contains(38));
        assertFalse(tree.remove(38));

        assertTrue(tree.isEmpty());
    }

    @Test
    public void testIterateEmpty(){
        assertFalse(tree.iterator().hasNext());
    }

    @Test
    public void testIterate(){
        tree.insert(42);
        tree.insert(0);
        tree.insert(-100);
        tree.insert(100);
        tree.insert(42);
        tree.insert(1);
        tree.insert(200);
        System.out.println(tree);
        tree.insert(-17);
        tree.insert(55);
        tree.insert(38);
        Iterator<Long> it = tree.iterator();
        assertTrue(it.hasNext());
        assertEquals(-100, it.next().longValue());
        assertTrue(it.hasNext());
        assertEquals(-17, it.next().longValue());
        assertTrue(it.hasNext());
        assertEquals(0, it.next().longValue());
        assertTrue(it.hasNext());
        assertEquals(1, it.next().longValue());
        assertTrue(it.hasNext());
        assertEquals(38, it.next().longValue());
        assertTrue(it.hasNext());
        assertEquals(42, it.next().longValue());
        assertTrue(it.hasNext());
        assertEquals(55, it.next().longValue());
        assertTrue(it.hasNext());
        assertEquals(100, it.next().longValue());
        assertTrue(it.hasNext());
        assertEquals(200, it.next().longValue());
        assertFalse(it.hasNext());
    }


    @Test
    public void testClear(){
        tree.insert(55);
        tree.insert(38);
        tree.clear();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        assertFalse(tree.iterator().hasNext());
    }

    @Test
    public void testSize(){
        assertEquals(0, tree.size());
        tree.insert(55);
        assertEquals(1, tree.size());
        tree.insert(38);
        assertEquals(2, tree.size());
        tree.insert(-7);
        assertEquals(3, tree.size());
        tree.insert(100);
        assertEquals(4, tree.size());
        tree.insert(100);
        assertEquals(4, tree.size());
        tree.remove(38);
        assertEquals(3, tree.size());
        tree.remove(38);
        assertEquals(3, tree.size());
        tree.remove(55);
        assertEquals(2, tree.size());
    }
}
