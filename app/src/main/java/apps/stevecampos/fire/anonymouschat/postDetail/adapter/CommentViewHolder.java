package apps.stevecampos.fire.anonymouschat.postDetail.adapter;

import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Comment;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;
import apps.stevecampos.fire.anonymouschat.postDetail.PostDetailListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @stevecampos on 5/12/2017.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtComment)
    TextView txtComment;
    @BindView(R.id.cardView)
    CardView cardView;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Comment comment, final PostDetailListener listener) {
        User user = comment.getUser();
        @DrawableRes int userAvatar = 0;
        String userName = "";
        if (user != null) {
            userAvatar = user.getAvatarDrawable();
            userName = user.getReadableName(itemView.getResources());
        }

        imgAvatar.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), userAvatar));
        txtName.setText(userName);
        txtComment.setText(comment.getCommentText());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCommentSelected(comment);
                }
            }
        });
    }
}
