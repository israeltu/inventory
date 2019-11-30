package com.example.demo.exceptions;

public class ItemNotFoundException  extends RuntimeException {

    public ItemNotFoundException(long itemNo) {
        super("Item id not found : " + itemNo);
    }

}