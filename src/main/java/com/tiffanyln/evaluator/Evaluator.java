package com.tiffanyln.evaluator;

import com.tiffanyln.exceptions.IllegalInfixFormat;
import com.tiffanyln.interfaces.Queue;
import com.tiffanyln.interfaces.Stack;
import com.tiffanyln.structures.DynamicArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class uses posfix notation to solve equations
 *
 * @author Tiffany Le-Nguyen
 */
public class Evaluator {
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    // Stack which contains the operators
    private Stack<String> operators;
    // Infix expression converted to postfix
    private Queue<String> postfix;

    // Precedence of next value in the infix
    private int precedence;

    // Counter for operators. Technically every odd position in infix = operator
    private int operatorCount;
    // Counter for operands (quantities). It should have 1 more than operators.
    // Technically, every even position in infix = operand
    private int operandCount;

    // Counter for open and parenthesis (should be equal or else invalid)
    private int openParenthesisCount;
    private int closedParenthesisCount;

    // True if the last string in the infix expression was an operand.
    private boolean lastIsOperand;

    // The higher the precedence, the higher the priority
    private static final int DEFAULT_PRECEDENCE = 0;
    private static final int LOW_PRECEDENCE = 1;
    private static final int HIGH_PRECEDENCE = 2;

    public Evaluator() {
        log.debug("In Evaluator constructor");
        this.precedence = DEFAULT_PRECEDENCE;
        this.lastIsOperand = false;
        this.operandCount = 0;
        this.operatorCount = 0;
        this.openParenthesisCount = 0;
        this.closedParenthesisCount = 0;
    }

    /**
     * Convert to postfix and calculate the answer. The input is
     * @param infixExpression
     *      A Queue containing the infix expression
     * @return Queue<Double>
     *      A Queue with the result as the only entry
     * @throws IllegalInfixFormat
     */
    public Queue<Double> evaluate(Queue<String> infixExpression) throws IllegalInfixFormat {
        log.debug("In evaluate");

        // Check infix format is alright
        if (infixExpression == null) {
            throw new IllegalInfixFormat();
        }
        int size = infixExpression.size();
        // Infix in postfix
        postfix = new DynamicArray<>(size);
        // Stack which contains operators
        operators = new DynamicArray<>(size);

        for (int i = 0; i < size; i++) {
            String symbol = infixExpression.remove();
            log.debug("Symbol value: " + symbol);

            switch (symbol) {
                case "(":
                    openParenthesisCount++;
                    break;
                case ")":
                    closedParenthesisCount++;

                    // Remove all operators from stack
                    // and add to postfix expression
                    for (int j = 0; j < operators.size(); j++) {
                        postfix.add(operators.pop());
                    }
                    break;
                case "+":
                case "-":
                    if (!lastIsOperand) {
                        throw  new IllegalInfixFormat("There should be an operand before this operator, this order is invalid.");
                    }
                    // Represents that this is an operator
                    lastIsOperand = false;

                    precedence = LOW_PRECEDENCE; // 0
                    // @todo ???
                    operatorCount++;
                    break;
                case "*":
                case "/":
                    precedence = HIGH_PRECEDENCE;
                    break;
                default:
                    // This is an operand
                    precedence = DEFAULT_PRECEDENCE;
                    break;
            }

        }

        return new DynamicArray<>();
    }

    /**
     * Checks if there are as many open parenthesis as there are
     * open parenthesis
     *
     * @return true if valid, false if not
     */
    private boolean validParenthesis() {
        log.debug("In validParenthesis");
        return openParenthesisCount == closedParenthesisCount;
    }

    /**
     * Makes sure we're not given an invalid expression
     *
     * @param infix
     * @return true if invalid
     */
//    private boolean isInvalidInfix(Queue<String> infix) {
//        return infix == null || infix.isEmpty();
//    }

    /**
     * Process the Infix Queue by removing the strings one at a time from
     * the queue. With each string determine if it is an operand or an
     * operator. If it is an operand, add it to the Postfix Queue.
     */
//    private processInfix(Queue<String> infix) throws IllegalArgumentException {
//        log.debug("In processInfix()");
//
//        if (isInvalidInfix(infix)) {
//            log.error("Invalid infix expression: " + infix);
//            throw new IllegalArgumentException();
//        }
//
//        int size = infix.size();
//
//        postfix = new Queue<String>(size);
//
//    }
}
