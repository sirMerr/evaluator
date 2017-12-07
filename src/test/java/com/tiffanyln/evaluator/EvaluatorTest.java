package com.tiffanyln.evaluator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class EvaluatorTest {
    @Parameters
    public static Collection<Object[]> data() {
        /*Returns a fixed-size list backed by the specified array.  (Changes to
     * the returned list "write through" to the array.)  This method acts
                * as bridge between array-based and collection-based APIs, in
                * combination with {@link Collection#toArray}.  The returned list is
     * serializable and implements {@link RandomAccess
        }.
     */
        return Arrays.asList()
    }
}

}