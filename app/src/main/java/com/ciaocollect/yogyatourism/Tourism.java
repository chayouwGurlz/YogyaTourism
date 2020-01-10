package com.ciaocollect.yogyatourism;

import org.json.JSONObject;

public class Tourism {
    private String tourism_name;
    private String tourism_place;
    private String tourism_detail;
    private String tourism_picture;

    public String getTourism_name() {
        return tourism_name;
    }

    public String getTourism_place() {
        return tourism_place;
    }

    public String getTourism_detail() {
        return tourism_detail;
    }

    public String getTourism_picture() {
        return tourism_picture;
    }

    public Tourism(JSONObject object) {
        try{
            String tourism_name = object.getString("nama_pariwisata");
            String tourism_place = object.getString("alamat_pariwisata");
            String tourism_detail = object.getString("detail_pariwisata");
            String tourism_picture = object.getString("gambar_pariwisata");
            this.tourism_name = tourism_name;
            this.tourism_place = tourism_place;
            this.tourism_detail = tourism_detail;
            this.tourism_picture = tourism_picture;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
