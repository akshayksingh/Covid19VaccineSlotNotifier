package com.test.notify.covid19vaccine.slotsnotifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public
class CentersEntity implements Serializable {

  private Integer center_id;

  private String name;

  private String address;

  private String state_name;

  private String district_name;

  private String block_name;

  private String pincode;

  private Integer lat;

  private Integer longitude;

  private String from;

  private String to;

  private String fee_type;

  private SessionsEntity[] sessions;

  public
  SessionsEntity[] getSessions() {
    return sessions;
  }

  public
  Integer getCenter_id() {
    return center_id;
  }

  public
  String getName() {
    return name;
  }

  public
  String getAddress() {
    return address;
  }

  public
  String getState_name() {
    return state_name;
  }

  public
  String getDistrict_name() {
    return district_name;
  }

  public
  String getBlock_name() {
    return block_name;
  }

  public
  String getPincode() {
    return pincode;
  }

  public
  Integer getLat() {
    return lat;
  }

  public
  Integer getLongitude() {
    return longitude;
  }

  public
  String getFrom() {
    return from;
  }

  public
  String getTo() {
    return to;
  }

  public
  String getFee_type() {
    return fee_type;
  }
}
