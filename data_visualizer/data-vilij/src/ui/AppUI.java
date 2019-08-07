package ui;

import actions.AppActions;
import algorithms.Algorithm;
import algorithms.AlgorithmCategory;
import algorithms.AlgorithmConfiguration;
import algorithms.AlgorithmType;
import dataprocessors.DataSet;
import java.io.File;
import static java.io.File.separator;
import java.lang.reflect.Method;
import java.util.HashSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import settings.AppPropertyTypes;
import static settings.AppPropertyTypes.SCREENSHOT_ICON;
import static settings.AppPropertyTypes.SCREENSHOT_TOOLTIP;
import vilij.propertymanager.PropertyManager;
import static vilij.settings.PropertyTypes.GUI_RESOURCE_PATH;
import static vilij.settings.PropertyTypes.ICONS_RESOURCE_PATH;
import static vilij.settings.PropertyTypes.IS_WINDOW_RESIZABLE;
import vilij.templates.ApplicationTemplate;
import vilij.templates.UITemplate;

/**
 * This is the application's user interface implementation.
 *
 * @author Ritwik Banerjee
 */
public final class AppUI extends UITemplate
{

    /**
     * The application to which this class of actions belongs.
     */
    private ApplicationTemplate applicationTemplate;
    private GridPane buttonPane;
    private Button scrnshotButton; // toolbar button to take a screenshot of the data
    private LineChart<Number, Number> chart;          // the chart where data will be displayed
    private NumberAxis xAxis, yAxis;
    private Button doneButton;
    private Button editButton;
    private Button algType;
    private Button runButton;
    private RadioButton selectedRadioButton;
    protected String scrnshotIconPath;
    private ToggleGroup toggleGroup;
    private ToolBar toolBar;
    private Label label;
    private Label dataLabel;
    private Label iterationLabel;
    private Label outOfBoundsLabel;
    private TextArea textArea;       // text area for new data input
    private String loadFileDirectory;
    private String oldText;
    private String curText;
    private String savedText;
    private String allText;
    private String algorithmTypeName;
    private DataSet dataSet;
    private AlgorithmType algorithmType;
    private AlgorithmCategory selectedAlgorithmCategory;
    private HashSet<AlgorithmConfiguration> configuration;
    private AlgorithmConfiguration runConfiguration;
    private Thread algRunner;
    private VBox leftBox;
    private VBox algBox;
    private boolean hasNewText;     // whether or not the text area has any new data since last display
    private boolean saved;
    private boolean saveFileExists;
    private boolean algorithmRunning;
    private boolean algorithmExists;
    private boolean loadedData = false;
    private HashSet<Class> algorithmTypes;
    private HashSet<Class> algorithmNames;
    private HashSet<ConfigurationWindow> windows;
    private HashSet<Button> algorithmTypeButtons;
    private HashSet<RadioButton> algorithmNameRadios;
    private Algorithm algorithm;

    public LineChart<Number, Number> getChart()
    {
        return chart;
    }

    public LineChart makeChart()
    {
        LineChart tempChart;
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        tempChart = new LineChart<>(xAxis, yAxis);
        return tempChart;
    }

    public AppUI(Stage primaryStage, ApplicationTemplate applicationTemplate)
    {
        super(primaryStage, applicationTemplate);
        this.applicationTemplate = applicationTemplate;
        primaryStage.getScene().getStylesheets().add(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.MAIN_WINDOW_CSS_DIR.toString()));

//        randClass = new RandomClassifier(this);
        
        configuration = new HashSet<>();
        hasNewText = true;
        saved = true;
        saveFileExists = false;
        algorithmRunning = false;
        algorithmExists = false;
        chart = makeChart();
        chart.setCreateSymbols(false);
        chart.setAnimated(false);
        chart.getStylesheets().add(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.CSS_RESOURCE_DIR.toString()));
        textArea = new TextArea();
        // textArea.setEditable(false);
        savedText = textArea.getText();
        oldText = textArea.getText();
        curText = textArea.getText();
        allText = "";
        loadFileDirectory = "";
        newButton.setDisable(false);
        runButton = new Button(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.RUN_BUTTON.toString()));
        runButton.setDisable(true);
        runButton.setVisible(false);
        doneButton = new Button(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DONE_BUTTON.toString()));
        doneButton.setDisable(true);
