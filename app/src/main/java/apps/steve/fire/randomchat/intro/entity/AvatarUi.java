package apps.steve.fire.randomchat.intro.entity;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.main.ui.entity.Avatar;

/**
 * Created by Steve on 15/11/2017.
 */

public class AvatarUi {
    private String name;
    private @DrawableRes
    int imgDrawable;
    boolean isSelected;
    private String avatarName;

    public AvatarUi(String name, int imgDrawable, String avatarName) {
        this.name = name;
        this.imgDrawable = imgDrawable;
        this.avatarName = avatarName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }


    public static List<AvatarUi> getAvatarNonBinaryList(Resources res) {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi(res.getString(R.string.nb_young), R.drawable.nb_young, Avatar.NB_SAMURAI));
        avatarUiList.add(new AvatarUi(res.getString(R.string.nb_samurai), R.drawable.nb_samurai, Avatar.NB_SAMURAI));
        return avatarUiList;
    }

    public static List<AvatarUi> getAvatarManList(Resources res) {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi(res.getString(R.string.boy_knight), R.drawable.boy_knight, Avatar.BOY_KNIGHT));
        avatarUiList.add(new AvatarUi(res.getString(R.string.boy_japanese), R.drawable.boy_japanese, Avatar.BOY_JAPANESE));
        avatarUiList.add(new AvatarUi(res.getString(R.string.boy_casual), R.drawable.boy_casual, Avatar.BOY_CASUAL));
        avatarUiList.add(new AvatarUi(res.getString(R.string.boy_napoleon), R.drawable.boy_napoleon, Avatar.BOY_NAPOLEON));
        return avatarUiList;
    }

    public static List<AvatarUi> getAvatarWomanList(Resources res) {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi(res.getString(R.string.girl_casual), R.drawable.girl_casual, Avatar.GIRL_CASUAL));
        avatarUiList.add(new AvatarUi(res.getString(R.string.girl_cat), R.drawable.girl_cat, Avatar.GIRL_CAT));
        avatarUiList.add(new AvatarUi(res.getString(R.string.girl_cute), R.drawable.girl_cute, Avatar.GIRL_CUTE));
        avatarUiList.add(new AvatarUi(res.getString(R.string.girl_dark), R.drawable.girl_dark, Avatar.GIRL_DARK));
        return avatarUiList;
    }

}
