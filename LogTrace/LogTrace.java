package LogTrace;
import org.apache.log4j.Logger;

import java.util.ArrayList;

//------------------------------------------------------------------------------
public class LogTrace
{
    // create global logfile instance for program use
    private static Logger log = Logger.getLogger(LogTrace.class.getName());
    //------------------------------------------------------------------------------
    public static void PrintToLog(ArrayList myList)
    {
        for (int i = 0; i < myList.size(); i++)
        {
            log.debug(myList.get(i));
        } // for
    } // PrintToLog(ArrayList)
    //------------------------------------------------------------------------------
    public static void PrintToLog(ArrayList myList, String sPrefix)
    {
        for (int i = 0; i < myList.size(); i++)
        {
            log.debug(sPrefix + myList.get(i));
        } // for
    } // PrintToLog(ArrayList)
    //------------------------------------------------------------------------------
    public static void PrintToLog(ArrayList myList, String sPrefix, String sSuffix)
    {
        for (int i = 0; i < myList.size(); i++)
        {
            log.debug(sPrefix + myList.get(i) + sSuffix);
        } // for
    } // PrintToLog(ArrayList)
    //------------------------------------------------------------------------------
} // LogTrace
//------------------------------------------------------------------------------