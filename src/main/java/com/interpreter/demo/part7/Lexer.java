package com.interpreter.demo.part7;

/**
 * @author WangChen
 * @since 2022-02-21 14:28
 **/
public class Lexer {

    private String text;
    private int pos;
    private char currentChar;

    public Lexer(String text) {
        this.text = text;
        this.pos = 0;
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
            if (currentChar == '*') {
                advance();
                return new Token(Token.Type.MUL, currentChar);
            }
            if (currentChar == '/') {
                advance();
                return new Token(Token.Type.DIV, currentChar);
            }
            if (currentChar == '+') {
                advance();
                return new Token(Token.Type.PLUS, currentChar);
            }
            if (currentChar == '-') {
                advance();
                return new Token(Token.Type.SUB, currentChar);
            }
            if (currentChar == '(') {
                advance();
                return new Token(Token.Type.LPAREN, currentChar);
            }
            if (currentChar == ')') {
                advance();
                return new Token(Token.Type.RPAREN, currentChar);
            }
            error();
        }
        return new Token(Token.Type.EOF, null);
    }
}