//        backButtonClass = new Button(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.BACK_BUTTON.toString()));
//        backButtonCluster = new Button(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.BACK_BUTTON.toString()));
        editButton = new Button(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.EDIT_BUTTON.toString()));
        algType = new Button(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ALG_TYPE_BUTTON.toString()));
        algType.setId(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.BUTTON_ID.toString()));
//        classType = new Button(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.CLASS_BUTTON.toString()));
//        classType.setDisable(true);
//        clusterType = new Button(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.CLUSTER_BUTTON.toString()));
//        configClass = new RadioButton(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.RANDOM_CLASS_RADIO.toString()));
//        configCluster = new RadioButton(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.RANDOM_CLUSTER_RADIO.toString()));
        toggleGroup = new ToggleGroup();
//        configClass.setToggleGroup(toggleGroup);
//        configCluster.setToggleGroup(toggleGroup);
//        classGear = new ImageView(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.GEAR_PNG_DIR.toString()));
//        clusterGear = new ImageView(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.GEAR_PNG_DIR.toString()));
        textArea.setMinSize(230, 150);
        textArea.setMaxSize(230, 150);
        label = new Label(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.TEXT_AREA.toString()));
        dataLabel = new Label("");
        iterationLabel = new Label(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ITERATION_LABEL.toString()));
        outOfBoundsLabel = new Label("");
