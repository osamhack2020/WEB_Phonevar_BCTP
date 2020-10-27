package kr.osam.phonevar.domain;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Notification {
    private String title;
    private String body;
    private String image;

    private Notification(NotificationBuilder builder) {
        this.title = builder.title;
        this.body = builder.body;
        this.image = builder.image;
    }

    private Notification() {
    }

    public String getTitle() {
        return title;
    }

    @JsonSetter
    private void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    @JsonSetter
    private void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    @JsonSetter
    private void setImage(String image) {
        this.image = image;
    }

    public static class NotificationBuilder {
        private final String title;
        private final String body;
        private String image;

        public NotificationBuilder(String title, String body) {
            this.title = title;
            this.body = body;
        }

        public NotificationBuilder image(String image) {
            this.image = image;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}