package com.interpreter.demo.fuc;

/**
 * @author WangChen
 * @since 2022-02-19 15:39
 **/
public class SubCalculateFunction extends AbstractCalculateFunction{
    @Override
    public Integer get() {
        return left - right;
    }
}
