package apps.stevecampos.fire.anonymouschat.intro;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import apps.stevecampos.fire.anonymouschat.base.BasePresenter;
import apps.stevecampos.fire.anonymouschat.intro.entity.AvatarUi;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public interface IntroPresenter extends BasePresenter<IntroView> {

    void onSlideChanged(Fragment oldFragment, Fragment newFragment);

    void onNextGender();

    void onAvatarSelected(AvatarUi avatar);

    void onSkipPressed();

    void onDonePressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onSignInWithCredentialComplete(Task<AuthResult> task, FirebaseAuth mAuth);
}
