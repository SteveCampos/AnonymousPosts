package apps.stevecampos.fire.anonymouschat.posts.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;
import apps.stevecampos.fire.anonymouschat.posts.PostListener;
import apps.stevecampos.fire.anonymouschat.posts.holder.PostViewHolder;

/**
 * Created by @stevecampos on 29/11/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private List<Post> postList;
    private RecyclerView recycler;
    private PostListener listener;

    public PostAdapter(List<Post> postList, PostListener listener) {
        this.postList = postList;
        this.listener = listener;
    }

    public void setRecycler(RecyclerView recycler) {
        this.recycler = recycler;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bind(postList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private void scrollTo(int position) {
        if (recycler != null) {
            recycler.scrollToPosition(position);
        }
    }

    public void addOrUpdatePost(Post post) {
        boolean contains = postList.contains(post);
        if (contains) {
            updatePost(post);
        } else {
            addPost(post);
        }
    }

    public void addPost(Post post) {
        int position = 0;
        postList.add(position, post);
        notifyItemInserted(position);
        //scrollTo(position);
    }

    public void updatePost(Post post) {
        int position = postList.indexOf(post);
        if (position != -1) {
            postList.set(position, post);
            notifyItemChanged(position);
        }

    }

    public void deletePost(Post post) {
        int position = postList.indexOf(post);
        if (position != -1) {
            postList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addPostList(List<Post> postList) {
        this.postList.clear();
        this.postList.addAll(postList);
        notifyDataSetChanged();
    }

}

