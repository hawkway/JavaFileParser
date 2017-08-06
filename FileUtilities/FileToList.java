package FileUtilities;

import LogTrace.LogTrace;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//----------------------------------------------------------------------------
public class FileToList
{
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    //----------------------------------------------------------------------------
    // read filename and convert each line to ArrayList element
    public static ArrayList<String> GetFileContentsList(String sFileName)
    {
        log.info("Enter GetFileContentsList()");
        ArrayList<String> lsFileContents = new ArrayList<String>();

        try {
            String myLine = "";
            File myFile = new File(sFileName);

            if (myFile.exists()) {
                myFile = new File(sFileName);
                if(log.isDebugEnabled())
                    log.debug(String.format("File name found %s", sFileName));

                Scanner scanner = new Scanner(myFile);

                while (scanner.hasNext())
                {
                    myLine = scanner.nextLine();
                    lsFileContents.add(myLine);
                } // end while

                scanner.close();

            } // if file exists
            else
            {
                if(log.isDebugEnabled())
                    log.debug(String.format("File name not found %s", sFileName));
            } // else file dne

        } // end try
        catch(FileNotFoundException e)
        {
            if(log.isErrorEnabled()) {
                log.debug(String.format("File name found %s", sFileName));
                log.error(String.format("File not found exception: %s", e));
            } // if error
            e.printStackTrace();
        }
        catch (Exception e) {
            if(log.isErrorEnabled()) {
                log.error(String.format("Exception: %s", e));
            } // if error
            e.printStackTrace();
        } // end catch
        log.info("Exit GetFileContentsList()");
        return lsFileContents;
    } // GetFileContentsList
    //----------------------------------------------------------------------------
} // FileToList