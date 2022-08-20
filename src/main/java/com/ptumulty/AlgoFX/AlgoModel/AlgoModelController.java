package com.ptumulty.AlgoFX.AlgoModel;

import org.openide.util.Lookup;

public interface AlgoModelController extends Broadcaster<AlgoModelController.Listener>
{
    String getTitle();

    Lookup getCapabilities();

    void dispose();

    interface Listener
    {
        void capabilitiesChanged();
    }
}
