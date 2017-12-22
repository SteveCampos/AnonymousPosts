package apps.steve.fire.randomchat.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeTransform;
import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import apps.steve.fire.randomchat.BuildConfig;
import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.appinfo.AppinfoFragment;
import apps.steve.fire.randomchat.base.navigation.BackStrategy;
import apps.steve.fire.randomchat.base.navigation.Navigator;
import apps.steve.fire.randomchat.base.usecase.UseCaseHandler;
import apps.steve.fire.randomchat.base.usecase.UseCaseThreadPoolScheduler;
import apps.steve.fire.randomchat.chat.ChatActivity;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.data.source.local.UserLocalDataSource;
import apps.steve.fire.randomchat.data.source.remote.UserRemoteDataSource;
import apps.steve.fire.randomchat.data.source.remote.firebase.FireUser;
import apps.steve.fire.randomchat.intro.IntroActivity;
import apps.steve.fire.randomchat.intro.listener.GenderListener;
import apps.steve.fire.randomchat.main.adapter.ItemAdapter;
import apps.steve.fire.randomchat.main.listener.ItemListener;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Item;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;
import apps.steve.fire.randomchat.main.usecase.UpdateUserCoins;
import apps.steve.fire.randomchat.main.usecase.GetUser;
import apps.steve.fire.randomchat.main.usecase.PublishPost;
import apps.steve.fire.randomchat.messages.MessagesFragment;
import apps.steve.fire.randomchat.messages.listener.MessagesListener;
import apps.steve.fire.randomchat.postDetail.PostDetailFragment;
import apps.steve.fire.randomchat.postDetail.PostDetailListener;
import apps.steve.fire.randomchat.postpager.PostPagerFragment;
import apps.steve.fire.randomchat.posts.PostListener;
import apps.steve.fire.randomchat.posts.PostsFragment;
import apps.steve.fire.randomchat.profile.ProfileFragment;
import apps.steve.fire.randomchat.profile.ProfileListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.originqiu.library.EditTag;

import static android.view.Gravity.TOP;

