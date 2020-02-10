package com.example.czirjktibor.reminderapp.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.czirjktibor.reminderapp.MainActivity;
import com.example.czirjktibor.reminderapp.R;
import com.example.czirjktibor.reminderapp.entitys.Alarm;
import com.example.czirjktibor.reminderapp.fragments.DeleteDialogFragment;
import com.example.czirjktibor.reminderapp.fragments.EditorFragment;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Alarm> alarmList;
    private LayoutInflater inflater;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Alarm> data) {
        this.inflater = LayoutInflater.from(context);
        this.alarmList = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.alarm_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        holder.name.setText(alarm.getAlarmName());
        holder.time.setText(simpleDateFormat.format(alarm.getAlarmTime()));

        switch (alarm.getColor()){
            case BLUE:
                holder.circle.setImageTintList(context.getResources().getColorStateList(R.color.colorBlue));
                break;
            case RED:
                holder.circle.setImageTintList(context.getResources().getColorStateList(R.color.colorRed));
                break;
            case YELLOW:
                holder.circle.setImageTintList(context.getResources().getColorStateList(R.color.colorYellow));
                break;
            case ORANGE:
                holder.circle.setImageTintList(context.getResources().getColorStateList(R.color.colorOrange));
                break;
            case GREEN:
                holder.circle.setImageTintList(context.getResources().getColorStateList(R.color.colorGreen));
                break;
            case CYAN:
                holder.circle.setImageTintList(context.getResources().getColorStateList(R.color.colorCyan));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.itemName)
        TextView name;
        @BindView(R.id.itemTime)
        TextView time;
        @BindView(R.id.itemColor)
        ImageView circle;
        @BindView(R.id.itemEditButton)
        Button editButton;
        @BindView(R.id.alarmItem)
        RelativeLayout alarmItem;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            editButton.setOnClickListener(this);
            alarmItem.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            EditorFragment editorFragment = new EditorFragment();
            editorFragment.setAlarm(alarmList.get(getAdapterPosition()));
            ((MainActivity) context).changeFragment(editorFragment);
        }

        @Override
        public boolean onLongClick(View v) {
            DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
            deleteDialogFragment.setAlarm(alarmList.get(getAdapterPosition()));

            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();

            deleteDialogFragment.show(fragmentManager, "");
            return true;
        }
    }
}
