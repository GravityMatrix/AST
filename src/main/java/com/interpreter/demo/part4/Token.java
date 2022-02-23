package com.interpreter.demo.part4;

/**
 * @author WangChen
 * @since 2022-02-19 12:21
 **/
public class Token {

    private Type type;
    private Object value;

    public Token(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    enum Type {

        /**
         * int
         */
        INTEGER,

        /**
         * +
         */
        PLUS,

        /**
         * -
         */
        SUB,

        /**
         * 乘法
         */
        MUL,

        /**
         * 除法
         */
        DIV,

        /**
         * ''
         */
        WHITESPACE,

        /**
         * EOF (end-of-file) token 用于表示
         * 没有更多的输入用于词法分析
         */
        EOF,
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
