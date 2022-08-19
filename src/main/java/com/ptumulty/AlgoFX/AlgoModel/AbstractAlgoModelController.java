package com.ptumulty.AlgoFX.AlgoModel;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public abstract class AbstractAlgoModelController implements AlgoModelController
{
    protected InstanceContent lookupContent;
    private final Lookup capabilitiesLookup;

    public AbstractAlgoModelController()
    {
        lookupContent = new InstanceContent();
        capabilitiesLookup = new AbstractLookup(lookupContent);
    }

    @Override
    public Lookup getCapabilities()
    {
        return capabilitiesLookup;
    }
}
