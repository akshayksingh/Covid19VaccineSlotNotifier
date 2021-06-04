package com.test.notify.covid19vaccine.slotsnotifier.repository;

import com.test.notify.covid19vaccine.slotsnotifier.entity.InputParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public
interface InputRepository extends JpaRepository<InputParameter, Long> {
  InputParameter findByEmailAndPinCodeAndAge(String email, String pinCode, int age);
}
