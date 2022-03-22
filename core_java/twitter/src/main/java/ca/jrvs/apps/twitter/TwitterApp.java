package ca.jrvs.apps.twitter;

public class TwitterApp {

    private final static String ARGUMENTS_NUMBER_ERROR_MESSAGE = "Please provide the right number of arguments.";
    private final static String OPTION_ARGUMENT_ERROR_MESSAGE = "Incorrect option argument.";
    private final static String USAGE_ERROR_MESSAGE = "Usage\n" +
            "TwitterApp post|show|delete \"tweet_text\" \"latitude:longitude\"\n" +
            "\nArguments:\n" +
            "tweet_text         - tweet_text cannot exceed 140 UTF-8 encoded characters. \n" +
            "latitude:longitude - Geo location.";

    //Methods to manage argument errors
    private static void argumentErrorManager(String specificMessage) {
        System.out.println(specificMessage + "\n\n" + USAGE_ERROR_MESSAGE);
    }

    static boolean checkArgsLength(String[] arguments) {
        return (arguments[0].equalsIgnoreCase("show") && arguments.length == 2)
                || arguments.length == 3;
    }

    static boolean checkOptionArgument(String option) {
        return option.equalsIgnoreCase("post")
                || option.equalsIgnoreCase("show")
                || option.equalsIgnoreCase("delete");
    }

    //Methods for the processing of commands.
    static void methodSwitch(String[] arguments) {

        if (arguments[0].equalsIgnoreCase("post"))

        else if (arguments[0].equalsIgnoreCase("show"))

        else if (arguments[0].equalsIgnoreCase("delete"))

    }

    public static void main(String[] args) {

        if (checkArgsLength(args)) {

            argumentErrorManager(ARGUMENTS_NUMBER_ERROR_MESSAGE);
            return;
        }

        if (!checkOptionArgument(args[0])) {

            argumentErrorManager(OPTION_ARGUMENT_ERROR_MESSAGE);
            return;
        }

        methodSwitch(args);
    }
}
