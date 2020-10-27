package kr.osam.phonevar.controller;

import io.swagger.annotations.*;
import kr.osam.phonevar.domain.*;
import kr.osam.phonevar.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MainController {
    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @ApiOperation(value = "특정 유저의 로깅 기록 불러오기")
    @RequestMapping(value = "/user/{id}/log", method = RequestMethod.GET)
    public UserLogList getUserLog(@PathVariable Integer id) {

        return mainService.getUserLogList(id);
    }

    @ApiOperation(value = "기기 등록")
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public User registerDevice(@RequestBody UserMinified userMinified) {

        return mainService.registerDevice(userMinified);
    }

    @ApiOperation(value = "특정 유저의 정보 변경하기")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) {

        return mainService.updateUser(user);
    }

    @ApiOperation(value = "특정 부대의 간부들 또는 한 명에게 푸시 알림 전송")
    @RequestMapping(value = "/fcm", method = RequestMethod.POST)
    public String sendFcmMessage(@RequestBody Message message) throws IOException {

        return mainService.sendFcmMessage(message);
    }

    @ApiOperation(value = "부대 목록 반환")
    @RequestMapping(value = "/unit/list", method = RequestMethod.GET)
    public List<UnitInfo> getUnitList() {

        return mainService.getUnitList();
    }

    @ApiOperation(value = "부대 이름으로 고유 코드 가져오기")
    @RequestMapping(value = "/unit", method = RequestMethod.GET)
    public UnitInfo getUnitInfoByName(@RequestParam("name") String unitName) {

        return mainService.getUnitInfoByName(unitName);
    }

    @ApiOperation(value = "로그 전송")
    @RequestMapping(value = "/user/log", method = RequestMethod.POST)
    public HashMap<String, Object> createUserLog(@RequestHeader(value = "Authorization") String authorization, @RequestBody UserLogList userLogList) throws IOException {

        return mainService.createUserLogs(authorization, userLogList);
    }
}
