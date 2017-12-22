package apps.steve.fire.randomchat.profile;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.base.usecase.UseCaseHandler;
import apps.steve.fire.randomchat.base.usecase.UseCaseThreadPoolScheduler;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.data.source.local.UserLocalDataSource;
import apps.steve.fire.randomchat.data.source.remote.UserRemoteDataSource;
import apps.steve.fire.randomchat.data.source.remote.firebase.FireUser;
import apps.steve.fire.randomchat.intro.usecase.UpdateUser;
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
    @BindView(R.id.root)
    ConstraintLayout root;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(User user, boolean editable) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(getBundle(user, editable));
        return fragment;
    }

    public static Bundle getBundle(User user, boolean editable) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_USER, Parcels.wrap(user));
        bundle.putBoolean(ARG_USER_EDITABLE, editable);
        return bundle;
    }

    private User user;
    private boolean editable;
    private ProfileListener listener;
    private ProfilePresenter presenter;

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
        initPresenter();
    }

    private void initPresenter() {
        presenter = new ProfilePresenterImpl(
                getResources(),
                new UseCaseHandler(new UseCaseThreadPoolScheduler()),
                new UpdateUser(new UserRepository(new UserLocalDataSource(), new UserRemoteDataSource(new FireUser(), null)))
        );
        setPresenter(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (presenter != null) {
            presenter.onCreate();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        getUser();
        if (presenter != null) {
            presenter.onCreateView();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        if (presenter != null) {
            presenter.onViewCreated();
            presenter.attachView(this);
            presenter.setUser(user, editable);
        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        initBtnMessage();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    private void initBtnMessage() {
        txtPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        txtButton.setText(getString(R.string.profile_text_sendmessage));
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        unbinder.unbind();
        if (presenter != null) {
            presenter.onDestroyView();
        }
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        listener = null;
        if (presenter != null) {
            presenter.onDetach();
        }
        super.onDetach();
    }

    private void getUser() {
        user = Parcels.unwrap(getArguments().getParcelable(ARG_USER));
        editable = getArguments().getBoolean(ARG_USER_EDITABLE);
        if (user != null) {
            Log.d(TAG, "user id: " + user.getId());
        }
    }

    @Override
    public void showName(String name, boolean editable) {
        txtName.setText(name);
        setTextEditable(txtName, editable);
    }

    private void setTextEditable(TextView view, boolean editable) {
        if (editable) {
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mode_edit_grey_24dp, 0);
        }
    }

    @Override
    public void showAvatar(@DrawableRes int avatarDrawable, boolean editable) {
        Glide.with(getContext())
                .asDrawable()
                .load(avatarDrawable)
                .into(imgProfile);
    }

    @Override
    public void showLocation(String location, boolean editable) {
        txtLocalization.setText(location);
    }

    @Override
    public void showCoinCount(long coinCount) {
        txtCoins.setText(String.valueOf(coinCount));
    }

    @Override
    public void showDescription(String description, boolean editable) {
        txtDescription.setText(description);
        setTextEditable(txtDescription, editable);
    }

    @Override
    public void showDialogEditName() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_edittext, root, false);
        final TextInputEditText editText = view.findViewById(R.id.editext);
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.profile_title_editname)
                .setView(view)
                .setPositiveButton(R.string.global_text_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        presenter.onEditNameSubmit(editText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.global_text_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create();
        dialog.show();
    }

    @OnClick(R.id.includeBtnSendMessage)
    public void btnSendMessageClicked() {
        listener.onBtnMessageClicked(user);
    }

    @Override
    public void setPresenter(ProfilePresenter presenter) {
        presenter.onAttach();
    }

    @OnClick(R.id.txtName)
    public void onEditNameClicked() {
        presenter.onEditNameClicked();
    }

    @OnClick(R.id.txtDescription)
    public void onEditDescClicked() {
        presenter.onEditDescriptionClicked();
    }

    @Override
    public void showDialogEditDescription() {
        Log.d(TAG, "showDialogEditDescription");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_edittext, root, false);
        final TextInputEditText editText = view.findViewById(R.id.editext);
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.profile_title_editdesc)
                .setView(view)
                .setPositiveButton(R.string.global_text_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        presenter.onEditDescriptionSubmit(editText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.global_text_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create();
        dialog.show();
    }


}
