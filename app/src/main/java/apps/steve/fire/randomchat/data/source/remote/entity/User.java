package apps.steve.fire.randomchat.data.source.remote.entity;

import android.text.TextUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import static apps.steve.fire.randomchat.data.source.remote.firebase.FireUser.PATH_USER;

/**
 * Created by @stevecampos on 22/11/2017.
 */

@IgnoreExtraProperties
public class User {
    public String id;
    public String name;
    public String gender;
    public String description;
    public String avatar;
    public int coins;
    public long postCount;
    public long commentCount;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public long getPostCount() {
        return postCount;
    }

    public void setPostCount(long postCount) {
        this.postCount = postCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(PATH_USER + id + "/id", id);
        if (!TextUtils.isEmpty(name)) {
            result.put(PATH_USER + id + "/name", name);
        }
        if (!TextUtils.isEmpty(gender)) {
            result.put(PATH_USER + id + "/gender", gender);
        }
        if (!TextUtils.isEmpty(description)) {
            result.put(PATH_USER + id + "/description", description);
        }
        if (!TextUtils.isEmpty(avatar)) {
            result.put(PATH_USER + id + "/avatar", avatar);
        }
        //result.put("coins", coins);
        //result.put("postCount", postCount);
        //result.put("commentCount", commentCount);
        return result;
    }
}
