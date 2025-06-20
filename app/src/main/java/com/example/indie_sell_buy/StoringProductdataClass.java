package com.example.indie_sell_buy;

public class StoringProductdataClass {

    private String Pname;
    private String Pdescription;
    private String Pprice;
    private String Pdiscountedprice;
    private String PImageUpload;

    public StoringProductdataClass(String pname, String pdescription, String pprice, String pdiscountedprice) {
        this.Pname = pname;
        this.Pdescription = pdescription;
        this.Pprice = pprice;
        this.Pdiscountedprice = pdiscountedprice;
        this.PImageUpload = PImageUpload;
    }

    public StoringProductdataClass() {

    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getPdescription() {
        return Pdescription;
    }

    public void setPdescription(String pdescription) {
        Pdescription = pdescription;
    }

    public String getPprice() {
        return Pprice;
    }

    public void setPprice(String pprice) {
        Pprice = pprice;
    }

    public String getPdiscountedprice() {
        return Pdiscountedprice;
    }

    public void setPdiscountedprice(String pdiscountedprice) {
        Pdiscountedprice = pdiscountedprice;
    }

    public String getPImageUpload() {


        return PImageUpload;
    }

    public void setPImageUpload(String PImageUpload) {

        this.PImageUpload = PImageUpload;
    }
}

