package apps.steve.fire.randomchat.chat.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.main.ui.entity.Message;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Steve on 16/12/2017.
 */

public class ReceptorMessageHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtMessage)
    TextView txtMessage;

    public ReceptorMessageHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Message message) {
        txtMessage.setText(message.getMessageText());
        Glide.
                with(itemView.getContext())
                .asDrawable()
                .load(message.getUser().getAvatarDrawable())
                .into(imgProfile);
    }
}
