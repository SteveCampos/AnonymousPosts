package apps.stevecampos.fire.anonymouschat.chat.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Steve on 16/12/2017.
 */

public class EmisorMessageHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtMessage)
    TextView txtMessage;

    public EmisorMessageHolder(View itemView) {
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
