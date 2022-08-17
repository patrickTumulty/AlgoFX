package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.ceramic.components.ChoiceComponent;
import com.ptumulty.ceramic.components.ComponentSettingGroup;
import com.ptumulty.ceramic.utility.FxUtils;
import com.ptumulty.ceramic.utility.ThreadUtils;
import javafx.beans.value.ChangeListener;
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
    private final AlgoLauncherGridView launcherGridView;
    private final StackPane mainStackPane;
    private final Map<String, AlgoView> algoViewMap;
    private final BorderPane headerPane;
    private HBox secondaryActionsContainer;
    private HBox capabilitiesActionsContainer;
    private BorderPane visualizationOverlay;
    private BorderPane actionsPane;
    private AlgoView currentAlgoView;
    private Button backButton;
    private BorderPane algoViewLayout;
    private Rectangle settingsSeparator;
    private BorderPane settingPopOverBorderPane;
    private VBox algoTitlePane;
    private Button algoInfoButton;
    private Button algoResetButton;
    private Button algoSettingsButton;
    private Button algoActionButton;
    private ChangeListener<Boolean> actionBusyListener;

    public AlgoViewLauncherView()
    {
        mainStackPane = new StackPane();
        algoViewMap = new HashMap<>();

        populateAlgoViewMap();

        launcherGridView = new AlgoLauncherGridView(this, algoViewMap);

        addGridView();

        configureBackButton();

        configureActionsContainer();

        visualizationOverlay = new BorderPane();

        visualizationOverlay.setBottom(actionsPane);
        BorderPane.setAlignment(actionsPane, Pos.CENTER);

        headerPane = new BorderPane();
        headerPane.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);
        BorderPane.setMargin(backButton, new Insets(10));

        visualizationOverlay.setTop(headerPane);
        BorderPane.setAlignment(headerPane, Pos.CENTER);

        configureAlgoTitlePanel();








    }

    private void configureActionsContainer()
    {
        actionsPane = new BorderPane();

        secondaryActionsContainer = new HBox();
        secondaryActionsContainer.setSpacing(10);
        secondaryActionsContainer.setAlignment(Pos.CENTER_LEFT);
        actionsPane.setLeft(secondaryActionsContainer);
        BorderPane.setMargin(secondaryActionsContainer, new Insets(0, 0, 10, 10));
        BorderPane.setAlignment(secondaryActionsContainer, Pos.CENTER);

        capabilitiesActionsContainer = new HBox();
        capabilitiesActionsContainer.setSpacing(10);
        capabilitiesActionsContainer.setAlignment(Pos.CENTER_RIGHT);
        actionsPane.setRight(capabilitiesActionsContainer);
        BorderPane.setMargin(capabilitiesActionsContainer, new Insets(0, 10, 0, 10));
        BorderPane.setAlignment(capabilitiesActionsContainer, Pos.CENTER);

        configureAlgoActionButton();

        configureAlgoSettingsButton();

        configureAlgoResetButton();
    }

    private void configureBackButton()
    {
        backButton = new Button();
        backButton.setOnAction(event ->
                FxUtils.run(() ->
                {
                    addGridView();
                    removeBackButton();
                    mainStackPane.getChildren().remove(algoInfoButton);
                }));
        backButton.setMinWidth(50);
        backButton.setMinHeight(50);
        backButton.setShape(new Circle(50));
        FontIcon arrowLeftIcon = new FontIcon(FontAwesomeSolid.ARROW_LEFT);
        arrowLeftIcon.setIconColor(Color.MINTCREAM);
        arrowLeftIcon.setIconSize(20);
        backButton.setGraphic(arrowLeftIcon);
    }

    private void configureAlgoInfoButton()
    {
        algoInfoButton = new Button();
        algoInfoButton.setMinWidth(50);
        algoInfoButton.setMinHeight(50);
        algoInfoButton.setShape(new Circle(50));
        FontIcon fontIcon = new FontIcon(FontAwesomeSolid.CHART_BAR);
        fontIcon.setIconColor(Color.MINTCREAM);
        fontIcon.setIconSize(20);
        algoInfoButton.setGraphic(fontIcon);
    }

    private void addGridView()
    {
        if (currentAlgoView != null)
        {
            mainStackPane.getChildren().remove(0);
        }
        mainStackPane.getChildren().add(0, launcherGridView);
        StackPane.setAlignment(launcherGridView, Pos.CENTER);
    }

    private void removeBackButton()
    {
        mainStackPane.getChildren().remove(backButton);
    }

    private void addBackButton()
    {
        mainStackPane.getChildren().add(1, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(10));
    }

    private void populateAlgoViewMap()
    {
        for (AlgoView algoView : Lookup.getDefault().lookupAll(AlgoView.class))
        {
            algoViewMap.put(algoView.getTitle(), algoView);
        }
    }

    public void setAlgoView(AlgoView algoView)
    {
        setCurrentAlgoView(algoView);

        FxUtils.run(() ->
        {
            mainStackPane.getChildren().remove(launcherGridView);

            algoActionButton.setText(algoView.getAlgoActionName());

            configureAlgoViewTitle(algoView);

            configureAlgoModesIfPresent(algoView);

            mainStackPane.getChildren().add(algoView.getVisualizationPane());
            StackPane.setAlignment(algoView.getVisualizationPane(), Pos.CENTER);
            StackPane.setMargin(algoView.getVisualizationPane(), new Insets(10));

            mainStackPane.getChildren().add(visualizationOverlay);
        });
    }

    private void addAlgoInfoButton()
    {
        mainStackPane.getChildren().add(2, algoInfoButton);
        StackPane.setAlignment(algoInfoButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(algoInfoButton, new Insets(10));
    }

    private void configureAlgoModesIfPresent(AlgoView algoView)
    {
        if (algoView.getAlgoModes().isPresent())
        {
            ChoiceComponent<String> modes = new ChoiceComponent<>(algoView.getAlgoModes().get());
            modes.getRenderer().setStyle("-fx-font-size: 18;");
            algoTitlePane.getChildren().add(modes.getRenderer());
        }
    }

    private void configureAlgoViewTitle(AlgoView algoView)
    {
        Label algoViewLabel = new Label(algoView.getTitle());
        algoViewLabel.setTextAlignment(TextAlignment.CENTER);
        algoViewLabel.setAlignment(Pos.CENTER);
        algoViewLabel.setStyle("-fx-font-size: 30;");
        algoTitlePane.getChildren().add(algoViewLabel);
    }

    private void configureAlgoTitlePanel()
    {
        algoTitlePane = new VBox();
        algoTitlePane.getStyleClass().add("algoTitlePanel");
        algoTitlePane.setAlignment(Pos.CENTER);
        algoTitlePane.setSpacing(10);

        headerPane.setCenter(algoTitlePane);
        BorderPane.setAlignment(algoTitlePane, Pos.CENTER);
    }

    private void configureAlgoResetButton()
    {
        algoResetButton = new Button("Reset");
        algoResetButton.getStyleClass().add("algoReset");
        algoResetButton.getStyleClass().add("algoControl");

        algoResetButton.setOnAction(event -> ThreadUtils.run(() -> currentAlgoView.doAlgoReset()));

        secondaryActionsContainer.getChildren().add(algoResetButton);
    }

    private void configureAlgoSettingsButton()
    {
        algoSettingsButton = new Button();
        algoSettingsButton.getStyleClass().add("algoSettings");
        algoSettingsButton.getStyleClass().add("algoControl");
        FontIcon cogIcon = new FontIcon(FontAwesomeSolid.COG);
        cogIcon.setIconColor(Color.WHITE);
        algoSettingsButton.setGraphic(cogIcon);
        Circle circle = new Circle();
        circle.radiusProperty().bind(algoSettingsButton.heightProperty());
        algoSettingsButton.minWidthProperty().bind(algoSettingsButton.heightProperty());
        algoSettingsButton.setShape(circle);
        algoSettingsButton.setOnAction(event -> showSettings());

        secondaryActionsContainer.getChildren().add(algoSettingsButton);
    }

    private void configureAlgoActionButton()
    {
        algoActionButton = new Button();
        algoActionButton.getStyleClass().add("algoAction");
        algoActionButton.getStyleClass().add("algoControl");
        actionBusyListener = (observable, oldValue, newValue) ->
        {
            if (newValue)
            {
                FxUtils.run(() -> algoActionButton.setText("Cancel"));
                algoActionButton.setOnAction(event -> currentAlgoView.doAlgoCancel());
            }
            else
            {
                FxUtils.run(() -> algoActionButton.setText(currentAlgoView.getAlgoActionName()));
                algoActionButton.setOnAction(event -> ThreadUtils.run(() -> currentAlgoView.doAlgoAction()));
            }
        };
        algoActionButton.setOnAction(event -> ThreadUtils.run(() -> currentAlgoView.doAlgoAction()));
        actionsPane.setCenter(algoActionButton);
        BorderPane.setAlignment(algoActionButton, Pos.CENTER);
        BorderPane.setMargin(algoActionButton, new Insets(10));
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
        Label title = new Label(currentAlgoView.getTitle() + " Settings");
        title.setStyle("-fx-font-size: 20");
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);

        headerPane.getChildren().add(0, title);
        StackPane.setAlignment(title, Pos.CENTER);
        StackPane.setMargin(title, new Insets(20));

        FontIcon xGraphic = new FontIcon(CarbonIcons.CLOSE);
        xGraphic.setIconColor(Color.MINTCREAM);
        xGraphic.setIconSize(30);
        Button closeButton = new Button();
        closeButton.getStyleClass().add("algoCloseButton");
        closeButton.setGraphic(xGraphic);
        closeButton.setOnAction(closeEvent -> hideSettings());
        headerPane.getChildren().add(1, closeButton);
        StackPane.setAlignment(closeButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(closeButton, new Insets(20));
        settingPopOverBorderPane.setTop(headerPane);

        double sceneHeight = mainStackPane.getScene().getHeight();
        FxUtils.setStaticRegionSize(settingPopOverBorderPane, 500, (int) sceneHeight - 50);
        settingPopOverBorderPane.getStyleClass().add("algoSettingsPanel");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        for (ComponentSettingGroup settingGroup : currentAlgoView.getSettings())
        {
            settingGroup.getRenderer().minWidthProperty().bind(vBox.widthProperty());
            vBox.getChildren().add(settingGroup.getRenderer());
        }

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event ->
        {
            if (event.getDeltaX() != 0)
            {
                event.consume();
            }
        });
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vBox.minWidthProperty().bind(scrollPane.widthProperty().subtract(40));

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
            currentAlgoView.busyProperty().removeListener(actionBusyListener);
            algoResetButton.disableProperty().unbind();
        }

        currentAlgoView = algoView;

        currentAlgoView.busyProperty().addListener(actionBusyListener);
        algoResetButton.disableProperty().bind(currentAlgoView.busyProperty());

        currentAlgoView.initView();
    }

    public Pane getView()
    {
        return mainStackPane;
    }
}
