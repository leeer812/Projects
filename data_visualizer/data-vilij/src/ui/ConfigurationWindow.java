/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import algorithms.AlgorithmCategory;
import algorithms.AlgorithmConfiguration;
import algorithms.AlgorithmType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import settings.AppPropertyTypes;
import vilij.templates.ApplicationTemplate;

/**
 *
 * @author goreg
 */
public class ConfigurationWindow
{

    private Stage primaryStage;
    private ApplicationTemplate appTemp;
    private AppUI appUI;
    private AlgorithmType algType;
    private AlgorithmCategory algCat;
    private int minValue;
    private int maxLabels;
    // private final int MAX_CHARS = 10;
    private int maxIt;
    private int updateInt;
    private int clusterNumber = 2;
    private String defaultEntry = "1";
    private String algorithmName;
    private String algorithmType;
    private boolean contRun;
    
    public ConfigurationWindow(AppUI appUI, String algorithmType, String algorithmName)
    {
        minValue = 1;
        maxLabels = 4;
        this.appUI = appUI;
        this.algCat = algCat;
        this.algorithmType = algorithmType;
        this.algorithmName = algorithmName;
        appTemp = new ApplicationTemplate();
        layout();
    }
    
    public ConfigurationWindow(AppUI appUI, AlgorithmType algType, String algorithmName)
    {
        minValue = 1;
        maxLabels = 4;
        this.appUI = appUI;
        this.algCat = algCat;
        this.algType = algType;
        this.algorithmName = algorithmName;
        appTemp = new ApplicationTemplate();
        layout();
    }

    public ConfigurationWindow(AppUI appUI, AlgorithmType algType, AlgorithmCategory algCat)
    {
        minValue = 1;
        maxLabels = 4;
        this.appUI = appUI;
        this.algCat = algCat;
        this.algType = algType;
        appTemp = new ApplicationTemplate();
        layout();
    }

    public void showOpenDialog()
    {
        primaryStage.show();
    }

    public void layout()
    {
        
        VBox root = new VBox();
        root.setPadding(new Insets(10, 0, 0, 10));
        root.setSpacing(15);
        Label title = new Label(appTemp.manager.getPropertyValue(AppPropertyTypes.ALG_RUN_CONFIG.toString()));
        title.setId(appTemp.manager.getPropertyValue(AppPropertyTypes.CONFIG_ID.toString()));
        HBox maxItBox = new HBox();
        Label maxItLabel = new Label(appTemp.manager.getPropertyValue(AppPropertyTypes.MAX_IT_LABEL.toString()));
        TextArea maxItTextArea = new TextArea("5");
        maxItTextArea.setPrefSize(300, 2);
//        // maxItTextArea.setTextFormatter(new TextFormatter<String>(change -> 
//            change.getControlNewText().length() <= MAX_CHARS-1 ? change : null));
        maxItBox.getChildren().addAll(maxItLabel, maxItTextArea);
        HBox updateIntBox = new HBox();
        Label updateIntLabel = new Label(appTemp.manager.getPropertyValue(AppPropertyTypes.UPDATE_INT_LABEL.toString()));
        TextArea updateIntTextArea = new TextArea(defaultEntry);
//        updateIntTextArea.setTextFormatter(new TextFormatter<String>(change -> 
//            change.getControlNewText().length() <= MAX_CHARS-1 ? change : null));
        updateIntTextArea.setPrefSize(300, 2);
        updateIntBox.getChildren().addAll(updateIntLabel, updateIntTextArea);
        HBox clusterNumberBox = new HBox();
        Label clusterNumberLabel = new Label(appTemp.manager.getPropertyValue(AppPropertyTypes.CLUSTER_NUM_LABEL.toString()));
        TextArea clusterNumberTextArea = new TextArea("2");
        clusterNumberTextArea.setPrefSize(300, 2);
        clusterNumberBox.getChildren().addAll(clusterNumberLabel, clusterNumberTextArea);
        HBox contRunBox = new HBox();
        Pane spacer = new Pane();
        contRunBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);
        spacer.setMaxWidth(145);
        Label contRunLabel = new Label(appTemp.manager.getPropertyValue(AppPropertyTypes.CONTRUN_LABEL.toString()));
        RadioButton contRunRadio = new RadioButton();
        Button submitButton = new Button(appTemp.manager.getPropertyValue(AppPropertyTypes.SUBMIT_BUTTON.toString()));
        contRunBox.getChildren().addAll(contRunLabel, contRunRadio, spacer, submitButton);
        if (algorithmType.equals("Clusterer"))
        {
            root.getChildren().addAll(title, maxItBox, updateIntBox, clusterNumberBox, contRunBox);
        } else
        {
            root.getChildren().addAll(title, maxItBox, updateIntBox, contRunBox);
        }
        primaryStage = new Stage();
        primaryStage.setTitle(appTemp.manager.getPropertyValue(AppPropertyTypes.ALG_CONFIG_WINDOW_TITLE.toString()));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(appTemp.manager.getPropertyValue(AppPropertyTypes.CONFIG_WINDOW_CSS_DIR.toString()));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.initOwner(appUI.getPrimaryWindow());
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        // primaryStage.initStyle(StageStyle.UNDECORATED);
        
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
        });
        
