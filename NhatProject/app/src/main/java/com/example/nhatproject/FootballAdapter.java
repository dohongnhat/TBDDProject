package com.example.nhatproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class FootballAdapter extends RecyclerView.Adapter<FootballAdapter.FootballGameViewHolder> {
    Context context;
    List<FootballGame> list;

    public FootballAdapter(Context context, List<FootballGame> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FootballAdapter.FootballGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.list_item,parent,false);
        FootballGameViewHolder viewHolder=new FootballGameViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FootballAdapter.FootballGameViewHolder holder, int position) {
        holder.homeTeam.setText(list.get(position).getHomeTeam());
        holder.awayTeam.setText(list.get(position).getAwayTeam());
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getTime());
//        FootballGame footballGame = new FootballGame(list.get(position).getHomeTeam(),list.get(position).getAwayTeam(),
//                list.get(position).getDate(),list.get(position).getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, EditGameActivity.class);
                intent.putExtra("game",list.get(position));
//                intent.putExtra("game", footballGame);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FootballGameViewHolder extends RecyclerView.ViewHolder {
        TextView homeTeam, awayTeam, date, time;

        public FootballGameViewHolder(@NonNull View view) {
            super(view);
            homeTeam = view.findViewById(R.id.txthometeam);
            awayTeam = view.findViewById(R.id.txtawayteam);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);
        }
    }
}
