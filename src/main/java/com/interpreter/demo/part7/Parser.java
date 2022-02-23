package com.interpreter.demo.part7;

import cn.hutool.core.collection.CollUtil;

import java.util.Set;

/**
 * @author WangChen
 * @since 2022-02-19 12:28
 **/
public class Parser {

    private Lexer lexer;
    private Token currentToken;
    private static Set<Token.Type> symbols0 = CollUtil.newHashSet(Token.Type.DIV, Token.Type.MUL);
    private static Set<Token.Type> symbols1 = CollUtil.newHashSet(Token.Type.PLUS, Token.Type.SUB);

    public Parser(Lexer lexer) {
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

    public AST factor() {
        Token current = this.currentToken;
        if (current.getType().equals(Token.Type.INTEGER)){
            this.eat(Token.Type.INTEGER);
            return new Num(current);
        } else if (current.getType().equals(Token.Type.LPAREN)){
            this.eat(Token.Type.LPAREN);
            AST result = expr();
            this.eat(Token.Type.RPAREN);
            return result;
        }
        return null;
    }


    public AST term() {

        /*
         * term : factor ((MUL | DIV) factor)
         */
        AST node = this.factor();

        while (symbols0.contains(this.currentToken.getType())) {
            Token currToken = this.currentToken;
            if (currentToken.getType().equals(Token.Type.MUL)) {
                eat(Token.Type.MUL);
            } else if (currentToken.getType().equals(Token.Type.DIV)) {
                eat(Token.Type.DIV);
            }
            node = new BinOp(node, currToken, factor());
        }
        return node;
    }

    public AST expr() {
        /*
         *         expr   : term ((PLUS | MINUS) term)*
         *         term   : factor ((MUL | DIV) factor)*
         *         factor : INTEGER
         */
        AST node = this.term();
        while (symbols1.contains(this.currentToken.getType())) {
            Token currToken = this.currentToken;
            if (currentToken.getType().equals(Token.Type.PLUS)) {
                eat(Token.Type.PLUS);
            } else if (currentToken.getType().equals(Token.Type.SUB)) {
                eat(Token.Type.SUB);
            }
            node = new BinOp(node, currToken, term());
        }
        return node;
    }


    public AST parse(){
        return expr();
    }

}
