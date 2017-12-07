package com.tiffanyln.evaluator;

import com.tiffanyln.interfaces.Queue;
import com.tiffanyln.interfaces.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class uses posfix notation to solve equations
 *
 * @author Tiffany Le-Nguyen
 */
public class Evaluator {
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    // The input to the evaluator is a queue containing
    // the infix expression and the output is a queue with
    // the result as the only entry.
    private Stack<String> stack;
    private Queue<String> postfix;

    // Precedence of next value in the infix
    private int precedence;

    // True if the last string in the infix expression was an operand.
    private boolean lastIsOperand;

    // The higher the precedence, the higher the priority
    private static final int NO_PRECENDENCE = 0;
    private static final int LOW_PRECEDENCE = 1;
    private static final int HIGH_PRECEDENCE = 2;

    public Evaluator() {
        this.precedence = NO_PRECENDENCE;
        this.lastIsOperand = false;
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
