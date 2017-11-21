package apps.steve.fire.randomchat.intro;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public class IntroPresenterImpl implements IntroPresenter {

    private static final String TAG = IntroPresenterImpl.class.getSimpleName();
    private IntroView view;
    private Resources resources;

    public IntroPresenterImpl(Resources resources) {
        this.resources = resources;
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
    int genderString = 0;
    private @DrawableRes
    int genderDrawable;

    private void onGenderSlideFocus() {
        switch (genderString) {
            default:
                setGenderMan();
                break;
            case STRING_GENDER_WOMAN:
                setGenderWoman();
                break;
            case STRING_GENDER_NON_BINARY:
                setGenderNonbinary();
                break;
        }
        showGender();
    }

    private void setGenderMan() {
        genderString = STRING_GENDER_MAN;
        genderDrawable = DRAWABLE_GENDER_MAN;
    }

    private void setGenderWoman() {
        genderString = STRING_GENDER_WOMAN;
        genderDrawable = DRAWABLE_GENDER_WOMAN;
    }

    private void setGenderNonbinary() {
        genderString = STRING_GENDER_NON_BINARY;
        genderDrawable = DRAWABLE_GENDER_NONBINARY;
    }

    private void showGender() {
        if (view != null) {
            view.showGenderImg(genderDrawable);
            view.showGenderImgDescr(resources.getString(genderString));
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
            case STRING_GENDER_WOMAN:
                setGenderNonbinary();
                break;
            case STRING_GENDER_NON_BINARY:
                setGenderMan();
                break;
        }
        showGender();
        setAvatarList();
    }

    private AvatarUi avatarOldSelected;

    @Override
    public void onAvatarSelected(AvatarUi avatarActualSelected) {
        avatarActualSelected.setSelected(true);
        if (view != null) {
            if (avatarOldSelected == null) {
                view.updateAvatar(avatarActualSelected);
                avatarOldSelected = avatarActualSelected;
            } else {
                if (avatarOldSelected.equals(avatarActualSelected)) return;
                avatarOldSelected.setSelected(false);
                view.toggleAvatars(avatarOldSelected, avatarActualSelected);
                avatarOldSelected = avatarActualSelected;
            }
        }
    }

    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

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
            case STRING_GENDER_WOMAN:
                setWomanAvatars();
                break;
            case STRING_GENDER_NON_BINARY:
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
}
