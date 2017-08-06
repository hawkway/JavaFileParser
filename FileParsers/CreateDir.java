package FileParsers;

import FileUtilities.*;
import LogTrace.LogTrace;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.regex.*;

//----------------------------------------------------------------------------
public class CreateDir
{
    //----------------------------------------------------------------------------
    private static Hashtable<String, ArrayList<String>> htNameList;
    private static Hashtable<String, Integer> htNameCount;
    private static Hashtable<String, String> htFileDest;
    private static Hashtable<String, Boolean> htHasDir;
    private static Hashtable<String, String> htCreateList;
    private static ArrayList<String> lsNameList;
    private static ArrayList<String> lsDirNames;
    private static ArrayList<String> lsDirList;
    private static ArrayList<String> lsFileList;
    private static ArrayList<String> lsCreateDir;
    private static ArrayList<String> lsItemsToProcess;
    private static final String CMD_MOVE = "mv";
    private static final String SCRIPT_HEADER = "#!/bin/bash\n";
    private static final String EXECUTE_DIR = "cd ~/xxxx/xxxx\n";
    private static final String CREATEDIR_SCRIPT_NAME = "/home/xxxx/xxxx/myCreateDirScript.sh";
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    //----------------------------------------------------------------------------
    public static void Run(String sNameList, String sDirName)
    {
        log.info("Enter CreateDir.Run()");
        lsNameList = FileToList.GetFileContentsList(sNameList);
        InitHash();
        CrawlDir(sDirName);
        ProcessTargets();
        RemoveEmptyNames();
        SetNamesToProcess();
        CheckForDir();
        PrintDirInfo();
        CorrectDirNames();
        FindNeededDir();
        CreateDirNames();
        CreateBashScript();
        log.info("Exit CreateDir.Run()");
    } // Run(String)
    //----------------------------------------------------------------------------
    private static void InitHash()
    {
        log.info("Enter CreateDir.InitHash()");
        htNameList = new Hashtable<>();
        htNameCount = new Hashtable<>();

        for (int i = 0; i < lsNameList.size(); i++)
        {
            String sMyName = lsNameList.get(i);
            Integer iCount = 0;
            ArrayList<String> lsNewList = new ArrayList<>();
            htNameCount.put(sMyName, iCount);
            htNameList.put(sMyName, lsNewList);
        } // for
        log.info("Exit CreateDir.InitHash()");
    } // InitHash
    //----------------------------------------------------------------------------
    private static void CrawlDir(String sDirName)
    {
        log.info("Enter CreateDir.CrawlDir()");
        try {
            File [] listOfFiles;
            File myDir = new File(sDirName);
            if (myDir.exists())
            {
                lsDirList = new ArrayList<>();
                lsDirNames = new ArrayList<>();
                lsFileList = new ArrayList<>();
                listOfFiles = myDir.listFiles();
                for (int i = 0; i < listOfFiles.length; i++)
                {
                    String myName = listOfFiles[i].getName();
                    if (listOfFiles[i].isDirectory())
                    {
                        // add original dir name to list
                        log.debug("    dir name: " + myName);
                        lsDirList.add(myName);
                        // add modified dir name to list
                        myName = myName.replaceAll("\\sS\\w*$", "");
                        log.debug("    fixed dir: " + myName);
                        lsDirNames.add(myName);
                    } // if
                    else if (listOfFiles[i].isFile() &&
                            myName.toLowerCase().contains(".xxx"))
                    {
                        log.debug("    file name: " + myName);
                        lsFileList.add(myName);
                    } // else
                } // for
            } // if
        } // try
        catch (Exception e)
        {
            log.error(String.format("Exception thrown: %s", e));
            e.printStackTrace();
        } // catch
        log.info("Exit CreateDir.CrawlDir()");
    } // CrawlDir(String)
    //----------------------------------------------------------------------------
    private static void ProcessTargets()
    {
        log.info("Enter CreateDir.ProcessTargets()");
        String myTarget;
        Integer tempCount;
        ArrayList<String> myTempList;

        // for each item in the target list
        for (int i = 0; i < lsFileList.size(); i++)
        {
            myTarget = lsFileList.get(i);

            // if valid
            if ((myTarget.toLowerCase().contains("xxxx") && myTarget.toLowerCase().contains("xxxx")) ||
                    (myTarget.toLowerCase().contains("xxxx") && myTarget.toLowerCase().contains("xxxx")))
            {
                log.debug("Valid target: " + myTarget);
                String myName;

                // for every name in list
                for (int j = 0; j < lsNameList.size(); j++)
                {
                    myName = lsNameList.get(j);
                    boolean inName = true;

                    // split name into word tokens
                    String [] nameTokens = myName.split("\\s+");

                    // for each part of the name
                    for (String token : nameTokens)
                    {
                        // if part of name doesn't exist
                        if (!myTarget.toLowerCase().contains(token.toLowerCase()))
                            inName = false;
                    } // token list

                    if(inName)
                    {
                        log.debug("    name is: " + myName);
                        log.debug("    match found: " + myTarget);

                        // get list from hash
                        myTempList = htNameList.get(myName);
                        // add item to list
                        myTempList.add(myTarget);
                        // put back in hash
                        htNameList.put(myName, myTempList);
                        // update item count
                        tempCount = htNameCount.get(myName);
                        log.debug("    count before: " + tempCount);
                        // use extra step to ensure correct numbers
                        tempCount++;
                        log.debug("    count after: " + tempCount);
                        // put back in hash
                        htNameCount.put(myName, tempCount);
                    } // if

                } // for each name in nameList
            } // if valid
        } // target list
        log.info("Exit CreateDir.ProcessTargets()");
    } // ProcessTargets
    //----------------------------------------------------------------------------
    private static void RemoveEmptyNames()
    {
        // remove entries with zero entries
        log.info("Enter CreateDir.RemoveEmptyNames()");

        // add names to be removed to this list
        ArrayList<String> lsRemoveMe = new ArrayList<>();

        // for each key in hash
        for  (String myKey : htNameCount.keySet())
        {
            // if key value is zero
            if (htNameCount.get(myKey) == 0)
            {
                // add key to list for removal
                log.debug("        key is zero for: " + myKey);
                lsRemoveMe.add(myKey);
            } // if
            else
            {
                log.debug("    key exists for: " + myKey);
            } // else
        } // for

        // remove names added to list from hash
        for (int i = 0; i < lsRemoveMe.size(); i++)
        {
            htNameCount.remove(lsRemoveMe.get(i));
        } // for

        log.info("Exit CreateDir.RemoveEmptyNames()");
    } // RemoveEmptyNames
    //----------------------------------------------------------------------------
    private static void SetNamesToProcess()
    {
        // move names to process list
        log.info("Enter CreateDir.SetNamesToProcess()");

        lsItemsToProcess = new ArrayList<>();
        ArrayList<String> lsTempList;
        ArrayList<String> lsRemoveList = new ArrayList<>();

        // for each key in hash
        for  (String myKey : htNameList.keySet())
        {
            // get list of items ArrayList
            lsTempList = htNameList.get(myKey);

            // if value exists
            if (!lsTempList.isEmpty())
            {
                // add item to list for processing
                log.debug("key: " + myKey);
                lsItemsToProcess.add(myKey);
            } // if
            else
            {
                // remove item
                log.debug("  remove empty key: " + myKey);
                lsRemoveList.add(myKey);
            } // else

            // print name in nameList
            for (String myName : lsTempList)
            {
                log.debug("    " + myName);
            } // for
        } // for

        // remove empty names from hash
        for (int i = 0; i < lsRemoveList.size(); i++)
        {
            htNameList.remove(lsRemoveList.get(i));
        } // for

        log.info("Exit CreateDir.SetNamesToProcess()");
    } // SetNamesToProcess
    //----------------------------------------------------------------------------
    private static void CheckForDir()
    {
        // check if files have existing dir
        log.info("Enter CreateDir.CheckForDir()");

        // init vars
        htHasDir = new Hashtable<>();
        htFileDest = new Hashtable<>();

        // set all values to FALSE
        for (String myItem : lsItemsToProcess)
        {
            htHasDir.put(myItem, false);
        } // for

        // for each item to process
        for  (String myName : lsItemsToProcess)
        {
            log.debug("checking for existing dir: " + myName);
            boolean inName;

            // for each directory name
            // need to use fori indexing to access parallel lists
            for (int i = 0; i < lsDirNames.size(); i++)
            {
                // get current dir name
                String myDir = lsDirNames.get(i);

                // split dir name into word tokens
                String [] nameTokens = myDir.split("\\s");

                // reset for each iteration
                inName = true;

                // for every word in the name
                for (String myToken : nameTokens)
                {
                    // check lowercase == lowercase
                    String lcToken = myToken.toLowerCase();

                    // if part of name is not in list
                    if (!myName.toLowerCase().contains(lcToken))
                        inName = false;
                } // foreach word in name

                // if dir exists
                if (inName)
                {
                    // get list of episodes for this dir
                    ArrayList<String> lsItems = htNameList.get(myDir.toLowerCase());
                    // match items of this name to existing dir
                    for (String listItem : lsItems)
                    {
                        log.debug("    filename: " + listItem);
                        htFileDest.put(listItem, lsDirList.get(i));
                    } // for

                    log.debug("    dir name is: " + lsDirNames.get(i));
                    log.debug("    original dir is: " + lsDirList.get(i));
                    log.debug("    match found: " + myName);

                    htHasDir.put(myName, true);
                    log.debug("    hasDir: " + htHasDir.get(myName));
                } // if hasDir

            } // for dirNames

        } // foreach item to process

        log.info("Exit CreateDir.CheckForDir()");
    } // CheckForDir
    //----------------------------------------------------------------------------
    private static void PrintDirInfo()
    {
        // display info for name having dir (T/F)
        log.info("Enter CreateDir.PrintDirInfo()");

        // for each key in hash
        for  (String myKey : htHasDir.keySet())
        {
            // get value of key
            boolean myValue = htHasDir.get(myKey);

            // if dir exists for key
            if (myValue == true)
            {
                // print key
                log.debug("  key: " + myKey);
                log.debug("    has dir: " + myValue);
            } // if
            else
            {
                // display key
                log.debug("  key: " + myKey);
                log.debug("    no dir: " + myKey);
            } // else
        } // for

        log.info("Exit CreateDir.PrintDirInfo()");
    } // PrintDirInfo
    //----------------------------------------------------------------------------
    private static void CorrectDirNames()
    {
        // add escaped space characters to dir names
        log.info("Enter CreateDir.CorrectDirNames()");

        // for each key in hash
        for  (String myKey : htFileDest.keySet())
        {
            // get value of key
            String myValue = htFileDest.get(myKey);

            // print key
            log.debug("  key: " + myKey);
            // fix name
            myValue = myValue.replaceAll("\\s", "\\\\ ");
            // print fixed name
            log.debug("  fixed name: " + myValue);

            // if dest exists
            if (myValue != null)
            {
                // add name back to hash
                htFileDest.put(myKey, myValue);
            } // if
        } // for

        log.info("Exit CreateDir.CorrectDirNames()");
    } // CorrectDirNames
    //----------------------------------------------------------------------------
    private static void FindNeededDir()
    {
        // find 3+ matching names that need new dir
        log.info("Enter CreateDir.FindNeededDir()");

        log.debug("has 3+: ");

        ArrayList<String> lsTempList;
        lsCreateDir = new ArrayList<>();

        // for each key in hash
        for  (String myKey : htNameCount.keySet())
        {
            // get value of key
            int myValue = htNameCount.get(myKey);

            // if there are more than 3 and no dir
            if (myValue >= 3 && !htHasDir.get(myKey))
            {
                // add name to create dir list
                lsCreateDir.add(myKey);

                // get list of all names
                lsTempList = htNameList.get(myKey);

                // display each name
                log.debug("    key: " + myKey);
                for (String myName : lsTempList)
                {
                    log.debug("        name: " + myName);
                } // for
            } // if

            // if no dir and less than 3
            else if (!htHasDir.get(myKey))
            {
                // remove name from list
                htNameList.remove(myKey);
            } // else if
        } // for

        log.info("Exit CreateDir.FindNeededDir()");
    } // FindNeededDir
    //----------------------------------------------------------------------------
    private static void CreateDirNames()
    {
        // create the proper names of the new dirs
        log.info("Enter CreateDir.CreateNewDir()");

        // vars
        Matcher m;
        String myEntry;
        String sFilename;
        String sDirName;
        ArrayList<String> lsTempList;
        htCreateList = new Hashtable<>();
        Pattern p = Pattern.compile("S\\d+");

        // for each name in list
        for  (String myName : lsCreateDir)
        {
            // get value of key
            String myUpperName;
            htCreateList = new Hashtable<>();

            // print old name
            log.debug("  old name: " + myName);
            // fix name
            myUpperName = Character.toString(myName.charAt(0)).toUpperCase() + myName.substring(1);
            log.debug("  fixed name: " + myUpperName);

            // get filename
            lsTempList = htNameList.get(myName);
            sFilename = lsTempList.get(0);
            log.debug("  filename: " + sFilename);

            // match the pattern in the filename
            m = p.matcher(sFilename);

            // check for null
            if (m.find())
            {
                // get info
                myEntry = m.group();
                log.debug("  info: " + myEntry);

                // get full dir name
                sDirName = myUpperName + " " + myEntry;
                log.debug("  full dir name: " + sDirName);
                // add escape chars
                sDirName = sDirName.replaceAll("\\s+", "\\\\ ");
                log.debug("  fixed dir name: " + sDirName);
                // add final dir name to list
                htCreateList.put(myName, sDirName);
            } // if
            else
            {
                log.error("INFO NOT FOUND FOR ITEM");
            } // else

        } // for

        log.info("Exit CreateDir.CreateNewDir()");
    } // CreateNewDir
    //----------------------------------------------------------------------------
    private static void CreateBashScript()
    {
        // create the proper names of the new dirs
        log.info("Enter CreateDir.CreateBashScript()");

        Matcher m;
        String sDestination;
        ArrayList<String> lsItemList;
        Pattern p = Pattern.compile("\\s");
        ArrayList<String> lsMyScript = new ArrayList<>();

        lsMyScript.add(SCRIPT_HEADER);
        lsMyScript.add(EXECUTE_DIR);

        // create new dirs
        for (String myKey : htCreateList.keySet())
        {
            // set destination
            sDestination = htCreateList.get(myKey);

            // write script
            lsMyScript.add("echo \"" + sDestination + "\"\n");
            lsMyScript.add("mkdir " + sDestination + "\n");
        } // for

        // move files to their destination
        for (String myKey : htNameList.keySet())
        {
            // get list of items
            lsItemList = htNameList.get(myKey);

            // process all
            for (String myItem : lsItemList)
            {
                // get destination for each
                sDestination = htFileDest.get(myItem);

                // check if spaces in filename
                m = p.matcher(myItem);

                if (m.find())
                {
                    // replace all spaces with "\ " for bash
                    myItem = myItem.replaceAll("\\s", "\\\\ ");
                    log.debug("     fixed name: " + myItem);
                } // if

                if (sDestination != null) {
                    // move to destination
                    lsMyScript.add(CMD_MOVE + " " + myItem + " " + sDestination + "\n");
                } // if
            } // for
        } // for

        lsMyScript.add("exit 0");

        WriteFile.WriteListToFile(CREATEDIR_SCRIPT_NAME, lsMyScript);

        log.info("Exit CreateDir.CreateBashScript()");
    } // CreateBashScript
    //----------------------------------------------------------------------------
} // CreateDir