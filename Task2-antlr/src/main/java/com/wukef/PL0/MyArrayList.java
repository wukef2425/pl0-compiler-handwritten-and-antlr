package com.wukef.PL0;

import java.util.ArrayList;

class MyArrayList<E> extends ArrayList<E> {
    private final int baseIndex = 100; // 基地址 100
    @Override
    public E set(int index, E element) {
        return super.set(index - baseIndex, element);
    }
    @Override
    public int indexOf(Object o) {
        int internalIndex = super.indexOf(o);
        if(internalIndex == -1) {
            return -1;
        } else {
            return internalIndex + baseIndex;
        }
    }
}