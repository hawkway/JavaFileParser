package FileUtilities;
import org.apache.log4j.Logger;
import LogTrace.LogTrace;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
//----------------------------------------------------------------------------
public class General
{
    private final static String VALID_FILE_EXTENSION_TXT = ".txt";
    private final static String VALID_FILE_EXTENSION_MKV = ".mkv";
    private final static String VALID_FILE_EXTENSION_YML = ".yml";
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    //----------------------------------------------------------------------------
    public static boolean doesFileExist(File myFile)
    {
        log.info("Enter doesFileExist(File)");
        log.info("Exit doesFileExist(File)");
        return myFile.exists();
    } // doesFileExist(File)
    //----------------------------------------------------------------------------
    public static boolean doesTxtFileExist(String sFileName)
    {
        log.info("Enter doesFileExist(String)");
        boolean isSuccessful = false;
        try
        {
            File myFile = new File(ValidateTxtFile(sFileName));
            if (myFile.exists())
            {
                isSuccessful = true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        log.info("Exit doesFileExist(String)");
        return isSuccessful;
    } // doesFileExist(String)
    //----------------------------------------------------------------------------
    public static boolean doesConfigFileExist(String sFileName)
    {
        log.info("Enter doesConfigFileExist(String)");
        boolean isSuccessful = false;
        try
        {
            File myFile = new File(ValidateConfigFile(sFileName));
            if (myFile.exists())
            {
                isSuccessful = true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        log.info("Exit doesConfigFileExist(String)");
        return isSuccessful;
    } // doesConfigFileExist(String)
    //----------------------------------------------------------------------------
    public static String GetDateTimeString()
    {
        log.info("Enter GetDateTimeString()");
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        Date currDate = new Date();
        log.info("Exit GetDateTimeString()");
        return sdfDateFormat.format(currDate);
    } // GetDateTimeString
    //----------------------------------------------------------------------------
    public static String ValidateTxtFile(String sFileName)
    {
        log.info("Enter ValidateTxtFile()");
        String sValidName = "";
        if (sFileName.endsWith(VALID_FILE_EXTENSION_TXT))
        {
            sValidName = sFileName;
        }
        else
        {
            sValidName = sFileName.concat(".txt");
        }
        log.info("Exit ValidateTxtFile()");
        return sValidName;
    } // ValidateTxtFile
    //----------------------------------------------------------------------------
    public static boolean ValidateMkvFile(String sFileName)
    {
        log.info("Enter ValidateMkvFile()");
        String sValidName = "";
        boolean isValid = false;
        if (sFileName.endsWith(VALID_FILE_EXTENSION_MKV))
        {
            isValid = true;
        }
        log.info("Exit ValidateMkvFile()");
        return isValid;
    } // ValidateMkvFile
    //----------------------------------------------------------------------------
    public static String ValidateConfigFile(String sFileName)
    {
        log.info("Enter ValidateConfigFile()");
        String sValidName = "";
        if (sFileName.endsWith(VALID_FILE_EXTENSION_YML))
        {
            sValidName = sFileName;
        }
        else
        {
            sValidName = sFileName.concat(".txt");
        }
        log.info("Exit ValidateConfigFile()");
        return sValidName;
    } // ValidateConfigFile
    //----------------------------------------------------------------------------
} // General
