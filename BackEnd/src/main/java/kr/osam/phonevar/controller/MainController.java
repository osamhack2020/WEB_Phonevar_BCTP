package kr.osam.phonevar.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.osam.phonevar.domain.UserLog;
import kr.osam.phonevar.service.MainService;

@RestController
@RequestMapping(value = "/api")
public class MainController {
    @Autowired
    private MainService mainService;
    
    @RequestMapping(value = "/users/log", method = RequestMethod.GET)
      public List<UserLog> getUsersLog() {
          
          return mainService.getUsersLog();
      }
    
//     @RequestMapping(value = "/users/log", method = RequestMethod.GET)
//       public UserLog test() {
//           return mainService.getUserLog();
//       }
    
//     @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
//       public UserLog test() {
//           return mainService.getUserLog();
//       }
}
