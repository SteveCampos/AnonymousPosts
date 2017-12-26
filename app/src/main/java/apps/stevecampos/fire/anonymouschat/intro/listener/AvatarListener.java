package apps.stevecampos.fire.anonymouschat.intro.listener;

import apps.stevecampos.fire.anonymouschat.intro.entity.AvatarUi;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public interface AvatarListener {
    void onAvatarSelected(AvatarUi avatar);
    void onAvatarSlideResume();
}
