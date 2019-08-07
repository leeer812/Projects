package dataprocessors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.chart.XYChart;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javafx.scene.Cursor;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Tooltip;
import settings.AppPropertyTypes;
import vilij.propertymanager.PropertyManager;

/**
 * The data files used by this data visualization applications follow a
 * tab-separated format, where each data point is named, labeled, and has a
 * specific location in the 2-dimensional X-Y plane. This class handles the
 * parsing and processing of such data. It also handles exporting the data to a
 * 2-D plot.
 * <p>
 * A sample file in this format has been provided in the application's
 * <code>resources/data</code> folder.
 *
 * @author Ritwik Banerjee
 * @see XYChart
 */
public final class TSDProcessor
{
    
    public static class BlankLabelException extends Exception
    {
        public BlankLabelException()
        {
            super(String.format("Blank label name."));
        }
    }

    public static class InvalidDataNameException extends Exception
    {

        private static final String NAME_ERROR_MSG = "All data instance names must start with the @ character.";

        public InvalidDataNameException(String name)
        {
            super(String.format("Invalid name '%s'. " + NAME_ERROR_MSG, name));
        }
    }

    public static class DuplicateNameException extends Exception
    {

        private String name;

        public DuplicateNameException(String n)
        {
            super(String.format("Duplicate name '%s'. ", n));
            name = n;
        }

        public String getName()
        {
            return name;
        }
    }
    
    //private ApplicationTemplate applicationTemplate = new ApplicationTemplate();
    PropertyManager manager = PropertyManager.getManager();
    private HashMap<String, String> dataLabels;
    private HashMap<String, Point2D> dataPoints;
    private int instances;
    private int min;
    private int max;
    private int minY;
    private int maxY;

    public TSDProcessor()
    {
        dataLabels = new HashMap<>();
        dataPoints = new HashMap<>();
    }
    
    private class ExceptionHolder
    {
        private Exception exception;
        
        public ExceptionHolder()
        {
            exception = null;
        }
        
        public void setException(Exception e)
        {
            exception = e;
        }
        
        public Exception getException()
        {
            return exception;
        }
    }
    

