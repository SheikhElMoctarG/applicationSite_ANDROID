package com.sheikh.exe_apk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sheikh.exe_apk.Model.ListItem;
import com.sheikh.exe_apk.R;

import java.util.List;

public class MyAdapture extends RecyclerView.Adapter<MyAdapture.ViewHolder> {
    private List<ListItem> listItems ;
    private Context context;
    public MyAdapture(Context context, List<ListItem> listItems){
        this.context = context;
        this.listItems = listItems;
    }
    @NonNull
    @Override
    public MyAdapture.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapture.ViewHolder holder, int position) {
        ListItem item = listItems.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public ViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title_in_row);
        }
    }
}
