package apps.steve.fire.randomchat.intro.entity;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import apps.steve.fire.randomchat.R;

/**
 * Created by Steve on 15/11/2017.
 */

public class AvatarUi {
    private String name;
    private @DrawableRes
    int imgDrawable;
    boolean isSelected;

    public AvatarUi(String name, int imgDrawable) {
        this.name = name;
        this.imgDrawable = imgDrawable;
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

    public static List<AvatarUi> getAvatarList() {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        return avatarUiList;
    }

    public static List<AvatarUi> getAvatarNonBinaryList(Resources res) {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi(res.getString(R.string.nb_young), R.drawable.nb_young));
        avatarUiList.add(new AvatarUi(res.getString(R.string.nb_samurai), R.drawable.nb_samurai));
        return avatarUiList;
    }

    public static List<AvatarUi> getAvatarManList(Resources res) {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi(res.getString(R.string.boy_knight), R.drawable.boy_knight));
        avatarUiList.add(new AvatarUi(res.getString(R.string.boy_japanese), R.drawable.boy_japanese));
        avatarUiList.add(new AvatarUi(res.getString(R.string.boy_casual), R.drawable.boy_casual));
        avatarUiList.add(new AvatarUi(res.getString(R.string.boy_napoleon), R.drawable.boy_napoleon));
        return avatarUiList;
    }

    public static List<AvatarUi> getAvatarWomanList(Resources res) {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi(res.getString(R.string.girl_casual), R.drawable.girl_casual));
        avatarUiList.add(new AvatarUi(res.getString(R.string.girl_cat), R.drawable.girl_cat));
        avatarUiList.add(new AvatarUi(res.getString(R.string.girl_cute), R.drawable.girl_cute));
        avatarUiList.add(new AvatarUi(res.getString(R.string.girl_dark), R.drawable.girl_dark));
        return avatarUiList;
    }

}
