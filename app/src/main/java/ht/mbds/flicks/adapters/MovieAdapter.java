package ht.mbds.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import ht.mbds.flicks.R;
import ht.mbds.flicks.model.Movie;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Ermano
 * on 2/11/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    class ViewHolder {
        ImageView cover;
        ImageView play;
        TextView tile;
        TextView desc;
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;
    }

    public MovieAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, R.layout.movie_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Movie movie = getItem(position);
        String base_url = "https://image.tmdb.org/t/p/";
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_item, parent, false);

            viewHolder.cover = (ImageView) convertView.findViewById(R.id.movie_item_img_cover);
            viewHolder.play = (ImageView) convertView.findViewById(R.id.movie_item_img_play);
            viewHolder.tile = (TextView) convertView.findViewById(R.id.movie_item_tv_title);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.movie_item_tv_desc);
            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.movie_item_ll_desc);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.movie_item_ll_cover);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /*// Use OkHttpClient singleton
        OkHttpClient client = new OkHttpClient();
        Picasso picasso = new Picasso.Builder(getContext()).downloader(new OkHttp3Downloader(client)).build();*/
        String imgUri = base_url;


        if (movie.getVote_average() > 5) {

            int orientation = getContext().getResources().getConfiguration().orientation;

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                viewHolder.linearLayout.setVisibility(View.GONE);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewHolder.linearLayout.setVisibility(View.VISIBLE);
            }

            viewHolder.play.setVisibility(View.VISIBLE);

            imgUri += "w1280/" + movie.getBackdrop_path();

        }else {
            viewHolder.linearLayout.setVisibility(View.VISIBLE);
            viewHolder.play.setVisibility(View.GONE);
            imgUri += "w500/" + movie.getPoster_path();
        }

        Picasso.with(getContext())
                .load(imgUri)
                .placeholder(R.drawable.loading)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(viewHolder.cover, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        viewHolder.tile.setText(movie.getOriginal_title());
        viewHolder.desc.setText(String.format(Locale.US, "%s ...", movie.getOverview().substring(0, 100)));

        return convertView;
    }
}