//        classWindow = null;
//        clusterWindow = null;
        algorithmTypes = new HashSet<Class>();
        algorithmNames = new HashSet<Class>();
        windows = new HashSet<ConfigurationWindow>();
        selectedRadioButton = null;
        algorithm = null;
        algorithmTypeName = "";
        algorithmTypeButtons = new HashSet<>();
        algorithmNameRadios = new HashSet<>();
        
        algorithmTypes = ClassFinder.find(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ALG_TYPE_PACKAGE.toString()));
        algorithmNames = ClassFinder.find(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ALG_NAME_PACKAGE.toString()));
    }

    @Override
    protected void setResourcePaths(ApplicationTemplate applicationTemplate)
    {
        super.setResourcePaths(applicationTemplate);
        PropertyManager manager = applicationTemplate.manager;
        String iconsPath = "/" + String.join(separator,
                manager.getPropertyValue(GUI_RESOURCE_PATH.name()),
                manager.getPropertyValue(ICONS_RESOURCE_PATH.name()));
        scrnshotIconPath = String.join(separator, iconsPath, manager.getPropertyValue(SCREENSHOT_ICON.name()));
    }

    @Override
    protected void setToolBar(ApplicationTemplate applicationTemplate)
    {
        super.setToolBar(applicationTemplate);
        PropertyManager manager = applicationTemplate.manager;
        scrnshotButton = setToolbarButton(scrnshotIconPath, manager.getPropertyValue(SCREENSHOT_TOOLTIP.name()), true);
        toolBar = new ToolBar(newButton, saveButton, loadButton, printButton, exitButton, scrnshotButton);
    }

    @Override
    protected void setToolbarHandlers(ApplicationTemplate applicationTemplate)
    {
        applicationTemplate.setActionComponent(new AppActions(applicationTemplate));
        AppActions action = new AppActions(applicationTemplate);
        newButton.setOnAction(e -> applicationTemplate.getActionComponent().handleNewRequest());
        saveButton.setOnAction(e -> applicationTemplate.getActionComponent().handleSaveRequest());
        loadButton.setOnAction(e -> applicationTemplate.getActionComponent().handleLoadRequest());
        exitButton.setOnAction(e -> applicationTemplate.getActionComponent().handleExitRequest());
        printButton.setOnAction(e -> applicationTemplate.getActionComponent().handlePrintRequest());
        scrnshotButton.setOnAction(e -> action.handleScreenshotRequest());
    }

    @Override
    public void initialize()
    {
        layout();
        setWorkspaceActions();
    }

    @Override
    public void clear()
    {
        chart.getData().clear();
    }

    @Override
    public void setWindow(ApplicationTemplate applicationTemplate)
    {
        primaryStage.setTitle(applicationTitle);
        primaryStage.setResizable(applicationTemplate.manager.getPropertyValueAsBoolean(IS_WINDOW_RESIZABLE.name()));
        appPane = new VBox();
        appPane.getChildren().add(toolBar);

        primaryScene = windowWidth < 1 || windowHeight < 1 ? new Scene(appPane)
                : new Scene(appPane, windowWidth, windowHeight);
        primaryStage.getIcons().add(logo);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private void layout()
    {
        GridPane gPane = new GridPane();
        buttonPane = new GridPane();
        buttonPane.setMaxWidth(230);
        algBox = new VBox();
        gPane.setPadding(new Insets(15, 15, 0, 15));
        leftBox = new VBox();
        HBox labelBox = new HBox();
        HBox buttonBox = new HBox();
        leftBox.setSpacing(10);
        
        algBox.getChildren().addAll(algType);
        
        for (Class algorithmType : algorithmTypes)
        {
            Button algorithmTypeButton = new Button(algorithmType.getSimpleName());
            algorithmTypeButton.setPrefWidth(230);
            algorithmTypeButtons.add(algorithmTypeButton);

            VBox algorithmTypeVBox = new VBox();
            algorithmTypeVBox.setSpacing(10);
            
            Button backButton = new Button(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.BACK_BUTTON.toString()));
            backButton.setPrefWidth(230);
            backButton.setOnAction(e -> handleBackRequest());
            algorithmTypeVBox.getChildren().add(backButton);
            
            for (Class algorithmName : algorithmNames)
            {
                if (algorithmType.isAssignableFrom(algorithmName))
                {
                    HBox algorithmHBox = new HBox();
                    Pane spacer = new Pane();
                    algorithmHBox.setHgrow(spacer, Priority.ALWAYS);
                    spacer.setMinSize(10, 1);
                    RadioButton algorithmRadio = new RadioButton(algorithmName.getSimpleName());
                    algorithmNameRadios.add(algorithmRadio);
                    algorithmRadio.setToggleGroup(toggleGroup);
                    algorithmRadio.setOnAction(e ->
                    {
                        if (algorithmRadio.isSelected())
                        {
                            boolean existingConfig = false;
                            for (AlgorithmConfiguration configuration : configuration)
                            {
                                if (configuration.getAlgorithmName().equals(algorithmName.getSimpleName()))
                                {
                                    existingConfig = true;
                                }
                            }
                            if (existingConfig && !algorithmRunning)
                            {
                                runButton.setDisable(false);
                            } else
                            {
                                runButton.setDisable(true);
                            }
                            selectedRadioButton = algorithmRadio;
                        } else
                        {
                            runButton.setDisable(true);
                            selectedRadioButton = null;
                        }
                    });

                    ImageView algorithmGear = new ImageView(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.GEAR_PNG_DIR.toString()));
                    algorithmGear.setOnMouseClicked(e ->
                    {
                        boolean windowExists = false;
                        for (ConfigurationWindow window : windows)
                        {
                            if (window.getAlgorithmName().equals(algorithmName.getSimpleName()))
                            {
                                window.showOpenDialog();
                                windowExists = true;
                                break;
                            }
                        }
                        if (windowExists == false)
                        {
                            ConfigurationWindow window = new ConfigurationWindow(this, this.algorithmTypeName, algorithmName.getSimpleName());
                            windows.add(window);
                            windowExists = true;
                            window.showOpenDialog();
                        }
                    });
                    algorithmHBox.getChildren().addAll(algorithmRadio, spacer, algorithmGear);
                    algorithmTypeVBox.getChildren().add(algorithmHBox);
                    
                }
            }
            algorithmTypeButton.setOnAction(e ->
            {
                algorithmTypeName = algorithmTypeButton.getText();
                buttonPane.getChildren().remove(0, buttonPane.getChildren().size());
                buttonPane.getChildren().add(algorithmTypeVBox);
                ((AppUI) applicationTemplate.getUIComponent()).displayRun(true);
            });
            
            algBox.getChildren().add(algorithmTypeButton);
        }
        
        VBox chartBox = new VBox();
        chartBox.setSpacing(10);

        labelBox.getChildren().add(label);
        labelBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(doneButton, editButton);
        
        Pane labelSpacer = new Pane();
        HBox iterationHolder = new HBox();
        iterationHolder.setHgrow(labelSpacer, Priority.ALWAYS);
        labelSpacer.setMinSize(50, 1);
        labelSpacer.setMaxSize(210, 1);
        iterationHolder.getChildren().addAll(labelSpacer, iterationLabel);
        
        Pane outOfBoundsSpacer = new Pane();
        HBox outOfBoundsHolder = new HBox();
        outOfBoundsHolder.setHgrow(labelSpacer, Priority.ALWAYS);
        outOfBoundsSpacer.setMinSize(150, 1);
        outOfBoundsSpacer.setMaxSize(210, 1);
        outOfBoundsHolder.getChildren().addAll(outOfBoundsSpacer, outOfBoundsLabel);
        chartBox.getChildren().addAll(chart, iterationHolder, outOfBoundsHolder);
        
        algType.setPrefSize(230, 50);
        buttonPane.getChildren().addAll(algBox);
        buttonPane.setVisible(false);
        leftBox.getChildren().addAll(labelBox, buttonBox, textArea, dataLabel, buttonPane, runButton);
        leftBox.setVisible(false);
        gPane.add(leftBox, 0, 0);
        gPane.add(chartBox, 1, 0);
        appPane.getChildren().add(gPane);
    }

    private void setWorkspaceActions()
    {
        setTextAreaActionProperties();
        doneButton.setOnAction(e -> handleDoneRequest());
        editButton.setOnAction(e -> handleEditRequest());
        runButton.setOnAction(e -> handleRunRequest());
    }

    public boolean getSaveStatus()
    {
        return saved;
    }

    public void setSavedText()
    {
        savedText = textArea.getText();
    }

    public void saveFileTrue()
    {
        saveFileExists = true;
    }

    public void saveFileFalse()
    {
        saveFileExists = false;
    }

    public void newText()
    {
        oldText = "";
        allText = "";
        textArea.setText("");
        hasNewText = true;
    }

    public boolean hasNewTextData()
    {
        return ((hasNewText && hasTextData()) || !saved);
    }

    private void setTextAreaActionProperties()
    {
        textArea.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue)
            {
                if ((allText.split("\n").length > 10))
                {
                    try
                    {
                        String allTextUpdate = "", textAreaUpdate = "";
                        String[] allTextLines = allText.split("\n"), textAreaLines = textArea.getText().split("\n");
                        int start = 10;

                        // set first ten lines of allText with the first ten lines of textAreaLines
                        if (textAreaLines.length < 10)
                        {
                            for (int i = 0; i < textAreaLines.length; i++)
                            {
                                if (i != textAreaLines.length - 1)
                                {
                                    allTextUpdate += textAreaLines[i] + "\n";
                                } else
                                {
                                    allTextUpdate += textAreaLines[i];
                                }
                            }
                        } else
                        {
                            for (int i = 0; i < 10; i++)
                            {
                                if (i != 9)
                                {
                                    allTextUpdate += textAreaLines[i] + "\n";
                                } else
                                {
                                    allTextUpdate += textAreaLines[i];
                                }
                            }
                        }

                        if (textArea.getText().split("\n").length > 10)
                        {
                            start = textArea.getText().split("\n").length + 1;
                        }

                        if (allTextLines.length > 10)
                        {
                            // set the rest of allText equal to the lines after line ten of allTextLines
                            for (int i = start; i < allTextLines.length; i++)
                            {
                                allTextUpdate += "\n" + allTextLines[i];
                            }
                            // place first ten lines of updated allText into textArea
                            String[] allTextLinesUpdate = allTextUpdate.split("\n");
                            if (allTextLinesUpdate.length > 10)
                            {
                                for (int i = 0; i < 10; i++)
                                {
                                    if (i != 9)
                                    {
                                        textAreaUpdate += allTextLinesUpdate[i] + "\n";
                                    } else
                                    {
                                        textAreaUpdate += allTextLinesUpdate[i];
                                    }
                                }
                            } else
                            {
                                textAreaUpdate = allTextUpdate;
                            }
                        } else
                        {
                            textAreaUpdate = allTextUpdate;
                        }

                        allText = allTextUpdate;
                        if (textArea.getText().split("\n").length < 10)
                        {
                            textArea.setText(textAreaUpdate);
                        }
                    } catch (Exception e)
                    {
                        // do nothing
                    }
                } else
                {
                    allText = textArea.getText();
                }

                if (allText.split("\n").length > 10)
                {
                    curText = allText;
                } else
                {
                    curText = textArea.getText();
                }

                if (!curText.equals(oldText))
                {
                    hasNewText = true;
                } else
                {
                    hasNewText = false;
                }

                if (!curText.equals(savedText))
                {
                    saved = false;
                } else
                {
                    saved = true;
                }

                if (curText.equals(""))
                {
                    disableSave();
                    disableNew();
                } else
                {
                    enableSave();
                    enableNew();
                }
            }
        });
    }

    public void handleDoneRequest()
    {
        {
            try
            {
                dataSet = new DataSet(chart, this);
                dataSet.processString(allText);
                String dataLabelText = String.format(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DATA_LABEL_INSTANCE_LABEL.toString()), dataSet.getInstanceCount(), dataSet.getLabelCount());
                if (!loadFileDirectory.equals(""))
                {
                    dataLabelText += String.format(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DATA_LABEL_LOAD_DIR.toString()) + loadFileDirectory);
                }
                dataLabelText += String.format(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DATA_LABEL_LABELS.toString()));
                for (int i = 0; i < dataSet.getLabelCount(); i++)
                {
                    dataLabelText += String.format(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.LABEL_PREFIX.toString()) + dataSet.getLabelList()[i]);
                }
                
                boolean disable = false;
                if (dataSet.getInstanceCount() == 0)
                    disable = true;
                for (Button algorithmTypeButton : algorithmTypeButtons)
                    {
                        algorithmTypeButton.setDisable(disable);
                    }
                
                for (Class algorithmType : algorithmTypes)
                {
                    try{
                    if (algorithmType.getMethod(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.GET_MIN_LABELS_METHOD.toString()))!=null)
                    {
                        for (Button algorithmTypeButton : algorithmTypeButtons)
                        {
                            if (algorithmTypeButton.getText().equals(algorithmType.getSimpleName()))
                            {
                                Method method = algorithmType.getMethod(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.GET_MIN_LABELS_METHOD.toString()));
                                int minLabels = (int) method.invoke(algorithmType);
                                if (dataSet.getLabelCount() != minLabels)
                                    algorithmTypeButton.setDisable(true);
                                else
                                    algorithmTypeButton.setDisable(false);
                            }
                        }
                        
                    }
                    }
                    catch(Exception e)
                    {
                        // do nothing since there is no method in this class
                    }
                }
                
                for (Class algorithmName : algorithmNames)
                {
                    try{
                    if (algorithmName.getMethod(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.GET_MIN_INSTANCES_METHOD.toString()))!=null)
                    {
                            for (RadioButton algorithmRadio : algorithmNameRadios)
                            {
                                if (algorithmRadio.getText().equals(algorithmName.getSimpleName()))
                                {
                                    Method method = algorithmName.getMethod(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.GET_MIN_INSTANCES_METHOD.toString()));
                                    int minInstances = (int) method.invoke(algorithmName);
                                    if (dataSet.getLocations().size() < minInstances)
                                    {
                                        algorithmRadio.setDisable(true);
                                    } else
                                    {
                                        algorithmRadio.setDisable(false);
                                    }
                                }
                            }

                        }
                    }
                    catch(Exception e)
                    {
                        
                    }
                }
                
