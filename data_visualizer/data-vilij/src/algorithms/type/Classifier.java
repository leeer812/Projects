/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.type;

import algorithms.Algorithm;
import java.util.List;

/**
 *
 * @author goreg
 */
public abstract class Classifier implements Algorithm {

    /**
     * See Appendix C of the SRS. Defining the output as a
     * list instead of a triple allows for future extension
     * into polynomial curves instead of just straight lines.
     * See 3.4.4 of the SRS.
     */
    protected List<Integer> output;
    private static int minLabels = 2;

    public List<Integer> getOutput() { return output; }
    
    public String getAlgorithmName()
    {
        return this.getClass().getSimpleName();
    }
    
    public static int getMinLabels()
    {
        return minLabels;
    }
}