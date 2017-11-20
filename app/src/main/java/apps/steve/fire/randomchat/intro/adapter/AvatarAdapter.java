package apps.steve.fire.randomchat.intro.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.intro.listener.AvatarListener;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

/**
 * Created by Steve on 15/11/2017.
 */

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarHolder> {
    private List<AvatarUi> items;
    private AvatarListener listener;

    public AvatarAdapter(List<AvatarUi> items, AvatarListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public AvatarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avatarslide_item_avatar, parent, false);
        return new AvatarHolder(view);
    }

    @Override
    public void onBindViewHolder(AvatarHolder holder, int position) {
        AvatarUi avatar = items.get(position);
        holder.bind(avatar, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setAvatarList(List<AvatarUi> avatarList) {
        this.items.clear();
        this.items.addAll(avatarList);
        notifyDataSetChanged();
    }

    public void updateAvatar(AvatarUi avatarUi) {
        int position = items.indexOf(avatarUi);
        if (position == -1) return;
        avatarUi.setSelected(true);
        items.set(position, avatarUi);
        notifyItemChanged(position);
    }

    public void toggleAvatars(AvatarUi old, AvatarUi actual) {
        int positionOld = items.indexOf(old);
        int positionActual = items.indexOf(actual);
        if (positionOld != -1) {
            items.set(positionOld, old);
            notifyItemChanged(positionOld);
        }
        if (positionActual != -1) {
            items.set(positionActual, actual);
            notifyItemChanged(positionActual);
        }
    }

    public static final class AvatarHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgDrawable)
        ImageView imgDrawable;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.imgSelect)
        ImageView imgSelect;

        public AvatarHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final AvatarUi avatar, final AvatarListener listener) {
            imgDrawable.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), avatar.getImgDrawable()));
            txtName.setText(avatar.getName());
            int visibility = View.GONE;
            if (avatar.isSelected()) {
                visibility = VISIBLE;
            }
            imgSelect.setVisibility(visibility);
            imgDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onAvatarSelected(avatar);
                    }
                }
            });
        }
    }
}
