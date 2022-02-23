package com.interpreter.demo.part4;

import cn.hutool.core.collection.CollUtil;

import java.util.Set;

/**
 * @author WangChen
 * @since 2022-02-19 12:28
 **/
public class Interpreter {

    private Lexer lexer;
    private Token currentToken;
    private static Set<Token.Type> types = CollUtil.newHashSet(Token.Type.DIV, Token.Type.MUL);

    public Interpreter(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }


    /* <--------------------------------- Parser / Interpreter code start---------------------> **/

    public void error() {
        throw new RuntimeException("Invalid syntax");
    }

    public void eat(Token.Type type) {
        if (this.currentToken.getType().equals(type)) {
            currentToken = lexer.getNextToken();
        } else {
            this.error();
        }
    }

    public Integer factor() {
        Token current = this.currentToken;
        this.eat(Token.Type.INTEGER);
        return (int) current.getValue();
    }

    public Integer expr() {
        /*
         * 算术表达式解析器/解释器
         *
         *   expr : factor ((MUL | DIV) factor)*
         *   factor : INTEGER
         */
        Integer result = this.factor();
        while (types.contains(this.currentToken.getType())) {
            if (currentToken.getType().equals(Token.Type.MUL)) {
                eat(Token.Type.MUL);
                result = result * factor();
            } else if (currentToken.getType().equals(Token.Type.DIV)) {
                eat(Token.Type.DIV);
                result = result / factor();
            }
        }
        return result;
    }

    /* <--------------------------------- Parser / Interpreter code end---------------------> **/


    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter(new Lexer("100 / 10 * 200"));
        Integer expr = interpreter.expr();
        System.out.println(expr);
    }
}
