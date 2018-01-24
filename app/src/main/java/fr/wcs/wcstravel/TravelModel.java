package fr.wcs.wcstravel;

/**
 * Created by apprenti on 1/24/18.
 */

public class TravelModel {

    private String mAirline;
    private String mDepartureDate;
    private String mPrice;
    private String mReturnDate;
    private String mTravel;

    public TravelModel() {}

    public TravelModel(String airline, String departureDate, String price, String returnDate, String travel) {
        mAirline = airline;
        mDepartureDate = departureDate;
        mPrice = price;
        mReturnDate = returnDate;
        mTravel = travel;
    }

    public String getAirline() {
        return mAirline;
    }

    public void setAirline(String airline) {
        mAirline = airline;
    }

    public String getDepartureDate() {
        return mDepartureDate;
    }

    public void setDepartureDate(String departureDate) {
        mDepartureDate = departureDate;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getReturnDate() {
        return mReturnDate;
    }

    public void setReturnDate(String returnDate) {
        mReturnDate = returnDate;
    }

    public String getTravel() {
        return mTravel;
    }

    public void setTravel(String travel) {
        mTravel = travel;
    }
}
