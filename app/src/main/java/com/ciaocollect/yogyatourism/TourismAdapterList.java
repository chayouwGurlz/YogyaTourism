package com.ciaocollect.yogyatourism;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class TourismAdapterList extends RecyclerView.Adapter<TourismAdapterList.TourismAdapterHolder> {
    private Context context;
    private ArrayList<Tourism> listTourism = new ArrayList<>();

    public TourismAdapterList(Context context) {
        this.context = context;
    }

    void setListTourism(ArrayList<Tourism> listTourism) {
        this.listTourism = listTourism;
    }

    @NonNull
    @Override
    public TourismAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_layout, viewGroup, false);
        return new TourismAdapterHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TourismAdapterHolder holder, int position) {
        holder.bind(listTourism.get(position));
    }

    @Override
    public int getItemCount() {
        return listTourism.size();
    }

    class TourismAdapterHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView tvName;
        TextView tvPlace;
        TextView tvOverview;

        TourismAdapterHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.img_photo);
            tvName = itemView.findViewById(R.id.txt_title);
            tvPlace = itemView.findViewById(R.id.txt_place);
        }

        void bind(Tourism tourism) {
            Glide.with(context)
                    .load(tourism.getTourism_picture())
                    .override(100, 100)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.baseline_person_black_48dp)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("TAG", "Error loading image", e);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(ivPicture);
            tvName.setText(tourism.getTourism_name());
            tvPlace.setText(tourism.getTourism_place());
        }
    }
}
