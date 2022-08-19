package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.AlgoFX.AlgoModel.AlgoModelController;
import com.ptumulty.AlgoFX.AlgoModel.AlgoModelManager;
import com.ptumulty.ceramic.FourCornerPane;
import com.ptumulty.ceramic.components.ChoiceComponent;
import com.ptumulty.ceramic.utility.FxUtils;
import com.ptumulty.ceramic.utility.ThreadUtils;
import javafx.beans.value.ChangeListener;
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

public class AlgoViewLauncherView implements AlgoModelManager.AlgoModelManagerListener
{
    private final AlgoLauncherGridView launcherGridView;
    private final StackPane mainStackPane;
    private final Map<String, AlgoAsset> algoAssetMap;
    private final FourCornerPane fourCornerOverlay;
    private final ChoiceComponent<String> algoModesComponent;
    private final AlgoSettingsPane algoSettingsPane;
    private AlgoAsset currentAlgoAsset;
    private Button backButton;
    private Region settingsSeparator;
    private VBox algoTitlePane;
    private Button algoResetButton;
    private Button algoSettingsButton;
    private Button algoActionButton;
    private ChangeListener<Boolean> actionBusyListener;
    private Label algoViewLabel;

    public AlgoViewLauncherView()
    {
        Lookup.getDefault().lookup(AlgoModelManager.class).addListener(this);

        mainStackPane = new StackPane();
        algoAssetMap = new HashMap<>();

        populateAlgoViewMap();

        launcherGridView = new AlgoLauncherGridView();

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

        configureSeparator();

        algoSettingsPane = new AlgoSettingsPane(mainStackPane);
        algoSettingsPane.addListener(this::hideSettings);
    }

    private void configureBackButton()
    {
        backButton = new Button();
        backButton.setOnAction(event -> Lookup.getDefault().lookup(AlgoModelManager.class).setAlgoModelController(null));

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

    public void setAlgoView(AlgoAsset algoAsset)
    {
        setCurrentAlgoAsset(algoAsset);

        FxUtils.run(() ->
        {
            mainStackPane.getChildren().remove(launcherGridView);

            algoActionButton.setText(algoAsset.getAlgoActionName());

            algoViewLabel.setText(algoAsset.getTitle());

            algoSettingsPane.dispose();
            algoSettingsPane.attachAlgoAsset(algoAsset);

            configureAlgoModesIfPresent(algoAsset);

            fourCornerOverlay.setCenter(algoAsset.getVisualizationPane());
            BorderPane.setAlignment(algoAsset.getVisualizationPane(), Pos.CENTER);
            BorderPane.setMargin(algoAsset.getVisualizationPane(), new Insets(10));

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
        mainStackPane.getChildren().remove(algoSettingsPane);
    }

    private void showSettings()
    {
        mainStackPane.getChildren().add(settingsSeparator);
        mainStackPane.getChildren().add(algoSettingsPane);
    }

    private void configureSeparator()
    {
        settingsSeparator = new Region();
        settingsSeparator.prefWidthProperty().bind(mainStackPane.widthProperty());
        settingsSeparator.prefHeightProperty().bind(mainStackPane.heightProperty());
        settingsSeparator.setOnMouseClicked(event -> hideSettings());
        settingsSeparator.setStyle("-fx-background-color: white");
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

    @Override
    public void algoModelChanged(AlgoModelController newModel)
    {
        if (newModel == null)
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
        }
        else
        {
            AlgoAsset algoAsset = AlgoAssetFactory.create(newModel);
            if (algoAsset == null)
            {
                System.out.println("NULL");
                return;
            }

            setAlgoView(algoAsset);
        }
    }
}
