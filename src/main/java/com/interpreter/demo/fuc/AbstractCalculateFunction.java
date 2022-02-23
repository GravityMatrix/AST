package com.interpreter.demo.fuc;

import java.util.function.Supplier;

/**
 * @author WangChen
 * @since 2022-02-19 15:38
 **/
public abstract class AbstractCalculateFunction implements Supplier<Integer> {

    protected Integer left;
    protected Integer right;

    public AbstractCalculateFunction addLeft(Integer left) {
        this.left = left;
        return this;
    }

    public AbstractCalculateFunction addRight(Integer right) {
        this.right = right;
        return this;
    }

    public Integer getResult(){
        return get();
    }
}
