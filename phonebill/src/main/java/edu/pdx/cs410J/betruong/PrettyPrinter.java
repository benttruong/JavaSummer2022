package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.AppointmentBookDumper;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill>{
    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }
    @Override
    public void dump(PhoneBill phoneBill) throws IOException {

    }

    public void consolePrint(PhoneBill phoneBill){

    }

}
