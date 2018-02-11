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

import butterknife.BindView;
import butterknife.ButterKnife;
import ht.mbds.flicks.R;
import ht.mbds.flicks.model.Movie;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Ermano
 * on 2/11/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    class ViewHolder {
        @BindView(R.id.movie_item_img_cover) ImageView img_cover;
        @BindView(R.id.movie_item_img_play) ImageView img_play;
        @BindView(R.id.movie_item_tv_title) TextView tv_tile;
        @BindView(R.id.movie_item_tv_desc) TextView tv_desc;
        @BindView(R.id.movie_item_ll_desc) LinearLayout ll_desc;
        @BindView(R.id.movie_item_ll_cover) RelativeLayout rl_cover;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
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

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_item, parent, false);

            viewHolder = new ViewHolder(convertView);

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
                viewHolder.ll_desc.setVisibility(View.GONE);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewHolder.ll_desc.setVisibility(View.VISIBLE);
            }

            viewHolder.img_play.setVisibility(View.VISIBLE);

            imgUri += "w1280/" + movie.getBackdrop_path();

        }else {
            viewHolder.ll_desc.setVisibility(View.VISIBLE);
            viewHolder.img_play.setVisibility(View.GONE);
            imgUri += "w500/" + movie.getPoster_path();
        }

        Picasso.with(getContext())
                .load(imgUri)
                .placeholder(R.drawable.loading)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(viewHolder.img_cover, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        viewHolder.tv_tile.setText(movie.getOriginal_title());
        viewHolder.tv_desc.setText(String.format(Locale.US, "%s ...", movie.getOverview().substring(0, 100)));

        return convertView;
    }
}
