package com.example.searchjob;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    List<MyJobs> list;

    public MyAdapter(HomeActivity main2Activity, List<MyJobs> list) {
        this.context = main2Activity;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.get().load(list.get(position).getCompany_logo()).into(holder.imageView);
        holder.tv1.setText(list.get(position).getTitle());
        holder.tv2.setText(list.get(position).getCreated_at());
        holder.tv3.setText(list.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageView;
        TextView tv1, tv2, tv3;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tv1 = itemView.findViewById(R.id.jobtitle);
            tv2 = itemView.findViewById(R.id.date_created);
            tv3 = itemView.findViewById(R.id.location);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int pos = getAdapterPosition();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.id, list.get(pos).getId());
            intent.putExtra(DetailActivity.company, list.get(pos).getCompany());
            intent.putExtra(DetailActivity.company_logo, list.get(pos).getCompany_logo());
            intent.putExtra(DetailActivity.company_url, list.get(pos).getCompanyurl());
            intent.putExtra(DetailActivity.created_at, list.get(pos).getCreated_at());
            intent.putExtra(DetailActivity.description, list.get(pos).getDescription());
            intent.putExtra(DetailActivity.how_to_apply, list.get(pos).getHow_to_apply());
            intent.putExtra(DetailActivity.location, list.get(pos).getLocation());
            intent.putExtra(DetailActivity.Type, list.get(pos).getType());
            intent.putExtra(DetailActivity.Url, list.get(pos).getUrl());
            intent.putExtra(DetailActivity.title, list.get(pos).getTitle());

            SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.widgetsp), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(context.getResources().getString(R.string.ST), list.get(pos).getTitle());
            editor.commit();

            Intent intent1 = new Intent(context, AppWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, AppWidget.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(intent1);

            context.startActivity(intent);

        }
    }
}