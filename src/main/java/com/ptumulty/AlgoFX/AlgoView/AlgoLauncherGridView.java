package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.ceramic.utility.FxUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

import java.util.Map;

public class AlgoLauncherGridView extends GridPane
{
    public static final int NUMBER_OF_COLUMNS = 3;
    private final Map<String, AlgoView> algoViewMap;
    private final AlgoViewLauncherView launcherView;

    AlgoLauncherGridView(AlgoViewLauncherView launcherView, Map<String, AlgoView> algoViewMap)
    {
        this.algoViewMap = algoViewMap;
        this.launcherView = launcherView;

        setAlignment(Pos.CENTER);
        setHgap(30);
        setVgap(30);

        configureGridView();
    }

    private void configureGridView()
    {
        int counter = 0;
        for (AlgoView algoView : algoViewMap.values())
        {
            LabeledTile labeledTile = new LabeledTile(algoView.getTitle());
            labeledTile.setOnMouseClicked(event -> launcherView.setAlgoView(algoView));
            add(labeledTile, counter % NUMBER_OF_COLUMNS, counter / NUMBER_OF_COLUMNS);
            counter++;
        }
    }

    private static class LabeledTile extends Region
    {
        public static final int LAUNCHER_TILE_SIZE = 175;

        LabeledTile(String text)
        {
            FxUtils.setStaticRegionSize(this, LAUNCHER_TILE_SIZE, LAUNCHER_TILE_SIZE);
            Label label = new Label(text);
            label.getStyleClass().add("algoLabeledTile");
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setWrapText(true);

            label.maxWidthProperty().bind(maxWidthProperty());
            label.prefWidthProperty().bind(prefWidthProperty());
            label.minWidthProperty().bind(minWidthProperty());
            label.maxHeightProperty().bind(maxHeightProperty());
            label.prefHeightProperty().bind(prefHeightProperty());
            label.minHeightProperty().bind(minHeightProperty());

            getChildren().add(label);
        }
    }
}
