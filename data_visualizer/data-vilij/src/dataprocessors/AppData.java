package dataprocessors;

import ui.AppUI;
import vilij.components.DataComponent;
import vilij.templates.ApplicationTemplate;
import java.nio.file.Path;
import javafx.scene.chart.LineChart;

/**
 * This is the concrete application-specific implementation of the data
 * component defined by the Vilij framework.
 *
 * @author Ritwik Banerjee
 * @see DataComponent
 */
public class AppData implements DataComponent
{

    private DataSet dataset;
    private final ApplicationTemplate applicationTemplate;
    private LineChart chart;

    public AppData(ApplicationTemplate applicationTemplate)
    {
        this.applicationTemplate = applicationTemplate;
        chart = ((AppUI) this.applicationTemplate.getUIComponent()).getChart();
        dataset = new DataSet();
        dataset.setChart(chart);
    }

    @Override
    public void loadData(Path dataFilePath)
    {
        // TODO: Not A PART OF HW 1
    }

    public void loadData(String dataString)
    {
        try{
            dataset.processString(dataString);
            clear();
            displayData();
        }
        catch (Exception e)
        {
            // do nothing since there was an error.
        }
                
    }

    @Override
    public void saveData(Path dataFilePath)
    {
        // TODO: NOT A PART OF HW 1
    }

    @Override
    public void clear()
    {
        ((AppUI) this.applicationTemplate.getUIComponent()).clear();
    }

    public void displayData()
    {
        dataset.updateChart();
    }
}
