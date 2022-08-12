package sg.edu.rp.c346.id20026389.carparkavailability;

import java.util.ArrayList;

public class Carpark {
    private String carparkNumber;
    private int total_lots;
    private String lot_type;
    private int lots_available;

    public Carpark(String carparkNumber, int total_lots, String lot_type, int lots_available) {
        this.carparkNumber = carparkNumber;
        this.total_lots = total_lots;
        this.lot_type = lot_type;
        this.lots_available = lots_available;
    }

    public String getCarparkNumber() {
        return carparkNumber;
    }

    public void setCarparkNumber(String carparkNumber) {
        this.carparkNumber = carparkNumber;
    }

    public int getTotal_lots() {
        return total_lots;
    }

    public void setTotal_lots(int total_lots) {
        this.total_lots = total_lots;
    }

    public String getLot_type() {
        return lot_type;
    }

    public void setLot_type(String lot_type) {
        this.lot_type = lot_type;
    }

    public int getLots_available() {
        return lots_available;
    }

    public void setLots_available(int lots_available) {
        this.lots_available = lots_available;
    }

    @Override
    public String toString() {
        return "CARPARK\n" +"Carpark Number: " + carparkNumber +
                "\nLot Type: " + lot_type+ "\nTotal Lots: " + total_lots +
                 "\nLots Available: " + lots_available;
    }
}
