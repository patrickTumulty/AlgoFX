package com.ptumulty.AlgoFX.AlgoModel.Sorting.ArrayModel;

import java.util.Random;

public interface ArrayModel<T>
{
    T get(int i);

    void set(int i, T value);

    void swap(int i, int j);

    void scramble();

    void roll(int n);

    int size();

    void addListener(Listener<T> listener);

    void removeListener(Listener<T> listener);

    static <V> void fill(ArrayModel<V> array, V value)
    {
        for (int i = 0; i < array.size(); i++)
            array.set(i, value);
    }

    static void fillRandom(ArrayModel<Integer> array, int lowerBounds, int upperBounds)
    {
        Random random = new Random();
        for (int i = 0; i < array.size(); i++)
            array.set(i, random.nextInt(lowerBounds, upperBounds));
    }

    static void fillSequential(ArrayModel<Integer> array)
    {
        fillSequential(array, 0);
    }

    static void fillSequential(ArrayModel<Integer> array, int offset)
    {
        for (int i = 0; i < array.size(); i++)
            array.set(i, i + offset);
    }

    interface Listener<T>
    {
        void valueSet(int i, T value);

        void currentElement(int i, T value);

        void aboutToSwap(int i, T iValue, int j, T jValue);

        void rolled();
    }
}
