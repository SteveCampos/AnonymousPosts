package apps.stevecampos.fire.anonymouschat.profile;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCaseHandler;
import apps.stevecampos.fire.anonymouschat.intro.usecase.UpdateUser;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by Steve on 8/12/2017.
 */

public class ProfilePresenterImpl implements ProfilePresenter {
    private static final String TAG = ProfilePresenterImpl.class.getSimpleName();
    private ProfileView view;
    private User user;
    private boolean editable;

    private Resources res;
    private UseCaseHandler handler;
    private UpdateUser useCaseUpdateUser;

    public ProfilePresenterImpl(Resources res, UseCaseHandler handler, UpdateUser useCaseUpdateUser) {
        this.res = res;
        this.handler = handler;
        this.useCaseUpdateUser = useCaseUpdateUser;
    }

    @Override
    public void attachView(ProfileView view) {
        this.view = view;
    }

    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        paintUser();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onAttach() {
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreateView() {
        Log.d(TAG, "onCreateView");
    }

    @Override
    public void onViewCreated() {
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        view = null;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
    }

    @Override
    public void setUser(User user, boolean editable) {
        this.user = user;
        this.editable = editable;
    }

    @Override
    public void onEditNameClicked() {
        showDialogEditName();
    }

    private void showDialogEditName() {
        if (!editable) return;

        if (view != null) {
            view.showDialogEditName();
        }
    }


    @Override
    public void onEditNameSubmit(String name) {
        if (TextUtils.isEmpty(name)) return;

        User userEdited = new User();
        userEdited.setId(user.getId());
        userEdited.setName(name);
        updateUser(userEdited);
    }

    @Override
    public void onEditDescriptionClicked() {
        Log.d(TAG, "onEditDescriptionClicked");
        showDialogEditDescription();
    }

    private void showDialogEditDescription() {
        if (view != null) {
            view.showDialogEditDescription();
        }
    }

    @Override
    public void onEditDescriptionSubmit(String description) {
        Log.d(TAG, "onEditDescriptionSubmit");
        if (TextUtils.isEmpty(description)) return;

        User userEdited = new User();
        userEdited.setId(user.getId());
        userEdited.setDescription(description);
        updateUser(userEdited);
    }

    private void updateUser(User userEdited) {
        Log.d(TAG, "updateUser");
        handler.execute(
                useCaseUpdateUser,
                new UpdateUser.RequestValues(userEdited),
                new UseCase.UseCaseCallback<UpdateUser.ResponseValue>() {
                    @Override
                    public void onSuccess(UpdateUser.ResponseValue response) {
                        Log.d(TAG, "updateUser onSuccess");
                        if (user != null) {
                            user = response.getUser();
                            paintUser();
                        }
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "updateUser onError");
                    }
                }
        );
    }

    private void paintUser() {
        Log.d(TAG, "paintUser");
        if (user == null) return;
        @DrawableRes int avatarDrawable = 0;
        String userName = "";
        String description = user.getDescription();
        String location = user.getLocation();
        long coinsCount = user.getCoins();
        long commentCount = user.getCommentCount();
        long postCount = user.getPostCount();
        if (user != null) {
            avatarDrawable = user.getAvatarDrawable();
            userName = user.getReadableName(res);
        }
        if (TextUtils.isEmpty(description)) {
            description = res.getString(R.string.user_default_description);
        }

        if (TextUtils.isEmpty(location)) {
            location = res.getString(R.string.user_default_location);
        }

        showName(userName);
        showAvatar(avatarDrawable);
        showDescription(description);
        showLocation(location);
        showCoins(coinsCount);
    }

    private void showCoins(long coinsCount) {
        if (view != null) {
            view.showCoinCount(coinsCount);
        }
    }

    private void showLocation(String location) {
        if (view != null) {
            view.showLocation(location, editable);
        }
    }

    private void showDescription(String description) {
        if (view != null) {
            view.showDescription(description, editable);
        }
    }

    private void showAvatar(@DrawableRes int avatarDrawable) {
        if (view != null) {
            view.showAvatar(avatarDrawable, editable);
        }
    }

    private void showName(String userName) {
        if (view != null) {
            view.showName(userName, editable);
        }
    }

}
