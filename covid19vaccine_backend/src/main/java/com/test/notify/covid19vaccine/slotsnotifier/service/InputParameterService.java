package com.test.notify.covid19vaccine.slotsnotifier.service;

import com.test.notify.covid19vaccine.slotsnotifier.repository.InputRepository;
import com.test.notify.covid19vaccine.slotsnotifier.entity.InputParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public
class InputParameterService {

  @Autowired
  private InputRepository inputRepository;

  public
  List<InputParameter> getAllInputs() {
    try {
      return inputRepository.findAll();
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  public
  InputParameter createNewInput(InputParameter ip) {
    try {
      return inputRepository.save(ip);
    } catch (DataIntegrityViolationException e) {
      throw null;
    }
  }

  public
  boolean doesInputsExist(InputParameter ipe) {
    InputParameter inputParameter =
        inputRepository.findByEmailAndPinCodeAndAge(ipe.getEmail(), ipe.getPinCode(), ipe.getAge());
    if (null != inputParameter) {
      return true;
    }
    return false;
  }
}
