package com.ptumulty.AlgoFX.AlgoModel.Sorting.ArrayModel;

import com.ptumulty.ceramic.utility.ArrayUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ArrayModelImpl<T> implements ArrayModel<T>
{
    private final T[] model;
    private final List<Listener<T>> listeners;

    public ArrayModelImpl(int size)
    {
        model = (T[]) new Object[size];
        listeners = new LinkedList<>();
    }

    @Override
    public T get(int i)
    {
        T value = model[i];
        listeners.forEach(listener -> listener.currentElement(i, value));
        return value;
    }

    @Override
    public void set(int i, T value)
    {
        listeners.forEach(listener -> listener.valueSet(i, value));
        model[i] = value;
    }

    @Override
    public void swap(int i, int j)
    {
        listeners.forEach(listener -> listener.aboutToSwap(i, model[i], j, model[j]));
        ArrayUtils.swap(model, i, j);
    }

    @Override
    public void roll(int n)
    {
        ArrayUtils.roll(model, n);
        listeners.forEach(Listener::rolled);
    }

    @Override
    public void scramble()
    {
        Random random = new Random();
        for (int i = 0; i < size(); i++)
        {
            int r1 = random.nextInt(0, size());
            int r2 = random.nextInt(0, size());
            swap(r1, r2);
        }
    }

    @Override
    public int size()
    {
        return model.length;
    }

    @Override
    public void addListener(Listener<T> listener)
    {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<T> listener)
    {
        listeners.remove(listener);
    }
}
