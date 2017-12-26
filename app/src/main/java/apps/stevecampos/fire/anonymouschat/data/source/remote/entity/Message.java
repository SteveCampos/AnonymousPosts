package apps.stevecampos.fire.anonymouschat.data.source.remote.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @stevecampos on 11/12/2017.
 */

@IgnoreExtraProperties
public class Message {
    private String id;
    private String messageText;
    private String userId;
    private long timestamp;
    private String contentType;
    private String mediaUrl;
    private int messageStatus;
    private boolean incommingMessage;

    public Message() {
    }

    public Message(String id, String messageText, long timestamp, String contentType, String mediaUrl, String userId, int messageStatus) {
        this.id = id;
        this.messageText = messageText;
        this.timestamp = timestamp;
        this.contentType = contentType;
        this.mediaUrl = mediaUrl;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    public boolean isIncommingMessage() {
        return incommingMessage;
    }

    public void setIncommingMessage(boolean incommingMessage) {
        this.incommingMessage = incommingMessage;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("messageText", messageText);
        result.put("userId", userId);
        result.put("timestamp", timestamp);
        result.put("contentType", contentType);
        result.put("mediaUrl", mediaUrl);
        result.put("messageStatus", messageStatus);
        return result;
    }
}
