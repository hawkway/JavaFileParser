import FileParsers.*;
//------------------------------------------------------------------------------
class mainDriver
{
    // directories
    private static final String DIRECTORY_ITEMS = "/home/xxxx/xxxx/xxxx";
    private static final String DIRECTORY_TARGETS = "/home/xxxx/xxxx/xxxx";
    private static final String DIRECTORY_EXTRACT_INPUT = "/home/xxxx/xxxx/xxxx";
    private static final String DIRECTORY_EXTRACT_OUTPUT = "/home/xxxx/xxxx/xxxx";
    // scripts
    private static final String SCRIPT_PARSE = "/home/xxxx/xxxx/myJavaParseScript.sh";
    private static final String SCRIPT_FIX_NAMES = "/home/xxxx/xxxx/fixNamesScript.sh";
    private static final String SCRIPT_MOVE_OUTPUT = "/home/xxxx/xxxx/move_xxxx.sh";
    private static final String SCRIPT_UNRAR = "/home/xxxx/xxxx/unrar.pl";
    private static final String SCRIPT_CHECK_DIR = "/home/xxxx/xxxx/checkDir.pl";
    private static final String SCRIPT_CHECK_DIR_OUTPUT = "/home/xxxx/xxxx/myPerlCreateDirScript.sh";
    private static final String SCRIPT_CREATE_DIR = "/home/xxxx/xxxx/myCreateDirScript.sh";
    // output
    private static final String NAMES_1 = "/home/xxxx/xxxx/xxxx/List1.txt";
    private static final String NAMES_2 = "/home/xxxx/xxxx/xxxx/List2.txt";
    private static final String NAMES_3 = "/home/xxxx/xxxx/xxxx/List3.txt";
    // lists
    private static final String LIST_TARGETS = "/home/xxxx/xxxx/xxxx/Targets.txt";
    private static final String LIST_PROCESSED = "/home/xxxx/xxxx/xxxx/ProcessedList.txt";
    private static final String LIST_EXTRACTED = "/home/xxxx/xxxx/xxxx/ExtractedNames.txt";
    //------------------------------------------------------------------------------
    public static void main(String[] args)
    {
        //========================================================================
        // put directory contents into a file list
        //========================================================================
        LinuxUtilities.DirContentsToList(DIRECTORY_TARGETS, LIST_TARGETS);

        //========================================================================
        // config file ---> raw names in file
        //========================================================================
        SourceParser.ParseConfigFile(NAMES_1, 0, 1);
        SourceParser.ParseConfigFile(NAMES_2, 2, 3);
        SourceParser.ParseConfigFile(NAMES_3, 4, 5);

        //========================================================================
        // parse the file names from target list and create/exec move script
        //========================================================================
        ParseList.Run(NAMES_1, LIST_TARGETS, LIST_PROCESSED);
        LinuxUtilities.RunBashScript(SCRIPT_PARSE);

        ParseList.Run(NAMES_2, LIST_TARGETS, LIST_PROCESSED);
        LinuxUtilities.RunBashScript(SCRIPT_PARSE);

        ParseList.Run(NAMES_3, LIST_TARGETS, LIST_PROCESSED);
        LinuxUtilities.RunBashScript(SCRIPT_PARSE);

        //========================================================================
        // run perl script to extract files and clean up
        //========================================================================
        LinuxUtilities.RunPerlScript(SCRIPT_UNRAR);
        LinuxUtilities.RemoveInputFiles();

        //========================================================================
        // put extracted file names into a list for renaming
        //========================================================================
        LinuxUtilities.DirContentsToList(DIRECTORY_EXTRACT_OUTPUT, LIST_EXTRACTED);

        //========================================================================
        // rename shitty file names, and move output to destination
        //========================================================================
        FixNames.Run(LIST_TARGETS, LIST_EXTRACTED);
        LinuxUtilities.RunBashScript(SCRIPT_FIX_NAMES);
        LinuxUtilities.RunBashScript(SCRIPT_MOVE_OUTPUT);

        //========================================================================
        // check for existing directories, run generated script
        //========================================================================
        CreateDir.Run(NAMES_1, DIRECTORY_ITEMS);
        LinuxUtilities.RunBashScript(SCRIPT_CREATE_DIR);

        CreateDir.Run(NAMES_2, DIRECTORY_ITEMS);
        LinuxUtilities.RunBashScript(SCRIPT_CREATE_DIR);

        CreateDir.Run(NAMES_3, DIRECTORY_ITEMS);
        LinuxUtilities.RunBashScript(SCRIPT_CREATE_DIR);
    } // main
    //------------------------------------------------------------------------------
} // mainDriver
//------------------------------------------------------------------------------
