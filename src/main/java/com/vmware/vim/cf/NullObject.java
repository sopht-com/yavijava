package com.vmware.vim.cf;

final public class NullObject {
    final public static NullObject NULL = new NullObject();

    private NullObject() {
    }

    @Override
    public String toString() {
        return "null";
    }

}
