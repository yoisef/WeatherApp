package weatherapp.weatherapp;

public class currentobject {

    String mycoun;
    String mycity;
    String mydata;
    String mycondi;
    String mytemp;



    String imgurl;

    public currentobject(String coun,String cit ,String dat,String con,String tempe )
    {
        this.mycoun=coun;
        this.mycity=cit;
        this.mydata=dat;
        this.mycondi=con;
        this.mytemp=tempe;


    }

    public String getMycoun() {
        return mycoun;
    }

    public void setMycoun(String mycoun) {
        this.mycoun = mycoun;
    }

    public String getMycity() {
        return mycity;
    }

    public void setMycity(String mycity) {
        this.mycity = mycity;
    }

    public String getMydata() {
        return mydata;
    }

    public void setMydata(String mydata) {
        this.mydata = mydata;
    }

    public String getMycondi() {
        return mycondi;
    }

    public void setMycondi(String mycondi) {
        this.mycondi = mycondi;
    }

    public String getMytemp() {
        return mytemp;
    }

    public void setMytemp(String mytemp) {
        this.mytemp = mytemp;
    }
    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
