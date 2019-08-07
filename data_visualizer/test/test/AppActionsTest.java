/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import actions.AppActions;
import java.io.File;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author goreg
 */
public class AppActionsTest
{
    
    public AppActionsTest()
    {
    }
    
    /**
     * Tests an average case for saving a file.
     */
    @Test
    public void saveAverageCase()
    {
        /** This is string is an average file as it contains one instance, one
         *  label, and one set of valid coordinates.
         */
        String testString = "@testInstance\ttestLabel\t1,1";
        String savedTestString = "";
        
        // The save directory has been declared to be within the data folder
        // named saver.tsd
        File saveDirectory = new File("data-vilij\\resources\\data\\saver.tsd");
        
        /** The AppActions class attempts to save the file to the provided
        * directory and then loads the saved file into <code>savedTestString</code>.
        * If an exception is thrown by the saving or reading process, then we
        * catch it and the test is failed.
        */
        AppActions actions = new AppActions();
        try{
        actions.saveFileTest(testString, saveDirectory);
        savedTestString = actions.readFileTest(saveDirectory);
        }
        catch (Exception e)
        {
            fail();
        }
        
        // We assert that the saving and loading process went successfully by
        // asserting that the saved string is equal to the original string that
        // was saved.
        assert(testString.equals(savedTestString));
    }
    
    /**
     * Tests a case where an error as expected due to an invalid file path. The
     * expected outcome is that the test results in the <code>IOException</code>
     * being thrown due to the program trying to save a valid file to an invalid
     * file path.
     * @throws Exception 
     */
    @Test (expected = IOException.class)
    public void saveErrorCaseInvalidPath() throws Exception
    {
        // The string to be saved is an average string with no errors in its
        // TSD format.
        String testString = "@testInstance\ttestLabel\t1,1";
        String savedTestString = "";
        
        // The file directory is invalid and does not actually lead to any
        // path in the project.
        File saveDirectory = new File("data-vilij\\data\\saver.tsd");
        
        /** Try to save with AppActions and an <code>IOException</code> is
         * expected due to the invalid path. There is no assert needed since
         * it is expected that an exception is thrown in the saving process.
         */
        AppActions actions = new AppActions();
        actions.saveFileTest(testString, saveDirectory);
        savedTestString = actions.readFileTest(saveDirectory);
    }
    
}
