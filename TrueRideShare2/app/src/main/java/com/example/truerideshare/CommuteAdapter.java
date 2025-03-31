package com.example.truerideshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CommuteAdapter extends RecyclerView.Adapter<CommuteAdapter.CommuteViewHolder> {

    private List<Commute> commuteList;

    public CommuteAdapter(List<Commute> commuteList) {
        this.commuteList = commuteList;
    }

    @Override
    public CommuteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commute, parent, false);
        return new CommuteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommuteViewHolder holder, int position) {
        Commute commute = commuteList.get(position);
        holder.dateTextView.setText(commute.getDate());
        holder.timeTextView.setText(commute.getTime());
        holder.departureTextView.setText(commute.getDeparture());
        holder.destinationTextView.setText(commute.getDestination());
        holder.seatsTextView.setText("Seats: " + commute.getMaxSeats() + " (Occupied: " + commute.getOccupiedSeats() + ")");
    }

    @Override
    public int getItemCount() {
        return commuteList.size();
    }

    public static class CommuteViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView, timeTextView, departureTextView, destinationTextView, seatsTextView;

        public CommuteViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            departureTextView = itemView.findViewById(R.id.departureTextView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
            seatsTextView = itemView.findViewById(R.id.seatsTextView);
        }
    }
}
