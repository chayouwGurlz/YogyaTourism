package com.ciaocollect.yogyatourism;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_TOURISM = "extra_tourism";
    ImageView ivPhoto;
    TextView tvTitle;
    TextView tvPlace;
    TextView tvOverview;
    Button btLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivPhoto = (ImageView)findViewById(R.id.detail_photo);
        tvTitle = (TextView) findViewById(R.id.detail_title);
        tvPlace = (TextView) findViewById(R.id.detail_place);
        tvOverview = (TextView) findViewById(R.id.detail_overview);
        btLocation = (Button) findViewById(R.id.detail_location);

        final Tourism tourism = getIntent().getParcelableExtra(EXTRA_TOURISM);
        Glide.with(this)
                .load(tourism.getTourism_picture())
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
                .into(ivPhoto);
        tvTitle.setText(tourism.getTourism_name());
        tvPlace.setText(tourism.getTourism_place());
        tvOverview.setText(tourism.getTourism_detail());
        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.co.id/maps?q=" + tourism.getTourism_place()));
                startActivity(intent);
            }
        });
    }
}
