package apps.stevecampos.fire.anonymouschat.postDetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Comment;
import apps.stevecampos.fire.anonymouschat.postDetail.PostDetailListener;

/**
 * Created by @stevecampos on 5/12/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private List<Comment> comments = new ArrayList<>();
    private PostDetailListener listener;
    private RecyclerView recyclerView;

    public CommentAdapter(PostDetailListener listener) {
        this.listener = listener;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment, listener);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addComment(Comment comment) {
        boolean contains = comments.contains(comment);
        if (!contains) {
            int position = 0;
            comments.add(position, comment);
            notifyItemInserted(position);
            scrollToPosition(position);
        }
    }

    private void scrollToPosition(int position) {
        if (recyclerView != null) {
            recyclerView.scrollToPosition(position);
        }
    }
}
