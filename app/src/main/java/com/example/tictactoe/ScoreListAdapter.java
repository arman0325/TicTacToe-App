package com.example.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ScoreListAdapter extends ArrayAdapter<Grades> {

    private static final String TAG = "ScoreListAdapter";
    private Context mContext;
    int mRes;

    public ScoreListAdapter(Context context, int resource, List<Grades> objects){
        super(context, resource, objects);
        mContext = context;
        mRes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String no = getItem(position).getNo();
        String id = getItem(position).getId();
        String score = getItem(position).getScore();
        String gameTime = getItem(position).getGameTime();

        Grades name = new Grades(no,id,score,gameTime);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRes,parent,false);

        TextView textViewNo = (TextView)convertView.findViewById(R.id.textViewNo);
        TextView textViewid = (TextView)convertView.findViewById(R.id.textViewId);
        TextView textViewscore = (TextView)convertView.findViewById(R.id.textViewScore);
        TextView textViewgameTime = (TextView)convertView.findViewById(R.id.textViewTime);

        textViewNo.setText(no);
        textViewid.setText(id);
        textViewscore.setText(score);
        textViewgameTime.setText(gameTime);

        return convertView;
    }
}
