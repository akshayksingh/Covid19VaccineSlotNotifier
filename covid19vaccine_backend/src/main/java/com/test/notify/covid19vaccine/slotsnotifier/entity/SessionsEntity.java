package com.test.notify.covid19vaccine.slotsnotifier.entity;

import java.io.Serializable;

public
class SessionsEntity implements Serializable {

  private String session_id;

  private String date;

  private Integer available_capacity;

  private Integer available_capacity_dose1;

  private Integer available_capacity_dose2;

  private Integer min_age_limit;

  private String vaccine;

  private String[] slots;

  public
  String getSession_id() {
    return session_id;
  }

  public
  String getDate() {
    return date;
  }

  public
  Integer getAvailable_capacity() {
    return available_capacity;
  }

  public
  Integer getAvailable_capacity_dose1() {
    return available_capacity_dose1;
  }

  public
  Integer getAvailable_capacity_dose2() {
    return available_capacity_dose2;
  }

  public
  Integer getMin_age_limit() {
    return min_age_limit;
  }

  public
  String getVaccine() {
    return vaccine;
  }

  public
  String[] getSlots() {
    return slots;
  }
}
