package FileParsers;
import java.util.*;
import FileUtilities.*;
import LogTrace.LogTrace;
import org.apache.log4j.Logger;
//----------------------------------------------------------------------------
public class SourceParser
{
    private static final String SOURCE_FILE = "/home/xxxx/xxxx/xxxx.xxx";
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    private static ArrayList<String> lsMyContents;
    private static ArrayList<Integer> lsMyRange;
    private static String sFileDestination = "";
    //----------------------------------------------------------------------------
    public static void ParseConfigFile(String sFileOutput, int iLowerBound, int iUpperBound)
    {
        log.info("Enter SourceParser.ParseConfigFile()");
        sFileDestination = sFileOutput;
        lsMyContents = new ArrayList<String>();
        lsMyContents = FileToList.GetFileContentsList(SOURCE_FILE);
        ProcessNames(iLowerBound, iUpperBound);
        log.info("Exit SourceParser.ParseConfigFile()");
    } // FileParsers(String)
    //----------------------------------------------------------------------------
    private static void ProcessNames(int iLowerBound, int iUpperBound)
    {
        log.info("Enter SourceParser.ProcessNames()");
        FindTextRegions();
        TrimFileToRegion(iLowerBound, iUpperBound);
        TrimTextString();
        WriteFile.WriteListToFile(sFileDestination, lsMyContents);
        log.info("Exit SourceParser.ProcessNames()");
    } // GetNames(String, String)
    //----------------------------------------------------------------------------
    private static void FindTextRegions()
    {
        log.info("Enter SourceParser.FindTextRegions()");
        String sMyLine = "";
        lsMyRange = new ArrayList<Integer>();
        for (int i = 0; i < lsMyContents.size(); i++)
        {
            sMyLine = lsMyContents.get(i);
            if (sMyLine.contains("#"))
            {
                // line number + zero index offset
                lsMyRange.add(i+1);
            } // if
        } // for
        log.info("Exit SourceParser.FindTextRegions()");
    } // FindTextRegions
    //----------------------------------------------------------------------------
    private static void TrimFileToRegion(int iLowerBound, int iUpperBound)
    {
        log.info("Enter SourceParser.TrimFileToRegion()");
        int iCurrentLastIndex;
        int iStartingEndIndex = lsMyContents.size();
        iLowerBound = lsMyRange.get(iLowerBound);
        iUpperBound = lsMyRange.get(iUpperBound);

        // remove excess from front
        for (int i = 0; i <= iLowerBound; i++)
        {
            lsMyContents.remove(0);
        } // for lower bound

        // remove excess from end
        for (int i = iUpperBound; i <= iStartingEndIndex; i++)
        {
            iCurrentLastIndex = lsMyContents.size()-1;
            lsMyContents.remove(iCurrentLastIndex);
        } // for upper bound
        log.info("Exit SourceParser.TrimFileToRegion()");
    } // TrimFileToRegion(int, int)
    //----------------------------------------------------------------------------
    private static void TrimTextString()
    {
        log.info("Enter SourceParser.TrimTextString()");
        String sMyLine = "";

        for (int i = 0; i < lsMyContents.size(); i++)
        {
            sMyLine = lsMyContents.get(i);
            lsMyContents.set(i, sMyLine.replaceAll("^\\s+\\-\\s", ""));
        } // for
        log.info("Exit SourceParser.TrimTextString()");
    } // TrimTextString
    //----------------------------------------------------------------------------
} // FileParsers