package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.ceramic.components.ChoiceComponent;
import com.ptumulty.ceramic.components.ComponentSettingGroup;
import com.ptumulty.ceramic.utility.FxUtils;
import com.ptumulty.ceramic.utility.ThreadUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.carbonicons.CarbonIcons;
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
    private BorderPane algoViewLayout;
    private Rectangle settingsSeparator;
    private BorderPane settingPopOverBorderPane;

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
            mainStackPane.getChildren().remove(0);
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

            algoViewLayout = new BorderPane();
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);
            algoViewLayout.setTop(vBox);
            BorderPane.setAlignment(vBox, Pos.CENTER);
            BorderPane.setMargin(vBox, new Insets(50, 10, 10, 10));

            Label algoViewLabel = new Label(algoView.getTitle());
            vBox.getChildren().add(algoViewLabel);

            ChoiceComponent<String> modes = new ChoiceComponent<>(algoView.getAlgoModes());
            vBox.getChildren().add(modes.getRenderer());

            algoViewLayout.setCenter(algoView.getVisualizationPane());
            BorderPane.setAlignment(algoView.getVisualizationPane(), Pos.CENTER);

            VBox algoActionControlPanel = configureActionControlPane();

            algoViewLayout.setBottom(algoActionControlPanel);
            BorderPane.setMargin(algoActionControlPanel, new Insets(10, 10, 50, 10));
            BorderPane.setAlignment(algoActionControlPanel, Pos.CENTER);

            mainStackPane.getChildren().add(0, algoViewLayout);

            addBackButton();
        });
    }

    private VBox configureActionControlPane()
    {
        Button algoActionButton = new Button(currentAlgoView.getAlgoActionName());
        algoActionButton.disableProperty().bind(currentAlgoView.busyProperty());
        algoActionButton.setOnAction(event -> ThreadUtils.run(() -> currentAlgoView.doAlgoAction()));

        Button settingsButton = new Button();
        settingsButton.setGraphic(new FontIcon(FontAwesomeSolid.COG));
        Circle circle = new Circle();
        circle.radiusProperty().bind(settingsButton.heightProperty());
        settingsButton.setShape(circle);
        settingsButton.setOnAction(event -> showSettings());

        Button algoResetButton = new Button("Reset");
        algoResetButton.disableProperty().bind(currentAlgoView.busyProperty());
        algoResetButton.setOnAction(event -> ThreadUtils.run(() -> currentAlgoView.doAlgoReset()));

        HBox subActionHBox = new HBox(algoResetButton, settingsButton);
        subActionHBox.setSpacing(10);
        subActionHBox.setAlignment(Pos.CENTER);

        VBox algoActionControlPanel = new VBox(algoActionButton, subActionHBox);
        algoActionControlPanel.setSpacing(10);
        algoActionControlPanel.setAlignment(Pos.CENTER);
        return algoActionControlPanel;
    }

    private void hideSettings()
    {
        mainStackPane.getChildren().remove(settingsSeparator);
        mainStackPane.getChildren().remove(settingPopOverBorderPane);
    }

    private void showSettings()
    {
        configureSeparator();

        configureSettingPopoverPane();

        mainStackPane.getChildren().add(2, settingsSeparator);
        mainStackPane.getChildren().add(3, settingPopOverBorderPane);
    }

    private void configureSettingPopoverPane()
    {
        settingPopOverBorderPane = new BorderPane();

        StackPane headerPane = new StackPane();
        Label title = new Label("Settings");
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);

        headerPane.getChildren().add(0, title);
        StackPane.setAlignment(title, Pos.CENTER);
        StackPane.setMargin(title, new Insets(20));

        FontIcon xGraphic = new FontIcon(CarbonIcons.CLOSE);
        Button closeButton = new Button();
        closeButton.setGraphic(xGraphic);
        closeButton.setOnAction(closeEvent -> hideSettings());
        headerPane.getChildren().add(1, closeButton);
        StackPane.setAlignment(closeButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(closeButton, new Insets(20));
        settingPopOverBorderPane.setTop(headerPane);

        double sceneHeight = mainStackPane.getScene().getHeight();
        FxUtils.setStaticRegionSize(settingPopOverBorderPane, 425, (int) sceneHeight - 50);
        settingPopOverBorderPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(20), null))); // TODO change to css

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        for (ComponentSettingGroup settingGroup : currentAlgoView.getSettings())
        {
            settingGroup.getRenderer().minWidthProperty().bind(vBox.widthProperty());
            vBox.getChildren().add(settingGroup.getRenderer());
        }

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event ->
        {
            if (event.getDeltaX() != 0)
            {
                event.consume();
            }
        });
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vBox.minWidthProperty().bind(scrollPane.widthProperty());

        settingPopOverBorderPane.setCenter(scrollPane);
        BorderPane.setMargin(scrollPane, new Insets(0, 10, 10, 10));
    }

    private void configureSeparator()
    {
        settingsSeparator = new Rectangle(mainStackPane.getScene().getWidth(), mainStackPane.getScene().getHeight());
        settingsSeparator.setOnMouseClicked(event -> hideSettings());
        settingsSeparator.setFill(Color.WHITE);
        settingsSeparator.setOpacity(0.4f);
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
