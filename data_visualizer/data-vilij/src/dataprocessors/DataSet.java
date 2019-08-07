package dataprocessors;

import javafx.geometry.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import settings.AppPropertyTypes;
import static settings.AppPropertyTypes.LOAD_ERROR_TITLE;
import ui.AppUI;
import vilij.components.ErrorDialog;
import vilij.propertymanager.PropertyManager;
import vilij.templates.ApplicationTemplate;

/**
 * This class specifies how an algorithm will expect the dataset to be. It is
 * provided as a rudimentary structure only, and does not include many of the
 * sanity checks and other requirements of the use cases. As such, you can
 * completely write your own class to represent a set of data instances as long
 * as the algorithm can read from and write into two {@link java.util.Map}
 * objects representing the name-to-label map and the name-to-location (i.e.,
 * the x,y values) map. These two are the {@link DataSet#labels} and
 * {@link DataSet#locations} maps in this class.
 *
 * @author Ritwik Banerjee
 */
public class DataSet
{
    public static class InvalidCoordinateException extends Exception
    {

        private static final String COORDINATE_ERROR_MSG = "Coordinates must be in the form of #, #";

        public InvalidCoordinateException(String name)
        {
            super(String.format("Invalid coordinates '%s'." + COORDINATE_ERROR_MSG, name));
        }
    }
    
    public static class InvalidDataNameException extends Exception
    {

        private static final String NAME_ERROR_MSG = "All data instance names must start with the @ character. ";

        public InvalidDataNameException(String name)
        {
            super(String.format("Invalid name '%s'. " + NAME_ERROR_MSG, name));
        }
    }

    private static String nameFormatCheck(String name) throws InvalidDataNameException
    {
        if (!name.startsWith("@"))
        {
            throw new InvalidDataNameException(name);
        }
        return name;
    }

    private static Point2D locationOf(String locationString)
    {
        String[] coordinateStrings = locationString.trim().split(",");
        return new Point2D(Double.parseDouble(coordinateStrings[0]), Double.parseDouble(coordinateStrings[1]));
    }
    
    PropertyManager manager = PropertyManager.getManager();
    private Map<String, String> labels;
    private Map<String, Point2D> locations;
    private XYChart<Number, Number> chart;
    private AppUI appUI;
    private int instances = 0;

    /**
     * Creates an empty dataset.
     */
    public DataSet(XYChart<Number, Number> chart, AppUI appUI)
    {
        this.chart = chart;
        this.appUI = appUI;
        labels = new HashMap<>();
        locations = new HashMap<>();
    }
    
    public DataSet()
    {
        labels = new HashMap<>();
        locations = new HashMap<>();
    }

    public Map<String, String> getLabels()
    {
        return labels;
    }

    public Map<String, Point2D> getLocations()
    {
        return locations;
    }

    public void updateLabel(String instanceName, String newlabel)
    {
        if (labels.get(instanceName) == null)
        {
            throw new NoSuchElementException();
        }
        labels.put(instanceName, newlabel);
    }

    private void addInstance(String tsdLine) throws InvalidDataNameException
    {
        String[] arr = tsdLine.split("\t");
        labels.put(nameFormatCheck(arr[0]), arr[1]);
        locations.put(arr[0], locationOf(arr[2]));
    }

    public static DataSet fromTSDFile(Path tsdFilePath, XYChart<Number, Number> chart, AppUI appUI) throws IOException
    {
        DataSet dataset = new DataSet();
        StringBuilder tsdString = new StringBuilder();
        Files.lines(tsdFilePath).forEach(line ->
        {
            try
            {
                dataset.addInstance(line);
            } catch (InvalidDataNameException e)
            {
            }
        });
        dataset.chart = chart;
        dataset.appUI = appUI;
        return dataset;
    }

