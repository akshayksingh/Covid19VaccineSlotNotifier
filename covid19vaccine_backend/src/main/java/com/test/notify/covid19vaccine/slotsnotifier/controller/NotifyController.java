package com.test.notify.covid19vaccine.slotsnotifier.controller;

import com.test.notify.covid19vaccine.slotsnotifier.entity.InputParameter;
import com.test.notify.covid19vaccine.slotsnotifier.service.InputParameterService;
import com.test.notify.covid19vaccine.slotsnotifier.service.NotifySlotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public
class NotifyController {

  @Autowired
  NotifySlotsService notifySlotsService;

  @Autowired
  InputParameterService inputParameterService;

  @GetMapping(path = "/")
  public
  ResponseEntity<String> welcome() {
    return new ResponseEntity<>("Welcome to Notifier of Covid Vaccine slots", HttpStatus.OK);
  }

  @PostMapping(path = "/notify")
  public
  ResponseEntity<String> notifyMe(@RequestBody InputParameter ipe) {
    if (null != ipe) {
      boolean alreadyExist = inputParameterService.doesInputsExist(ipe);
      if (!alreadyExist) {
        inputParameterService.createNewInput(ipe);
      } else {
        return new ResponseEntity<>("You have already subscribed on this", HttpStatus.CONFLICT);
      }

      CompletableFuture<String> res =
          notifySlotsService.getResult(ipe.getEmail(), ipe.getPinCode(), ipe.getAge());
      CompletableFuture.allOf(res);
      return new ResponseEntity<>("Notification Registered for " + ipe.getEmail(),
          HttpStatus.ACCEPTED);
    }
    return new ResponseEntity<>("Request is not valid", HttpStatus.BAD_REQUEST);
  }
}
