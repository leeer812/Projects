package actions;

import dataprocessors.DataSet;
import dataprocessors.TSDProcessor;
import java.io.File;
import static java.io.File.separator;
import java.io.FileWriter;
import vilij.components.ActionComponent;
import vilij.templates.ApplicationTemplate;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import settings.AppPropertyTypes;
import ui.AppUI;
import ui.AppUI.saveOption;
import vilij.components.ConfirmationDialog;
import vilij.components.ConfirmationDialog.Option;

/**
 * This is the concrete implementation of the action handlers required by the
 * application.
 *
 * @author Ritwik Banerjee
 */
public final class AppActions implements ActionComponent
{

    /**
     * The application to which this class of actions belongs.
     */
    private ApplicationTemplate applicationTemplate;
    private DataSet dataset;
    private LineChart chart;
    private File saveFile;
    /**
     * Path to the data file currently active.
     */
    Path dataFilePath;

    public static class LoadCancelException extends Exception
    {

        public LoadCancelException()
        {
            super(String.format("Canceled Load"));
        }
    }
    
    public AppActions()
    {
        dataset = new DataSet();
        saveFile = null;
        // dataFilePath = Paths.get(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DATA_RESOURCE_PATH.name()));
    }

    public AppActions(ApplicationTemplate applicationTemplate)
    {
        this.applicationTemplate = applicationTemplate;
        dataset = new DataSet();
        saveFile = null;
        dataFilePath = Paths.get(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DATA_RESOURCE_PATH.name()));
    }

    public void setupChart()
    {
        chart = ((AppUI) applicationTemplate.getUIComponent()).getChart();
    }

    @Override
    public void handleNewRequest()
    {
        if (((AppUI) applicationTemplate.getUIComponent()).hasTextData()) // if there is new text in the text area
        {
            try
            {
                if (((AppUI) applicationTemplate.getUIComponent()).getLoadedData())
                {
                    configureErasedData();
                } else if (!promptToSave())
                {
                    configureErasedData();
                } else
                {
                    dataset.processString(((AppUI) applicationTemplate.getUIComponent()).getAllText());
                    saveData();
                    removeChartData();
                    configureErasedData();
                }
            } catch (Exception e)
            {
                // do nothing if user cancels
            }
        } else
        {
            removeChartData();
            configureErasedData();
        }
    }

    @Override
    public void handleSaveRequest()
    {
        try
        {
            dataset.processString(((AppUI) applicationTemplate.getUIComponent()).getAllText());
            saveData();
        } catch (Exception e)
        {
            // do nothing if user cancels
        }
    }

    @Override
    public void handleLoadRequest()
    {
        if (((AppUI) applicationTemplate.getUIComponent()).hasTextData()) // if there is new text in the text area
        {
            try
            {
                if (!((AppUI) applicationTemplate.getUIComponent()).getLoadedData() && promptToSave())
                {
                    dataset.processString(((AppUI) applicationTemplate.getUIComponent()).getAllText());
                    saveData();
                    ((AppUI) applicationTemplate.getUIComponent()).setSave(saveOption.NOT_SAVED);
                    ((AppUI) applicationTemplate.getUIComponent()).setSavedText();
                    loadFile();
                    configureUI();
                } else
                {
                    ((AppUI) applicationTemplate.getUIComponent()).setSave(saveOption.NOT_SAVED);
                    ((AppUI) applicationTemplate.getUIComponent()).setSavedText();
                    loadFile();
                    configureUI();
                }
            } catch (Exception e)
            {
                // do nothing since user canceled load/save
            }
        } else
        {
            try
            {
                loadFile();
                configureUI();
            } catch (Exception e)
            {
                // do nothing since user canceled load/save
            }
        }
        ((AppUI) applicationTemplate.getUIComponent()).enableRunButton(false);
    }

    public void readFile(File file)
    {
        String textArea = "";
        String allText = "";
        int lineCount = 1;
        try
        {
            try
            {
                Scanner input = new Scanner(file);
                while (input.hasNextLine())
                {
                    if (lineCount <= 10)
                    {
                        if (lineCount == 1)
                        {
                            textArea += input.nextLine();
                        } else
                        {
                            textArea += "\n" + input.nextLine();
                        }
                        allText = textArea;
                        lineCount++;

                    } else
                    {
                        allText += "\n" + input.nextLine();
                        lineCount++;
                    }

                }
                input.close();
            } catch (Exception e)
            {

            }

            dataset.processString(allText);
            ((AppUI) applicationTemplate.getUIComponent()).setAllText("");
            ((AppUI) applicationTemplate.getUIComponent()).setText(textArea);

            if (lineCount > 10)
            {

            }
            ((AppUI) applicationTemplate.getUIComponent()).setAllText(allText);
            ((AppUI) applicationTemplate.getUIComponent()).setLoadFileDirectory(file);
        } catch (Exception e)
        {
            // do nothing since there was an error in the data
        }
    }
    
    public String readFileTest(File file) throws Exception
    {
        String textArea = "";
        String allText = "";
        int lineCount = 1;
        try
        {
            try
            {
                Scanner input = new Scanner(file);
                while (input.hasNextLine())
                {
                    if (lineCount <= 10)
                    {
                        if (lineCount == 1)
                        {
                            textArea += input.nextLine();
                        } else
                        {
                            textArea += "\n" + input.nextLine();
                        }
                        allText = textArea;
                        lineCount++;

                    } else
                    {
                        allText += "\n" + input.nextLine();
                        lineCount++;
                    }

                }
                input.close();
            } catch (Exception e)
            {

            }

            TSDProcessor processor = new TSDProcessor();
            processor.processString(allText);

            if (lineCount > 10)
            {

            }
            
        } catch (Exception e)
        {
            throw e;
        }
        return allText;
    }

    @Override
    public void handleExitRequest()
    {
        if (((AppUI) applicationTemplate.getUIComponent()).getAlgorithmRunning())
        {
            try
            {
                if (promptToExitAlgorithmRunning()) // asks user if they want to exit without saving
                {
                    if (!((AppUI) applicationTemplate.getUIComponent()).getLoadedData())
                    {
                        if (!((AppUI) applicationTemplate.getUIComponent()).getSaveStatus()) // if the text area has unsaved text
                        {
                            try
                            {
                                if (promptToExitSave()) // asks user if they want to exit without saving
                                {
                                    System.exit(0);
                                    // remove all instances of TSDPROCESSOR from project and replace it with dataset
                                    // so that i can test tsdprocessor by removing all UI components from tsdprocessor
                                    // since UI components cause initialization to crash the test files
                                }
                            } catch (IOException e)
                            {
                                return;
                            }
                        } else
                        {
                            System.exit(0);
                        }
                    } else
                    {
                        System.exit(0);
                    }
                } else
                {
                    return;
                }
            } catch (IOException e)
            {
                return;
            }
        }
        else if (!((AppUI) applicationTemplate.getUIComponent()).getLoadedData())
        {
            if (!((AppUI) applicationTemplate.getUIComponent()).getSaveStatus()) // if the text area has unsaved text
            {
                try
                {
                    if (promptToExitSave()) // asks user if they want to exit without saving
                    {
                        System.exit(0);
                    }
                } catch (IOException e)
                {
                    return;
                }
            } else
            {
                System.exit(0);
            }
        } else
        {
            System.exit(0);
        }
    }

    @Override
    public void handlePrintRequest()
    {
        // TODO: NOT A PART OF HW 1
    }

    public void handleScreenshotRequest()
    {
        setupChart();
        FileChooser fileChooser = new FileChooser();
        setInitialDirectory(fileChooser);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.PICTURE_FILE_EXT_DESC.toString()),
                applicationTemplate.manager.getPropertyValue(AppPropertyTypes.PICTURE_FILE_EXT.toString()));
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.SAVE_DIRECTORY_TITLE.toString()));
        File file = fileChooser.showSaveDialog(((AppUI) applicationTemplate.getUIComponent()).getPrimaryWindow());
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        try
        {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null),
                    applicationTemplate.manager.getPropertyValue(AppPropertyTypes.PNG.toString()), file);
        } catch (Exception e)
        {
            // do nothing
        }
    }

    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. The user will be presented
     * with three options:
     * <ol>
     * <li><code>yes</code>, indicating that the user wants to save the work and
     * continue with the action,</li>
     * <li><code>no</code>, indicating that the user wants to continue with the
     * action without saving the work, and</li>
     * <li><code>cancel</code>, to indicate that the user does not want to
     * continue with the action, but also does not want to save the work at this
     * point.</li>
     * </ol>
     *
     * @return <code>false</code> if the user presses the <i>cancel</i>, and
     * <code>true</code> otherwise.
     */
    private boolean promptToSave() throws IOException
    {
        if (((AppUI) applicationTemplate.getUIComponent()).getSaveStatus())
        {
            return false;
        }

        ConfirmationDialog confDial = ConfirmationDialog.getDialog();
        confDial.show(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.SAVE_UNSAVED_WORK_TITLE.toString()),
                applicationTemplate.manager.getPropertyValue(AppPropertyTypes.SAVE_UNSAVED_WORK.toString()));
        Option choice = confDial.getSelectedOption();
        switch (choice)
        {
            case NO:
                return false;
            case CANCEL:
                throw new IOException();
            case YES:
                return true;
        }
        return false;
    }

    private boolean promptToExitSave() throws IOException
    {
        ConfirmationDialog confDial = ConfirmationDialog.getDialog();
        confDial.show(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.EXIT_UNSAVED_TITLE.toString()),
                applicationTemplate.manager.getPropertyValue(AppPropertyTypes.EXIT_UNSAVED_WARNING.toString()));
        Option choice = confDial.getSelectedOption();
        switch (choice)
        {
            case NO:
                return false;
            case CANCEL:
                throw new IOException();
            case YES:
                return true;
        }
        return false;
    }

    private boolean promptToExitAlgorithmRunning() throws IOException
    {
        ConfirmationDialog confDial = ConfirmationDialog.getDialog();
        confDial.show(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.EXIT_WHILE_RUNNING_TITLE.toString()),
                applicationTemplate.manager.getPropertyValue(AppPropertyTypes.EXIT_WHILE_RUNNING_WARNING.toString()));
        Option choice = confDial.getSelectedOption();
        switch (choice)
        {
            case NO:
                return false;
            case CANCEL:
                throw new IOException();
            case YES:
                return true;
        }
        return false;
    }

    public void saveFile(String textArea, File selectedDir)
    {
        try
        {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(selectedDir);
            fileWriter.write(textArea);
            fileWriter.close();
            ((AppUI) applicationTemplate.getUIComponent()).setSave(saveOption.SAVED);
        } catch (IOException e)
        {
            Alert alert = new Alert(ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    
    public void saveFileTest(String textArea, File selectedDir) throws Exception
    {
        try
        {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(selectedDir);
            fileWriter.write(textArea);
            fileWriter.close();
        } catch (IOException e)
        {
            throw e;
        }
    }

    private void removeChartData()
    {
        setupChart();
        chart.getData().remove(0, chart.getData().size());
        ((AppUI) applicationTemplate.getUIComponent()).newText();
        ((AppUI) applicationTemplate.getUIComponent()).disableNew();
        ((AppUI) applicationTemplate.getUIComponent()).disableSave();
    }

    private void saveData() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        setInitialDirectory(fileChooser);
        if (saveFile == null)
        {

            File selectedDir = fileChooser.showSaveDialog(((AppUI) applicationTemplate.getUIComponent()).getPrimaryWindow());
            try
            {
                if (selectedDir != null)
                {
                    saveFile(((AppUI) applicationTemplate.getUIComponent()).getAllText(), selectedDir);
                    saveFile = selectedDir;
                } else
                {
                    throw new IOException();
                }
            } catch (IOException e)
            {
                throw new IOException();
            } catch (Exception e)
            {
                Alert alert = new Alert(ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }

        } else
        {
            try
            {
                saveFile(((AppUI) applicationTemplate.getUIComponent()).getAllText(), saveFile);
            } catch (Exception e)
            {
                Alert alert = new Alert(ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }

        if (((AppUI) applicationTemplate.getUIComponent()).getSaveStatus())
        {
            ((AppUI) applicationTemplate.getUIComponent()).setSave(saveOption.SAVED);
            ((AppUI) applicationTemplate.getUIComponent()).disableSave();
        }
    }

    private void configureFileChooser(FileChooser fileChooser)
    {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT_DESC.toString()),
                applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT.toString()));
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.SAVE_DIRECTORY_TITLE.toString()));
    }

    private void setInitialDirectory(FileChooser fileChooser)
    {
        try
        {
            String initialDirectory = String.join(separator,
                    applicationTemplate.manager.
                            getPropertyValue(AppPropertyTypes.DATA_RESOURCE_DIR.toString()),
                    dataFilePath.toString());
            File initial = new File(initialDirectory);
            fileChooser.setInitialDirectory(initial);
        } catch (Exception e)
        {

        }
    }

    private void loadFile() throws LoadCancelException
    {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        setInitialDirectory(fileChooser);
        fileChooser.setTitle(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.LOAD_TITLE.toString()));

        File file = fileChooser.showOpenDialog(((AppUI) applicationTemplate.getUIComponent()).getPrimaryWindow());
        if (file != null)
        {
            readFile(file);
        } else
        {
            throw new LoadCancelException();
        }
    }

    private void configureErasedData()
    {
        ((AppUI) applicationTemplate.getUIComponent()).stopAlgorithm();
        removeChartData();
        ((AppUI) applicationTemplate.getUIComponent()).newData();
        ((AppUI) applicationTemplate.getUIComponent()).disableScreenShot();
        ((AppUI) applicationTemplate.getUIComponent()).setSave(saveOption.NOT_SAVED);
        ((AppUI) applicationTemplate.getUIComponent()).setSavedText();
        ((AppUI) applicationTemplate.getUIComponent()).setLoadFileDirectory(null);
        ((AppUI) applicationTemplate.getUIComponent()).enableEditing(false);
        ((AppUI) applicationTemplate.getUIComponent()).enableDoneButton(true);
        ((AppUI) applicationTemplate.getUIComponent()).displayLeftPane(true);
        ((AppUI) applicationTemplate.getUIComponent()).displayAlgorithmTypes(false);
        ((AppUI) applicationTemplate.getUIComponent()).setDataLabelText("");
        ((AppUI) applicationTemplate.getUIComponent()).enableRun(false);
        ((AppUI) applicationTemplate.getUIComponent()).displayRun(false);
        ((AppUI) applicationTemplate.getUIComponent()).setLoadedData(false);
        ((AppUI) applicationTemplate.getUIComponent()).setOutOfBoundsLabel(false);

        saveFile = null;

    }

    private void configureUI()
    {
        ((AppUI) applicationTemplate.getUIComponent()).stopAlgorithm();
        ((AppUI) applicationTemplate.getUIComponent()).newData();
        ((AppUI) applicationTemplate.getUIComponent()).displayLeftPane(true);
        ((AppUI) applicationTemplate.getUIComponent()).displayAlgorithmTypes(true);
        ((AppUI) applicationTemplate.getUIComponent()).disableSave();
        ((AppUI) applicationTemplate.getUIComponent()).enableRun(false);
        ((AppUI) applicationTemplate.getUIComponent()).displayRun(false);
        ((AppUI) applicationTemplate.getUIComponent()).handleDoneRequest();
        ((AppUI) applicationTemplate.getUIComponent()).enableEditing(false);
        ((AppUI) applicationTemplate.getUIComponent()).enableRunButton(false);
        ((AppUI) applicationTemplate.getUIComponent()).setLoadedData(true);
        ((AppUI) applicationTemplate.getUIComponent()).setOutOfBoundsLabel(false);
    }
}