    public static DataSet fromTSDString(String tsdString, XYChart<Number, Number> chart, AppUI appUI) throws IOException
    {
        DataSet dataset = new DataSet();
        
        if (!tsdString.isEmpty())
        {
            try
            {
                dataset.processString(tsdString);
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        dataset.chart = chart;
        dataset.appUI = appUI;
        return dataset;
    }

    public void updateGUICluster(int iteration)
    {
        updateChart();
        Platform.runLater( () -> appUI.setIterationLabel(iteration));
    }
    
    public synchronized void updateChart()
    {
        ApplicationTemplate applicationTemplate = new ApplicationTemplate();

        Platform.runLater(() -> chart.getData().clear());

        HashSet<String> labels = new HashSet<>(getLabels().values());
        for (String label : labels)
        {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(label);
            Platform.runLater(() ->
            {
                chart.getData().add(series);
            });
            getLabels().entrySet().stream().filter(entry -> entry.getValue().equals(label)).forEach(entry ->
            {
                Point2D point = getLocations().get(entry.getKey());
                XYChart.Data<Number, Number> data = new XYChart.Data<>(point.getX(), point.getY());
                Platform.runLater(() -> series.getData().add(data));

            });

            Platform.runLater(() ->
            {
                // making each of the node's lines transparent
                for (XYChart.Series<Number, Number> nums : chart.getData())
                {
                    nums.getNode().setStyle(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.CSS_STROKE_WIDTH_0.toString()));
                    nums.getNode().setStyle(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.CSS_STROKE_TRANSPARENT.toString()));
                }

                // adding tool tips to each of the nodes in each of the series in the chart
                for (XYChart.Series<Number, Number> serie : chart.getData())
                {
                    for (XYChart.Data<Number, Number> data : serie.getData())
                    {
                        for (HashMap.Entry<String, Point2D> entry : getLocations().entrySet())
                        {
                            double xValue = entry.getValue().getX();
                            double yValue = entry.getValue().getY();
                            if (xValue == data.getXValue().doubleValue() && yValue == data.getYValue().doubleValue())
                            {
                                Tooltip.install(data.getNode(), new Tooltip(entry.getKey()));
                            }
                        }
                        data.getNode().setCursor(Cursor.WAIT);
                    }
                }
                
            });
        }
        
        double minX = getMinX(); double maxX = getMaxX();
        double minY = getMinY(); double maxY = getMaxY();
        double deltaX;
        double deltaY;
        
        if (maxX != minX && maxY != minY)
        {
        deltaX = (maxX - minX) / 5;
        deltaY = (maxY - minY) / 5;
        }
        else
        {
            deltaX = Math.log(minX);
            deltaY = Math.log(minY);
        }
        Platform.runLater(() -> { 
        ((NumberAxis)chart.getXAxis()).setLowerBound(minX - deltaX);
        ((NumberAxis)chart.getXAxis()).setUpperBound(maxX + deltaX);
        ((NumberAxis)chart.getYAxis()).setLowerBound(minY - deltaY);
        ((NumberAxis)chart.getYAxis()).setUpperBound(maxY + deltaY);
         
        ((NumberAxis)chart.getXAxis()).setAutoRanging(false);
        ((NumberAxis)chart.getYAxis()).setAutoRanging(false);
        });
        
    }

    public void updateGUIClass(List<Integer> output, int iteration)
    {
        setLine(output.get(0), output.get(1), output.get(2), chart, iteration);
        appUI.setIterationLabel(iteration);
        
    }

