package FileParsers;

import FileUtilities.*;
import LogTrace.LogTrace;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseList {
    //----------------------------------------------------------------------------
    private static ArrayList<String> lsNameList;
    private static ArrayList<String> lsTargetList;
    private static ArrayList<String> lsOutputList;
    private static ArrayList<String> lsTransferList;
    private static ArrayList<String> lsNamesWithSpaces;
    private static ArrayList<Integer> lsRemovalIndex;
    private static final String COPY_CMD = "cp -R";
    private static final String DEST_DIR = "~/xxxx/xxxx";
    private static final String DEST_EXTRACT = "~/xxxx/xxxx";
    private static final String SCRIPT_HEADER = "#!/bin/bash\n";
    private static final String EXECUTE_DIR = "cd ~/xxxx/xxxx/xxxx\n";
    private static final String PROCESSED_FILE = "/home/xxxx/xxxx/xxxx/ProcessedList.txt";
    private static final String PARSE_SCRIPT_NAME = "/home/xxxx/xxxx/myJavaParseScript.sh";
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    //----------------------------------------------------------------------------
    public static void Run(String sSourceNameFile, String sTargetNameFIle, String sOutputNameFile)
    {
        log.info("Enter ParseList.Run()");
        InitializeLists(sSourceNameFile, sTargetNameFIle, sOutputNameFile);
        TrimOutputList();
        ProcessTargets();
        PrintTargets();
        CheckIfProcessed();
        RemoveProcessedNames();
        DisplayFinalTargets();
        CreateBashScript();
        UpdateProcessedFile();
        log.info("Exit ParseList.Run()");
    } // ParseList(String, String)
    //----------------------------------------------------------------------------
    private static void InitializeLists(String sSourceNameFile, String sTargetNameFIle, String sOutputNameFile)
    {
        log.info("Enter ParseList.InitializeLists()");
        lsNameList = FileToList.GetFileContentsList(sSourceNameFile);
        lsTargetList = FileToList.GetFileContentsList(sTargetNameFIle);
        lsOutputList = FileToList.GetFileContentsList(sOutputNameFile);
        log.info("Exit ParseList.InitializeLists()");
    } // InitializeLists
    //----------------------------------------------------------------------------
    private static void TrimOutputList()
    {
        log.info("Enter ParseList.TrimOutputList()");
        if (lsOutputList.size() >= 40)
        {
            for (int i = 0; i < 20; i++)
            {
                lsOutputList.remove(0);
            } // for
        } // if
        log.info("Exit ParseList.TrimOutputList()");
    } // TrimOutputList
    //----------------------------------------------------------------------------
    private static void ProcessTargets()
    {
        log.info("Enter ParseList.ProcessTargets()");
        String myTarget;
        lsTransferList = new ArrayList<>();

        // for each item in the target list
        for (int i = 0; i < lsTargetList.size(); i++)
        {
            myTarget = lsTargetList.get(i);

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
                        log.debug("    Target Found: " + myTarget);
                        log.debug("    Matched: " + myName);
                        lsTransferList.add(myTarget);
                    } // if

                } // name list
            } // if valid
        } // target list
        log.info("Exit ParseList.ProcessTargets()");
    } // ProcessTargets
    //----------------------------------------------------------------------------
    private static void PrintTargets()
    {
        log.info("Enter ParseList.PrintTargets()");
        log.debug("Selected Targets:");

        for (int i = 0; i < lsTransferList.size(); i++)
        {
            log.debug("    " + lsTransferList.get(i));
        } // for
        log.info("Exit ParseList.PrintTargets()");
    } // PrintTargets
    //----------------------------------------------------------------------------
    private static void CheckIfProcessed()
    {
        log.info("Enter ParseList.CheckIfProcessed()");
        lsRemovalIndex = new ArrayList<>();

        for (int i = 0; i < lsTransferList.size(); i++)
        {
            String myItem = lsTransferList.get(i);
            log.debug("name: " + myItem);
            // matched name is in the already processed list
            if (lsOutputList.contains(myItem))
            {
                log.debug("    item is in processed list, adding removal index: " + i);
                lsRemovalIndex.add(i);
            } // if
        } // for
        log.info("Exit ParseList.CheckIfProcessed()");
    } // CheckIfProcessed
    //----------------------------------------------------------------------------
    private static void RemoveProcessedNames()
    {
        log.info("Enter ParseList.RemoveProcessedNames()");
        int iLastIndex = lsRemovalIndex.size() - 1;
        int iRemoveIndex;
        log.debug("last removal index: " + iLastIndex);
        // remove from being processed twice
        for (int i = iLastIndex; i >= 0; i--)
        {
            iRemoveIndex = lsRemovalIndex.get(i);
            log.debug("Trying to remove index: " + iRemoveIndex);
            log.debug("    Removing name: " + lsTransferList.get(iRemoveIndex));
            lsTransferList.remove(iRemoveIndex);
        } // for
        log.info("Exit ParseList.RemoveProcessedNames()");
    } // RemoveProcessedNames
    //----------------------------------------------------------------------------
    private static void DisplayFinalTargets()
    {
        log.info("Enter ParseList.DisplayFinalTargets()");
        log.debug("Final Targets:");

        Matcher m;
        String myName;
        Pattern p = Pattern.compile("\\s");
        lsNamesWithSpaces = new ArrayList<>();

        for (int i = 0; i < lsTransferList.size(); i++)
        {
            myName = lsTransferList.get(i);
            log.debug("    " + myName);

            m = p.matcher(myName);

            // check if file name has spaces
            if (m.find())
            {
                // add original name to other list
                lsNamesWithSpaces.add(myName);
                // escape space char for bash script
                myName = myName.replaceAll("\\s", "\\\\ ");
                log.debug("    fixed name: " + myName);
                lsTransferList.set(i, myName);
            } // if

        } // for
        log.info("Exit ParseList.DisplayFinalTargets()");
    } // DisplayFinalTargets
    //----------------------------------------------------------------------------
    private static void CreateBashScript()
    {
        log.info("Enter ParseList.CreateBashScript()");
        String sDestination;
        ArrayList<String> lsMyScript = new ArrayList<>();

        lsMyScript.add(SCRIPT_HEADER);
        lsMyScript.add(EXECUTE_DIR);

        for (int i = 0; i < lsTransferList.size(); i++)
        {
            lsMyScript.add("echo \"COPYING: " + lsTransferList.get(i) + "\"" + "\n");

            // if you are a directory, copy to extract folder
            if (!General.ValidateMkvFile(lsTransferList.get(i)))
            {
                sDestination = DEST_EXTRACT;
            }
            // if you are mkv file, copy directly to final destination
            else
            {
                sDestination = DEST_DIR;
            }
            lsMyScript.add(COPY_CMD + " " + lsTransferList.get(i) + " " + sDestination + "\n");
        } // for

        lsMyScript.add("exit 0");

        WriteFile.WriteListToFile(PARSE_SCRIPT_NAME, lsMyScript);
        log.info("Exit ParseList.CreateBashScript()");
    } // CreateBashScript
    //----------------------------------------------------------------------------
    private static void SwapNames()
    {
        // write names without spaces to ProcessedList.txt
        log.info("Enter ParseList.SwapNames()");

        boolean inName;
        String myTransferName;

        for (int i = 0; i < lsTransferList.size(); i++)
        {
            myTransferName = lsTransferList.get(i);
            log.debug("  transfer name: " + myTransferName);

            for (String myName : lsNamesWithSpaces)
            {
                String [] originalNames = myName.split("\\s");

                log.debug("  trying to match: " + myName);

                // set true for each item
                inName = true;

                // if any part of OG name is not in transferName
                // mark the flag as false
                for (String word : originalNames)
                {
                    if (!myTransferName.contains(word))
                    {
                        inName = false;
                    } // if
                } // for

                if (inName)
                {
                    // if names match, swap them
                    lsTransferList.set(i, myName);
                    log.debug("  name matches, updating list");
                } // if
            } // for
        } // for

        log.info("Exit ParseList.SwapNames()");
    } // SwapNames
    //----------------------------------------------------------------------------
    private static void UpdateProcessedFile()
    {
        log.info("Enter ParseList.UpdateProcessedFile()");

        // write names without spaces to ProcessedFile
        if (!lsNamesWithSpaces.isEmpty())
        {
            SwapNames();
        } // if

        // add new items to processed list
        lsOutputList.addAll(lsTransferList);

        WriteFile.WriteListToFile(PROCESSED_FILE, lsOutputList);
        log.info("Exit ParseList.UpdateProcessedFile()");
    } // UpdateProcessedFile
    //----------------------------------------------------------------------------
} // ParseList
