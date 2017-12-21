package apps.steve.fire.randomchat.main.ui.entity;

/**
 * Created by @stevecampos on 11/12/2017.
 */

public class Message {

    private String id;
    private String messageText;
    private User user;
    private long timestamp;
    private String contentType;
    private String mediaUrl;
    private int messageStatus;
    private boolean mainUser;

    public Message() {
    }

    public Message(String id, String messageText, User user, long timestamp, String contentType, String mediaUrl, int messageStatus) {
        this.id = id;
        this.messageText = messageText;
        this.user = user;
        this.timestamp = timestamp;
        this.contentType = contentType;
        this.mediaUrl = mediaUrl;
        this.messageStatus = messageStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", messageText='" + messageText + '\'' +
                ", user=" + user +
                ", timestamp=" + timestamp +
                ", contentType='" + contentType + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", messageStatus=" + messageStatus +
                '}';
    }

    public boolean isMainUser() {
        return mainUser;
    }

    public void setMainUser(boolean mainUser) {
        this.mainUser = mainUser;
    }

    @Override
    public boolean equals(Object obj) {
        boolean success = false;
        if (obj instanceof Message) {
            Message message = (Message) obj;
            if (message.getId() != null && message.getId().equals(id)) {
                success = true;
            }
        }
        return success;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

}
