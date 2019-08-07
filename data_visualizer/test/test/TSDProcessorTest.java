/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataprocessors.DataSet;
import dataprocessors.TSDProcessor;
import javafx.geometry.Point2D;
import org.junit.Test;

/**
 *
 * @author goreg
 */
public class TSDProcessorTest
{
    
    public TSDProcessorTest()
    {
    }
    
    

    /**
     * Tests an average case for creating an instance with a line of data.
     */
    @Test
    public void testAverageInstance() throws Exception
    {
        // An average instance name with no errors since it begins with the
        // @ character.
        String instanceName = "@testInstance";
        // An average label name with no errors since it is not blank and is
        // not null.
        String label = "testLabel";
        // Average x and y values for an instance. 
        double xValue = 1;
        double yValue = 0;
        
        // Constructs a string using the instance name, label, and the x and y
        // values for the processor to create an instance with.
        String testCase = String.format("%s\t%s\t%f,%f", instanceName, label, xValue, yValue);
        TSDProcessor instance = new TSDProcessor();
        // The processor processes the string and creates an instance within its
        // HashSets.
        instance.processString(testCase);
        /** We assert that the instance exists within the HashSets of the processor
        * and that the instance within the hashsets have equal instance names,
        * label names, and x and y values as the original input string.
        */ 
        assert(instance.getLabels().values().toArray()[0].equals(label));
        assert(instance.getLabels().keySet().toArray()[0].equals(instanceName));
        assert(((Point2D)instance.getPoints().values().toArray()[0]).getX() == xValue);
        assert(((Point2D)instance.getPoints().values().toArray()[0]).getY() == yValue);
    }
    
    /**
     * Tests a case using the minimum boundary values for creating an instance.
     * @throws Exception 
     */
    @Test
    public void testMinBoundaryInstance() throws Exception
    {
        // Average instance and label names.
        String instanceName = "@testInstance";
        String label = "testLabel";
        
        // Integer.MIN_VALUE are the minimum possible values for creating an
        // instance due to the constraints of the Java Virtual Machine.
        double xValue = Integer.MIN_VALUE;
        double yValue = Integer.MIN_VALUE;
        
        // We construct a string using the names and coordinate values and
        // process them with the processor.
        String testCase = String.format("%s\t%s\t%f,%f", instanceName, label, xValue, yValue);
        TSDProcessor instance = new TSDProcessor();
        instance.processString(testCase);
        
        // Assert that the instance name, label name, and x and y values of the
        // instance created in the processor's hashset are equal to the values
        // of the original string.
        assert(instance.getLabels().values().toArray()[0].equals(label));
        assert(instance.getLabels().keySet().toArray()[0].equals(instanceName));
        assert(((Point2D)instance.getPoints().values().toArray()[0]).getX() == xValue);
        assert(((Point2D)instance.getPoints().values().toArray()[0]).getY() == yValue);
    }
    
    /**
     * Tests the maximum boundary value case for creating an instance
    */
    @Test
    public void testMaxBoundaryInstance() throws Exception
    {
        // Average instance and label names
        String instanceName = "@testInstance";
        String label = "testLabel";
        
        // Integer.MAX_VALUE are the minimum possible values for creating an
        // instance due to the constraints of the Java Virtual Machine.
        double xValue = Integer.MAX_VALUE;
        double yValue = Integer.MAX_VALUE;
        
        // We construct a string using the names and coordinate values and
        // process them with the processor.
        String testCase = String.format("%s\t%s\t%f,%f", instanceName, label, xValue, yValue);
        TSDProcessor instance = new TSDProcessor();
        instance.processString(testCase);
        
        // Assert that the instance name, label name, and x and y values of the
        // instance created in the processor's hashset are equal to the values
        // of the original string.
        assert(instance.getLabels().values().toArray()[0].equals(label));
        assert(instance.getLabels().keySet().toArray()[0].equals(instanceName));
        assert(((Point2D)instance.getPoints().values().toArray()[0]).getX() == xValue);
        assert(((Point2D)instance.getPoints().values().toArray()[0]).getY() == yValue);
    }
    
    /**
     * Tests an error case for creating an instance due to having a duplicate
     * instance name in the input string. We expect that the 
     * <code>DuplicateNameException</code> is thrown.
     * @throws Exception 
     */
    @Test (expected = TSDProcessor.DuplicateNameException.class)
    public void duplicateInstance() throws Exception
    {
        // Creates an input string with two of instances with the same name,
        // @testInstance. 
        String duplicateCase = "@testInstance\ttestLabel\t1,1\n" + "@testInstance\ttestLabel\t1,2";
        // Attempt to process the input string. We expect that an exception is
        // thrown due to the presence of a duplicate instance name, which is not
        // allowed.
        TSDProcessor instance = new TSDProcessor();
        instance.processString(duplicateCase);
    }
    
