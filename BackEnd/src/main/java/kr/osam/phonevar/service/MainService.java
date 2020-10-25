package kr.osam.phonevar.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import kr.osam.phonevar.domain.UnitInfo;
import kr.osam.phonevar.domain.User;
import kr.osam.phonevar.domain.UserLog;
import kr.osam.phonevar.domain.UserMinified;

public interface MainService {
    List<UserLog> getUserLog(Integer id);

    String sendFcmMessage(String targetToken) throws IOException;

    UnitInfo getUnitInfoByName(String unitName);

    User registerDevice(UserMinified userMinified);

    User updateUser(User user);

    HashMap<String, Object> createUserLog(HashMap<String, Object> data);
}
