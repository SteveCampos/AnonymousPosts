package apps.steve.fire.randomchat.posts.holder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.Utils;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;
import apps.steve.fire.randomchat.posts.PostListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

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
    @BindView(R.id.tagGroup)
    public TagGroup tagView;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void bind(final Post post, final PostListener listener) {
        long postTimestamp = post.getTimestamp();
        long now = new Date().getTime();
        String contentText = post.getContentText();
        User user = post.getUser();
        if (user != null) {
            txtName.setText(user.getReadableName(itemView.getResources()));
            //imgProfile.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), user.getAvatarDrawable()));
            Glide.with(itemView.getContext())
                    .asDrawable()
                    .load(user.getAvatarDrawable())
                    .into(imgProfile);
        }
        txtContent.setText(contentText);
        long favoriteCount = post.getFavoriteCount();
        long commentCount = post.getCommentCount();
        txtTime.setText(Utils.calculateTimePassed(postTimestamp, now, itemView.getResources()));
        txtLikesCount.setText(String.valueOf(favoriteCount));
        txtCommentsCount.setText(String.valueOf(commentCount));
        if (post.isPopular()) {
            imgMoreHorizontal.setVisibility(View.VISIBLE);
        } else {
            imgMoreHorizontal.setVisibility(View.GONE);
        }
        List<String> hashtags = post.getHashtags();
        if (!hashtags.isEmpty()) {
            tagView.setTags(hashtags);
            //          tagView.setVisibility(View.VISIBLE);
        } else {
            tagView.setTags(itemView.getResources().getString(R.string.fragment_posts_mssg_notags));
//            tagView.setVisibility(View.GONE);
        }

        tagView.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                if (listener != null) {
                    listener.onTagClick(post, tag);
                }
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onPostSelected(post);
                }
            }
        });
    }
}
