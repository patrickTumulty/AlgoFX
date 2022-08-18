package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.ceramic.FourCornerPane;
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
    private final Map<String, AlgoAsset> algoAssetMap;
    private final FourCornerPane fourCornerOverlay;
    private final ChoiceComponent<String> algoModesComponent;
    private AlgoAsset currentAlgoAsset;
    private Button backButton;
    private Rectangle settingsSeparator;
    private BorderPane settingPopOverBorderPane;
    private VBox algoTitlePane;
    private Button algoResetButton;
    private Button algoSettingsButton;
    private Button algoActionButton;
    private ChangeListener<Boolean> actionBusyListener;
    private Label algoViewLabel;

    public AlgoViewLauncherView()
    {
        mainStackPane = new StackPane();
        algoAssetMap = new HashMap<>();

        populateAlgoViewMap();

        launcherGridView = new AlgoLauncherGridView(this, algoAssetMap);

        addGridView();

        fourCornerOverlay = new FourCornerPane();
        fourCornerOverlay.setSpacing(10);
        fourCornerOverlay.setInsets(10);

        configureAlgoSettingsButton();
        configureAlgoResetButton();
        configureBackButton();

        fourCornerOverlay.getTopLeft().getChildren().add(backButton);
        fourCornerOverlay.getBottomLeft().getChildren().add(algoSettingsButton);
        fourCornerOverlay.getBottomLeft().getChildren().add(algoResetButton);
        fourCornerOverlay.setPickOnBounds(false);

        configureAlgoTitlePanel();
        algoModesComponent = new ChoiceComponent<>();
        algoModesComponent.getRenderer().setStyle("-fx-font-size: 18;");
        configureAlgoViewTitle();
        fourCornerOverlay.setTopNode(algoTitlePane);

        configureAlgoActionButton();
        fourCornerOverlay.setBottomNode(algoActionButton);
    }

    private void configureBackButton()
    {
        backButton = new Button();
        backButton.setOnAction(event ->
        {
            currentAlgoAsset.dispose();

            FxUtils.run(() ->
            {
                addGridView();
                if (currentAlgoAsset.getAlgoModes().isPresent())
                {
                    algoTitlePane.getChildren().remove(algoModesComponent.getRenderer());
                }
                mainStackPane.getChildren().remove(fourCornerOverlay);
            });
        });

        backButton.setMinWidth(50);
        backButton.setMinHeight(50);
        backButton.setShape(new Circle(50));
        FontIcon arrowLeftIcon = new FontIcon(FontAwesomeSolid.ARROW_LEFT);
        arrowLeftIcon.setIconColor(Color.MINTCREAM);
        arrowLeftIcon.setIconSize(20);
        backButton.setGraphic(arrowLeftIcon);
    }

    private void addGridView()
    {
        if (currentAlgoAsset != null)
        {
            mainStackPane.getChildren().remove(0);
        }
        mainStackPane.getChildren().add(0, launcherGridView);
        StackPane.setAlignment(launcherGridView, Pos.CENTER);
    }

    private void populateAlgoViewMap()
    {
        for (AlgoAsset algoView : Lookup.getDefault().lookupAll(AlgoAsset.class))
        {
            algoAssetMap.put(algoView.getTitle(), algoView);
        }
    }

    public void setAlgoView(AlgoAsset algoView)
    {
        setCurrentAlgoAsset(algoView);

        FxUtils.run(() ->
        {
            mainStackPane.getChildren().remove(launcherGridView);

            algoActionButton.setText(algoView.getAlgoActionName());

            algoViewLabel.setText(algoView.getTitle());

            configureAlgoModesIfPresent(algoView);

            mainStackPane.getChildren().add(algoView.getVisualizationPane());
            StackPane.setAlignment(algoView.getVisualizationPane(), Pos.CENTER);
            StackPane.setMargin(algoView.getVisualizationPane(), new Insets(10));

            mainStackPane.getChildren().add(fourCornerOverlay);
        });
    }

    private void configureAlgoModesIfPresent(AlgoAsset algoView)
    {
        if (algoView.getAlgoModes().isPresent())
        {
            algoModesComponent.attachModel(algoView.getAlgoModes().get());
            algoTitlePane.getChildren().add(algoModesComponent.getRenderer());
        }
    }

    private void configureAlgoViewTitle()
    {
        algoViewLabel = new Label();
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
    }

    private void configureAlgoResetButton()
    {
        algoResetButton = new Button("Reset");
        algoResetButton.getStyleClass().add("algoReset");
        algoResetButton.getStyleClass().add("algoControl");

        algoResetButton.setOnAction(event -> ThreadUtils.run(() -> currentAlgoAsset.doAlgoReset()));
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
                algoActionButton.setOnAction(event -> currentAlgoAsset.doAlgoCancel());
            }
            else
            {
                FxUtils.run(() -> algoActionButton.setText(currentAlgoAsset.getAlgoActionName()));
                algoActionButton.setOnAction(event -> ThreadUtils.run(() -> currentAlgoAsset.doAlgoAction()));
            }
        };
        algoActionButton.setOnAction(event -> ThreadUtils.run(() -> currentAlgoAsset.doAlgoAction()));
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
        Label title = new Label(currentAlgoAsset.getTitle() + " Settings");
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
        for (ComponentSettingGroup settingGroup : currentAlgoAsset.getSettings())
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

    private void setCurrentAlgoAsset(AlgoAsset algoView)
    {
        if (currentAlgoAsset != null)
        {
            currentAlgoAsset.dispose();
            currentAlgoAsset.busyProperty().removeListener(actionBusyListener);
            algoResetButton.disableProperty().unbind();
        }

        currentAlgoAsset = algoView;

        currentAlgoAsset.busyProperty().addListener(actionBusyListener);
        algoResetButton.disableProperty().bind(currentAlgoAsset.busyProperty());

        currentAlgoAsset.initView();
    }

    public Pane getView()
    {
        return mainStackPane;
    }
}