//                
                dataLabel.setText(dataLabelText);
                textArea.setEditable(false);
                editButton.setDisable(false);
                doneButton.setDisable(true);
                buttonPane.setVisible(true);
                for (AlgorithmConfiguration configuration : configuration)
                {
                    if (configuration.getAlgorithmName().equals(selectedRadioButton.getText())
                            && selectedRadioButton != null
                            && configuration.getAlgorithmType() != null
                            && !algorithmRunning)
                    {
                        runButton.setDisable(false);
                    }
                }
            } catch (Exception e)
            {
                // do nothing since there is no method in this class
            }
        }
    }

    private void handleEditRequest()
    {
        buttonPane.setVisible(false);
        textArea.setEditable(true);
        runButton.setDisable(true);
        doneButton.setDisable(false);
        editButton.setDisable(true);
    }

    private void handleBackRequest()
    {
        algorithmType = null;
        buttonPane.getChildren().remove(0, buttonPane.getChildren().size());
        buttonPane.getChildren().add(algBox);
        ((AppUI) applicationTemplate.getUIComponent()).displayRun(false);
    }

    private void handleRunRequest()
    {
        boolean hasConfiguration = false;
        
        for (AlgorithmConfiguration configuration : configuration)
        {
            if (configuration.getAlgorithmName().equals(selectedRadioButton.getText()))
            {
                runConfiguration = configuration;
                hasConfiguration = true;
            }
        }
        
        if (hasConfiguration)
        {
            if (!algorithmExists)
            {
                for (Class algorithmType : algorithmTypes)
                {
                    if (runConfiguration.getAlgorithmType().equals(algorithmType.getSimpleName()))
                    {
                        if (runConfiguration.getAlgorithmName().equals(selectedRadioButton.getText()))
                    {
                        ConfigurationWindow window = null;
                        for (ConfigurationWindow w : windows)
                        {
                            if (w.getAlgorithmName().equals(selectedRadioButton.getText()))
                                window = w;
                        }
                        algorithmExists = true;
                        int maxIteration = window.getMaxIt();
                        int updateInterval = window.getUpdateInt();
                        int numberOfClusters = window.getClusterNumber();
                        boolean toContinue = window.getContRun();
                        clear();
                        
                        for (Class algorithmName : algorithmNames)
                        {
                            if (algorithmName.getSimpleName().equals(selectedRadioButton.getText()))
                            {
                                try
                                {
                                    algorithm = (Algorithm) algorithmName.getConstructors()[0].newInstance(dataSet, maxIteration, updateInterval, numberOfClusters, toContinue, this);
                                } catch (Exception ex)
                                {
                                }
                            }
                        }
                        }
                        algRunner = new Thread(algorithm);
                        disableScreenShot();
                        setAlgorithmRunning(true);
                        enableRunButton(false);
                        editButton.setDisable(true);
                        algRunner.start();
                    }
                }
            }
            else if (algorithmExists)
            {
                if (!algorithmRunning)
                {
                    this.enableScreenShot();
                    algorithm.resume();
                }
            }
        }
    }

    public void setConfiguration(AlgorithmConfiguration config)
    {
        boolean existingConfig = false;

        for (AlgorithmConfiguration configuration : configuration)
        {
            if (configuration.getAlgorithmName() == config.getAlgorithmName())
            {
                configuration = config;
                existingConfig = true;
            }
        }

        if (existingConfig == false)
        {
            configuration.add(config);
        }

        tryEnableRunButton();
    }

    public void tryEnableRunButton()
    {
        for (AlgorithmConfiguration configuration : configuration)
        {
            if (selectedRadioButton != null && algorithmTypeName != null 
                    && configuration.getAlgorithmName().equals(selectedRadioButton.getText())
                    && !algorithmRunning)
            {
                runButton.setDisable(false);
            }
        }

    }

    public enum saveOption
    {
        SAVED, NOT_SAVED;
    }

    public void setSave(saveOption option)
    {
        if (option == saveOption.SAVED)
        {
            savedText = textArea.getText();
            saved = true;
        } else
        {
            saved = false;
        }
    }

    public boolean hasSaveFile()
    {
        return saveFileExists;
    }

    public boolean hasTextData()
    {
        if (textArea.getText().equals(""))
        {
            return false;
        } else
        {
            return true;
        }
    }

    public String getTextArea()
    {
        return textArea.getText();
    }

    public void enableNew()
    {
        newButton.setDisable(false);
    }

    public void enableSave()
    {
        saveButton.setDisable(false);
    }

    public void disableNew()
    {
        newButton.setDisable(true);
    }

    public void disableSave()
    {
        saveButton.setDisable(true);
    }

    public void disableScreenShot()
    {
        scrnshotButton.setDisable(true);
    }

    public void enableScreenShot()
    {
        scrnshotButton.setDisable(false);
    }

    public void setText(String text)
    {
        textArea.setText(text);
    }
    
    public void setAllText(String text)
    {
        allText = text;
    }

    public String getAllText()
    {
        if (allText.split("\n").length > 10)
        {
            return allText;
        } else
        {
            return textArea.getText();
        }
    }

    public void setLoadFileDirectory(File file)
    {
        if (file != null)
        {
            loadFileDirectory = file.getName();
        } else
        {
            loadFileDirectory = "";
        }
    }

    public void enableEditing(boolean value)
    {
        editButton.setDisable(!value);
    }

    public void displayLeftPane(boolean value)
    {
        leftBox.setVisible(value);
    }

    public void displayAlgorithmTypes(boolean value)
    {
        buttonPane.setVisible(value);
    }

    public void setDataLabelText(String text)
    {
        dataLabel.setText(text);
    }

    public void enableRun(boolean value)
    {
        runButton.setDisable(!value);
    }

    public void newData()
    {
        clear();
        textArea.setEditable(true);
        buttonPane.getChildren().remove(0, buttonPane.getChildren().size());
        buttonPane.getChildren().add(algBox);
        algorithmType = null;
        configuration = new HashSet<>();
//        runConfiguration = null;
//
//        if (configClass.isSelected())
//        {
//            selectedAlgorithmCategory = AlgorithmCategory.RandomClass;
//        } else if (configCluster.isSelected())
//        {
//            selectedAlgorithmCategory = AlgorithmCategory.RandomCluster;
//        } else
//        {
//            selectedAlgorithmCategory = null;
//        }
    }

    public void displayRun(boolean value)
    {
        runButton.setVisible(value);
    }

    public void enableDoneButton(boolean value)
    {
        doneButton.setDisable(!value);
    }

    public void setDataSet(DataSet dataSet)
    {
        this.dataSet = dataSet;
    }

    public void setAlgorithmRunning(boolean value)
    {
        algorithmRunning = value;
    }

    public void enableRunButton(boolean value)
    {
        runButton.setDisable(!value);
    }
    
    public void setAlgorithmExists(boolean value)
    {
        algorithmExists = value;
    }
    
    public boolean getAlgorithmRunning()
    {
        return algorithmRunning;
    }
    
    public void stopAlgorithm()
    {
        if (algorithmRunning)
            algorithm.stopThread();
        
        chart.getXAxis().setAutoRanging(true);
        chart.getYAxis().setAutoRanging(true);
        setIterationLabel(0);
        setAlgorithmRunning(false);
        setAlgorithmExists(false);
        disableScreenShot();
        enableRunButton(false);
    }
    
    public void setLoadedData(boolean value)
    {
        loadedData = value;
    }
    
    public boolean getLoadedData()
    {
        return loadedData;
    }
    
    public void setIterationLabel(int iteration)
    {
        iterationLabel.setText(String.format("Iteration: %d", iteration));
    }
    
    public void setOutOfBoundsLabel(boolean value)
    {
        if (value)
            outOfBoundsLabel.setText(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.OUT_OF_BOUNDS_LABEL.toString()));
        if (!value)
            outOfBoundsLabel.setText("");
    }
    
    public void tryEnableEditButton()
    {
        if (!loadedData)
            editButton.setDisable(false);
    }
}
