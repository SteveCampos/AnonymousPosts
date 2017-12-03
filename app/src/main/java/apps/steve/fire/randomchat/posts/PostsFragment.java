package apps.steve.fire.randomchat.posts;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.base.BasePresenter;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.posts.adapter.PostAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment implements PostView {


    @BindView(R.id.txtEmpty)
    TextView txtEmpty;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    Unbinder unbinder;
    @BindView(R.id.progress)
    ProgressBar progress;

    private PostListener listener;

    public PostsFragment() {
        // Required empty public constructor
    }

    public static PostsFragment newInstance() {
        return new PostsFragment();
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
    }

    private PostAdapter adapter;

    private void setupAdapter() {
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostAdapter(new ArrayList<Post>(), listener);
        adapter.setRecycler(recycler);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void setPresenter(BasePresenter presenter) {

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
        adapter.addPost(post);
    }

    @Override
    public void changePost(Post post) {
        adapter.changePost(post);
    }

    @Override
    public void deletePost(Post post) {
        adapter.deletePost(post);
    }

    @Override
    public void addPostList(List<Post> postList) {
        adapter.addPostList(postList);
    }
}
