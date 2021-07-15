package com.sourabh.value;

public abstract class ValueObject<T, E extends Exception> {
    protected T value;
    protected E exception;

    public T getValue() {
        return value;
    }

    public T getOrThrow() throws E {
        if (value == null) {
            throw exception;
        }

        return value;
    }

    public boolean isValid() {
        return value != null;
    }
}
