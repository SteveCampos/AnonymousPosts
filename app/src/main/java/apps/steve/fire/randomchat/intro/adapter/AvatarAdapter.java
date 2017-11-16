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
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Steve on 15/11/2017.
 */

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarHolder> {
    private List<AvatarUi> items;

    public AvatarAdapter(List<AvatarUi> items) {
        this.items = items;
    }

    @Override
    public AvatarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avatarslide_item_avatar, parent, false);
        return new AvatarHolder(view);
    }

    @Override
    public void onBindViewHolder(AvatarHolder holder, int position) {
        AvatarUi avatar = items.get(position);
        holder.bind(avatar);
    }

    @Override
    public int getItemCount() {
        return items.size();
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

        public void bind(AvatarUi avatar) {
            imgDrawable.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), avatar.getImgDrawable()));
            txtName.setText(avatar.getName());
        }
    }
}
