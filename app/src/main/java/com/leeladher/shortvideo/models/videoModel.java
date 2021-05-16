package com.leeladher.shortvideo.models;

public class videoModel {
    private String vtitle,vurl;

    public videoModel( String name, String videoUri) {
        if (name.trim().equals("")) {
            name = "not available";
        }
        vtitle = name;
        vurl = videoUri;
    }

    public videoModel() {
    }


    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }
}
