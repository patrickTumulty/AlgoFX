package com.ptumulty.AlgoFX.AlgoView;


import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.carbonicons.CarbonIcons;
import org.kordamp.ikonli.javafx.FontIcon;

public class AlgoButtonUtils
{
    public static Button createCloseButton(int iconSize)
    {
        FontIcon xGraphic = new FontIcon(CarbonIcons.CLOSE);
        xGraphic.setIconColor(Color.MINTCREAM);
        xGraphic.setIconSize(iconSize);
        Button closeButton = new Button();
        closeButton.getStyleClass().add("algoCloseButton");
        closeButton.setGraphic(xGraphic);
        return closeButton;
    }
}
