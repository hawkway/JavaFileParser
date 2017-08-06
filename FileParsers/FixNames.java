package FileParsers;

import java.util.*;
import java.util.regex.*;
import FileUtilities.*;
import LogTrace.LogTrace;
import org.apache.log4j.Logger;

//----------------------------------------------------------------------------
public class FixNames
{
    private static ArrayList<String> lsMyTargets;
    private static ArrayList<String> lsMyNames;
    private static ArrayList<String> lsFixedNames;
    private static ArrayList<String> lsLowerNames;
    private static final String CMD_MV = "/usr/bin/mv";
    private static final String PATTERN_LC_STARTING = "^[a-z]";
    private static final String SCRIPT_HEADER = "#!/bin/bash\n\n";
    private static final String SCRIPT_OUTPUT_NAME = "/home/xxxx/xxxx/fixNamesScript.sh";
    private static final String EXECUTE_DIR = "cd ~/xxxx/xxxx/xxxx\n\n";
    private static final String DIR_PREFIX = "/home/xxxx/xxxx/xxxx/";
    private static final String DIR_DESTINATION = "/home/xxxx/xxxx/xxxx/";
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    //----------------------------------------------------------------------------
    public static void Run(String sTargetList, String sNamesToFix)
    {
        log.info("Enter FixNames.Run()");
        lsMyTargets = FileToList.GetFileContentsList(sTargetList);
        lsMyNames = FileToList.GetFileContentsList(sNamesToFix);
        FindNames();
        CreateBashScript();
        log.info("Exit FixNames.Run()");
    } // Run
    //----------------------------------------------------------------------------
    private static void FindNames()
    {
        log.info("Enter FixNames.FindNames()");
        Matcher myMatcher;
        Pattern myPattern = Pattern.compile(PATTERN_LC_STARTING);
        lsLowerNames = new ArrayList<>();
        lsFixedNames = new ArrayList<>();

        for (int i = 0; i < lsMyNames.size(); i++)
        {
            String myName = lsMyNames.get(i);
            myMatcher = myPattern.matcher(myName);

            if (myMatcher.find())
            {
                log.debug("Trying to match: " + myName);

                for (int j = 0; j < lsMyTargets.size(); j++)
                {
                    String myTarget = lsMyTargets.get(j);
                    if (myName.contains(myTarget.toLowerCase()))
                    {
                        log.debug("    name is: " + myName);
                        log.debug("    match found: " + myTarget);
                        lsLowerNames.add(myName);
                        lsFixedNames.add(myTarget);
                    } // if
                } // for
            } // if
        } // for
        log.info("Enter FixNames.FindNames()");
    } // FindNames
    //----------------------------------------------------------------------------
    private static void CreateBashScript()
    {
        log.info("Enter FixNames.CreateBashScript()");
        ArrayList<String> lsMyScript = new ArrayList<>();

        lsMyScript.add(SCRIPT_HEADER);
        lsMyScript.add(EXECUTE_DIR);

        for (int i = 0; i < lsLowerNames.size(); i++)
        {
            lsMyScript.add("echo \"FIXING: " + lsLowerNames.get(i) + "\"" + "\n");
            lsMyScript.add(CMD_MV + " " + DIR_PREFIX + lsLowerNames.get(i) + " " + DIR_DESTINATION +
                    lsFixedNames.get(i) + ".xxx\n");
        } // for

        lsMyScript.add("exit 0");

        WriteFile.WriteListToFile(SCRIPT_OUTPUT_NAME, lsMyScript);
        log.info("Exit FixNames.CreateBashScript()");
    } // CreateBashScript
    //----------------------------------------------------------------------------
} // FixNames