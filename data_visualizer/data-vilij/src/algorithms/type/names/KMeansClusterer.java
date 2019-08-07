package algorithms.type.names;

import algorithms.type.Clusterer;
import dataprocessors.DataSet;
import javafx.geometry.Point2D;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import ui.AppUI;

/**
 * @author Ritwik Banerjee
 */
public class KMeansClusterer extends Clusterer
{

    private DataSet dataset;
    private List<Point2D> centroids;
    StringBuilder tsdString;
    private final int maxIterations;
    private final int updateInterval;
    private static int minInstances = 2;
    private final AtomicBoolean tocontinue;
    private boolean continuous;
    private final AppUI appUI;
    boolean stop = false;

    public KMeansClusterer(DataSet dataset, int maxIterations, int updateInterval, int numberOfClusters, boolean toContinue, AppUI appUI)
    {
        super(numberOfClusters);
        minInstances = numberOfClusters;
        this.dataset = dataset;
        this.maxIterations = maxIterations;
        this.updateInterval = updateInterval;
        this.appUI = appUI;
        this.tocontinue = new AtomicBoolean(false);
        continuous = toContinue;
        tsdString = new StringBuilder();
    }

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

    @Override
    public void run()
    {
        appUI.disableScreenShot();
        appUI.setAlgorithmRunning(true);
        appUI.enableRunButton(false);
        initializeCentroids();

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

    private void initializeCentroids()
    {
        Set<String> chosen = new HashSet<>();
        List<String> instanceNames = new ArrayList<>(dataset.getLabels().keySet());
        Random r = new Random();
        while (chosen.size() < numberOfClusters)
        {
            int i = r.nextInt(instanceNames.size());
            while (i != instanceNames.size() && chosen.contains(instanceNames.get(i)))
            {
                 i = (++i % instanceNames.size());
            }
            chosen.add(instanceNames.get(i));
        }
        centroids = chosen.stream().map(name -> dataset.getLocations().get(name)).collect(Collectors.toList());
        tocontinue.set(true);
    }

    private void assignLabels()
    {
        dataset.getLocations().forEach((instanceName, location) ->
        {
            double minDistance = Double.MAX_VALUE;
            int minDistanceIndex = -1;
            for (int i = 0; i < centroids.size(); i++)
            {
                double distance = computeDistance(centroids.get(i), location);
                if (distance < minDistance)
                {
                    minDistance = distance;
                    minDistanceIndex = i;
                }
            }
            dataset.getLabels().put(instanceName, Integer.toString(minDistanceIndex));
        });
    }

    private void recomputeCentroids()
    {
        tocontinue.set(false);
        IntStream.range(0, numberOfClusters).forEach(i ->
        {
            AtomicInteger clusterSize = new AtomicInteger();
            Point2D sum = dataset.getLabels()
                    .entrySet()
                    .stream()
                    .filter(entry -> i == Integer.parseInt(entry.getValue()))
                    .map(entry -> dataset.getLocations().get(entry.getKey()))
                    .reduce(new Point2D(0, 0), (p, q) ->
                    {
                        clusterSize.incrementAndGet();
                        return new Point2D(p.getX() + q.getX(), p.getY() + q.getY());
                    });
            Point2D newCentroid = new Point2D(sum.getX() / clusterSize.get(), sum.getY() / clusterSize.get());

            if (!newCentroid.equals(centroids.get(i)))
            {
                centroids.set(i, newCentroid);
                tocontinue.set(true);
            }

        });
    }

    private static double computeDistance(Point2D p, Point2D q)
    {
        return Math.sqrt(Math.pow(p.getX() - q.getX(), 2) + Math.pow(p.getY() - q.getY(), 2));
    }

    public synchronized void updateChart(int iteration)
    {
        dataset.updateGUICluster(iteration);
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

    private void runIteration(int iteration)
    {
        assignLabels();
        recomputeCentroids();
    }

    private synchronized void nonContinuousRun()
    {
        int iteration = 0;
        while (iteration++ < maxIterations && tocontinue.get())
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

            runIteration(iteration);

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

    private synchronized void continuousRun()
    {
        int iteration = 0;
        while (iteration++ < maxIterations & tocontinue.get())
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

            runIteration(iteration);

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

    public void stopThread()
    {
        stop = true;
    }
    
    public static int getMinInstances()
    {
        return minInstances;
    }
}
