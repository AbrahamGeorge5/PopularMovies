package com.example.android.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abgeorge on 10/1/2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Trailer> trailers;
    private final static String YOUTUBE_IMAGE_URL = "http://img.youtube.com/vi";
    private final static String IMAGE_NUMBER = "0.jpg";
    private final static String YOUTUBE_VIDEO_URL_APP = "vnd.youtube:";
    private final static String YOUTUBE_VIDEO_URL_BROWSER = "https://www.youtube.com/watch?v=";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgView;

        public MyViewHolder(View view) {
            super(view);
            imgView = (ImageView) view.findViewById(R.id.imgView);

        }
    }


    public TrailerAdapter(Context context, ArrayList<Trailer> trailers) {
        this.context = context;
        this.trailers = trailers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Uri baseUri = Uri.parse(YOUTUBE_IMAGE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(trailers.get(position).getKey());
        uriBuilder.appendPath(IMAGE_NUMBER);
        Picasso.with(context).load(uriBuilder.toString()).fit().centerCrop().into(holder.imgView);
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_VIDEO_URL_APP + trailers.get(position).getKey()));
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(YOUTUBE_VIDEO_URL_BROWSER + trailers.get(position).getKey()));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
