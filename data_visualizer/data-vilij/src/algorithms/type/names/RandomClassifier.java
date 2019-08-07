package algorithms.type.names;

import algorithms.type.Classifier;
import dataprocessors.DataSet;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import ui.AppUI;

/**
 *
 */
public class RandomClassifier extends Classifier
{

    private static final Random RAND = new Random();
    // this mock classifier doesn't actually use the data, but a real classifier will
    private DataSet dataset;
    private AppUI appUI;
    private final int maxIterations;
    private final int updateInterval;
    private boolean stop = false;
    // currently, this value does not change after instantiation
    private final AtomicBoolean tocontinue;

    @Override
    public int getMaxIterations()
    {
        return maxIterations;
    }

    @Override
    public int getUpdateInterval()
    {
        return updateInterval;
    }

    @Override
    public boolean tocontinue()
    {
        return tocontinue.get();
    }

    public RandomClassifier(AppUI appUI)
    {
        maxIterations = -1;
        updateInterval = -1;
        this.appUI = appUI;
        tocontinue = new AtomicBoolean(false);
    }

    public RandomClassifier(DataSet dataset, int maxIterations, int updateInterval, int numberOfClusters, boolean toContinue, AppUI appUI)
    {
        this.dataset = dataset;
        dataset.updateChart();
        this.maxIterations = maxIterations;
        this.updateInterval = updateInterval;
        this.tocontinue = new AtomicBoolean(toContinue);
        this.appUI = appUI;
    }

    @Override
    public void run()
    {
        appUI.disableScreenShot();
        appUI.setAlgorithmRunning(true);
        appUI.enableRunButton(false);
        if (tocontinue.get())
        {
            continuousRun();
        } else
        {
            nonContinuousRun();
        }
        appUI.setAlgorithmRunning(false);
        appUI.setAlgorithmExists(false);
        if (!stop)
        {
            appUI.enableScreenShot();
            appUI.tryEnableRunButton();
            appUI.tryEnableEditButton();
        }
        else
            appUI.enableRunButton(false);
    }

    public synchronized void continuousRun()
    {
        for (int i = 1; i <= maxIterations; i++)
        {
            if (stop)
            {
                try
                {
                    this.wait();
                } catch (InterruptedException ex)
                {
                }
            }
                int xCoefficient =  new Long(-1 * Math.round((2 * RAND.nextDouble() - 1) * 10)).intValue();
            int yCoefficient = 10;
            int constant     = RAND.nextInt(11);

                // this is the real output of the classifier
                output = Arrays.asList(xCoefficient, yCoefficient, constant);

                // everything below is just for internal viewing of how the output is changing
                // in the final project, such changes will be dynamically visible in the UI
                if (i % updateInterval == 0)
                {
                    final int iteration = i;

                    if (i > maxIterations * .6 && RAND.nextDouble() < 0.05)
                    {
                        if (Platform.isFxApplicationThread())
                        {
                            updateChart(i);
                        }
                        Platform.runLater(() ->
                        {
                            updateChart(iteration);
                        });
                        break;
                    }

                    if (Platform.isFxApplicationThread())
                    {
                        updateChart(i);
                    }
                    Platform.runLater(() ->
                    {
                        updateChart(iteration);
                    });

                    try
                    {
                        Thread.sleep(500);
                    } catch (InterruptedException ex)
                    {
                    }
                }
            
        }
    }

    public synchronized void nonContinuousRun()
    {
        for (int i = 1; i <= maxIterations; i++)
        {
            if (stop)
            {
                try
                {
                    this.wait();
                } catch (InterruptedException ex)
                {
                }
            }
                int xCoefficient =  new Long(-1 * Math.round((2 * RAND.nextDouble() - 1) * 10)).intValue();
            int yCoefficient = 10;
            int constant     = RAND.nextInt(11);

                output = Arrays.asList(xCoefficient, yCoefficient, constant);

                if (i % updateInterval == 0)
                {
                    appUI.enableScreenShot();
                    appUI.setAlgorithmRunning(false);
                    appUI.enableRunButton(true);
                    final int iteration = i;

                    if (i > maxIterations * .6 && RAND.nextDouble() < 0.05)
                    {
                        if (Platform.isFxApplicationThread())
                        {
                            updateChart(i);
                        }
                        Platform.runLater(() ->
                        {
                            updateChart(iteration);
                        });
                        break;
                    }

                    if (Platform.isFxApplicationThread())
                    {
                        updateChart(i);
                    }
                    Platform.runLater(() ->
                    {
                        updateChart(iteration);
                    });

                    try
                    {
                        if (i != maxIterations)
                        {
                            this.wait();
                        }
                    } catch (InterruptedException e)
                    {
                    }
                }
            
        }
    }
    
    public synchronized void resume()
    {
        this.notifyAll();
    }

    public void stopThread()
    {
        stop = true;
    }
    
    public void updateChart(int iteration)
    {
        dataset.updateGUIClass(output, iteration);
    }
}