//        maxItTextArea.textProperty().addListener(new ChangeListener<String>()
//        {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
//            {
//                try{
//                if (Integer.parseInt(newValue) < minValue)
//                {
//                    maxItTextArea.setText(defaultEntry);
//                    Alert alert = new Alert(AlertType.WARNING);
//                    alert.setContentText(appTemp.manager.getPropertyValue(AppPropertyTypes.MORE_THAN_ONE_MSG.toString()));
//                    alert.show();
//                }
//                maxIt = Integer.parseInt(maxItTextArea.getText());
//                }
//                catch(Exception e)
//                {
//                    maxItTextArea.setText(defaultEntry);
//                    Alert alert = new Alert(AlertType.WARNING);
//                     alert.setContentText(appTemp.manager.getPropertyValue(AppPropertyTypes.MORE_THAN_ONE_MSG.toString()));
//                    alert.show();
//                }
//            }
//            
//        });
        
//        updateIntTextArea.textProperty().addListener(new ChangeListener<String>()
//        {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
//            {
//                try{
//                if (Integer.parseInt(newValue) < minValue)
//                {
//                    updateIntTextArea.setText(defaultEntry);
//                    Alert alert = new Alert(AlertType.WARNING);
//                     alert.setContentText(appTemp.manager.getPropertyValue(AppPropertyTypes.MORE_THAN_ONE_MSG.toString()));
//                    alert.show();
//                }
//                updateInt = Integer.parseInt(newValue);
//                }
//                catch (Exception e)
//                {
//                    updateIntTextArea.setText(defaultEntry);
//                    Alert alert = new Alert(AlertType.WARNING);
//                     alert.setContentText(appTemp.manager.getPropertyValue(AppPropertyTypes.MORE_THAN_ONE_MSG.toString()));
//                    alert.show();
//                }
//            }
//            
////        });
//        
//        clusterNumberTextArea.textProperty().addListener(new ChangeListener<String>()
//        {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
//            {
//                try{
//                if (Integer.parseInt(newValue) < minValue || Integer.parseInt(newValue) > maxLabels)
//                {
//                    clusterNumberTextArea.setText(defaultEntry);
//                    Alert alert = new Alert(AlertType.WARNING);
//                     alert.setContentText(appTemp.manager.getPropertyValue(AppPropertyTypes.LESS_THAN_FOUR_MSG.toString()));
//                    alert.show();
//                }
//                clusterNumber = Integer.parseInt(clusterNumberTextArea.getText());
//                }
//                catch (Exception e)
//                {
//                    clusterNumberTextArea.setText(defaultEntry);
//                    Alert alert = new Alert(AlertType.WARNING);
//                     alert.setContentText(appTemp.manager.getPropertyValue(AppPropertyTypes.LESS_THAN_FOUR_MSG.toString()));
//                    alert.show();
//                }
//            }
//            
//        });
        
        contRunRadio.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                contRun = contRunRadio.isSelected();
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                boolean exception = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                
                try{
                    maxIt = Integer.parseInt(maxItTextArea.getText());
                    if (maxIt < 1)
                        throw new Exception();
                }
                catch (Exception ex)
                {
                    exception = true;
                    maxItTextArea.setText("5");
                    maxIt = 5;
                    alert.setContentText(alert.getContentText() + "\n\n" + appTemp.manager.getPropertyValue(AppPropertyTypes.INVALID_CONFIG_FORMAT_MSG.toString()));
                }
                try{
                    updateInt = Integer.parseInt(updateIntTextArea.getText());
                    if (updateInt < 1)
                        throw new NumberFormatException();
                }
                catch (Exception ex)
                {
                    exception = true;
                    updateIntTextArea.setText("1");
                    updateInt = 1;
                    alert.setContentText(alert.getContentText() + "\n\n" + appTemp.manager.getPropertyValue(AppPropertyTypes.INVALID_CONFIG_UPDATE.toString()));
                }
                try{
                    if (algorithmType.equals("Clusterer"))
                        clusterNumber = Integer.parseInt(clusterNumberTextArea.getText());
                    if (clusterNumber < 2)
                    {
                        clusterNumberTextArea.setText("2");
                        clusterNumber = 2;
                        exception = true;
                    alert.setContentText(alert.getContentText() + "\n\n" + appTemp.manager.getPropertyValue(AppPropertyTypes.INVALID_CLUSTER_NUMBER.toString()));
                    }
                    if (clusterNumber > 4)
                    {
                        clusterNumberTextArea.setText("4");
                        clusterNumber = 4;
                        exception = true;
                    alert.setContentText(alert.getContentText() + "\n\n" + appTemp.manager.getPropertyValue(AppPropertyTypes.INVALID_CLUSTER_NUMBER.toString()));
                    }
                }
                catch (Exception ex)
                {
                    exception = true;
                    clusterNumberTextArea.setText("2");
                    clusterNumber = 2;
                    alert.setContentText(alert.getContentText() + "\n\n" + appTemp.manager.getPropertyValue(AppPropertyTypes.INVALID_CLUSTER_NUMBER.toString()));
                }
                
                contRun = contRunRadio.isSelected();

                    AlgorithmConfiguration configuration
                            = new AlgorithmConfiguration(ConfigurationWindow.this);

                    appUI.setConfiguration(configuration);
                
                if (exception)
                    alert.show();
                else 
                    primaryStage.close();

                    
            }
        });
    }

    public int getMaxIt()
    {
        return maxIt;
    }

    public int getUpdateInt()
    {
        return updateInt;
    }

    public int getClusterNumber()
    {
        return clusterNumber;
    }

    public AlgorithmType getAlgType()
    {
        return algType;
    }

    public AlgorithmCategory getAlgCat()
    {
        return algCat;
    }

    public boolean getContRun()
    {
        return contRun;
    }
    
    public String getAlgorithmName()
    {
        return algorithmName;
    }
    
    public String getAlgorithmType()
    {
        return algorithmType;
    }
}
