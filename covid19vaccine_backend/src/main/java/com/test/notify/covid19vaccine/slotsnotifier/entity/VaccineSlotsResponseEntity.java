package com.test.notify.covid19vaccine.slotsnotifier.entity;


import java.io.Serializable;

public
class VaccineSlotsResponseEntity implements Serializable {

  private CentersEntity[] centers;

  public
  CentersEntity[] getCenters() {
    return centers;
  }
}

