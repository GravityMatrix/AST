package com.interpreter.demo.part3;

import cn.hutool.core.collection.CollUtil;

import java.util.Set;

/**
 * @author WangChen
 * @since 2022-02-19 12:28
 **/
public class Interpreter {

    private String text;
    private int pos;
    private Token currentToken;
    private char currentChar;
    private static Set<Token.Type> types = CollUtil.newHashSet(Token.Type.PLUS, Token.Type.SUB);

    public Interpreter(String text) {
        this.text = text;
        this.pos = 0;
        this.currentToken = null;
        this.currentChar = text.charAt(this.pos);
    }



    /* ---------------------------------- Lexer code start-------------------------------- **/


    public void error() {
        throw new RuntimeException("Error parsing input");
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
     * 推进`pos`指针和设置 `current_char` 变量
     */
    public void advance() {
        this.pos = pos + 1;
        if (this.pos > text.length() - 1) {
            currentChar = Character.MIN_VALUE;
        } else {
            currentChar = text.charAt(pos);
        }
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


    /* <---------------------------------- Lexer code end --------------------------------> **/




    /* <--------------------------------- Parser / Interpreter code start---------------------> **/

    public void eat(Token.Type type) {
        if (this.currentToken.getType().equals(type)) {
            currentToken = getNextToken();
        } else {
            error();
        }
    }


    /**
     * @return int
     */
    public Integer term() {
        Token current = this.currentToken;
        this.eat(Token.Type.INTEGER);
        return (int) current.getValue();
    }

    public Integer expr() {
        // 从输入设置为当前标记
        this.currentToken = getNextToken();
        Integer result = term();
        while (types.contains(this.currentToken.getType())) {
            if (currentToken.getType().equals(Token.Type.PLUS)) {
                eat(Token.Type.PLUS);
                result = result + term();
            } else if (currentToken.getType().equals(Token.Type.SUB)) {
                eat(Token.Type.SUB);
                result = result - term();
            }
        }
        return result;
    }

    /* <--------------------------------- Parser / Interpreter code end---------------------> **/


    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter("100 - 2 + 100");
        Integer expr = interpreter.expr();
        System.out.println(expr);
    }
}
