package com.tiffanyln.evaluator;

import com.tiffanyln.exceptions.IllegalInfixFormat;
import com.tiffanyln.interfaces.Queue;
import com.tiffanyln.structures.DynamicArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EvaluatorTest {
    Queue<String> infixExpression;
    double expectedAnswer;

    @Parameters(name = "{index} plan[{0} = {1}]")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new DynamicArray<>(new String[]{"1", "2", "3"}), 2},
                {new DynamicArray<>(new String[]{"1", "+", "3"}), 4},
                {new DynamicArray<>(new String[]{"1", "+", "-3"}), -2},
                {new DynamicArray<>(new String[]{"-1", "+", "3"}),2},
                {new DynamicArray<>(new String[]{"+1", "+", "3"}), 4},
                {new DynamicArray<>(new String[]{"+1", "+", "+3"}), 4},
                {new DynamicArray<>(new String[]{"+1", "+", "3"}),2},
                {new DynamicArray<>(new String[]{"-400", "+", "-1"}), -401},
                {new DynamicArray<>(new String[]{"1", "-", "3"}), -2},
                {new DynamicArray<>(new String[]{"1", "-", "-3"}), 4},
                {new DynamicArray<>(new String[]{"-1", "-", "3"}),-4},
                {new DynamicArray<>(new String[]{"-400", "-", "-1"}), -399},
                {new DynamicArray<>(new String[]{"3", "*", "3"}), 9},
                {new DynamicArray<>(new String[]{"5", "*", "-3"}), -15},
                {new DynamicArray<>(new String[]{"-23", "*", "3"}),-69},
                {new DynamicArray<>(new String[]{"-44", "*", "-1"}), 44},
                {new DynamicArray<>(new String[]{"3", "/", "3"}), 0},
                {new DynamicArray<>(new String[]{"5", "/", "-3"}), 5/-3},
                {new DynamicArray<>(new String[]{"-23", "/", "3"}),-23/3},
                {new DynamicArray<>(new String[]{"-44", "/", "-1"}), 44},
                /* Multiplication/Division with 0 */
                {new DynamicArray<>(new String[]{"3", "*", "0"}), 0},
                {new DynamicArray<>(new String[]{"0", "*", "-3"}), 0},
                {new DynamicArray<>(new String[]{"0", "*", "-0"}),0},
                {new DynamicArray<>(new String[]{"3", "/", "0"}), Double.NEGATIVE_INFINITY},
                {new DynamicArray<>(new String[]{"0", "/", "-3"}), Double.NaN},
                {new DynamicArray<>(new String[]{"0", "/", "0"}),Double.NaN},
                /* Parenthesis */
                {new DynamicArray<>(new String[]{"3", "+", "2", "*", "(", "5", "*", "7",")", "*", "0"}), 0},
                {new DynamicArray<>(new String[]{"(","3", "*", "-3",")"}), -9},
                {new DynamicArray<>(new String[]{"+2", "(","3", "*", "-3",")"}), -18},
                /* Decimals */
                {new DynamicArray<>(new String[]{"1.5", "*", "3"}), 4.5}
        });
    }

    public EvaluatorTest(Queue<String> infixExpression, double expectedAnswer) {
        this.infixExpression = infixExpression;
        this.expectedAnswer = expectedAnswer;
    }

    @Test
    public void testEvaluate() throws IllegalInfixFormat {
        double result = Double.parseDouble(new Evaluator().evaluate(infixExpression).remove());

        assertEquals(expectedAnswer, result, 0);
    }
}