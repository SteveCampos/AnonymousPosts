package apps.stevecampos.fire.anonymouschat.posts;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCaseHandler;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCaseThreadPoolScheduler;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.data.source.local.UserLocalDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.remote.UserRemoteDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.remote.firebase.FireUser;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;
import apps.stevecampos.fire.anonymouschat.posts.usecase.GetPopularPosts;
import apps.stevecampos.fire.anonymouschat.posts.adapter.PostAdapter;
import apps.stevecampos.fire.anonymouschat.posts.usecase.GetPostWithTag;
import apps.stevecampos.fire.anonymouschat.posts.usecase.GetRecentPosts;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment implements PostView {


    private static final String TAG = PostsFragment.class.getSimpleName();
    @BindView(R.id.txtEmpty)
    TextView txtEmpty;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    Unbinder unbinder;
    @BindView(R.id.progress)
    ProgressBar progress;

    private PostListener listener;
    private PostPresenter presenter;
    private PostAdapter adapter;


    public PostsFragment() {
        // Required empty public constructor
    }

    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_TAG = "extra_tag";
    public static final int TYPE_POPULAR = 0;
    public static final int TYPE_RECENTS = 1;
    public static final int TYPE_HASHTAG = 2;

    public static PostsFragment newInstance(int type, String tag) {
        PostsFragment fragment = new PostsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        bundle.putString(EXTRA_TAG, tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static PostsFragment newInstance(int type) {
        return newInstance(type, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PostListener) {
            listener = (PostListener) context;
        } else {
            throw new ClassCastException(context.getClass().getSimpleName() + "" +
                    "must implement PostListener");
        }
        initPresenter();
    }

    private void initPresenter() {
        UserRepository repository = new UserRepository(
                new UserLocalDataSource(),
                new UserRemoteDataSource(new FireUser(), null)
        );
        presenter = new PostPresenterImpl(
                new UseCaseHandler(new UseCaseThreadPoolScheduler()),
                new GetPopularPosts(repository),
                new GetRecentPosts(repository),
                new GetPostWithTag(repository)
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (presenter != null) {
            presenter.onCreateView();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
        if (presenter != null) {
            presenter.attachView(this);
            presenter.onViewCreated();
            presenter.setExtras(getArguments());
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

    private void setupAdapter() {
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostAdapter(new ArrayList<Post>(), listener);
        adapter.setRecycler(recycler);
        recycler.setAdapter(adapter);
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
    public void showEmptyView() {
        txtEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        txtEmpty.setVisibility(View.GONE);
    }

    @Override
    public void addPost(Post post) {
        hideProgress();
        hideEmptyView();
        adapter.addOrUpdatePost(post);
    }

    @Override
    public void changePost(Post post) {
        adapter.updatePost(post);
    }

    @Override
    public void deletePost(Post post) {
        adapter.deletePost(post);
    }

    @Override
    public void addPostList(List<Post> postList) {
        adapter.addPostList(postList);
    }

    @Override
    public void setPresenter(PostPresenter presenter) {
        presenter.onAttach();
    }
}
