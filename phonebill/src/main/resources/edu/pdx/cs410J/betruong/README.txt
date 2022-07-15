Project2 - Ben Truong
This application will record a new phone call and assign
that phone call to a phone bill with a customer name
with information provided from command line arguments.

A phone call is initiated by a person with a given phone
number at a given time, is received by a person with a
given phone number, and terminates at a given time.

This is how you use it from the command line:
java -jar target/phonebill-2022.0.0.jar [options] <args>

args are (in this order):
    customer        : Person whose phone bill we’re modeling
    callerNumber    : Phone number of caller
    calleeNumber    : Phone number of person who was called
    begin           : Date and time call began (24-hour time)
    end             : Date and time call ended (24-hour time)

options are (options may appear in any order):
    -textFile file  : could be just a file name or
                      a path with a file name included
                      where to read/write the phone bill
    -print          : Prints a description of the new phone call
    -README         : Prints a README for this project and exits

Date and time should be in the format: mm/dd/yyyy hh:mm