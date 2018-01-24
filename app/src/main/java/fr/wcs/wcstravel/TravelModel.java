package fr.wcs.wcstravel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by apprenti on 1/24/18.
 */

public class TravelModel implements Parcelable {

    private String airline;
    private String departure_date;
    private String price;
    private String return_date;
    private String travel;

    public TravelModel() {}

    protected TravelModel(Parcel in) {
        airline = in.readString();
        departure_date = in.readString();
        price = in.readString();
        return_date = in.readString();
        travel = in.readString();
    }

    public static final Creator<TravelModel> CREATOR = new Creator<TravelModel>() {
        @Override
        public TravelModel createFromParcel(Parcel in) {
            return new TravelModel(in);
        }

        @Override
        public TravelModel[] newArray(int size) {
            return new TravelModel[size];
        }
    };

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(airline);
        dest.writeString(departure_date);
        dest.writeString(price);
        dest.writeString(return_date);
        dest.writeString(travel);
    }
}
