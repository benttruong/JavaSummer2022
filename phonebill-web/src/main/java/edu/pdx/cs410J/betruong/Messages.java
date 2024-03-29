package edu.pdx.cs410J.betruong;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String phoneCallFormat(String caller, String callee, String begin, String end )
    {
        return String.format( "Phone call from %s to %s from %s to %s", caller, callee, begin, end );
    }


}
