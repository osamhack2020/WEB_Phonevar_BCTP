package kr.osam.phonevar.domain;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class UserLogList {
    private Integer userId;
    private List<UserLog> userLogs;

    public UserLogList() {
    }

    public UserLogList(Integer userId, List<UserLog> userLogs) {
        this.userId = userId;
        this.userLogs = userLogs;
    }

    public Integer getUserId() {
        return userId;
    }

    @JsonSetter
    private void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<UserLog> getUserLogs() {
        return userLogs;
    }

    @JsonSetter
    private void setUserLogs(List<UserLog> userLogs) {
        this.userLogs = userLogs;
    }
}
