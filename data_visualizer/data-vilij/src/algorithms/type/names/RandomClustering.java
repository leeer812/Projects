/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.type.names;

import algorithms.type.Clusterer;
import dataprocessors.DataSet;
import ui.AppUI;

/**
 *
 * @author goreg
 */
public class RandomClustering extends Clusterer
{

    private DataSet dataset;
    
    private int maxIterations;
    private int updateInterval;
    private static int minInstances = 1;
    private AppUI appUI;
    private boolean continuous;
    private boolean stop = false;
    
    public RandomClustering(DataSet dataset, int maxIterations, int updateInterval, int numberOfClusters, boolean cont, AppUI appUI)
    {
        super(numberOfClusters);
        this.maxIterations = maxIterations;
        this.updateInterval = updateInterval;
        continuous = cont;
        this.dataset = dataset;
        this.appUI = appUI;
    }

    @Override
    public void run()
    {
        appUI.disableScreenShot();
        appUI.setAlgorithmRunning(true);
        appUI.enableRunButton(false);

        if (continuous)
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
        } else
        {
            appUI.enableRunButton(false);
        }
    }
    
    private void assignLabels() {
        dataset.getLocations().forEach((instanceName, location) -> {
                dataset.getLabels().put(instanceName, Integer.toString((int)(Math.random() * numberOfClusters+1)));
        });
    }
    
    private synchronized void continuousRun()
    {
        int iteration = 0;
        while (iteration++ < maxIterations)
        {
            try
            {
                if (stop)
                {
                    this.wait();
                }
            } catch (InterruptedException interruptedException)
            {
            }

            assignLabels();

            if (iteration % updateInterval == 0)
            {
                updateChart(iteration);
            }

            try
            {
                Thread.sleep(500);
            } catch (InterruptedException ex)
            {
            }
        }
    }
    
    private void nonContinuousRun()
    {
        int iteration = 0;
        while (iteration++ < maxIterations)
        {
            try
            {
                if (stop)
                {
                    this.wait();
                }
            } catch (InterruptedException interruptedException)
            {
            }

            assignLabels();

            if (iteration % updateInterval == 0)
            {
                updateChart(iteration);
                appUI.enableScreenShot();
                appUI.setAlgorithmRunning(false);
                appUI.enableRunButton(true);
                if (iteration != maxIterations)
                {
                    pause();
                }
            }
        }
    }
    
    public synchronized void updateChart(int iteration)
    {
        dataset.updateGUICluster(iteration);
    }
    
    public int getMaxIterations()
    {
        return maxIterations;
    }

    public int getUpdateInterval()
    {
        return updateInterval;
    }

    public boolean tocontinue()
    {
        return continuous;
    }
    
    public synchronized void pause()
    {
        try
        {
            this.wait();
        } catch (InterruptedException ex)
        {
            // do nothing
        }
    }
    
    public synchronized void resume()
    {
        this.notifyAll();
    }
    
    public void stopThread()
    {
        this.stop = true;
    }
    
    public int getMinInstances()
    {
        return minInstances;
    }
}
