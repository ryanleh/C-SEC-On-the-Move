package com.app.csec_otm.interfaces;

import com.app.csec_otm.R;


/**
 * Created by ryanleh on 7/28/15.
 */
public class ProductFile {
    private String id;
    private String name;
    private String parent_id;

    public ProductFile(Integer id,String name,String parent_id){
        this.name = name;
        this.id = this.name + id;
        this.parent_id = parent_id;
    }


    public interface TABLES{
        String CATEGORIES = "base_techcategory";
        String CAPABILITIES = "base_techcapability";
        String TECHNOLOGIES = "base_technology";
        String TECH_TYPES = "base_techtypes";
    }

    public interface CATEGORIES_COLUMNS{
        String ID = "base_techcategory._id";
        String CAT = "base_techcategory.cat";
        String SLUG = "base_techcategory.slug";
    }

    public interface CAPABILITIES_COLUMNS{
        String ID = "base_techcapability._id";
        String CAT_ID = "base_techcapability.cat_id";
        String CAP = "base_techcapability.cap";
        String SLUG = "base_techcapability.slug";
        String CAP_ID = "base_techcapability.cap_id";

    }
    public interface TECHNOLOGIES_COLUMNS{
        String ID = "base_technology._id";
        String CAT_ID = "base_technology.cat_id";
        String CAP_ID = "base_technology.cap_id";
        String TECHNOLOGY = "base_technology.technology";
        String SLUG = "base_technology.slug";
    }
    public interface TECH_TYPES_COLUMNS{
        String ID = "base_techtypes._id";
        String CAT_ID = "base_techtypes.cat_id";
        String CAP_ID = "base_techtypes.cap_id";
        String TECHNOLOGY_ID = "base_techtypes.technology_id";
        String TECH_TYPE = "base_techtypes.tech_type";
        String SLUG = "base_techtypes.slug";
    }

    public String getId() {
        return id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public String getName() {
        return name;
    }
}
