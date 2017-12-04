package apps.steve.fire.randomchat.profile;

import android.support.annotation.DrawableRes;

/**
 * Created by @stevecampos on 4/12/2017.
 */

public interface ProfileView {
    void showName(String name);

    void showAvatar(@DrawableRes int avatarDrawable);

    void showLocation(String location);

    void showCoinCount(long coinCount);

    void showDescription(String description);


}
