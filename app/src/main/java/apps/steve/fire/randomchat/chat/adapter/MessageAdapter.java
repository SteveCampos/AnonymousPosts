package apps.steve.fire.randomchat.chat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.chat.adapter.holder.EmisorMessageHolder;
import apps.steve.fire.randomchat.chat.adapter.holder.ReceptorMessageHolder;
import apps.steve.fire.randomchat.main.ui.entity.Message;
import butterknife.BindView;

/**
 * Created by Steve on 16/12/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messages = new ArrayList<>();
    public static final int TYPE_MAIN_USER = 1;
    public static final int TYPE_RECEPTOR = 2;

    public MessageAdapter() {
    }

    private RecyclerView recyclerView;

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.isMainUser()) {
            return TYPE_MAIN_USER;
        }
        return TYPE_RECEPTOR;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_MAIN_USER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_emisor_message, parent, false);
                holder = new EmisorMessageHolder(view);
                break;
            default:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_receptor_message, parent, false);
                holder = new ReceptorMessageHolder(v1);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        switch (holder.getItemViewType()) {
            case TYPE_MAIN_USER:
                ((EmisorMessageHolder) holder).bind(message);
                break;
            default:
                ((ReceptorMessageHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        if (!messages.contains(message)) {
            this.messages.add(message);
            int position = getItemCount() - 1;
            notifyItemInserted(position);
            scrollToPosition(position);
        }
    }

    private void scrollToPosition(int position) {
        if (recyclerView != null) {
            recyclerView.scrollToPosition(position);
        }
    }

    public void updateMessage(Message message) {
        if (messages.contains(message)) {
            int position = messages.indexOf(message);
            this.messages.set(position, message);
            notifyItemInserted(position);
        }
    }

    public void deleteMessage(Message message) {
        if (messages.contains(message)) {
            int position = messages.indexOf(message);
            this.messages.remove(position);
            notifyItemRemoved(position);
        }
    }
}
