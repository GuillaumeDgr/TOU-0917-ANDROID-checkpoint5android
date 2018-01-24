package fr.wcs.wcstravel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

/**
 * Created by apprenti on 1/24/18.
 */

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {

    private List<TravelModel> mModelList;

    public TravelAdapter(List<TravelModel> travelModelList) {
        mModelList = travelModelList;
    }

    @Override
    public TravelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flight, parent, false);

        return new TravelAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TravelAdapter.ViewHolder holder, int position) {
        TravelModel model = mModelList.get(position);

        holder.mTextViewAirline.setText(model.getAirline());
        holder.mTextDepartureDate.setText(model.getDeparture_date());
        holder.mTextViewReturnDate.setText(model.getReturn_date());
        holder.mTextViewPrice.setText(model.getPrice());
        holder.mTextViewTravel.setText(model.getTravel());

        holder.mImageButtonConvertEuro.setVisibility(View.GONE);
        holder.mImageButtonConvertEuro.setOnClickListener(v -> {
            Double priceDouble = Double.parseDouble(holder.mTextViewPrice.getText().toString());
            String newPrice = convertPrice(priceDouble, "EUR", "USD");
            holder.mTextViewPrice.setText(newPrice);

            holder.mImageButtonConvertEuro.setVisibility(View.GONE);
            holder.mImageButtonConvertDollar.setVisibility(View.VISIBLE);
        });

        holder.mImageButtonConvertDollar.setOnClickListener(v -> {
            String price = model.getPrice();
            Double priceDouble = Double.parseDouble(price);
            String newPrice = convertPrice(priceDouble, "USD", "EUR");
            holder.mTextViewPrice.setText(newPrice);

            holder.mImageButtonConvertEuro.setVisibility(View.VISIBLE);
            holder.mImageButtonConvertDollar.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewAirline, mTextDepartureDate, mTextViewReturnDate, mTextViewPrice, mTextViewTravel;
        ImageButton mImageButtonConvertEuro, mImageButtonConvertDollar;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextViewAirline = itemView.findViewById(R.id.textViewAirline);
            mTextDepartureDate = itemView.findViewById(R.id.textDepartureDate);
            mTextViewReturnDate = itemView.findViewById(R.id.textViewReturnDate);
            mTextViewPrice = itemView.findViewById(R.id.textViewPrice);
            mTextViewTravel = itemView.findViewById(R.id.textViewTravel);
            mImageButtonConvertEuro = itemView.findViewById(R.id.imageButtonConvertEuro);
            mImageButtonConvertDollar = itemView.findViewById(R.id.imageButtonConvertDollar);
        }
    }

    public String convertPrice(Double price, String currencyEntry, String currencyReturn) {
        String newPriceString = "";
        if (currencyEntry.equals("EUR") && currencyReturn.equals("USD")) {
            Double newPrice = price * 1.24;
            newPriceString = String.valueOf(newPrice);
        }
        if (currencyEntry.equals("USD") && currencyReturn.equals("EUR")) {
            Double newPrice = price * 0.81;
            newPriceString = String.valueOf(newPrice);
        }
        return newPriceString;
    }
}
