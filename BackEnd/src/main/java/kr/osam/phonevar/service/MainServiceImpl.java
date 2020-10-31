package kr.osam.phonevar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.osam.phonevar.domain.*;
import kr.osam.phonevar.mapper.UnitInfoMapper;
import kr.osam.phonevar.mapper.UserLogMapper;
import kr.osam.phonevar.mapper.UserMapper;
import kr.osam.phonevar.util.FireBaseCloudMessage;
import kr.osam.phonevar.util.JwtFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Service("mainService")
public class MainServiceImpl implements MainService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final String PROJECT_URL = "https://fcm.googleapis.com/v1/projects/phonevar-8799e/messages:send";
    private final UserLogMapper userLogMapper;
    // private final FireBaseCloudMessage fireBaseCloudMessage;
    private final UnitInfoMapper unitInfoMapper;
    private final UserMapper userMapper;
    private final JwtFactory jwtFactory;

    @Autowired
    public MainServiceImpl(UserLogMapper userLogMapper, UnitInfoMapper unitInfoMapper, UserMapper userMapper, JwtFactory jwtFactory) {
        this.userLogMapper = userLogMapper;
        // this.fireBaseCloudMessage = fireBaseCloudMessage;
        this.unitInfoMapper = unitInfoMapper;
        this.userMapper = userMapper;
        this.jwtFactory = jwtFactory;
    }

    @Override
    public UserLogList getUserLogList(Integer id) {

        return new UserLogList(id, userLogMapper.getUserLog(id));
    }

    @Override
    public User registerDevice(UserMinified userMinified) {
        User user = userMapper.getUserByServiceNumberAndName(userMinified);
        user.setToken(jwtFactory.encodeJwtWithUser(user));
        user.update(userMinified);
        userMapper.updateUser(user);

        return user;
    }

    @Override
    public User updateUser(User user) {
        User nowUser = userMapper.getUserById(user.getId());
        nowUser.update(user);
        userMapper.updateUser(nowUser);
        nowUser.setToken(jwtFactory.encodeJwtWithUser(nowUser));

        return nowUser;
    }

    @Override
    public HashMap<String, Object> createUserLogs(String authorization, UserLogList userLogList) throws IOException {
        // 토큰 검증
        if (!authorization.startsWith("Bearer ")) {
            return new HashMap<String, Object> () {{
                put("success", false);
            }};
        }

        String jwtToken = authorization.substring(7);
        UserMinified userMinified = jwtFactory.decodeJwt(jwtToken);
        User user = userMapper.getUserByServiceNumberAndName(userMinified);

        if (!userLogList.getUserId().equals(user.getId())) {
            return new HashMap<String, Object> () {{
                put("success", false);
            }};
        }
        
        // 로그 생성
        List<UserLog> userLogs = userLogList.getUserLogs();
        
        for (UserLog userLog : userLogs) {
            userLog.setUserId(userLogList.getUserId());
            userLogMapper.createUserLog(userLog);
        }

        // 로그가 있었다면 상태 코드를 위험으로 변경
        // 간부들에게 푸시 알림 전송
        if (userLogs.size() > 0) {
            user.setStatusCode(400);
            userMapper.updateUser(user);

            Integer unitInfo = unitInfoMapper.getUnitCodeById(user.getUnitId());
            sendFcmMessage(new Message.MessageBuilder(
                    new Notification.NotificationBuilder(
                            "부정행위 감지",
                            user.getOrganization() + " 소속 " + user.getName() + "의 부정행위가 감지되었습니다."
                    ).build()
            ).topic(unitInfo.toString()).build());
        }

        return new HashMap<String, Object> () {{
            put("success", true);
        }};
    }

    @Override
    public List<UnitInfo> getUnitList() {

        return unitInfoMapper.getUnitList();
    }

    @Override
    public String sendFcmMessage(Message message) throws IOException {
        FireBaseCloudMessage fireBaseCloudMessage = new FireBaseCloudMessage();
        Request request = new Request.Builder()
                .url(PROJECT_URL)
                .post(RequestBody.create(objectMapper.writeValueAsString(makeFcmMessage(message)), okhttp3.MediaType.parse("application/json; charset=utf-8")))
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + fireBaseCloudMessage.getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                .build();

        return Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).string();
    }

    private FcmMessage makeFcmMessage(Message message) throws JsonProcessingException {

        return new FcmMessage.FcmMessageBuilder(
                new Message.MessageBuilder(
                        new Notification.NotificationBuilder(
                                message.getNotification().getTitle(),
                                message.getNotification().getBody()
                        ).build()
                ).topic(message.getTopic()).build()
        ).build();
    }

    private FcmMessage makeFcmMessage(String title, String body, String topic) throws JsonProcessingException {

        return new FcmMessage.FcmMessageBuilder(
                new Message.MessageBuilder(
                        new Notification.NotificationBuilder(
                                title,
                                body
                        ).build()
                ).topic(topic).build()
        ).build();
    }

    @Override
    public UnitInfo getUnitInfoByName(String unitName) {

        return unitInfoMapper.getUnitInfoByName(unitName);
    }
	
    @Override
    public List<User> getUserList(Integer unitCode) {
        List<User> userList = null;
        if (unitCode == null) {
            userList = userMapper.getUserList();
        }
        else {
            Integer unitId = unitInfoMapper.getIdByUnitCode(unitCode);
            userList = userMapper.getUserListByUnitId(unitId);
        }
        return userList;
    }
}
