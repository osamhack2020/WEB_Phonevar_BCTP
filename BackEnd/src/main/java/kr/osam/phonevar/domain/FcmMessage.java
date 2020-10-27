package kr.osam.phonevar.domain;

public class FcmMessage {
    private final Boolean validateOnly;
    private final Message message;

    private FcmMessage(FcmMessageBuilder builder) {
        this.validateOnly = builder.validateOnly;
        this.message = builder.message;
    }

    public Boolean getValidateOnly() {
        return validateOnly;
    }

    public Message getMessage() {
        return message;
    }

    public static class FcmMessageBuilder {
        private final Message message;
        private Boolean validateOnly = false;

        public FcmMessageBuilder(Message message) {
            this.message = message;
        }

        public FcmMessageBuilder validateOnly(Boolean validateOnly) {
            this.validateOnly = validateOnly;
            return this;
        }

        public FcmMessage build() {
            return new FcmMessage(this);
        }
    }
}
