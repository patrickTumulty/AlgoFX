package com.ptumulty.AlgoFX;

public interface ArrayModel<T>
{
    T get(int i);

    void set(int i, T value);

    void swap(int i, int j);

    void scramble();

    int size();

    void addListener(Listener<T> listener);

    void removeListener(Listener<T> listener);

    interface Listener<T>
    {
        void valueSet(int i, T value);

        void currentElement(int i, T value);
    }
}