package quicksort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

/**
 * Unit tests for in-place quicksort edge cases
 * 
 * @author Michael Borgwardt
 */
public class QuickSortTest {
    private static QuickSort qs = new QuickSort(new Random(42));

    @Test(expected = NullPointerException.class)
    public void testNull() {
        qs.quicksort(null);
    }

    @Test
    public void testEmpty() {
        int[] orig = new int[0];
        int[] array = orig.clone();
        qs.quicksort(array);
        assertTrue(Arrays.equals(orig, array));
    }

    @Test
    public void testSingle() {
        int[] orig = new int[] { -100 };
        int[] array = orig.clone();
        qs.quicksort(array);
        assertTrue(Arrays.equals(orig, array));
    }

    @Test
    public void testTwo() {
        int[] expected = new int[] { 1, 2 };
        int[] array = new int[] { 2, 1 };
        qs.quicksort(array);
        assertTrue(Arrays.equals(expected, array));
    }

    @Test
    public void testThree() {
        int[] expect = new int[] { Integer.MIN_VALUE, 0, Integer.MAX_VALUE };
        int[] array = new int[] { 0, Integer.MAX_VALUE, Integer.MIN_VALUE };
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testThree2() {
        int[] expect = new int[] { 1, 1, 1 };
        int[] array = new int[] { 1, 1, 1 };
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testThree3() {
        int[] expect = new int[] { 1, 2, 2 };
        int[] array = new int[] { 1, 2, 2 };
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testThree4() {
        int[] expect = new int[] { 1, 2, 2 };
        int[] array = new int[] { 2, 2, 1 };
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testThree5() {
        int[] expect = new int[] { 1, 2, 2 };
        int[] array = new int[] { 2, 1, 2 };
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testFour() {
        int[] expect = new int[] { Integer.MIN_VALUE, -100, 0,
                Integer.MAX_VALUE };
        int[] array = new int[] { 0, Integer.MAX_VALUE, Integer.MIN_VALUE, -100 };
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testPivot() {
        int[] expect = new int[] { 5, 5, 6, 6, 4, 4, 5, 5, 4, 4, 6, 6, 5, 5 };
        int[] array = expect.clone();
        Arrays.sort(expect);
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testSame() {
        int[] expect = new int[] { 1, 1, 1, 1, 1, 1, 1 };
        int[] array = expect.clone();
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testSameMany() {
        int[] expect = new int[100000];
        for (int i = 0; i < expect.length; i++) {
            expect[i] = 1;
        }
        int[] array = expect.clone();
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testPresortedAsc() {
        int[] expect = new int[100000];
        for (int i = 0; i < expect.length; i++) {
            expect[i] = i;
        }
        int[] array = expect.clone();
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testPresortedDesc() {
        int[] expect = new int[100000];
        for (int i = 0; i < expect.length; i++) {
            expect[i] = expect.length - i;
        }
        int[] array = expect.clone();
        Arrays.sort(expect);
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

    @Test
    public void testScrambled() {
        Random rnd = new Random(424242);
        int[] expect = new int[100000];
        for (int i = 0; i < expect.length; i++) {
            expect[i] = rnd.nextInt();
        }
        int[] array = expect.clone();
        Arrays.sort(expect);
        qs.quicksort(array);
        assertTrue(Arrays.equals(expect, array));
    }

}