    /**
     * Tests an error case where the input string has an instance name that does
     * not begin with the <code>@</code> character. We expect the 
     * <code>InvalidDataNameException</code> to be thrown.
     * @throws Exception 
     */
    @Test (expected = TSDProcessor.InvalidDataNameException.class)
    public void startCharError() throws Exception
    {
        // Construct an input string where the instance name does not begin with
        // the @ character. This is not allowed and should throw an exception
        // when the processor attempts to process it.
        String testCase = "testInstance\ttestLabel\t1,1";
        TSDProcessor instance = new TSDProcessor();
        instance.processString(testCase);
    }
    
    /**
     * Tests an error case where the input string has an incomplete set of
     * coordinates and therefore we expect an exception to be thrown.
     * @throws Exception 
     */
    @Test (expected = Exception.class)
    public void invalidCoordinates() throws Exception
    {
        // We construct an input string with an incomplete set of coordinates
        // and attempt to process it with the processor. We expect that an
        // exception is thrown due to an incomplete set of coordinates not being allowed.
        String testCase = "@testInstance\ttestLabel\t1,";
        TSDProcessor instance = new TSDProcessor();
        instance.processString(testCase);
    }
    
    /**
     * Tests an error case where the input string has an invalid set of
     * coordinates due to the presence of a non-numeric character
     * and therefore we expect an exception to be thrown.
     * @throws Exception 
     */
    @Test (expected = Exception.class)
    public void invalidCoordinatesString() throws Exception
    {
        // We construct an input string with an invalid set of
        // coordinates due to the presence of a non-numeric character
        // and attempt to process it with the processor. We expect that an
        // exception is thrown due to an incomplete set of coordinates not being allowed.
        String testCase = "@testInstance\ttestLabel\t1,w";
        TSDProcessor instance = new TSDProcessor();
        instance.processString(testCase);
    }
    
    /**
     * Tests an error case where the input string has an invalid label name.
     * In this case, the label name is not present and we expect that the
     * <code>BlankLabelException</code> is thrown.
     * @throws Exception 
     */
    @Test (expected = TSDProcessor.BlankLabelException.class)
    public void emptyLabel() throws Exception
    {
        // Construct input string with no label name and attempt to process it.
        // We expect an exception to be thrown since a blank label name is not
        // a valid label name.
        String testCase = "@testInstance\t" + "" + "\t1,1";
        TSDProcessor instance = new TSDProcessor();
        instance.processString(testCase);
    }
    
    /**
     * Tests a case in creating an instance where the provided label name is
     * null. We expect this to go normally due to a null label name being allowed.
     * @throws Exception 
     */
    @Test
    public void nullLabel() throws Exception
    {
        // Create an input string with a valid instance name, null as the label name,
        // and Double.MAX_VALUE as the coordinates.
        String instanceName = "@testInstance";
        String label = "null";
        double xValue = Double.MAX_VALUE;
        double yValue = Double.MAX_VALUE;
        String testCase = String.format("%s\t%s\t%f,%f", instanceName, label, xValue, yValue);
        
        // Processes the input string and asserts that the instance created has the
        // same label name, instance name, and coordinate values as the input string.
        TSDProcessor instance = new TSDProcessor();
        instance.processString(testCase);
        assert(instance.getLabels().values().toArray()[0].equals(label));
        assert(instance.getLabels().keySet().toArray()[0].equals(instanceName));
        assert(((Point2D)instance.getPoints().values().toArray()[0]).getX() == xValue);
        assert(((Point2D)instance.getPoints().values().toArray()[0]).getY() == yValue);
    }
    
    /**
     * Tests an error case where the input string has too many coordinates. We
     * expect that the <code>InvalidCoordinateException</code> is thrown.
     * @throws Exception 
     */
    @Test (expected = DataSet.InvalidCoordinateException.class)
    public void threeCoordinates() throws Exception
    {
        // Constructs an input string with three coordinates and attempts to
        // process it. We expect an exception to be thrown due to the presence
        // of an additional coordinate. This is not allowed and is not a valid
        // coordinate set due to the 2D nature of our application.
        String testCase = "@testInstance\tnull\t1,1,2";
        TSDProcessor instance = new TSDProcessor();
        instance.processString(testCase);
    }
    
}
