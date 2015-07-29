package com.app.csec_otm.interfaces;

import com.app.csec_otm.R;

/**
 * Created by ryanleh on 7/28/15.
 */
public class Product {

    private int id,rating;
    private String name,maker,description;

    public final String TABLE = "base_product";

    public interface TABLE_COLUMNS {
        String ID = "id";
        String MFG_ID = "mfg_id";
        String NAME = "name";
        String DESCRIPTION = "description";
        String SLUG = "slug";
        String LOGO = "logo";
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }
}
