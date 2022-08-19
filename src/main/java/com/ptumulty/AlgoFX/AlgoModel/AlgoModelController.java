package com.ptumulty.AlgoFX.AlgoModel;

import org.openide.util.Lookup;

public interface AlgoModelController extends Broadcaster<AlgoModelController.Listener>
{
    Lookup getCapabilities();

    void dispose();

    interface Listener
    {
        void capabilitiesChanged();
    }
}
