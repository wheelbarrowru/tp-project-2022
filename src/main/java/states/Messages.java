package states;

public enum Messages {
    ACTIVE("is active"),
    CANCELED("has been canceled"),
    PROCESS("is in process"),
    REVIEW("is ready for review"),
    FINISHED("is finished"),
    APPROVED("your application has been approved"),
    IMPROVEMENT("needs improvement"),
    ACCEPTED("work accepted");

    private final String message;
    Messages(String message) {
        this.message=message;
    }

    public String getString() {
        return message;
    }
}