package com.interpreter.demo.part6;

import cn.hutool.core.collection.CollUtil;

import java.util.Set;

/**
 * @author WangChen
 * @since 2022-02-19 12:28
 **/
public class Interpreter {

    private Lexer lexer;
    private Token currentToken;
    private static Set<Token.Type> symbols0 = CollUtil.newHashSet(Token.Type.DIV, Token.Type.MUL);
    private static Set<Token.Type> symbols1 = CollUtil.newHashSet(Token.Type.PLUS, Token.Type.SUB);

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
        if (current.getType().equals(Token.Type.INTEGER)){
            this.eat(Token.Type.INTEGER);
            return (int) current.getValue();
        } else if (current.getType().equals(Token.Type.LPAREN)){
            this.eat(Token.Type.LPAREN);
            Integer result = expr();
            this.eat(Token.Type.RPAREN);
            return result;
        }
        return null;
    }


    public Integer term() {

        /*
         * term : factor ((MUL | DIV) factor)
         */
        Integer result = this.factor();

        while (symbols0.contains(this.currentToken.getType())) {
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

    public Integer expr() {
        /*
         *         expr   : term ((PLUS | MINUS) term)*
         *         term   : factor ((MUL | DIV) factor)*
         *         factor : INTEGER
         */
        Integer result = this.term();
        while (symbols1.contains(this.currentToken.getType())) {
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
        Interpreter interpreter = new Interpreter(new Lexer("8 / (1 + 1)"));
        Integer expr = interpreter.expr();
        System.out.println(expr);
    }
}
