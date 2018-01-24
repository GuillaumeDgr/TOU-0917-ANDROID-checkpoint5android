package fr.wcs.wcstravel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by apprenti on 1/24/18.
 */

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {

    private List<TravelModel> mTravelModelList;

    public TravelAdapter(List<TravelModel> travelModelList) {
        mTravelModelList = travelModelList;
    }

    @Override
    public TravelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flight, parent, false);

        return new TravelAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TravelAdapter.ViewHolder holder, int position) {
        TravelModel travelModel = mTravelModelList.get(position);

        holder.mTextViewAirline.setText(travelModel.getAirline());
        holder.mTextDepartureDate.setText(travelModel.getDepartureDate());
        holder.mTextViewReturnDate.setText(travelModel.getReturnDate());
        holder.mTextViewPrice.setText(travelModel.getPrice());
        holder.mTextViewTravel.setText(travelModel.getTravel());
    }

    @Override
    public int getItemCount() {
        return mTravelModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewAirline, mTextDepartureDate, mTextViewReturnDate, mTextViewPrice, mTextViewTravel;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextViewAirline = itemView.findViewById(R.id.textViewAirline);
            mTextDepartureDate = itemView.findViewById(R.id.textDepartureDate);
            mTextViewReturnDate = itemView.findViewById(R.id.textViewReturnDate);
            mTextViewPrice = itemView.findViewById(R.id.textViewPrice);
            mTextViewTravel = itemView.findViewById(R.id.textViewTravel);
        }
    }
}
