package apps.steve.fire.randomchat.intro;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.intro.listener.AvatarListener;
import apps.steve.fire.randomchat.intro.listener.GenderListener;

/**
 * Created by Steve on 11/11/2017.
 */

public class IntroActivity extends AppIntro2 implements IntroView, GenderListener, AvatarListener {

    private SliderPage newSlider(@StringRes int title,
                                 @StringRes int description,
                                 @DrawableRes int image,
                                 @ColorRes int bgColor,
                                 @ColorRes int titleColor,
                                 @ColorRes int descColor) {
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle(getString(title));
        sliderPage.setImageDrawable(image);
        sliderPage.setDescription(getString(description));
        int bgColorResolved = ContextCompat.getColor(IntroActivity.this, bgColor);
        int titleColorResolved = ContextCompat.getColor(IntroActivity.this, titleColor);
        int descColorResolved = ContextCompat.getColor(IntroActivity.this, descColor);
        sliderPage.setBgColor(bgColorResolved);
        sliderPage.setTitleColor(titleColorResolved);
        sliderPage.setDescColor(descColorResolved);
        return sliderPage;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SliderPage slideWelcome = newSlider(
                R.string.intro_slidewelcome_title,
                R.string.intro_slidewelcome_description,
                R.drawable.ic_tinder,
                android.R.color.white,
                R.color.md_blue_grey_900,
                R.color.md_blue_grey_300
        );
        SliderPage slideAnonymousProfile = newSlider(
                R.string.intro_slideanonymous_title,
                R.string.intro_slideanonymous_description,
                R.drawable.ic_incognito,
                android.R.color.white,
                R.color.md_blue_grey_900,
                R.color.md_blue_grey_300
        );

        SliderPage slidePost = newSlider(
                R.string.intro_slidepost_title,
                R.string.intro_slidepost_description,
                R.drawable.ic_post_image_minimal,
                android.R.color.white,
                R.color.md_blue_grey_900,
                R.color.md_blue_grey_300
        );
        SliderPage slideMessage = newSlider(
                R.string.intro_slideconversation_title,
                R.string.intro_slideconversation_description,
                R.drawable.ic_send_message_2,
                android.R.color.white,
                R.color.md_blue_grey_900,
                R.color.md_blue_grey_300
        );
        SliderPage slideHahstag = newSlider(
                R.string.intro_slidehashtags_title,
                R.string.intro_slidehashtags_description,
                R.drawable.ic_hashtag_1,
                android.R.color.white,
                R.color.md_blue_grey_900,
                R.color.md_blue_grey_300
        );


        addSlide(AppIntroFragment.newInstance(slideWelcome));
        addSlide(AppIntroFragment.newInstance(slideAnonymousProfile));
        addSlide(AppIntroFragment.newInstance(slidePost));
        addSlide(AppIntroFragment.newInstance(slideHahstag));
        addSlide(AppIntroFragment.newInstance(slideMessage));
        addSlide(GenderSlideFragment.newInstance());
        addSlide(AvatarSlideFragment.newInstance());
        init();


        /*
        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(firstFragment);
        addSlide(secondFragment);
        addSlide(thirdFragment);
        addSlide(fourthFragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);*/
    }

    private IntroPresenter presenter;

    public void init() {
        presenter = (IntroPresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new IntroPresenterImpl(getResources());
        }
        setPresenter(presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
        //presenter.onSlideChanged(oldFragment, newFragment);
    }

    @Override
    public void showStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public @Nullable
    GenderSlideFragment getGenderSlide() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment :
                fragments) {
            if (fragment instanceof GenderSlideFragment) {
                return (GenderSlideFragment) fragment;
            }
        }
        return null;
    }

    public @Nullable
    AvatarSlideFragment getAvatarSlide() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment :
                fragments) {
            if (fragment instanceof AvatarSlideFragment) {
                return (AvatarSlideFragment) fragment;
            }
        }
        return null;
    }


    @Override
    public void showGenderImg(int drawable) {
        GenderSlideFragment genderSlide = getGenderSlide();
        if (genderSlide != null) {
            genderSlide.setImg(drawable);
        }
    }

    @Override
    public void showGenderImgDescr(CharSequence description) {
        GenderSlideFragment genderSlide = getGenderSlide();
        if (genderSlide != null) {
            genderSlide.setImgDescription(description);
        }
    }

    @Override
    public void updateAvatar(AvatarUi avatar) {
        AvatarSlideFragment avatarSlide = getAvatarSlide();
        if (avatarSlide != null) {
            avatarSlide.updateAvatar(avatar);
        }
    }

    @Override
    public void toggleAvatars(AvatarUi old, AvatarUi actual) {
        AvatarSlideFragment avatarSlide = getAvatarSlide();
        if (avatarSlide != null) {
            avatarSlide.toggleAvatars(old, actual);
        }
    }

    @Override
    public void showAvatarList(List<AvatarUi> avatarList) {
        AvatarSlideFragment avatarSlide = getAvatarSlide();
        if (avatarSlide != null) {
            avatarSlide.showAvatarList(avatarList);
        }
    }

    @Override
    public void setPresenter(IntroPresenter presenter) {
        presenter.attachView(this);
        presenter.onCreate();
    }

    @Override
    public void onNextGender() {
        presenter.onNextGender();
    }

    @Override
    public void onGenderSlideResume() {
        presenter.onSlideChanged(null, getGenderSlide());
    }

    @Override
    public void onAvatarSelected(AvatarUi avatar) {
        presenter.onAvatarSelected(avatar);
    }

    @Override
    public void onAvatarSlideResume() {
        presenter.onSlideChanged(null, getAvatarSlide());
    }
}
