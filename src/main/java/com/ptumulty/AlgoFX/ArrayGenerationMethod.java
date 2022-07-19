package com.ptumulty.AlgoFX;

import java.util.Arrays;
import java.util.Optional;

public enum ArrayGenerationMethod
{
    SEQUENTIAL("Sequential"),
    RANDOM("Random"),
    STATIC_VALUE("Static Value");

    private final String name;

    ArrayGenerationMethod(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public static Optional<ArrayGenerationMethod> getFromName(String name)
    {
        return Arrays.stream(values())
                     .filter(arrayGenerationMethod -> arrayGenerationMethod.getName().equalsIgnoreCase(name))
                     .findFirst();
    }

    @Override
    public String toString()
    {
        return name;
    }
}
