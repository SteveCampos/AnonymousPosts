package apps.steve.fire.randomchat.main.ui.entity;

import android.support.annotation.DrawableRes;

/**
 * Created by Steve on 30/11/2017.
 */

public class User {
    private String id;
    private String name;
    private @DrawableRes
    int genderDrawable;
    private String description;
    private @DrawableRes
    int avatarDrawable;
    private long coins;
    private long postCount;
    private long commentCount;

    public User() {
    }

    public User(String id, String name, int genderDrawable, String description, int avatarDrawable, long coins, long postCount, long commentCount) {
        this.id = id;
        this.name = name;
        this.genderDrawable = genderDrawable;
        this.description = description;
        this.avatarDrawable = avatarDrawable;
        this.coins = coins;
        this.postCount = postCount;
        this.commentCount = commentCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGenderDrawable() {
        return genderDrawable;
    }

    public String getDescription() {
        return description;
    }

    public int getAvatarDrawable() {
        return avatarDrawable;
    }

    public long getCoins() {
        return coins;
    }

    public long getPostCount() {
        return postCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenderDrawable(int genderDrawable) {
        this.genderDrawable = genderDrawable;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvatarDrawable(int avatarDrawable) {
        this.avatarDrawable = avatarDrawable;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public void setPostCount(long postCount) {
        this.postCount = postCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
}
