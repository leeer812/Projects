package algorithms.type;

import algorithms.Algorithm;

public abstract class Clusterer implements Algorithm
{

    protected final int numberOfClusters;

    public int getNumberOfClusters()
    {
        return numberOfClusters;
    }

    public Clusterer(int k)
    {
        if (k < 2)
        {
            k = 2;
        } else if (k > 4)
        {
            k = 4;
        }
        numberOfClusters = k;
    }
    
    public String getAlgorithmName()
    {
        return this.getClass().getSimpleName();
    }
}
