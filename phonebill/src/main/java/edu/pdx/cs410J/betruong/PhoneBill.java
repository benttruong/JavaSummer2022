package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * This class represents a <code>PhoneBill</code>
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;

  private Collection<PhoneCall> phoneCalls;

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

    this.phoneCalls.add(call);
  }

  /**
   * <code>Collection</code> of phone calls from this phone bill
   * @return <code>phoneCalls</code>
   */
  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    return this.phoneCalls;
  }
}
