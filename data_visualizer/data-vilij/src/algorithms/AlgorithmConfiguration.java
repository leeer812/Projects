/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import ui.ConfigurationWindow;

/**
 *
 * @author goreg
 */
public class AlgorithmConfiguration
{

    private AlgorithmCategory category;
    private AlgorithmType type;
    private int maxIterations;
    private int updateInterval;
    private int clusterNumber;
    private int defaultValue;
    private String algorithmName;
    private String algorithmType;
    private boolean continuousRun;
    

    public AlgorithmConfiguration()
    {
        maxIterations = 25;
        updateInterval = 1;
        clusterNumber = 4;
        defaultValue = 1;
        continuousRun = true;

        category = null;
        type = null;
        algorithmName = null;
        algorithmType = null;
    }

    public AlgorithmConfiguration(ConfigurationWindow inputWindow)
    {
        if (inputWindow == null)
        {
            maxIterations = 25;
            updateInterval = 1;
            clusterNumber = 4;
            continuousRun = true;

            category = null;
            type = null;
            algorithmName = null;
            algorithmType = null;
        } else
        {
            category = inputWindow.getAlgCat();
            type = inputWindow.getAlgType();
            maxIterations = inputWindow.getMaxIt();
            updateInterval = inputWindow.getUpdateInt();
            clusterNumber = inputWindow.getClusterNumber();
            continuousRun = inputWindow.getContRun();
            algorithmName = inputWindow.getAlgorithmName();
            algorithmType = inputWindow.getAlgorithmType();
        }
    }

    public AlgorithmCategory getCategory()
    {
        return category;
    }

    public void setCategory(AlgorithmCategory category)
    {
        this.category = category;
    }

    public AlgorithmType getType()
    {
        return type;
    }

    public void setType(AlgorithmType type)
    {
        this.type = type;
    }

    public int getMaxIterations()
    {
        if (maxIterations < 1)
        {
            maxIterations = defaultValue;
        }
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations)
    {
        try
        {
            this.maxIterations = maxIterations;
        } catch (Exception e)
        {

        }
    }

    public int getUpdateInterval()
    {
        if (updateInterval < 1)
        {
            updateInterval = defaultValue;
        }
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval)
    {
        try
        {
            this.updateInterval = updateInterval;
        } catch (Exception e)
        {

        }
    }

    public int getClusterNumber()
    {
        if (clusterNumber < 2)
        {
            return 2;
        } else if (clusterNumber > 4)
        {
            return 4;
        }
        return clusterNumber;
    }

    public void setClusterNumber(int clusterNumber)
    {
        try
        {
            this.clusterNumber = clusterNumber;
        } catch (Exception e)
        {

        }

    }

    public boolean getContinuousRun()
    {
        return continuousRun;
    }

    public void setContinuousRun(boolean continuousRun)
    {
        this.continuousRun = continuousRun;
    }

    public void setAlgorithmName(String algorithmName)
    {
        this.algorithmName = algorithmName;
    }

    public String getAlgorithmName()
    {
        return algorithmName;
    }

    public void setAlgorithmType(String algorithmType)
    {
        this.algorithmType = algorithmType;
    }

    public String getAlgorithmType()
    {
        return algorithmType;
    }
    
    public void setDefaultValue(int value)
    {
        defaultValue = value;
    }
}
