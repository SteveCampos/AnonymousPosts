package apps.steve.fire.randomchat.posts.holder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @stevecampos on 29/11/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgProfile)
    public ImageView imgProfile;
    @BindView(R.id.txtName)
    public TextView txtName;
    @BindView(R.id.txtTime)
    public TextView txtTime;
    @BindView(R.id.txtContent)
    public TextView txtContent;
    @BindView(R.id.txtLikesCount)
    public TextView txtLikesCount;
    @BindView(R.id.txtCommentsCount)
    public TextView txtCommentsCount;
    @BindView(R.id.imgMoreHorizontal)
    public ImageView imgMoreHorizontal;
    @BindView(R.id.btnChat)
    public Button btnChat;
    @BindView(R.id.cardView)
    public CardView cardView;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Post post) {
        String contentText = post.getContentText();
        User user = post.getUser();
        if (user != null) {
            txtName.setText(user.getName());
            imgProfile.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), user.getAvatarDrawable()));
        }
        txtContent.setText(contentText);
        long favoriteCount = post.getFavoriteCount();
        long commentCount = post.getCommentCount();
        txtLikesCount.setText(String.valueOf(favoriteCount));
        txtCommentsCount.setText(String.valueOf(commentCount));
        if (post.isPopular()) {
            imgMoreHorizontal.setVisibility(View.VISIBLE);
        } else {
            imgMoreHorizontal.setVisibility(View.GONE);
        }
    }
}