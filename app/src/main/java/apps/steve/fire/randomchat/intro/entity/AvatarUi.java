package apps.steve.fire.randomchat.intro.entity;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.main.ui.entity.Avatar;

import static apps.steve.fire.randomchat.main.ui.entity.Avatar.*;

/**
 * Created by Steve on 15/11/2017.
 */

public class AvatarUi {
    private String avatarId;
    private @DrawableRes
    int avatarDrawable;
    boolean isSelected;
    private @StringRes int nameId;
    private String avatarName;

    public AvatarUi(String avatarId) {
        this.avatarId = avatarId;
        switch (avatarId) {
            case GIRL_CASUAL:
                avatarDrawable = R.drawable.girl_casual;
                nameId = R.string.girl_casual;
                break;
            case GIRL_CAT:
                avatarDrawable = R.drawable.girl_cat;
                nameId = R.string.girl_cat;
                break;
            case GIRL_CUTE:
                avatarDrawable = R.drawable.girl_cute;
                nameId = R.string.girl_cute;
                break;
            case GIRL_DARK:
                avatarDrawable = R.drawable.girl_dark;
                nameId = R.string.girl_dark;
                break;
            case BOY_KNIGHT:
                avatarDrawable = R.drawable.boy_knight;
                nameId = R.string.boy_knight;
                break;
            case BOY_JAPANESE:
                avatarDrawable = R.drawable.boy_japanese;
                nameId = R.string.boy_japanese;
                break;
            case BOY_CASUAL:
                avatarDrawable = R.drawable.boy_casual;
                nameId = R.string.boy_casual;
                break;
            case BOY_NAPOLEON:
                avatarDrawable = R.drawable.boy_napoleon;
                nameId = R.string.boy_napoleon;
                break;
            case NB_YOUNG:
                avatarDrawable = R.drawable.nb_young;
                nameId = R.string.nb_young;
                break;
            case NB_SAMURAI:
                avatarDrawable = R.drawable.nb_samurai;
                nameId = R.string.nb_samurai;
                break;
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public int getAvatarDrawable() {
        return avatarDrawable;
    }

    public void setAvatarDrawable(int avatarDrawable) {
        this.avatarDrawable = avatarDrawable;
    }

    public String getAvatarName(Resources res) {
        return res.getString(nameId);
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public static List<AvatarUi> getAvatarNonBinaryList(Resources res) {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi(Avatar.NB_YOUNG));
        avatarUiList.add(new AvatarUi(Avatar.NB_SAMURAI));
        return avatarUiList;
    }

    public static List<AvatarUi> getAvatarManList(Resources res) {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi(Avatar.BOY_KNIGHT));
        avatarUiList.add(new AvatarUi(Avatar.BOY_JAPANESE));
        avatarUiList.add(new AvatarUi(Avatar.BOY_CASUAL));
        avatarUiList.add(new AvatarUi(Avatar.BOY_NAPOLEON));
        return avatarUiList;
    }

    public static List<AvatarUi> getAvatarWomanList(Resources res) {
        List<AvatarUi> avatarUiList = new ArrayList<>();
        avatarUiList.add(new AvatarUi(Avatar.GIRL_CASUAL));
        avatarUiList.add(new AvatarUi(Avatar.GIRL_CAT));
        avatarUiList.add(new AvatarUi(Avatar.GIRL_CUTE));
        avatarUiList.add(new AvatarUi(Avatar.GIRL_DARK));
        return avatarUiList;
    }

}