    public synchronized void setLine(int a, int b, int constant, XYChart<Number, Number> chart, int iteration)
    {
        double min = getMinX();
        double max = getMaxX();
        
        double lowerBound = ((NumberAxis)chart.getYAxis()).getLowerBound();
        double upperBound = ((NumberAxis)chart.getYAxis()).getUpperBound();

        ApplicationTemplate applicationTemplate = new ApplicationTemplate();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.RANDOM_CLASS_RADIO.toString()));

        if (a == 0)
            a = 1;
        if (b == 0)
            b = 1;

        XYChart.Data<Number, Number> minNode = new XYChart.Data<>(min, ((((-1) * (min) * (a)) - constant) / b));
        series.getData().add(minNode);
        XYChart.Data<Number, Number> maxNode = new XYChart.Data<>(max, ((((-1) * (max) * (a)) - constant) / b));
        series.getData().add(maxNode);
        
        if (!(minNode.getYValue().doubleValue() >= lowerBound && maxNode.getYValue().doubleValue() <= upperBound))
            appUI.setOutOfBoundsLabel(true);
        else
            appUI.setOutOfBoundsLabel(false);
        
        // 0,0 10, 10
        // -2, 0
        // 0, 0
        
        Platform.runLater( () -> {
            if (chart.getData().size() == 2)
        {
            chart.getData().add(series);
            chart.getData().get(chart.getData().size()-1).getNode().setStyle(manager.getPropertyValue(AppPropertyTypes.CSS_STROKE_WIDTH_2.toString()));
        } else if (chart.getData().size() == 3)
        {
            chart.getData().get(2).setData(series.getData());
        }
        });
        
    }
    
    private double getMinX()
    {
        HashSet<Point2D> points = new HashSet<>(getLocations().values());
        
        double min = Integer.MAX_VALUE;
        for (Point2D point : points)
        {
            if (point.getX() < min)
                min = point.getX();
        }
        
        return min;
    }
    
    private double getMaxX()
    {
        HashSet<Point2D> points = new HashSet<>(getLocations().values());
        
        double max = Integer.MIN_VALUE;
        for (Point2D point : points)
        {
            if (point.getX() > max)
                max = point.getX();
        }
        
        return max;
    }
    
    private double getMinY()
    {
        HashSet<Point2D> points = new HashSet<>(getLocations().values());
        
        double min = Integer.MAX_VALUE;
        for (Point2D point : points)
        {
            if (point.getY() < min)
                min = point.getY();
        }
        
        return min;
    }
    
    private double getMaxY()
    {
        HashSet<Point2D> points = new HashSet<>(getLocations().values());
        
        double max = Integer.MIN_VALUE;
        for (Point2D point : points)
        {
            if (point.getY() > max)
                max = point.getY();
        }
        
        return max;
    }
    
    private String checkedname(String name) throws TSDProcessor.InvalidDataNameException
    {
        if (!name.startsWith("@"))
        {
            throw new TSDProcessor.InvalidDataNameException(name);
        }
        return name;
    }
    
    public void processString(String tsdString) throws Exception // throws Exception
    {
        if (tsdString.equals(""))
            return;
        
        labels.clear();
        locations.clear();
        
        AtomicInteger lineCount = new AtomicInteger(0); // initializes line count as 0
        StringBuilder errorMessage = new StringBuilder();
        ApplicationTemplate applicationTemplate = new ApplicationTemplate();
        Stream.of(tsdString.split("\n"))
                .map(line -> Arrays.asList(line.split("\t")))
                .forEach(list ->
                {
                    try
                    {
                        lineCount.getAndIncrement(); // increase line count
                        String name = checkedname(list.get(0));
                        
                        if (list.size() > 3)
                        {
                            String errorString = "";
                            for (int i=3; i<list.size(); i++)
                            {
                                errorString += "\t" + list.get(i);
                            }
                            throw new InvalidCoordinateException(errorString);
                        }
                        
                        if (list.get(2).split(",").length != 2)
                            throw new InvalidCoordinateException(list.get(2).toString());

                        if (labels.containsKey(name))
                        {
                            throw new TSDProcessor.DuplicateNameException(name);
                        }

                        String label = list.get(1);
                        
                        if (label.equals(""))
                            throw new TSDProcessor.BlankLabelException();
                        
                        String[] pair = list.get(2).split(",");
                        Point2D point = new Point2D(Double.parseDouble(pair[0]), Double.parseDouble(pair[1]));

                        labels.put(name, label);
                        locations.put(name, point);
                    } 
                    
                    catch (TSDProcessor.BlankLabelException e)
                    {
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.BLANK_LABEL_MSG.toString())
                        + applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString()) + lineCount.get() + "\n");
                    }
                    catch (TSDProcessor.DuplicateNameException e)
                    {
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DUP_NAME_MSG.name())
                                + applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DUP_NAME.toString())
                                + applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    } catch (TSDProcessor.InvalidDataNameException e)
                    {
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(
                                applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ERROR.toString())
                                + applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    } catch (NumberFormatException e)
                    {
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.NUMBER_FORMAT_MSG.name())
                                + applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ERROR.toString())
                                + applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    } 
                    catch (InvalidCoordinateException e)
                    {
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    }
                    catch (Exception e)
                    {
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.INVALID_FORMAT_MSG.name())
                                + applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ERROR.toString())
                                + applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    }
                });
        if (errorMessage.length() > 0)
        {
            labels.clear();
            locations.clear();
            ErrorDialog dialog = ErrorDialog.getDialog();
            dialog.show(applicationTemplate.manager.getPropertyValue(LOAD_ERROR_TITLE.toString()), errorMessage.toString());
            throw new Exception();
        }
        instances = tsdString.split("\n").length;
    }
    
    public int getInstanceCount()
    {
        return instances;
    }
    
    public int getLabelCount()
    {
        return getLabelList().length;
    }
    
    public String[] getLabelList()
    {
        HashSet<String> labelList = new HashSet<>();
        HashSet<String> labelArray = new HashSet<>();
        labelList.addAll(labels.values());
        
        for (String s : labelList)
        {
            if (!s.equals("") && !s.equals(manager.getPropertyValue(AppPropertyTypes.NULL.toString())))
                labelArray.add(s);
        }
        String[] array = labelArray.toArray(new String[labelArray.size()]);
        return array;
    }
    
    public void setChart(XYChart<Number, Number> chart)
    {
        this.chart = chart;
    }
}
