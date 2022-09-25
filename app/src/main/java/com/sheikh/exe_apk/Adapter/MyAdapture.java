package com.sheikh.exe_apk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sheikh.exe_apk.Model.ListItem;
import com.sheikh.exe_apk.R;

import java.util.List;

public class MyAdapture extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListItem> listItems ;
    private Context context;
    public MyAdapture(Context context, List<ListItem> listItems){
        this.context = context;
        this.listItems = listItems;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super();
        }
    }
}
