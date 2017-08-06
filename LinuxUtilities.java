import FileUtilities.WriteFile;
import LogTrace.LogTrace;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class LinuxUtilities
{
    private static final String CMD_LS = "/usr/bin/ls";
    private static final String CMD_SH = "/usr/bin/sh";
    private static final String CMD_PERL = "/usr/bin/perl";
    private static final String SCRIPT_RM_EXTRACT = "/home/xxxx/xxxx/xxxx.sh";
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    //----------------------------------------------------------------------------
    public static boolean DirContentsToList(String sDirectoryName, String sOutputFileName)
    {
        log.info("Enter LinuxUtilities.DirContentsToList()");
        boolean isSuccessful = false;
        ArrayList<String> myCommand = new ArrayList<String>();
        myCommand.add(CMD_LS);
        myCommand.add(sDirectoryName);
        ArrayList<String> myOutput = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder(myCommand);

        try
        {
            Process process = processBuilder.start();

            //Read out dir output
            InputStream myInputStream = process.getInputStream();
            InputStreamReader myInputStreamReader = new InputStreamReader(myInputStream);
            BufferedReader bufferedReader = new BufferedReader(myInputStreamReader);
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                myOutput.add(line);
            } // while

            WriteFile.WriteListToFile(sOutputFileName, myOutput);

            isSuccessful = true;
        } // try
        catch (IOException e)
        {
            isSuccessful = false;
            e.printStackTrace();
        } // catch
        log.info("Exit LinuxUtilities.DirContentsToList()");
        return isSuccessful;
    } // DirContentsToList(String, String)
    //----------------------------------------------------------------------------
    public static boolean RunPerlScript(String sScriptName)
    {
        log.info("Enter LinuxUtilities.RunPerlScript()");
        boolean isSuccessful = false;
        ArrayList<String> myCommand = new ArrayList<String>();
        myCommand.add(CMD_PERL);
        myCommand.add(sScriptName);

        ProcessBuilder processBuilder = new ProcessBuilder(myCommand);

        for (int i = 0; i < myCommand.size(); i++)
        {
            log.info(myCommand.get(i) + " ");
        }

        try
        {
            Process myProcess = processBuilder.start();
            if (myProcess.waitFor() == 0)
            {
                isSuccessful = true;
            } // if
        } // try
        catch (Exception e)
        {
            isSuccessful = false;
            e.printStackTrace();
        } // catch
        log.info("Exit LinuxUtilities.RunPerlScript()");
        return isSuccessful;
    } // RunPerlScript(String)
    //----------------------------------------------------------------------------
    public static boolean RemoveInputFiles()
    {
        log.info("Enter LinuxUtilities.RemoveInputFiles()");
        boolean isSuccessful = false;
        ArrayList<String> myCommand = new ArrayList<String>();
        myCommand.add(CMD_SH);
        myCommand.add(SCRIPT_RM_EXTRACT);

        ProcessBuilder processBuilder = new ProcessBuilder(myCommand);

        for (int i = 0; i < myCommand.size(); i++)
        {
            log.info(myCommand.get(i) + " ");
        }

        try
        {
            Process myProcess = processBuilder.start();
            if (myProcess.waitFor() == 0)
            {
                isSuccessful = true;
            } // if
        } // try
        catch (Exception e)
        {
            isSuccessful = false;
            e.printStackTrace();
        } // catch
        log.info("Exit LinuxUtilities.RemoveInputFiles()");
        return isSuccessful;
    } // RemoveInputFiles
    //----------------------------------------------------------------------------
    public static boolean RunBashScript(String sScriptName)
    {
        log.info("Enter LinuxUtilities.RunBashScript()");
        boolean isSuccessful = false;
        ArrayList<String> myCommand = new ArrayList<String>();
        myCommand.add(CMD_SH);
        myCommand.add(sScriptName);

        ProcessBuilder processBuilder = new ProcessBuilder(myCommand);

        for (int i = 0; i < myCommand.size(); i++)
        {
            log.info(myCommand.get(i) + " ");
        }

        try
        {
            Process myProcess = processBuilder.start();
            if (myProcess.waitFor() == 0)
            {
                isSuccessful = true;
            } // if
        } // try
        catch (Exception e)
        {
            isSuccessful = false;
            e.printStackTrace();
        } // catch
        log.info("Exit LinuxUtilities.RunBashScript()");
        return isSuccessful;
    } // RunBashScript
    //----------------------------------------------------------------------------
} // LinuxUtilities Class
