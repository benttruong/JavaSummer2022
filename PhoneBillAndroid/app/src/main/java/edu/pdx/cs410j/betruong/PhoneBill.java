package edu.pdx.cs410j.betruong;

import androidx.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


import edu.pdx.cs410J.AbstractPhoneBill;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {

    private final String customer;

    private ArrayList<PhoneCall> phoneCalls;

    /**
     * Creates a new <code>PhoneBill</code>
     *
     * @param customer
     *        Name of the person whose phone bill this is
     */
    public PhoneBill(String customer) {
        this.customer = customer;
        this.phoneCalls = new ArrayList<>();
    }

    /**
     * Name of the customer
     * @return <code>customer</code> name
     */
    @Override
    public String getCustomer() {
        return this.customer;
    }

    /**
     * Adding a new phone call to the phone bill
     * @param call
     *        A phone call to add
     */
    @Override
    public void addPhoneCall(PhoneCall call) {
        this.phoneCalls.add((PhoneCall) call);
        Collections.sort(phoneCalls);
    }


    /**
     * <code>Collection</code> of phone calls from this phone bill
     * @return <code>phoneCalls</code>
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.phoneCalls;
    }


    @VisibleForTesting
    public String getPrettyBillString() {
        String result = "==============================\n"
                + "Customer's name: " + this.getCustomer()
                + "\n" + "This phone bill contains " + this.phoneCalls.size() + " phone call(s)."
                + "\n----------------------";
        int count = 1;
        for (PhoneCall call:phoneCalls){
            result += "\n" + count + ". " + call.getPrettyCallString();
            ++count;
        }
        result += "\n===============================\n";
        return result;
    }
}
