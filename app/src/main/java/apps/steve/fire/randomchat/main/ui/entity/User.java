package apps.steve.fire.randomchat.main.ui.entity;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import org.parceler.Parcel;

import apps.steve.fire.randomchat.intro.entity.AvatarUi;

/**
 * Created by Steve on 30/11/2017.
 */
@Parcel
public class User {
    String id;
    String name;
    String gender;
    @DrawableRes
    int genderDrawable;
    String location;
    String description;
    String avatar;
    @DrawableRes
    int avatarDrawable;
    long coins;
    long postCount;
    long commentCount;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReadableName(Resources res) {
        //if the name is by-default the avatar id, get and convert into a readeable name
        int resName = 0;
        if (TextUtils.isEmpty(name) && !TextUtils.isEmpty(avatar)) {
            resName = new AvatarUi(avatar).getNameId();
            name = res.getString(resName);
        }
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
