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
        assertFalse(tree.insert(42));
        assertTrue(tree.contains(42));
        assertFalse(tree.contains(66));
        assertFalse(tree.isEmpty());
        assertTrue(tree.insert(42));
        assertTrue(tree.contains(42));
        assertFalse(tree.isEmpty());
    }

    @Test
    public void testAddMultiple(){
        assertFalse(tree.insert(42));
        assertFalse(tree.insert(5));
        assertFalse(tree.insert(17));
        assertFalse(tree.insert(-100));
        assertFalse(tree.insert(8));
        assertFalse(tree.insert(6));

        assertTrue(tree.contains(42));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(17));
        assertTrue(tree.contains(-100));
        assertTrue(tree.contains(8));
        assertTrue(tree.contains(6));
    }


    @Test
    public void testRemove(){
        tree.insert(42);
        assertTrue(tree.remove(42));
        assertTrue(tree.isEmpty());
        assertFalse(tree.remove(42));
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

        assertTrue(tree.remove(42));
        assertFalse(tree.contains(42));
        assertFalse(tree.remove(42));
        assertTrue(tree.contains(-1));

        assertTrue(tree.remove(-1));
        assertFalse(tree.contains(-1));
        assertFalse(tree.remove(-1));
        assertTrue(tree.contains(100));

        assertTrue(tree.remove(100));
        assertFalse(tree.contains(100));
        assertFalse(tree.remove(100));
        assertTrue(tree.contains(0));

        assertTrue(tree.remove(0));
        assertFalse(tree.contains(0));
        assertFalse(tree.remove(0));
        assertTrue(tree.contains(-17));

        assertTrue(tree.remove(-17));
        assertFalse(tree.contains(-17));
        assertFalse(tree.remove(-17));
        assertTrue(tree.contains(38));

        assertTrue(tree.remove(38));
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
        System.out.println(tree);
        tree.insert(55);
        tree.insert(38);
        System.out.println(tree);
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


}
