package FileUtilities;

import LogTrace.LogTrace;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
//----------------------------------------------------------------------------
public class WriteFile
{
    private final static String FILE_ENCODING_UTF8 = "UTF-8";
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    //----------------------------------------------------------------------------
    public static boolean AppendToFile(String sFileName, String sContent)
    {
        log.info("Enter WriteToFile()");
        boolean isSuccessful = false;
        try
        {
            File myFile = new File(sFileName);
            if (myFile.exists())
            {
                log.debug(String.format("File exists: %s", sFileName));
                try
                {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(sFileName, true));
                    PrintWriter printWriter = new PrintWriter(new BufferedWriter(bw));
                    printWriter.println(sContent);
                    printWriter.close();
                    isSuccessful = true;
                }
                catch(Exception e)
                {
                    log.error(String.format("Exception thrown: %s", e));
                    e.printStackTrace();
                }
            } // if file exists
            else
            {
                log.debug(String.format("File does not exist: %s", sFileName));
                CreateFile.NewFileByName(sFileName);
                AppendToFile(sFileName, sContent);
            } // else file dne
        } // try
        catch (Exception e)
        {
            log.error(String.format("Exception thrown: %s", e));
            e.printStackTrace();
        } // catch
        log.info("Exit WriteToFile()");
        return isSuccessful;
    } // AppendToFile(String, String)
    //----------------------------------------------------------------------------
    public static boolean AppendToFile(String sFileName, ArrayList lsMyData)
    {
        log.info("Enter WriteToFile(String, ArrayList<>)");
        boolean isSuccessful = false;
        try
        {
            File myFile = new File(sFileName);
            if (myFile.exists())
            {
                log.debug(String.format("File exists: %s", sFileName));
                try
                {
                    FileWriter fstream = new FileWriter(sFileName);
                    PrintWriter printWriter = new PrintWriter(fstream);

                    for (int i = 0; i < lsMyData.size(); i++)
                    {
                        printWriter.println(lsMyData.get(i).toString());
                    } // for

                    printWriter.close();
                    isSuccessful = true;
                }
                catch(Exception e)
                {
                    log.error(String.format("Exception thrown: %s", e));
                    e.printStackTrace();
                }
            } // if file exists
            else
            {
                CreateFile.NewFileByName(sFileName);
                AppendToFile(sFileName, lsMyData);
                log.debug(String.format("File does not exist: %s", sFileName));
            } // else file dne
        } // try
        catch (Exception e)
        {
            log.error(String.format("Exception thrown: %s", e));
            e.printStackTrace();
        } // catch
        log.info("Exit WriteToFile()");
        return isSuccessful;
    } // AppendToFile(String, ArrayList<String>)
    //----------------------------------------------------------------------------
    public static boolean WriteListToFile(String sFilePathAndName, ArrayList<String> lsFileContents)
    {
        log.info("Enter WriteListToFile()");
        boolean isSuccessful = false;
        try
        {
            File myFile = new File(sFilePathAndName);
            if (myFile.exists()) {
                log.info("File exists: " + sFilePathAndName);
                PrintWriter myWriter = new PrintWriter(sFilePathAndName, FILE_ENCODING_UTF8);
                for (int i = 0; i < lsFileContents.size(); i++) {
                    log.debug("    " + lsFileContents.get(i));
                    myWriter.println(lsFileContents.get(i));
                } // for
                myWriter.close();
                isSuccessful = true;
            } // if
            else
            {
                log.info("File does not exist: " + sFilePathAndName);
                CreateFile.NewFileByName(sFilePathAndName);
                WriteListToFile(sFilePathAndName, lsFileContents);
            } // else
        } // try
        catch (Exception e)
        {
            log.error(String.format("Exception thrown: %s", e));
            e.printStackTrace();
        } // catch
        log.info("Exit WriteListToFile()");
        return isSuccessful;
    } // WriteListToFile
    //----------------------------------------------------------------------------
} // WriteFile