package com.interpreter.demo.part2;

import com.interpreter.demo.fuc.AbstractCalculateFunction;
import com.interpreter.demo.fuc.PlusCalculateFunction;
import com.interpreter.demo.fuc.SubCalculateFunction;

/**
 * @author WangChen
 * @since 2022-02-19 12:28
 **/
public class Interpreter {

    private String text;
    private int pos;
    private Token currentToken;
    private char currentChar;

    public Interpreter(String text) {
        this.text = text;
        this.pos = 0;
        this.currentToken = null;
        this.currentChar = text.charAt(this.pos);
    }


    public void error() {
        throw new RuntimeException("Error parsing input");
    }


    public Token getNextToken() {
        while (Character.MIN_VALUE != this.currentChar) {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }
            if (Character.isDigit(currentChar)) {
                return new Token(Token.Type.INTEGER, integer());
            }
            if (currentChar == '+') {
                advance();
                return new Token(Token.Type.PLUS, currentChar);
            }
            if (currentChar == '-') {
                advance();
                return new Token(Token.Type.SUB, currentChar);
            } else {
                error();
            }
        }
        return new Token(Token.Type.EOF, null);
    }


    /**
     * 跳过空白
     */
    public void skipWhitespace() {
        while (Character.isWhitespace(this.currentChar)) {
            advance();
        }
    }

    /**
     * 计算值
     *
     * @return Integer
     */
    public Integer integer() {
        StringBuilder number = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            number.append(currentChar);
            advance();
        }
        return Integer.parseInt(number.toString());
    }

    /**
     * 推进指针并修改当前字符变量
     */
    public void advance() {
        this.pos = pos + 1;
        if (this.pos > text.length() - 1) {
            currentChar = Character.MIN_VALUE;
        } else {
            currentChar = text.charAt(pos);
        }
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
        Token left = this.currentToken;
        this.eat(Token.Type.INTEGER);
        AbstractCalculateFunction calculateFunction = null;
        Token op = this.currentToken;
        if (op.getType().equals(Token.Type.PLUS)) {
            eat(Token.Type.PLUS);
            calculateFunction = new PlusCalculateFunction();
        } else if (op.getType().equals(Token.Type.SUB)) {
            eat(Token.Type.SUB);
            calculateFunction = new SubCalculateFunction();
        }
        Token right = this.currentToken;
        this.eat(Token.Type.INTEGER);
        return calculateFunction.addLeft((int) left.getValue()).addRight((int) right.getValue()).getResult();
    }


    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter("100 - 2");
        Integer expr = interpreter.expr();
        System.out.println(expr);
    }
}
