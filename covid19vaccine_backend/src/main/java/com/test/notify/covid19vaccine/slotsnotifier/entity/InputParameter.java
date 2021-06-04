package com.test.notify.covid19vaccine.slotsnotifier.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public
class InputParameter implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String inputId;

  private String email;

  private String pinCode;

  private int age;

  public
  InputParameter() {
  }

  public
  InputParameter(String inputId, String email, String pinCode, int age) {
    this.inputId = inputId;
    this.email = email;
    this.pinCode = pinCode;
    this.age = age;
  }

  @Override
  public
  String toString() {
    return "InputParameter{" +
        "inputId='" +
        inputId +
        '\'' +
        ", email='" +
        email +
        '\'' +
        ", pinCode='" +
        pinCode +
        '\'' +
        ", age=" +
        age +
        '}';
  }

  public
  String getInputId() {
    return inputId;
  }

  public
  String getEmail() {
    return email;
  }

  public
  String getPinCode() {
    return pinCode;
  }

  public
  int getAge() {
    return age;
  }
}
