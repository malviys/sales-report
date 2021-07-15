package com.sourabh.value;

/**
* Represents the value. A value can either return value or throw an exception.
*/
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
