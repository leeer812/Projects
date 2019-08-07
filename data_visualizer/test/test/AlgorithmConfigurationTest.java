/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import algorithms.AlgorithmConfiguration;
import org.junit.Test;

/**
 *
 * @author goreg
 */
public class AlgorithmConfigurationTest
{
    public AlgorithmConfigurationTest()
    {
    }
    
    /**
     * Tests an average case of configuring an algorithm configuration's
     * values.
     */
    @Test
    public void testConfiguration()
    {
        AlgorithmConfiguration configuration = new AlgorithmConfiguration();
        
        // Instantiates the values that will be used to configure the algorithm
        int clusterNumber = 3;
        int maxIterations = 25;
        int updateInterval = 2;
        boolean continuous = true;
        /** These are average values because the minimum for max iterations and
         * update interval are 1. So 25 and 2 are values above the minimum
         * boundary values. The minimum and maximum cluster number is 2 and 4,
         * respectively, so 3 is an average value.
        */
        
        // Configures the algorithm configuration according to the input values
        configuration.setClusterNumber(clusterNumber);
        configuration.setMaxIterations(maxIterations);
        configuration.setUpdateInterval(updateInterval);
        configuration.setContinuousRun(continuous);
        
        // Asserts that the output configuration has the expected values from
        // the input settings. All settings should be equal to the input since
        // there were no out of bound values.
        assert(configuration.getClusterNumber()==clusterNumber);
        assert(configuration.getContinuousRun()==continuous);
        assert(configuration.getMaxIterations()==maxIterations);
        assert(configuration.getUpdateInterval()==updateInterval);
    }
    
    /**
     * Tests a boundary case for the maximum values in configuring an algorithm
    */
    @Test
    public void testConfigurationMaxBoundaryCases()
    {
        AlgorithmConfiguration configuration = new AlgorithmConfiguration();
        
        // 4 is the maximum number of clusters an algorithm can have
        int clusterNumber = 4;
        // Integer.MAX_VALUE is the maximum value an algorithm can have for its
        // max iterations and update interval
        int maxIterations = Integer.MAX_VALUE;
        int updateInterval = Integer.MAX_VALUE;
        boolean continuous = true;
        
        // Configures the algorithm based on the input values
        configuration.setClusterNumber(clusterNumber);
        configuration.setMaxIterations(maxIterations);
        configuration.setUpdateInterval(updateInterval);
        configuration.setContinuousRun(continuous);
        
        // Asserts that all the configuration values are equal to the input
        // setting values since all the inputs were within bounds.
        assert(configuration.getClusterNumber()==clusterNumber);
        assert(configuration.getContinuousRun()==continuous);
        assert(configuration.getMaxIterations()==maxIterations);
        assert(configuration.getUpdateInterval()==updateInterval);
    }
    
    /**
     * Tests a boundary case for the minimum values in configuring an algorithm
    */
    @Test 
    public void testConfigurationMinBoundaryCases()
    {
        AlgorithmConfiguration configuration = new AlgorithmConfiguration();
        
        // 2 is the minimum value for an algorithm's cluster number
        int clusterNumber = 2;
        // 1 is the minimum value for an algorithm's max iterations and update
        // interval. Any value below 1 is not accepted.
        int maxIterations = 1;
        int updateInterval = 1;
        boolean continuous = false;
        
        // Configures the algorithm according to the input values.
        configuration.setClusterNumber(clusterNumber);
        configuration.setMaxIterations(maxIterations);
        configuration.setUpdateInterval(updateInterval);
        configuration.setContinuousRun(continuous);
        
        // Asserts that all the configuration values are equal to the input
        // setting values since all the inputs were within bounds.
        assert(configuration.getClusterNumber()==clusterNumber);
        assert(configuration.getContinuousRun()==continuous);
        assert(configuration.getMaxIterations()==maxIterations);
        assert(configuration.getUpdateInterval()==updateInterval);
    }
    
    /**
     * Tests a case where the input values for the configuration settings are
     * out of bounds.
     */
    @Test 
    public void testConfigurationOutOfBoundaryCases()
    {
        AlgorithmConfiguration configuration = new AlgorithmConfiguration();
        
        // The minimum and maximum number of clusters allowed for the algorithm
        // are declared as 2 and 4.
        int minimumClusterNumber = 2;
        int maximumClusterNumber = 4;
        
        // The default value for the configuration to gracefully degrade invalid
        // input settings to is declared as 5.
        int defaultValue = 5;
        
        // We have a case for where the cluster number is 1, below the minimum
        // value allowed for cluster number, and a case for where cluster
        // number is 5, above the maximum allowed value.
        int clusterNumberLow = 1;
        int clusterNumberHigh = 5;
        // Max iterations is required to be above or equal to 1 so -1 is not
        // a valid value.
        int maxIterations = -1;
        // Update interval is required to be above or equal to 1 so 0 is not a
        // valid value.
        int updateInterval = 0;
        boolean continuous = false;
        
        // Attempts to set the algorithm configuration according to the input
        // values
        configuration.setDefaultValue(defaultValue);
        configuration.setClusterNumber(clusterNumberLow);
        configuration.setMaxIterations(maxIterations);
        configuration.setUpdateInterval(updateInterval);
        configuration.setContinuousRun(continuous);
        
        // Since the input value for cluster number was below 2, then we need
        // to assert that the cluster number has been gracefully degraded to
        // the minimum allowed cluster number of 2.
        assert(configuration.getClusterNumber()==minimumClusterNumber);
        
        // Since the input value for cluster number was above 4, then we need
        // to assert that the cluster number has been gracefully degraded to
        // the maximum allowed cluster number of 4.
        configuration.setClusterNumber(clusterNumberHigh);
        assert(configuration.getClusterNumber()==maximumClusterNumber);
        
        /** Since the input value for update interval and max iterations were
         * below 2, then we need to assert that these values have been
         * gracefully degraded into allowable values for the algorithm.
         * This is done by asserting that the configuration values are not
         * equal to the invalid input values and that they are equal to the
         * provided default value.
        */ 
        assert(configuration.getMaxIterations()!=maxIterations);
        assert(configuration.getUpdateInterval()!=updateInterval); 
        assert(configuration.getMaxIterations()==defaultValue);
        assert(configuration.getUpdateInterval()==defaultValue); 
        assert(configuration.getContinuousRun()==continuous);
    }
    
}
