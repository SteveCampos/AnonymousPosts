package apps.steve.fire.randomchat.intro;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.base.usecase.UseCaseHandler;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.intro.usecase.UpdateUser;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public class IntroPresenterImpl implements IntroPresenter {

    private static final String TAG = IntroPresenterImpl.class.getSimpleName();
    private IntroView view;
    private Resources resources;
    private UseCaseHandler useCaseHandler;
    private UpdateUser useCaseUpdateUser;

    IntroPresenterImpl(Resources resources, UseCaseHandler useCaseHandler, UpdateUser useCaseUpdateUser) {
        this.resources = resources;
        this.useCaseHandler = useCaseHandler;
        this.useCaseUpdateUser = useCaseUpdateUser;
    }

    @Override
    public void attachView(IntroView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        view = null;
    }

    private static final String GENDER_MAN = "man";
    private static final String GENDER_WOMAN = "woman";
    private static final String GENDER_NON_BINARY = "non_binary";

    private static final @DrawableRes
    int DRAWABLE_GENDER_MAN = R.drawable.boy_casual;
    private static final @DrawableRes
    int DRAWABLE_GENDER_WOMAN = R.drawable.gir_hipster;
    private static final @DrawableRes
    int DRAWABLE_GENDER_NONBINARY = R.drawable.nb_young;
    private static final @StringRes
    int STRING_GENDER_MAN = R.string.global_gender_man;
    private static final @StringRes
    int STRING_GENDER_WOMAN = R.string.global_gender_woman;
    private static final @StringRes
    int STRING_GENDER_NON_BINARY = R.string.global_gender_nonbinary;


    private @StringRes
    int genderResString = 0;
    private @DrawableRes
    int genderDrawable;
    private String genderString = GENDER_MAN;

    private void onGenderSlideFocus() {
        switch (genderString) {
            default:
                setGenderMan();
                break;
            case GENDER_WOMAN:
                setGenderWoman();
                break;
            case GENDER_NON_BINARY:
                setGenderNonbinary();
                break;
        }
        showGender();
    }

    private void setGenderMan() {
        genderResString = STRING_GENDER_MAN;
        genderDrawable = DRAWABLE_GENDER_MAN;
    }

    private void setGenderWoman() {
        genderResString = STRING_GENDER_WOMAN;
        genderDrawable = DRAWABLE_GENDER_WOMAN;
    }

    private void setGenderNonbinary() {
        genderResString = STRING_GENDER_NON_BINARY;
        genderDrawable = DRAWABLE_GENDER_NONBINARY;
    }

    private void showGender() {
        if (view != null) {
            view.showGenderImg(genderDrawable);
            view.showGenderImgDescr(resources.getString(genderResString));
        }
    }


    @Override
    public void onSlideChanged(Fragment oldFragment, Fragment newFragment) {
        if (newFragment instanceof GenderSlideFragment) {
            onGenderSlideFocus();
        }
        if (newFragment instanceof AvatarSlideFragment) {
            onAvatarSlideFocus();
        }
    }

    @Override
    public void onNextGender() {
        Log.d(TAG, "onNextGender");
        switch (genderString) {
            default:
                setGenderWoman();
                break;
            case GENDER_WOMAN:
                setGenderNonbinary();
                break;
            case GENDER_NON_BINARY:
                setGenderMan();
                break;
        }
        showGender();
        setAvatarList();
    }

    private AvatarUi avatarSelected;

    @Override
    public void onAvatarSelected(AvatarUi avatar) {
        avatar.setSelected(true);
        if (view != null) {
            if (avatarSelected == null) {
                view.updateAvatar(avatar);
                avatarSelected = avatar;
            } else {
                if (avatarSelected.equals(avatar)) return;
                avatarSelected.setSelected(false);
                view.toggleAvatars(avatarSelected, avatar);
                avatarSelected = avatar;
            }
        }
    }

    @Override
    public void onSkipPressed() {

    }

    @Override
    public void onDonePressed() {
        if (avatarSelected == null) return;

        if (view != null) {
            view.startSignInFlow(RC_SIGN_IN);
        }
    }

    private static final int RC_SIGN_IN = 9001;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                showProgress();
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                showError("Google sign in failed");
                // [START_EXCLUDE]
                //updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }

    @Override
    public void onSignInWithCredentialComplete(Task<AuthResult> task, FirebaseAuth mAuth) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithCredential:success");
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                updateUser(user);
            } else {
                showError("user null!!!");
            }
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.getException());
            showError("signInWithCredential:failure");
        }
    }


    private void showError(CharSequence error) {
        if (view != null) {
            view.hideProgress();
            view.showError(error);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        if (view != null) {
            view.firebaseAuthWithGoogle(acct);
        }
    }

    private List<AvatarUi> avatarUiList = new ArrayList<>();

    private void onAvatarSlideFocus() {
        if (!avatarUiList.isEmpty()) {
            showAvatarList();
            return;
        }
        setAvatarList();
    }

    private void setAvatarList() {
        switch (genderString) {
            default:
                setManAvatars();
                break;
            case GENDER_WOMAN:
                setWomanAvatars();
                break;
            case GENDER_NON_BINARY:
                setNonBinaryAvatars();
                break;
        }
        showAvatarList();
    }

    private void showAvatarList() {
        if (view != null) {
            view.showAvatarList(avatarUiList);
        }
    }

    private void setNonBinaryAvatars() {
        avatarUiList.clear();
        avatarUiList.addAll(AvatarUi.getAvatarNonBinaryList(resources));
    }

    private void setWomanAvatars() {
        avatarUiList.clear();
        avatarUiList.addAll(AvatarUi.getAvatarWomanList(resources));
    }

    private void setManAvatars() {
        avatarUiList.clear();
        avatarUiList.addAll(AvatarUi.getAvatarManList(resources));
    }

    private void updateUser(FirebaseUser user) {
        useCaseHandler.execute(useCaseUpdateUser,
                new UpdateUser.RequestValues(avatarSelected, genderString, user),
                new UseCase.UseCaseCallback<UpdateUser.ResponseValue>() {
                    @Override
                    public void onSuccess(UpdateUser.ResponseValue response) {
                        hideProgress();

                        boolean success = response.isSucess();
                        if (success) {
                            startMain();
                        } else {
                            showError("Login Error! Try Again!");
                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }

    private void showProgress() {
        if (view != null) {
            view.showProgress();
        }
    }

    private void hideProgress() {
        if (view != null) {
            view.hideProgress();
        }
    }


    private void startMain() {
        if (view != null) {
            view.startMain();
        }
    }
}
