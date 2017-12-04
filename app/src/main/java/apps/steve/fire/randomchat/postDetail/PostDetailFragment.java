package apps.steve.fire.randomchat.postDetail;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostDetailFragment extends Fragment implements PostDetailView {


    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtContentText)
    TextView txtContentText;
    @BindView(R.id.txtFavoriteCount)
    TextView txtFavoriteCount;
    @BindView(R.id.txtCommentCount)
    TextView txtCommentCount;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.fabSend)
    FloatingActionButton fabSend;
    @BindView(R.id.edtMessage)
    TextInputEditText edtMessage;
    @BindView(R.id.tilMessage)
    TextInputLayout tilMessage;
    @BindView(R.id.imgCrown)
    ImageView imgCrown;
    Unbinder unbinder;

    public PostDetailFragment() {
        // Required empty public constructor
    }

    public static final String TAG = "PostDetailFragment";
    public static final String ARG_POST = "post";

    public static PostDetailFragment newInstance(Post post) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_POST, Parcels.wrap(post));
        fragment.setArguments(bundle);
        return fragment;
    }

    private Post post;
    private PostDetailListener listener;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        if (context instanceof PostDetailListener) {
            listener = (PostDetailListener) context;
        } else {
            throw new ClassCastException(context.getClass().getSimpleName() + "" +
                    "must implement PostDetailListener");
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
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPost();
        Log.d(TAG, "onViewCreated");
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
        paintViews();
    }

    private void paintViews() {
        User user = post.getUser();
        if (user != null) {
            showUserAvatarDrawable(user.getAvatarDrawable());
        }
        String contentText = post.getContentText();
        long commentCount = post.getCommentCount();
        long favCount = post.getFavoriteCount();
        showContentText(contentText);
        showCommentCount(commentCount);
        showFavCount(favCount);
        boolean kingPost = post.isPopular();
        if (!kingPost) {
            hideCrown();
        }
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


    private void getPost() {
        post = Parcels.unwrap(getArguments().getParcelable(ARG_POST));
        if (post != null) {
            Log.d(TAG, "post id: " + post.getId());
        }
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showUserAvatarDrawable(@DrawableRes int avatarDrawable) {
        imgProfile.setImageDrawable(ContextCompat.getDrawable(getActivity(), avatarDrawable));
    }

    @Override
    public void showContentText(String contentText) {
        txtContentText.setText(contentText);
    }

    @Override
    public void showFavCount(long favCount) {
        txtFavoriteCount.setText(String.valueOf(favCount));
    }

    @Override
    public void showCommentCount(long commentCount) {
        txtCommentCount.setText(String.valueOf(commentCount));
    }

    @Override
    public void showCrown() {
        imgCrown.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCrown() {
        imgCrown.setVisibility(View.GONE);
    }

    @Override
    public void addComment(Comment comment) {

    }

    @Override
    public void onUserClicked(User user) {
        listener.onUserSelected(user);
    }

    @OnClick(R.id.imgProfile)
    public void onViewClicked() {
        User user = post.getUser();
        if (user != null) {
            onUserClicked(user);
        }
    }
}