public class MainActivity extends AppCompatActivity implements GenderListener, MainView, ItemListener, PostListener, PostDetailListener, ProfileListener, Navigator.FragmentChangeListener, MessagesListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    /*New Post Views*/
    @BindView(R.id.includeNewPostView)
    ConstraintLayout includeNewPostView;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.edit_tag_view)
    EditTag editTagView;
    @BindView(R.id.edtContent)
    TextInputEditText edtContent;
    @BindView(R.id.tilPost)
    TextInputLayout tilPost;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.txtButton)
    TextView txtButton;
    /*End New Post Views*/
    @BindView(R.id.rootView)
    CoordinatorLayout rootView;
    @BindView(R.id.splashScreen)
    ConstraintLayout splashScreen;
    @BindView(R.id.btnPro)
    Group fabPro;
    @BindView(R.id.btnRegular)
    Group fabRegular;
    @BindView(R.id.toolbar)
    TextView txtTitle;
    @BindView(R.id.icon)
    ImageView imgIconToolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.txtCoinReward)
    TextView txtCoinReward;


    /*Auth*/
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private RewardedVideoAd mRewardedVideoAd;

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private SlidingRootNav navListener;
    private MainPresenter presenter;
    private Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showSplashScreen();
        if (!checkPlayServicesAvaliability()) {
            return;
        }
        includeNewPostView.bringToFront();
        setupNav();
        initNavigator();
        addPostsFragment(savedInstanceState);
        initAuth();
        initPresenter();
        hideSplashScreen();
        setupRewardVideo();
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

    @Override
    public void showRewardVideo() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void showConfirmDialogToSeeRewardVideo() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.activity_main_dialog_reward_video_title)
                .setMessage(R.string.activity_main_dialog_reward_video_desc)
                .setPositiveButton(R.string.global_text_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        presenter.onConfirmedToShowRewardVideo();
                    }
                }).create();
        dialog.show();
    }


    private void setupRewardVideo() {
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(presenter);
    }

    private void initNavigator() {
        navigator = new Navigator(this, getSupportFragmentManager());
        navigator.setFragmentChangeListener(this);
        //navigator.restore(new NavigationState(PostPagerFragment.class.getName(), PostPagerFragment.class.getName(), false));
    }

    private void initAuth() {
        mAuth = FirebaseAuth.getInstance();
        String googleClientId = BuildConfig.default_web_client_id;
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(googleClientId)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    protected void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        mRewardedVideoAd.destroy(this);
        navListener = null;
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    private void initPresenter() {
        presenter = (MainPresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            UserRepository repository = new UserRepository(
                    new UserLocalDataSource(),
                    new UserRemoteDataSource(new FireUser(), currentUser)
            );
            presenter = new MainPresenterImpl(
                    currentUser,
                    getResources(),
                    new UseCaseHandler(new UseCaseThreadPoolScheduler()),
                    new PublishPost(repository),
                    new GetUser(repository),
                    new UpdateUserCoins(repository)
            );
        }
        setPresenter(presenter);
    }

    @OnClick(R.id.bgIcon)
    public void onBurgerIconClicked() {
        presenter.onBurgerIconClicked();
    }

    private ItemAdapter itemAdapter;

    public ImageView navImgProfile;
    public TextView navTxtName;

    private void setupNav() {
        navListener = new SlidingRootNavBuilder(this)
                .withMenuLayout(R.layout.navigation_view)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                //.withSavedState(savedInstanceState)
                .inject();

        RecyclerView recycler = findViewById(R.id.recycler);
        navImgProfile = findViewById(R.id.navImgProfile);
        navTxtName = findViewById(R.id.navTxtName);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(Item.getMenuList(getResources()), this);
        recycler.setAdapter(itemAdapter);
    }

    public static final String TAG_POSTS_FRAGMENT = "posts";

    private void addPostsFragment(Bundle savedInstanceState) {

        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }
        showPostPager();
        // Add the fragment to the 'fragment_container' FrameLayout
        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, postsFragment, TAG_POSTS_FRAGMENT)
                .addToBackStack(null)
                .commit();*/
    }


    @Override
    public void showPostPager() {
        PostPagerFragment postsFragment = new PostPagerFragment();
        showOrAdd(postsFragment, TAG_POSTS_FRAGMENT, false);
        //goTo(PostPagerFragment.class);
    }

    private void goTo(String tag, boolean keepState, boolean withCustomAnimation, Bundle bundle, BackStrategy.KEEP backStrategy) {
        navigator.goTo(tag, keepState, withCustomAnimation, bundle, backStrategy);
    }

    private void goTo(Class clazz) {
        goTo(clazz.getName(), true, false, Bundle.EMPTY, BackStrategy.KEEP.INSTANCE);
    }

    private void goTo(Class clazz, Bundle bundle) {
        goTo(clazz.getName(), true, false, bundle, BackStrategy.KEEP.INSTANCE);
    }

    @Override
    public void showPostDetail(Post post) {
        PostDetailFragment postDetailFragment = PostDetailFragment.newInstance(post);
        showOrAdd(postDetailFragment, "post-detail-fragment-" + post.getId(), true);
        //goTo(PostDetailFragment.class, PostDetailFragment.getBungle(post));
    }

    @Override
    public void showMessages(User user) {
        MessagesFragment messagesFragment = MessagesFragment.newInstance(user);
        showOrAdd(messagesFragment, "messages-fragment", false);
        //goTo(MessagesFragment.class);
    }

    @Override
    public void showPostsWithTag(Post post, String tag) {
        PostsFragment fragment = PostsFragment.newInstance(PostsFragment.TYPE_HASHTAG, tag);
        showOrAdd(fragment, "posts-hashtag-" + tag, false);
    }

    @Override
    public void showAppInfo() {
        AppinfoFragment appinfoFragment = AppinfoFragment.newInstance();
        showOrAdd(appinfoFragment, "appinfo-fragment", false);
        //goTo(AppinfoFragment.class);
    }

    @Override
    public void showCoins(long coins) {
        Log.d(TAG, "showCoins");
        txtCoinReward.setText(String.valueOf(coins));
    }

    @Override
    public void showProfile(User user, boolean editable) {
        ProfileFragment profileFragment = ProfileFragment.newInstance(user, editable);
        String tag = "profile-fragment-" + user.getId();
        if (editable) {
            addFragment(profileFragment, tag);
        } else {
            showOrAdd(profileFragment, tag, false);
        }
        //goTo(ProfileFragment.class, ProfileFragment.getBundle(user, editable));
    }

    private void showOrAdd(Fragment fragment, String tag, boolean animated) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragmentByTag == null) {
            addFragment(fragment, tag);
        } else {
            showFragment(tag, animated);
        }
    }

    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    private void showFragment(String tag, boolean animated) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragmentByTag != null && !fragmentByTag.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.show(fragmentByTag);
            if (animated) {
                transaction.setCustomAnimations(R.anim.slide_in_start, 0);
            }
            transaction.addToBackStack(null);
            transaction.commit();
            return;
        }
        Log.d(TAG, "showFragment skipped. Fragment is visible! or null!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNextGender() {

    }

    @Override
    public void onGenderSlideResume() {

    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        presenter.attachView(this);
        presenter.onCreate();
    }

    @Override
    public boolean checkPlayServicesAvaliability() {
        boolean avaliability = false;
        final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int resultCode = api.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(resultCode))
                api.getErrorDialog((this), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            else {
                GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
            }
        } else {
            avaliability = true;
        }

        return avaliability;
    }

    @Override
    public void showName(String name) {
        txtName.setText(name);
        navTxtName.setText(name);
    }

    @Override
    public void showCity(String city) {

    }

    @Override
    public void showAvatar(int avatar) {
        imgProfile.setImageDrawable(ContextCompat.getDrawable(this, avatar));
        navImgProfile.setImageDrawable(ContextCompat.getDrawable(this, avatar));
    }

    @Override
    public void showSplashScreen() {
        splashScreen.bringToFront();
        splashScreen.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSplashScreen() {
        splashScreen.setVisibility(View.GONE);
    }

    @Override
    public void showPublishKingImg() {
        txtPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_crown_1, 0);
    }

    @Override
    public void hidePublishKingImg() {
        txtPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    @Override
    public void showPublishDialog() {
        toogleNewPostVisibility(true);
    }

    @Override
    public void hidePublishDialog() {
        toogleNewPostVisibility(false);
    }

    @Override
    public void openNavigation() {
        navListener.openMenu(true);
    }

    @Override
    public void closeNavigation() {
        navListener.closeMenu(true);
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        presenter.onFabClicked();
    }

    @OnClick(R.id.bgBtnPro)
    public void onFabProClicked() {
        presenter.onFabProClicked();
    }

    @OnClick(R.id.bgBtnRegular)
    public void onFabRegularClicked() {
        presenter.onFabRegularClicked();
    }


    @OnClick(R.id.btnPost)
    public void onBtnPostClicked() {
        presenter.onSubmitPost(edtContent.getText().toString(), editTagView.getTagList());
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
        /*if (navigator.hasBackStack()) {
            navigator.goBack();
            return;
        }*/
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void toogleMenuItems(Item old, Item selected) {
        itemAdapter.toogleItem(old, selected);
    }

    @Override
    public void showFab() {
        TransitionManager.beginDelayedTransition(rootView);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFab() {
        TransitionManager.beginDelayedTransition(rootView);
        fab.setVisibility(View.GONE);
    }

    @Override
    public void changeTitle(String title) {
        TransitionManager.beginDelayedTransition(rootView,
                new TransitionSet()
                        .addTransition(new ChangeTransform().setDuration(200).setInterpolator(new FastOutSlowInInterpolator()).setStartDelay(200)));
        txtTitle.setRotationX(360f);
        txtTitle.setText(title);
    }

    @Override
    public void showPostBtns() {
        TransitionManager.beginDelayedTransition(rootView,
                new TransitionSet()
                        .addTransition(new ChangeBounds())
                        .addTransition(new Slide(TOP).setDuration(200).setInterpolator(new FastOutSlowInInterpolator()).setStartDelay(200))
                        .addTransition(new ChangeBounds()));
        fabPro.setVisibility(View.VISIBLE);
        fabRegular.setVisibility(View.VISIBLE);
        @DrawableRes int drawableRes = R.drawable.ic_close_grey_24dp;
        fab.setColorFilter(ContextCompat.getColor(this, android.R.color.white));
        fab.setImageResource(drawableRes);
    }

    @Override
    public void hidePostBtns() {
        @DrawableRes int drawableRes = R.drawable.ic_whatshot_white_24dp;
        fab.setImageResource(drawableRes);
        fabPro.setVisibility(View.GONE);
        fabRegular.setVisibility(View.GONE);
    }

    @Override
    public void addPost(Post post) {
        Log.d(TAG, "addPost: " + post.toString());
        PostsFragment fragment = getPostFragment();
        if (fragment != null) {
            fragment.addPost(post);
        }
    }

    @OnClick(R.id.imgProfile)
    public void onProfileClicked() {
    }

    @Override
    public void startChat(String userId, String receptorId) {
        ChatActivity.startChatActivity(
                this,
                userId,
                receptorId);
    }

    @Override
    public void startIntro() {
        IntroActivity.startIntroActivity(this);
    }

    @Override
    public void logout() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
    }

    @Override
    public void showError(String error) {
        Snackbar.make(rootView, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public int getBackStackEntryCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }

    @Override
    public void popBackStack() {
        getSupportFragmentManager().popBackStackImmediate();
    }


    private PostsFragment getPostFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof PostsFragment) {
                return (PostsFragment) fragment;
            }
        }
        return null;
    }


    private void toogleNewPostVisibility(boolean visibility) {
        TransitionManager.beginDelayedTransition(
                rootView,
                new TransitionSet()
                        .addTransition(new ChangeBounds())
                        .addTransition(new Slide(TOP).setDuration(200).setInterpolator(new FastOutSlowInInterpolator()).setStartDelay(200))
                        .addTransition(new ChangeBounds())
        );
        if (!visibility) {
            includeNewPostView.setRotation(-5f);
        } else {
            includeNewPostView.setRotation(0f);
        }
        includeNewPostView.setVisibility(visibility ? View.VISIBLE : View.GONE);
        boolean focussed = edtContent.requestFocus();
        Log.d(TAG, "focussed: " + focussed);
    }

    @Override
    public void onItemSelected(Item item) {
        presenter.onMenuItemSelected(item);
    }

    @Override
    public void onPostSelected(Post post) {
        Log.d(TAG, "onPostSelected");
        presenter.onPostSelected(post);
        /*
        hideFab();
        changeTitle(post.getUser().getReadableName(getResources()));
        showPostDetailFragment(post);*/
    }

    @Override
    public void onTagClick(Post post, String tag) {
        Log.d(TAG, "onTagClick tag: " + tag);
        showPostsWithTag(post, tag);
    }

    @Override
    public void onUserSelected(User user) {
        presenter.onUserSelected(user);
        //showProfileFragment(user);
    }

    @Override
    public void onCommentSelected(Comment comment) {
        Log.d(TAG, "onCommentSelected");
        presenter.onCommentSelected(comment);
    }

    @Override
    public void onBtnMessageClicked(User user) {
        Log.d(TAG, "onBtnMessageClicked");
        presenter.onSendMessageClicked(user);
    }

    @Override
    public void onFragmentChanged(@NotNull String currentTag, @NotNull Fragment currentFragment) {
        Log.d(TAG, "onFragmentChanged");
    }

    @OnClick(R.id.txtCoinReward)
    public void onCoinRewardClick() {
        presenter.coinsRewardClicked();
    }

}
