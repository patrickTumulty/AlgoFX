package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.ceramic.utility.FxUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.openide.util.Lookup;

import java.util.HashMap;
import java.util.Map;

public class AlgoViewLauncherView
{
    private final GridPane gridPane;
    private final StackPane mainStackPane;
    private final Map<String, AlgoView> algoViewMap;
    private AlgoView currentAlgoView;
    private final int columns;
    private Button backButton;

    public AlgoViewLauncherView(int cols)
    {
        columns = cols;

        mainStackPane = new StackPane();
        algoViewMap = new HashMap<>();

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(30);
        gridPane.setVgap(30);

        addGridView();

        configureBackButton();

        setAlgoMap();

        configureGrid();
    }

    private void configureBackButton()
    {
        backButton = new Button();
        backButton.setOnAction(event ->
                FxUtils.run(() ->
                {
                    addGridView();
                    removeBackButton();
                }));
        backButton.setMinWidth(50);
        backButton.setMinHeight(50);
        backButton.setShape(new Circle(50));
        FontIcon arrowLeftIcon = new FontIcon(FontAwesomeSolid.ARROW_LEFT);
        arrowLeftIcon.setIconSize(20);
        backButton.setGraphic(arrowLeftIcon);
    }

    private void addGridView()
    {
        if (currentAlgoView != null)
        {
            mainStackPane.getChildren().remove(currentAlgoView.getVisualization());
        }
        mainStackPane.getChildren().add(0, gridPane);
        StackPane.setAlignment(gridPane, Pos.CENTER);
    }

    private void removeBackButton()
    {
        mainStackPane.getChildren().remove(backButton);
    }

    private void addBackButton()
    {
        mainStackPane.getChildren().add(1, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(10, 10, 10, 10));
    }

    private void configureGrid()
    {
        int counter = 0;
        for (AlgoView algoView : algoViewMap.values())
        {
            LabeledTile labeledTile = new LabeledTile(algoView.getTitle());
            labeledTile.setOnMouseClicked(event -> setAlgoView(algoView));
            gridPane.add(labeledTile, counter % columns, counter / columns);
            counter++;
        }
    }

    private void setAlgoMap()
    {
        for (AlgoView algoView : Lookup.getDefault().lookupAll(AlgoView.class))
        {
            algoViewMap.put(algoView.getTitle(), algoView);
        }
    }

    private void setAlgoView(AlgoView algoView)
    {
        setCurrentAlgoView(algoView);

        FxUtils.run(() ->
        {
            mainStackPane.getChildren().remove(gridPane);
            mainStackPane.getChildren().add(0, algoView.getVisualization());

            addBackButton();
        });
    }

    private void setCurrentAlgoView(AlgoView algoView)
    {
        if (currentAlgoView != null)
        {
            currentAlgoView.dispose();
        }

        currentAlgoView = algoView;

        currentAlgoView.initView();
    }

    public Pane getView()
    {
        return mainStackPane;
    }

    private class LabeledTile extends Region
    {
        LabeledTile(String text)
        {
            FxUtils.setStaticRegionSize(this, 175, 175);
            Label label = new Label(text);
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setWrapText(true);
            label.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(30), null)));


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
