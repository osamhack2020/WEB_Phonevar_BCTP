package kr.osam.phonevar.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import kr.osam.phonevar.domain.*;

public interface MainService {
    UserLogList getUserLogList(Integer id);

    String sendFcmMessage(Message message) throws IOException;

    UnitInfo getUnitInfoByName(String unitName);

    User registerDevice(UserMinified userMinified);

    User updateUser(User user);

    HashMap<String, Object> createUserLogs(String authorization, UserLogList userLogList) throws IOException;

    List<UnitInfo> getUnitList();
}
