package apps.steve.fire.randomchat.messages.adapter.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.main.ui.entity.Message;
import apps.steve.fire.randomchat.main.ui.entity.User;
import apps.steve.fire.randomchat.messages.listener.MessagesListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @stevecampos on 22/12/2017.
 */

public class MessageHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtComment)
    TextView txtComment;
    @BindView(R.id.imgAlert)
    ImageView imgAlert;
    @BindView(R.id.cardView)
    CardView cardView;

    public MessageHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Message message, final MessagesListener listener) {

        User user = message.getUser();
        String userName = user.getReadableName(itemView.getResources());
        txtName.setText(userName);

        txtComment.setText(message.getMessageText());
        Glide.
                with(itemView.getContext())
                .asDrawable()
                .load(user.getAvatarDrawable())
                .into(imgAvatar);
        if (message.isIncommingMessage()) {
            imgAlert.setVisibility(View.VISIBLE);
        } else {
            imgAlert.setVisibility(View.GONE);
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onMessageClicked(message);
                }
            }
        });
    }
}
