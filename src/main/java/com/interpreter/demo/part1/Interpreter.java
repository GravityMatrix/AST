package com.interpreter.demo.part1;

import com.interpreter.demo.fuc.AbstractCalculateFunction;
import com.interpreter.demo.fuc.PlusCalculateFunction;
import com.interpreter.demo.fuc.SubCalculateFunction;

import java.util.function.Supplier;

/**
 * @author WangChen
 * @since 2022-02-19 12:28
 **/
public class Interpreter {

    private String text;
    private int pos;
    private Token currentToken;

    public Interpreter(String text) {
        this.text = text;
        this.pos = 0;
        this.currentToken = null;
    }


    public void error() {
        throw new RuntimeException("Error parsing input");
    }


    public Token getNextToken() {

        if (pos > text.length() - 1) {
            return new Token(Token.Type.EOF, null);
        }

        char currentChar = text.charAt(this.pos);

        if (Character.isDigit(currentChar)) {
            StringBuilder number = new StringBuilder();
            while (Character.isDigit(currentChar)) {
                 number.append(currentChar);
                pos = pos + 1;
                if (pos == text.length()) {
                    break;
                }
                currentChar = text.charAt(pos);
            }
            return new Token(Token.Type.INTEGER, number.toString());
        }

        if (currentChar == '+') {
            Token token = new Token(Token.Type.PLUS, currentChar);
            pos = pos + 1;
            return token;
        }

        if (currentChar == '-') {
            Token token = new Token(Token.Type.SUB, currentChar);
            pos = pos + 1;
            return token;
        }

        if (Character.isWhitespace(currentChar)) {
            Token token = new Token(Token.Type.WHITESPACE, currentChar);
            pos = pos + 1;
            return token;
        }

        error();
        return null;
    }


    public void eat(Token.Type type) {
        if (this.currentToken.getType().equals(type)) {
            currentToken = getNextToken();
        } else {
            error();
        }
    }


    public Integer expr() {
        this.currentToken = getNextToken();

        while (currentToken.getType().equals(Token.Type.WHITESPACE)) {
            eat(Token.Type.WHITESPACE);
        }
        Token left = this.currentToken;
        this.eat(Token.Type.INTEGER);

        while (currentToken.getType().equals(Token.Type.WHITESPACE)) {
            eat(Token.Type.WHITESPACE);
        }

        AbstractCalculateFunction calculateFunction = null;
        Token op = this.currentToken;
        if (op.getType().equals(Token.Type.PLUS)) {
            eat(Token.Type.PLUS);
            calculateFunction = new PlusCalculateFunction();
        } else if (op.getType().equals(Token.Type.SUB)) {
            eat(Token.Type.SUB);
            calculateFunction = new SubCalculateFunction();
        }
        while (currentToken.getType().equals(Token.Type.WHITESPACE)) {
            eat(Token.Type.WHITESPACE);
        }
        Token right = this.currentToken;
        this.eat(Token.Type.INTEGER);
        return calculateFunction.addLeft(Integer.parseInt((String) left.getValue())).addRight(Integer.parseInt((String) right.getValue())).getResult();
    }



    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter("100 - 2");
        Integer expr = interpreter.expr();
        System.out.println(expr);
    }
}
