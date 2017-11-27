package apps.steve.fire.randomchat.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.main.listener.ItemListener;
import apps.steve.fire.randomchat.main.ui.entity.Item;
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

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtItem)
        TextView txtItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Item item, final ItemListener listener) {
            txtItem.setText(item.getName());
            txtItem.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), 0, 0, 0);
            txtItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSelected(item);
                }
            });
        }
    }
}
