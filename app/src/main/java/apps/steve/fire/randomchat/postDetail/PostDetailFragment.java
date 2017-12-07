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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.base.usecase.UseCaseHandler;
import apps.steve.fire.randomchat.base.usecase.UseCaseThreadPoolScheduler;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.data.source.local.UserLocalDataSource;
import apps.steve.fire.randomchat.data.source.remote.UserRemoteDataSource;
import apps.steve.fire.randomchat.data.source.remote.firebase.FireUser;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;
import apps.steve.fire.randomchat.main.usecase.GetPostComments;
import apps.steve.fire.randomchat.main.usecase.PublishComment;
import apps.steve.fire.randomchat.postDetail.adapter.CommentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.INPUT_METHOD_SERVICE;

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

    public static Bundle getBungle(Post post) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_POST, Parcels.wrap(post));
        return bundle;
    }

    private Post post;
    private PostDetailListener listener;
    private PostDetailPresenter presenter;
    private CommentAdapter adapter;

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
        initPresenter();
    }

    private void initPresenter() {
        UserRepository repository = new UserRepository(
                new UserLocalDataSource(),
                new UserRemoteDataSource(new FireUser(), null)
        );
        presenter = new PostDetailPresenterImpl(
                new UseCaseHandler(new UseCaseThreadPoolScheduler()),
                new GetPostComments(repository),
                new PublishComment(repository)
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
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initCommentAdapter();
        if (presenter != null) {
            presenter.onCreateView();
        }
        return view;
    }

    private void initCommentAdapter() {
        adapter = new CommentAdapter(listener);
        adapter.setRecyclerView(recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPost();
        if (presenter != null) {
            presenter.attachView(this);
            presenter.onViewCreated();
            presenter.setPost(post);
        }
        Log.d(TAG, "onViewCreated");
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
        if (presenter != null) {
            presenter.onResume();
        }
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
    public void onDetach() {
        Log.d(TAG, "onDetach");
        listener = null;
        if (presenter != null) {
            presenter.onDetach();
        }
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
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
        Log.d(TAG, "addComment : " + comment.toString());
        adapter.addComment(comment);
    }

    @Override
    public void onUserClicked(User user) {
        listener.onUserSelected(user);
    }

    @Override
    public void showError(String error) {
        AlertDialog builder = new AlertDialog.Builder(getActivity())
                .setMessage(error)
                .create();
        builder.show();
    }

    @Override
    public void showEdtContent(String contentText) {
        edtMessage.setText(contentText);
    }

    @Override
    public void hideSoftboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    @OnClick(R.id.imgProfile)
    public void onViewClicked() {
        User user = post.getUser();
        if (user != null) {
            onUserClicked(user);
        }
    }

    @OnClick(R.id.fabSend)
    public void onFabSendClicked() {
        presenter.publishCommentBtnClicked(edtMessage.getText().toString());
    }

    @Override
    public void setPresenter(PostDetailPresenter presenter) {
        presenter.onAttach();
    }
}