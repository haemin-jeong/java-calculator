package com.caculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PostfixConverter {

    public static List<String> convert(String exp) throws IllegalArgumentException {
        List<String> postfix = new ArrayList<>();
        String[] split = exp.split(" ");
        Stack<String> stack = new Stack<>();

        if (isEmptyExpression(split)) throw new IllegalArgumentException();

        for (int i = 0; i < split.length; i++) {
            String s = split[i];

            if (isIdxForOperator(i)) {
                executeOperatorProcess(s, postfix, stack);
            } else {
                executeNumberProcess(s, postfix);
            }
        }

        while (!stack.isEmpty()) postfix.add(stack.pop());

        return postfix;
    }

    private static boolean isEmptyExpression(String[] expressions) {
        return expressions.length == 0;
    }

    private static void executeNumberProcess(String number, List<String> postfix) {
        if (!StringUtils.isNumber(number)) {
            throw new IllegalArgumentException();
        }
        postfix.add(number);
    }

    private static void executeOperatorProcess(String operator, List<String> postfix, Stack<String> stack) throws IllegalArgumentException {
        if (!isOperator(operator)) throw new IllegalArgumentException();

        if (stack.isEmpty() || getPriority(stack.peek()) < getPriority(operator)) {
            stack.push(operator);
            return;
        }

        while (!stack.isEmpty() && getPriority(stack.peek()) >= getPriority(operator)) {
            postfix.add(stack.pop());
        }

        stack.push(operator);
    }

    private static boolean isOperator(String operator) {
        return operator.equals("*") || operator.equals("/") || operator.equals("+") || operator.equals("-");
    }

    private static boolean isIdxForOperator(int index) {
        return (index + 1) % 2 == 0;
    }

    private static int getPriority(String operator) {
        if (operator.equals("*") || operator.equals("/")) return 2;
        if (operator.equals("+") || operator.equals("-")) return 1;
        return -1;
    }
}
