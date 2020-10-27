package kr.osam.phonevar.domain;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModelProperty;

public class Message {
    private Notification notification;
    @ApiModelProperty(hidden = true)
    private String token;
    private String topic;

    private Message(MessageBuilder builder) {
        this.notification = builder.notification;
        this.token = builder.token;
        this.topic = builder.topic;
    }

    private Message() {
    }

    public Notification getNotification() {
        return notification;
    }

    @JsonSetter
    private void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getToken() {
        return token;
    }

    @JsonSetter
    private void setToken(String token) {
        this.token = token;
    }

    public String getTopic() {
        return topic;
    }

    @JsonSetter
    private void setTopic(String topic) {
        this.topic = topic;
    }

    public static class MessageBuilder {
        private final Notification notification;
        private String token;
        private String topic;

        public MessageBuilder(Notification notification) {
            this.notification = notification;
        }

        public MessageBuilder token(String token) {
            this.token = token;
            return this;
        }

        public MessageBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}