package edu.pdx.cs410j.betruong;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
    @Override
    public String getCaller() {
        return "Caller String";
    }

    @Override
    public String getCallee() {
        return "Callee String";
    }

    @Override
    public String getBeginTimeString() {
        return "BeginTimeString";
    }

    @Override
    public String getEndTimeString() {
        return "EndTimeString";
    }
}
