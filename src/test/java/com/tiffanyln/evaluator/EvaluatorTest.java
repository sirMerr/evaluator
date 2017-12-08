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
        return Arrays.asList(
                /* Basic Arithmetic */
                makeTestInfix(new String[]{"1", "+", "3"}, 2),
                makeTestInfix(new String[]{"1", "+", "-3"}, -2),
                makeTestInfix(new String[]{"-1", "+", "3"},2),
                makeTestInfix(new String[]{"+1", "+", "3"}, 4),
                makeTestInfix(new String[]{"+1", "+", "+3"}, 4),
                makeTestInfix(new String[]{"+1", "+", "3"},2),
                makeTestInfix(new String[]{"-400", "+", "-1"}, -401),
                makeTestInfix(new String[]{"1", "-", "3"}, -2),
                makeTestInfix(new String[]{"1", "-", "-3"}, 4),
                makeTestInfix(new String[]{"-1", "-", "3"},-4),
                makeTestInfix(new String[]{"-400", "-", "-1"}, -399),
                makeTestInfix(new String[]{"3", "*", "3"}, 9),
                makeTestInfix(new String[]{"5", "*", "-3"}, -15),
                makeTestInfix(new String[]{"-23", "*", "3"},-69),
                makeTestInfix(new String[]{"-44", "*", "-1"}, 44),
                makeTestInfix(new String[]{"3", "/", "3"}, 0),
                makeTestInfix(new String[]{"5", "/", "-3"}, 5/-3),
                makeTestInfix(new String[]{"-23", "/", "3"},-23/3),
                makeTestInfix(new String[]{"-44", "/", "-1"}, 44),
                /* Multiplication/Division with 0 */
                makeTestInfix(new String[]{"3", "*", "0"}, 0),
                makeTestInfix(new String[]{"0", "*", "-3"}, 0),
                makeTestInfix(new String[]{"0", "*", "-0"},0),
                makeTestInfix(new String[]{"3", "/", "0"}, Double.NEGATIVE_INFINITY),
                makeTestInfix(new String[]{"0", "/", "-3"}, Double.NaN),
                makeTestInfix(new String[]{"0", "/", "0"},Double.NaN),
                /* Parenthesis */
                makeTestInfix(new String[]{"3", "+", "2", "*", "(", "5", "*", "7",")", "*", "0"}, 0),
                makeTestInfix(new String[]{"(","3", "*", "-3",")"}, -9),
                makeTestInfix(new String[]{"+2", "(","3", "*", "-3",")"}, -18),
                /* Decimals */
                makeTestInfix(new String[]{"1.5", "*", "3"}, 4.5)
        );
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

    /**
     * Makes test infix queue and the corresponding expected answer
     *
     * @param symbols
     *      Operands and operators to add to the infix queue in order of declaration
     * @param expectedAnswer
     *      Expected floating point number
     * @return Object[]
     *      The infix expression and expected answer
     */
    private static Object[] makeTestInfix(String[] symbols, double expectedAnswer) {
        Queue<String> expression = new DynamicArray<>();
        for (String symbol: symbols) {
            expression.add(symbol);
        }

        return new Object[]{expression, expectedAnswer};
    }
}