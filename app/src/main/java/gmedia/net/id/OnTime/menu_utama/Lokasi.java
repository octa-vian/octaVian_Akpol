package gmedia.net.id.OnTime.menu_utama;

public class Lokasi {
     double latitude,  longitude;

     public Lokasi(double latitude, double longitude){
         this.latitude = latitude;
         this.longitude = longitude;
     }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
}
