package com.ptumulty.AlgoFX.AlgoView;

import com.ptumulty.ceramic.components.ComponentSettingGroup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.carbonicons.CarbonIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.List;

public class AlgoSettingsPane extends BorderPane
{
    private final StackPane parent;
    private final VBox settingsVBox;
    private final StackPane headerPane;
    private final List<CloseListener> listeners;

    AlgoSettingsPane(StackPane parent)
    {
        this.parent = parent;
        listeners = new ArrayList<>();

        configureSettingPaneSize();

        headerPane = new StackPane();
        setTop(headerPane);

        configureTitleLabel();

        configureCloseButton();

        getStyleClass().add("algoSettingsPanel");

        settingsVBox = new VBox();
        settingsVBox.setAlignment(Pos.TOP_CENTER);

        configureScrollPane();

        addListener(this::dispose);
    }

    private void configureSettingPaneSize()
    {
        setPrefWidth(500);
        setMinWidth(500);
        setMaxWidth(500);
        minHeightProperty().bind(this.parent.heightProperty().subtract(50));
        maxHeightProperty().bind(this.parent.heightProperty().subtract(50));
        prefHeightProperty().bind(this.parent.heightProperty().subtract(50));
    }

    private void configureScrollPane()
    {
        ScrollPane settingsScrollPane = new ScrollPane(settingsVBox);
        settingsScrollPane.setFitToWidth(true);
        settingsScrollPane.addEventFilter(ScrollEvent.SCROLL, event ->
        {
            if (event.getDeltaX() != 0)
            {
                event.consume();
            }
        });
        settingsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        settingsVBox.minWidthProperty().bind(settingsScrollPane.widthProperty().subtract(40));

        setCenter(settingsScrollPane);
        BorderPane.setMargin(settingsScrollPane, new Insets(0, 10, 10, 10));
    }

    private void configureCloseButton()
    {
        FontIcon xGraphic = new FontIcon(CarbonIcons.CLOSE);
        xGraphic.setIconColor(Color.MINTCREAM);
        xGraphic.setIconSize(30);
        Button closeButton = new Button();
        closeButton.getStyleClass().add("algoCloseButton");
        closeButton.setGraphic(xGraphic);
        closeButton.setOnAction(closeEvent -> listeners.forEach(CloseListener::close));
        headerPane.getChildren().add(1, closeButton);
        StackPane.setAlignment(closeButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(closeButton, new Insets(20));
    }

    private void configureTitleLabel()
    {
        Label title = new Label("Settings");
        title.setStyle("-fx-font-size: 20");
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);

        headerPane.getChildren().add(0, title);
        StackPane.setAlignment(title, Pos.CENTER);
        StackPane.setMargin(title, new Insets(20));
    }

    public void attachAlgoAsset(AlgoModelView algoModelView)
    {
        for (ComponentSettingGroup settingGroup : algoModelView.getSettings())
        {
            settingGroup.getRenderer().minWidthProperty().bind(settingsVBox.widthProperty());
            settingsVBox.getChildren().add(settingGroup.getRenderer());
        }
    }

    public void dispose()
    {
        settingsVBox.getChildren().clear();
    }

    public void addListener(CloseListener closeListener)
    {
        listeners.add(closeListener);
    }

    public void removeListener(CloseListener closeListener)
    {
        listeners.remove(closeListener);
    }

    public interface CloseListener
    {
        void close();
    }
}
