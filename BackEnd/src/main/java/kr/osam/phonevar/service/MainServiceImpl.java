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
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Service("mainService")
public class MainServiceImpl implements MainService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final String PROJECT_URL = "https://fcm.googleapis.com/v1/projects/phonevar-8799e/messages:send";
    private final UserLogMapper userLogMapper;
    private final FireBaseCloudMessage fireBaseCloudMessage;
    private final UnitInfoMapper unitInfoMapper;
    private final UserMapper userMapper;
    private final JwtFactory jwtFactory;

    @Autowired
    public MainServiceImpl(UserLogMapper userLogMapper, FireBaseCloudMessage fireBaseCloudMessage, UnitInfoMapper unitInfoMapper, UserMapper userMapper, JwtFactory jwtFactory) {
        this.userLogMapper = userLogMapper;
        this.fireBaseCloudMessage = fireBaseCloudMessage;
        this.unitInfoMapper = unitInfoMapper;
        this.userMapper = userMapper;
        this.jwtFactory = jwtFactory;
    }

    @Override
    public List<UserLog> getUserLog(Integer id) {

        return userLogMapper.getUserLog(id);
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

        return nowUser;
    }

    @Override
    public HashMap<String, Object> createUserLog(HashMap<String, Object> data) {
        // ToDo: 로그 전송받으면 검증 및 기록하는 기능 추가 + 토큰 검증
        return null;
    }

    @Override
    public List<UnitInfo> getUnitList() {

        return unitInfoMapper.getUnitList();
    }

    @Override
    public String sendFcmMessage(Message message) throws IOException {
        Request request = new Request.Builder()
                .url(PROJECT_URL)
                .post(RequestBody.create(makeFcmMessage(message), okhttp3.MediaType.parse("application/json; charset=utf-8")))
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + fireBaseCloudMessage.getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                .build();

        return Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).string();
    }

    private String makeFcmMessage(Message message) throws JsonProcessingException {
        FcmMessage fcmMessage = new FcmMessage.FcmMessageBuilder(
                new Message.MessageBuilder(
                        new Notification.NotificationBuilder(
                                message.getNotification().getTitle(),
                                message.getNotification().getBody()
                        ).build()
                ).topic(message.getTopic()).build()
        ).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    @Override
    public UnitInfo getUnitInfoByName(String unitName) {

        return unitInfoMapper.getUnitInfoByName(unitName);
    }


}
