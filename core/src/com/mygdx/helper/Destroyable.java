package com.mygdx.helper;

public interface Destroyable {

    void reduceHealth(int amount);
    void destroy();

    boolean isDestroyed();
}
