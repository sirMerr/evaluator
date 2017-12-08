package com.tiffanyln.evaluator;

import com.sun.tools.javac.util.StringUtils;
import com.tiffanyln.exceptions.IllegalInfixFormat;
import com.tiffanyln.interfaces.Queue;
import com.tiffanyln.interfaces.Stack;
import com.tiffanyln.structures.DynamicArray;
import javafx.scene.control.PasswordField;
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
     * @return Queue<String>
     *      A Queue with the result as the only entry
     * @throws IllegalInfixFormat
     */
    public Queue<String> evaluate(Queue<String> infixExpression) throws IllegalInfixFormat {
        log.debug("In evaluate");
        log.debug("Infix Expression: " + infixExpression);

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
                    log.debug("In case '('");
                    openParenthesisCount++;
                    break;
                case ")":
                    log.debug("In case ')'");
                    closedParenthesisCount++;

                    // Remove all operators from stack
                    // and add to postfix expression
                    for (int j = 0; j < operators.size(); j++) {
                        postfix.add(operators.pop());
                    }
                    break;
                case "+":
                case "-":
                    log.debug("In case '+' or '-'");
                    // Throws is last symbol was not operand
                    checkLastIsOperand();
                    // Represents that this is an operator
                    lastIsOperand = false;

                    precedence = LOW_PRECEDENCE; // 1

                    pushOperator(symbol, isInParenthesis());
                    operatorCount++;
                    break;
                case "*":
                case "/":
                    log.debug("In case '*' or '/'");
                    // Throws is last symbol was not operand
                    checkLastIsOperand();
                    // Represents that this is an operator
                    lastIsOperand = false;

                    precedence = HIGH_PRECEDENCE; // 2

                    pushOperator(symbol, isInParenthesis());
                    operatorCount++;
                    break;
                default:
                    log.debug("In case operand");
                    // This is an operand
                    precedence = DEFAULT_PRECEDENCE; // 0
                    pushOperand(symbol);
                    lastIsOperand = true;
                    operandCount++;
                    break;
            }

        }

        // Counter for operands should have 1 more than operators.
        // and each open parenthesis should have a closing one
        if (operandCount != operatorCount + 1 ) {
            throw new IllegalInfixFormat("Operand count compared to operators is invalid-> operandCount: " + operandCount + " operatorCount: " + operatorCount);
        }
        if (openParenthesisCount != closedParenthesisCount) {
            throw new IllegalInfixFormat("Does not have matching open and closing parenthesis");
        }

        return calculate();
    }

    /**
     * Calculates postfix expression.
     *
     * @return
     */
    private Queue<String> calculate() {
        log.debug("In calculate()");
        getPostfix();
        Stack<String> operands = new DynamicArray<>();

        for (int i = 0; i < postfix.size(); i++) {
            log.debug("postfix.element(): " + postfix.element());
            while (isDouble(postfix.element())) {
                log.debug("isDouble triggered");
                // Loop and push until NaN is found
                operands.push(postfix.remove());
                log.debug("postfix.element(): " + postfix.element());
            }

            // operand-operand-operator
            String operand2 = operands.pop();
            String operand1 = operands.pop();
            String operator = postfix.remove();

            log.debug("Expression evaluated: " + operand1+operand2+operator);

            Double result = null;

            switch (operator) {
                case "+":
                    result = Double.parseDouble(operand1) + Double.parseDouble(operand2);
                    break;
                case "-":
                    result = Double.parseDouble(operand1) - Double.parseDouble(operand2);
                    break;
                case "*":
                    result = Double.parseDouble(operand1) * Double.parseDouble(operand2);
                    break;
                case "/":
                    result = Double.parseDouble(operand1) / Double.parseDouble(operand2);
                    break;
            }

            log.debug("Evaluated Result: " + result);
            operands.push(result + "");

            if (postfix.size() == 0) {
                return new DynamicArray<>(new String[]{operands.pop()});
            }
        }

        return new DynamicArray<>();
    }

    /**
     * @todo More javadoc
     * Handler for operator
     *
     * @param operator
     * @param isInParenthesis
     */
    private void pushOperator(String operator, boolean isInParenthesis) {
        log.debug("In pushOperator(operator= " + operator + ", isInParenthesis=" + isInParenthesis + ")");

        if (isInParenthesis) {
            // Add directly to operator stack
            log.debug("pushOperator() : operators.push(" + operator + ")");
            operators.push(operator);
        } else {
            switch (precedence) {
                case LOW_PRECEDENCE:
                    pushLowPrecedenceOperator(operator);
                    break;
                case HIGH_PRECEDENCE:
                    pushHighPrecedenceOperator(operator);
                    break;
                default:
                    log.error("Something went wrong in pushOperator");
                    break;
            }
        }
    }

    /**
     * Checks if the operator has equal or higher precedence to the one
     * on the operator stack.
     *
     * If equal, push it to {@code postfix} and if
     * higher, push it to {@code operators}
     *
     * @param newOperator
     *      "*" or "/", which are operators with high precedence
     *
     */
    private void pushHighPrecedenceOperator(String newOperator) {
        log.debug("In pushHighPrecedenceOperator(newOperator= " + newOperator + ")");

        for (int i = 0; i < operators.size(); i++) {
            String lastOperator = operators.peek();

            switch (lastOperator) {
                case "+":
                case "-":
                    // In here, newOperator is higher precedence
                    operators.push(newOperator);
                    return;
                case "*":
                case "/":
                    // In here, newOperator is equal precedence
                    postfix.add(operators.pop());
                    break;
            }
        }
        // Push to stack after it's handled
        operators.push(newOperator);

    }

    /**
     * Pop and push all operators in {@code operators} stack into
     * {@code postfix} queue and push new operator to @{code operators},
     * so that it only contains that one, starting a new cycle.
     *
     * @param newOperator
     *      "+" or "-", which are low precedence operators
     */
    private void pushLowPrecedenceOperator(String newOperator) {
        log.debug("In pushLowPrecedenceOperator(newOperator= " + newOperator + ")");

        for (int i = 0; i < operators.size(); i++) {
            postfix.add(operators.pop());
        }

        operators.push(newOperator);

    }

    /**
     *
     * @param operand
     * @throws IllegalInfixFormat
     */
    private void pushOperand(String operand) throws IllegalInfixFormat {
        log.debug("In pushOperand(operand= " + operand + ")");
        try {
            postfix.add(Double.parseDouble(operand) + "");
        } catch (NumberFormatException e) {
            throw new IllegalInfixFormat("Only numbers and operators are valid. We received operand: " + operand);
        }
    }

    /**
     * Pops and pushes any operators from the stack to our {@code postfix}
     * @return
     *      Finished postfix expression
     */
    private Queue<String> getPostfix() {
        log.debug("In getPostfix()");
        for (int i = 0; i < operators.size(); i++) {
            postfix.add(operators.pop());
        }

        return postfix;
    }
    /**
     * Checks if there are as many open parenthesis as there are
     * open parenthesis
     *
     * @return true if valid, false if not
     */
    private boolean isInParenthesis() {
        log.debug("In validParenthesis");
        return openParenthesisCount  > closedParenthesisCount;
    }

    private void checkLastIsOperand() throws IllegalInfixFormat {
        if (!lastIsOperand) {
            throw new IllegalInfixFormat("There should be an operand before this operator, this order is invalid.");
        }
    }

    private boolean isDouble(String value) {
        return value.matches("[+-]?([0-9]*[.])?[0-9]+");
    }
}