    /**
     * Processes the data and populated two {@link Map} objects with the data.
     *
     * @param tsdString the input data provided as a single {@link String}
     * @throws Exception if the input string does not follow the
     * <code>.tsd</code> data format
     */
    public void processString(String tsdString) throws Exception // throws Exception
    {
        ExceptionHolder holder = new ExceptionHolder();
        
        if (tsdString.equals(""))
            return;
        
        clear();
        AtomicInteger lineCount = new AtomicInteger(0); // initializes line count as 0
        StringBuilder errorMessage = new StringBuilder();
        // ApplicationTemplate applicationTemplate = new ApplicationTemplate();
        Stream.of(tsdString.split("\n"))
                .map(line -> Arrays.asList(line.split("\t")))
                .forEach(list ->
                {
                    try
                    {
                        lineCount.getAndIncrement(); // increase line count
                        String name = checkedname(list.get(0));
                        
                        if (list.get(2).split(",").length != 2)
                            throw new DataSet.InvalidCoordinateException(list.get(2).toString());

                        if (dataLabels.containsKey(name))
                        {
                            throw new DuplicateNameException(name);
                        }

                        String label = list.get(1);
                        
                        if (label.equals(""))
                            throw new BlankLabelException();
                        
                        String[] pair = list.get(2).split(",");
                        Point2D point = new Point2D(Double.parseDouble(pair[0]), Double.parseDouble(pair[1]));

                        dataLabels.put(name, label);
                        dataPoints.put(name, point);
                    } 
                    
                    catch (BlankLabelException e)
                    {
                        holder.setException(e);
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(manager.getPropertyValue(AppPropertyTypes.BLANK_LABEL_MSG.toString())
                        + manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString()) + lineCount.get() + "\n");
                    }
                    catch (DuplicateNameException e)
                    {
                        holder.setException(e);
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(manager.getPropertyValue(AppPropertyTypes.DUP_NAME_MSG.name())
                                + manager.getPropertyValue(AppPropertyTypes.DUP_NAME.toString())
                                + manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    } catch (InvalidDataNameException e)
                    {
                        holder.setException(e);
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(
                                manager.getPropertyValue(AppPropertyTypes.ERROR.toString())
                                + manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    } catch (NumberFormatException e)
                    {
                        holder.setException(e);
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(manager.getPropertyValue(AppPropertyTypes.NUMBER_FORMAT_MSG.name())
                                + manager.getPropertyValue(AppPropertyTypes.ERROR.toString())
                                + manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    } 
                    catch (DataSet.InvalidCoordinateException e)
                    {
                        holder.setException(e);
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    }
                    catch (Exception e)
                    {
                        holder.setException(e);
                        errorMessage.append(e.getClass().getSimpleName()).append(": ").append(manager.getPropertyValue(AppPropertyTypes.INVALID_FORMAT_MSG.name())
                                + manager.getPropertyValue(AppPropertyTypes.ERROR.toString())
                                + manager.getPropertyValue(AppPropertyTypes.ON_LINE.toString())
                                + lineCount.get() + "\n");
                    }
                });
        if (errorMessage.length() > 0)
        {
            clear();
            throw holder.getException();
        }
        instances = tsdString.split("\n").length;
        
    }

    /**
     * Exports the data to the specified 2-D chart.
     *
     * @param chart the specified chart
     */
    public void toChartData(XYChart<Number, Number> chart)
    {
        HashSet<String> labels = new HashSet<>(dataLabels.values());
        for (String label : labels)
        {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(label);
            chart.getData().add(series);
            dataLabels.entrySet().stream().filter(entry -> entry.getValue().equals(label)).forEach(entry ->
            {
                Point2D point = dataPoints.get(entry.getKey());
                XYChart.Data<Number, Number> data = new XYChart.Data<>(point.getX(), point.getY());
                series.getData().add(data);
            });

            for (XYChart.Series<Number, Number> nums : chart.getData())
            {
//                nums.getNode().setStyle(manager.getPropertyValue(AppPropertyTypes.CSS_STROKE_WIDTH_0.toString()));
//                nums.getNode().setStyle(manager.getPropertyValue(AppPropertyTypes.CSS_STROKE_TRANSPARENT.toString()));
            }
        }

        double yValues = 0;
        double totalPoints = 0;
        double avg = 0;
        
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;

        for (Object series : chart.getData())
        {
            if (!((XYChart.Series<Number, Number>) series).getName().equals(manager.getPropertyValue(AppPropertyTypes.RANDOM_CLASS_RADIO.toString())))
            {
                for (XYChart.Data<Number, Number> nums : ((XYChart.Series<Number, Number>) series).getData())
                {
                    if (min > nums.getXValue().intValue())
                    {
                        min = nums.getXValue().intValue();
                    }
                    if (max < nums.getXValue().intValue())
                    {
                        max = nums.getXValue().intValue();
                    }
                    if (minY > nums.getYValue().intValue())
                    {
                        minY = nums.getYValue().intValue();
                    }
                    if (maxY < nums.getYValue().intValue())
                    {
                        maxY = nums.getYValue().intValue();
                    }
                    yValues += nums.getYValue().intValue();
                    totalPoints++;
                }
            }
        }
        
        ((NumberAxis)chart.getXAxis()).setLowerBound(min - min/100.0);
        ((NumberAxis)chart.getXAxis()).setUpperBound(max + max/100.0);
        ((NumberAxis)chart.getYAxis()).setLowerBound(minY - minY/100.0);
        ((NumberAxis)chart.getYAxis()).setUpperBound(maxY + maxY/100.0);
         
        ((NumberAxis)chart.getXAxis()).setAutoRanging(false);
        ((NumberAxis)chart.getYAxis()).setAutoRanging(false);
        
        avg = yValues / totalPoints;

//        XYChart.Series<Number, Number> series = new XYChart.Series<>();
//        series.setName(manager.getPropertyValue(AppPropertyTypes.SERIES_AVG_NAME.toString()));
//
//        for (int i = min; i <= max; i++)
//        {
//            XYChart.Data<Number, Number> node = new XYChart.Data<>(i, avg);
//            series.getData().add(node);
//        }

        /*for (XYChart.Data<Number, Number> node : series.getData())
            node.getNode().setStyle("-fx-stroke: #25cc66e;");*/
//        chart.getData().add(series);
//        for (XYChart.Data<Number, Number> node : series.getData())
//        {
//            node.getNode().setStyle(manager.getPropertyValue(AppPropertyTypes.CSS_BACKGROUND_TRANSPARENT.toString()));
//        }

        for (XYChart.Series<Number, Number> serie : chart.getData())
        {
            for (XYChart.Data<Number, Number> data : serie.getData())
            {
                for (HashMap.Entry<String, Point2D> entry : dataPoints.entrySet())
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
    }

    void clear()
    {
        dataPoints.clear();
        dataLabels.clear();
    }

    private String checkedname(String name) throws InvalidDataNameException
    {
        if (!name.startsWith("@"))
        {
            throw new InvalidDataNameException(name);
        }
        return name;
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
        HashSet<String> labels = new HashSet<>();
        HashSet<String> labelArray = new HashSet<>();
        labels.addAll(dataLabels.values());
        
        for (String s : labels)
        {
            if (!s.equals("") && !s.equals(manager.getPropertyValue(AppPropertyTypes.NULL.toString())))
                labelArray.add(s);
        }
        String[] array = labelArray.toArray(new String[labelArray.size()]);
        return array;
    }
    
    public void setLine(int a, int b, int constant, XYChart<Number, Number> chart)
    {  
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        // series.setName(manager.getPropertyValue(AppPropertyTypes.RANDOM_CLASS_RADIO.toString()));
        
        if (a == 0)
            a = 1;
        if (b == 0)
            b = 1;

            XYChart.Data<Number, Number> minNode = new XYChart.Data<>(min, ((((-1)*(min)*(a)) - constant) / b));
            series.getData().add(minNode);
            XYChart.Data<Number, Number> maxNode = new XYChart.Data<>(max, ((((-1)*(max)*(a)) - constant) / b));
            series.getData().add(maxNode);
            
            
            
            // ax + by + c = 0
            // y = -ax - c / b
            
        if (chart.getData().size() == 2)
        {
            chart.getData().add(series);
        }
        else if (chart.getData().size() == 3)
        {
            chart.getData().get(2).setData(series.getData());
        }
    }
    
    public HashMap<String, Point2D> getPoints()
    {
        return dataPoints;
    }
    
    public HashMap<String, String> getLabels()
    {
        return dataLabels;
    }
}
