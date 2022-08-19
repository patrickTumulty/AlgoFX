package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.AlgoFX.AlgoModel.AlgoModelController;
import org.openide.util.Lookup;

public class AlgoAssetFactory
{
    public static AlgoAsset create(AlgoModelController algoModelController)
    {
        for (AlgoAsset algoAsset : Lookup.getDefault().lookupAll(AlgoAsset.class))
        {
            if (algoAsset.matchesController(algoModelController))
            {
                return algoAsset;
            }
        }
        return null;
    }
}
