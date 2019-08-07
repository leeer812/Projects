/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import dataprocessors.DataSet;
import ui.AppUI;

/**
 *
 * @author goreg
 */
public interface Algorithm extends Runnable {

    int getMaxIterations();

    int getUpdateInterval();

    boolean tocontinue();
    
    String getAlgorithmName();
    
    public void resume();
    
    public void stopThread();

}
