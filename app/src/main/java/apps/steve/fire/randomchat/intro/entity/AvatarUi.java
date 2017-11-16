package apps.steve.fire.randomchat.intro.entity;

import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

import apps.steve.fire.randomchat.R;

/**
 * Created by Steve on 15/11/2017.
 */

public class AvatarUi {
    private String name;
    private @DrawableRes
    int imgDrawable;

    public AvatarUi(String name, int imgDrawable) {
        this.name = name;
        this.imgDrawable = imgDrawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgDrawable() {
        return imgDrawable;
    }

    public void setImgDrawable(int imgDrawable) {
        this.imgDrawable = imgDrawable;
    }

    public static List<AvatarUi> getAvatarList() {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi("Caballero", R.drawable.boy_knight));
        avatarUiList.add(new AvatarUi("Japonés", R.drawable.boy_japanese));
        avatarUiList.add(new AvatarUi("Casual", R.drawable.boy_casual));
        avatarUiList.add(new AvatarUi("Napoleón", R.drawable.boy_napoleon));
        return avatarUiList;
    }
}
