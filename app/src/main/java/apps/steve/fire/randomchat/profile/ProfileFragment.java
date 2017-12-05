package apps.steve.fire.randomchat.profile;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.main.ui.entity.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileView {


    public static final String TAG = ProfileFragment.class.getSimpleName();
    public static final String ARG_USER = "user";
    private static final String ARG_USER_EDITABLE = "user_editable";
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtLocalization)
    TextView txtLocalization;
    @BindView(R.id.txtCoins)
    TextView txtCoins;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    Unbinder unbinder;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.txtButton)
    TextView txtButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(User user, boolean editable) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_USER, Parcels.wrap(user));
        bundle.putBoolean(ARG_USER_EDITABLE, editable);
        fragment.setArguments(bundle);
        return fragment;
    }

    private User user;
    private ProfileListener listener;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        if (context instanceof ProfileListener) {
            listener = (ProfileListener) context;
        } else {
            throw new ClassCastException(context.getClass().getSimpleName() + "" +
                    "must implement ProfileListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        getUser();
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        paintUser();
    }

    private void paintUser() {
        String name = user.getReadableName(getActivity().getResources());
        @DrawableRes int avatarDrawable = user.getAvatarDrawable();
        long coins = user.getCoins();
        long commentCount = user.getCommentCount();
        long postCount = user.getPostCount();

        String location = user.getLocation();
        String description = user.getDescription();
        if (TextUtils.isEmpty(description)) {
            description = getString(R.string.user_default_description);
        }
        if (TextUtils.isEmpty(location)) {
            location = getString(R.string.user_default_location);
        }

        showName(name);
        showAvatar(avatarDrawable);
        showCoinCount(coins);
        showDescription(description);
        showLocation(location);
        initBtnMessage();
    }

    private void initBtnMessage() {
        txtPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        txtButton.setText(getString(R.string.profile_text_sendmessage));
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        listener = null;
        super.onDetach();
    }

    private void getUser() {
        user = Parcels.unwrap(getArguments().getParcelable(ARG_USER));
        if (user != null) {
            Log.d(TAG, "user id: " + user.getId());
        }
    }

    @Override
    public void showName(String name) {
        txtName.setText(name);
    }

    @Override
    public void showAvatar(@DrawableRes int avatarDrawable) {
        imgProfile.setImageDrawable(ContextCompat.getDrawable(getActivity(), avatarDrawable));
    }

    @Override
    public void showLocation(String location) {
        txtLocalization.setText(location);
    }

    @Override
    public void showCoinCount(long coinCount) {
        txtCoins.setText(String.valueOf(coinCount));
    }

    @Override
    public void showDescription(String description) {
        txtDescription.setText(description);
    }

    @OnClick(R.id.includeBtnSendMessage)
    public void btnSendMessageClicked() {
        listener.onBtnMessageClicked(user);
    }

}
