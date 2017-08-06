package FileUtilities;

import LogTrace.LogTrace;
import org.apache.log4j.Logger;

import java.io.File;

//----------------------------------------------------------------------------
public class CreateFile
{
    private final static String VALID_FILE_EXTENSION_TXT = ".txt";
    private final static String VALID_FILE_EXTENSION_YML = ".yml";
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    //----------------------------------------------------------------------------
    public static boolean NewFileByName(String sFileName)
    {
        log.info("Enter NewFileByName()");
        boolean isSuccessful = false;
        try
        {
            File myFile = new File(sFileName);
            if (!myFile.exists())
            {
                log.debug("File does not exist.");
                log.debug(String.format("Creating file: %s.", sFileName));
                myFile.createNewFile();
            } // if file dne
        } // end try
        catch (Exception e) {
            log.error(String.format("Error occured in NewFileByName(): ", e));
            e.printStackTrace();
        } // end catch
        log.info("Exit NewFileByName()");
        return isSuccessful;
    } // NewFileByName(String)
    //----------------------------------------------------------------------------
    public static boolean OverwriteFileByName(String sFileName)
    {
        log.info("Enter NewFileByName()");
        boolean isSuccessful = false;
        try {
            log.debug("File does not exist.");
            log.debug(String.format("Creating file: %s.", sFileName));
            File myFile = new File(sFileName);
            myFile.createNewFile();
        } // end try
        catch (Exception e) {
            log.error(String.format("Error occured in NewFileByName(): ", e));
            e.printStackTrace();
        } // end catch
        log.info("Exit NewFileByName()");
        return isSuccessful;
    } // NewFileByName(String)
    //----------------------------------------------------------------------------
} // CreateFile