package apps.steve.fire.randomchat.main.ui.entity;

import java.util.List;

/**
 * Created by Steve on 26/11/2017.
 */

public class Post {
    private String id;
    private List<String> hashtags;
    private String content;

    public Post(String id, List<String> hashtags, String content) {
        this.id = id;
        this.hashtags = hashtags;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
