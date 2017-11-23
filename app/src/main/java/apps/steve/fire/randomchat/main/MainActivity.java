package apps.steve.fire.randomchat.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.transition.ChangeBounds;
import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.intro.listener.GenderListener;
import apps.steve.fire.randomchat.postpager.PostPagerFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.originqiu.library.EditTag;

import static android.view.Gravity.TOP;

public class MainActivity extends AppCompatActivity implements GenderListener, MainView {

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
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rootView)
    CoordinatorLayout rootView;

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private SlidingRootNav navListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showStatusBarTranslucent();

        includeNewPostView.bringToFront();
        addPostsFragment(savedInstanceState);
        setupNav(savedInstanceState);
    }

    private void setupNav(Bundle savedInstanceState) {
        navListener = new SlidingRootNavBuilder(this)
                .withMenuLayout(R.layout.navigation_view)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .inject();
    }


    public static final String TAG_POSTS_FRAGMENT = "posts";

    private void addPostsFragment(Bundle savedInstanceState) {

        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        // Create a new Fragment to be placed in the activity layout
        PostPagerFragment postsFragment = new PostPagerFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        postsFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, postsFragment, TAG_POSTS_FRAGMENT).commitNow();
    }

    public void showStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/
        }
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

    }

    @Override
    public void showSplashScreen() {

    }

    @Override
    public void hideSplashScreen() {

    }

    @Override
    public void showPublishDialog() {

    }

    @Override
    public void hidePublishDialog() {
        toogleNewPostVisibility(false);
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        toogleNewPostVisibility(true);
    }

    @OnClick(R.id.btnPost)
    public void onBtnPostClicked() {
        toogleNewPostVisibility(false);
    }

    @Override
    public void onBackPressed() {
        if (visible) {
            toogleNewPostVisibility(false);
        } else {
            super.onBackPressed();
        }
    }

    private boolean visible;

    private void toogleNewPostVisibility(boolean visibility) {
        visible = visibility;
        float alpha = 1f;
        if (visibility) {
            alpha = 0.5f;
        }
//        fragmentContainer.setAlpha(alpha);
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
}
