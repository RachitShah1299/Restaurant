package com.example.rachitshah.drawerlayout.Recent;

public class Res_Rec_DataModel {

    String volname;
    String dlocs;
    String reqids;
    String dons;

    public Res_Rec_DataModel(String vname, String dloc, String reqid, String don) {
        this.volname = vname;
        this.dlocs = dloc;
        this.reqids = reqid;
        this.dons = don;
    }


    public String getVolname() {
        return volname;
    }

    public void setVolname(String volname) {
        this.volname = volname;
    }

    public String getdlocs() {
        return dlocs;
    }

    public void setdlocs(String dlocs) {
        this.dlocs = dlocs;
    }

    public String getReqids() {
        return reqids;
    }

    public void setReqids(String reqids) {
        this.reqids = reqids;
    }

    public String getDons() {
        return dons;
    }

    public void setDons(String dons) {
        this.dons = dons;
    }
}
