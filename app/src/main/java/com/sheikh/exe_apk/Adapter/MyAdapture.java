package com.sheikh.exe_apk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sheikh.exe_apk.Model.ListItem;
import com.sheikh.exe_apk.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

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
        Picasso.get().load(item.getImage_link()).fit().centerCrop().error(R.drawable.logo_e).into(holder.image);
        holder.title.setText(slice(item.getTitle(), 50));
        holder.description.setText(slice(item.getDescription(), 80));
        holder.timeAgo.setText(timeAgo.getTimeAgo(item.getTimeAgo(), context));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView description;
        private TextView timeAgo;
        private Button read_more;
        public ViewHolder(@NonNull View view) {
            super(view);
            image = view.findViewById(R.id.image_post);
            title = view.findViewById(R.id.title_textView);
            description = view.findViewById(R.id.description);
            timeAgo = view.findViewById(R.id.time);
            read_more = view.findViewById(R.id.show_post);
        }
    }
    public String slice(String text, int max){
        if (text.length() > max){
            return text.substring(0, max-3).concat("...");
        } else {
            return text;
        }
    }
}
