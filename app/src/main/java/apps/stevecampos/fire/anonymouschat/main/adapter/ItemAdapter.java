package apps.stevecampos.fire.anonymouschat.main.adapter;

import android.support.annotation.ColorRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.main.listener.ItemListener;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Item;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Steve on 25/11/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> items;
    private ItemListener listener;

    public ItemAdapter(List<Item> items, ItemListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_nav, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void changeItem(Item item) {
        int position = items.indexOf(item);
        if (position != -1) {
            items.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void toogleItem(Item old, Item selected) {
        changeItem(old);
        changeItem(selected);
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtItem)
        TextView txtItem;
        @BindView(R.id.txtAlert)
        TextView txtAlert;
        @BindView(R.id.root)
        ConstraintLayout root;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Item item, final ItemListener listener) {
            txtItem.setText(item.getName());
            txtItem.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), 0, 0, 0);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSelected(item);
                }
            });
            @ColorRes int textColor = R.color.md_grey_500;
            if (item.isSelected()) {
                textColor = R.color.colorAccent;
            }
            txtItem.setTextColor(ContextCompat.getColor(itemView.getContext(), textColor));
            String notificationText = item.getNotificationText();
            if (TextUtils.isEmpty(notificationText)) {
                txtAlert.setVisibility(View.GONE);
            } else {
                txtAlert.setText(notificationText);
                txtAlert.setVisibility(View.VISIBLE);
            }
        }
    }
}
