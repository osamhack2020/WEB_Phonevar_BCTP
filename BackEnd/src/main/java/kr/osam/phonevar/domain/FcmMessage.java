package kr.osam.phonevar.domain;

public class FcmMessage {
    private Boolean validateOnly;
    private Message message;

    public Boolean getValidateOnly() {
        return this.validateOnly;
    }

    public void setValidateOnly(Boolean validateOnly) {
        this.validateOnly = validateOnly;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static class Message {
        private Notification notification;
        private String token;
        private String topic;

        public Notification getNotification() {
            return notification;
        }

        public void setNotification(Notification notification) {
            this.notification = notification;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }
    }

    public static class Notification {
        private String title;
        private String body;
        private String image;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
