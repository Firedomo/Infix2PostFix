import java.util.Stack; //imported for use in methods below
import java.util.Scanner;

class Postfix {
    /*
     * method that takes in a valid infix expression String
     * returns a String of the equivalent postfix expression
     * The result postfix String should have no spaces or parentheses
     */
    public static String convertToPostfix(String infix) {
        Stack<Character> operators = new Stack<>();  //a stack to hold the operator characters
        StringBuilder postfix = new StringBuilder(); //a StringBuilder to create the postfix result String
        //note 1: use postfix.append() to add a character to the end of the postfix result expression
        //note 2: you may want to use the helper method below, isLowerPrecendence(), to compare the precedence of two operators
        for (int i = 0; i < infix.length(); i++) {
            switch (infix.charAt(i)) {
                case 'x', 'y', 'z' -> postfix.append(infix.charAt(i));
                case '^' -> operators.push(infix.charAt(i));
                case '+', '-', '*', '/' -> {
                    while (!operators.isEmpty() && isLowerPrecedence(infix.charAt(i), operators.peek())) {
                        postfix.append(operators.peek());
                        operators.pop();
                    }
                    operators.push(infix.charAt(i));
                }
                case '(' -> operators.push(infix.charAt(i));
                case ')' -> { // Stack is not empty if infix expression is valid
                    char topOperator = operators.pop();
                    while (topOperator != '(') {
                        postfix.append(topOperator);
                        topOperator = operators.pop();
                    }
                }
                default -> {
                }
            }

        }
        //TODO: complete this method
        while (!operators.isEmpty()) {
            postfix.append(operators.pop());
        }

        return postfix.toString(); //return the final result postfix expression as a String
    }


    /*
     * method that takes in a valid postfix expression String
     * returns a double of the result of evaluating the expression
     * with the given values for the variables x,y,z
     * assumes postfix String has no spaces or parentheses
     */
    public static double evaluatePostfix(String postfix, double x, double y, double z) {
        Stack<Double> values = new Stack<>(); //a stack to hold the values in the algorithm
        for (int i = 0; i < postfix.length(); i++) {
            switch (postfix.charAt(i)) {
                case 'x' -> values.push(x);
                case 'y' -> values.push(y);
                case 'z' -> values.push(z);
                case '+', '-', '*', '/', '^' -> {
                    double operandTwo = values.pop();
                    double operandOne = values.pop();
                    switch (postfix.charAt(i)) {
                        case '+':
                            double result = operandOne + operandTwo;
                            values.push(result);
                            break;
                        case '-':
                            result = operandOne - operandTwo;
                            values.push(result);
                            break;
                        case '*':
                            result = operandOne * operandTwo;
                            values.push(result);
                            break;
                        case '/':
                            result = operandOne / operandTwo;
                            values.push(result);
                        case '^':
                            result = Math.pow(operandOne, operandTwo);
                            values.push(result);
                            break;
                    }
                }
            }

        }
        //TODO: complete this method


        return values.peek(); //final result should be at the top of the stack when algorithm is done
    }


    /*
     * a helper method for the convertToPostfix() method below
     * this method takes in two operator characters
     * returns true if the first operator is of lower or equal precedence than the second
     * returns false if the first operator is of higher precedence than the second
     * e.g. isLowerPrecedence('+', '*') will return true
     */
    private static boolean isLowerPrecedence(char first, char second) {
        if (first == '(' || first == ')' || second == '(' || second == ')') {
            return false; //return false if either character is a parentheses instead of an operator
        } else //* and / are only lower precedence to each other or ^
            //first must be higher precedence than second
            if (first == '+' || first == '-') { //+ and - are lower precedence than any other
            return true;
        } else return (first == '*' || first == '/') && (second != '+') && (second != '-');
    }


    //a test program that asks for an input infix and some values of the variables x,y,z and runs the two above methods
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter a valid infix expression using variables x,y,z:");
        String input = scnr.nextLine();
        System.out.println("Postfix:");
        String postfix = convertToPostfix(input);
        System.out.println(postfix);
        System.out.println("Enter values of variables.");
        System.out.print("x = ");
        double x = scnr.nextDouble();
        System.out.print("y = ");
        double y = scnr.nextDouble();
        System.out.print("z = ");
        double z = scnr.nextDouble();
        System.out.println("Result of expression:");
        System.out.println(evaluatePostfix(postfix, x, y, z));
    }

}