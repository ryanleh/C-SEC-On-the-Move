package com.app.csec_otm.interfaces;

public class Product
{
    private String description;
    private String maker;
    private String name;
    private int rating;

    public Product(String paramString1)
    {
        this.name = paramString1;
    }

    public Product(String maker, String name, String description)
    {
        this.maker = maker;
        this.name = name;
        this.description = description;
    }



    public static abstract interface BASE_CAP_COLS
    {
        String BASE_CAP = "base_techcapability.cap";
        String BASE_CAT_ID = "base_techcapability.cat_id";
        String ID = "base_techcapability._id";
    }

    public static abstract interface BASE_CAT_COLS
    {
        String BASE_CAT = "base_techcategory.cat";
        String ID = "base_techcategory._id";
    }

    public static abstract interface MFG_COLS
    {
        String ID = "base_manufacturer._id";
        String MFG = "base_manufacturer.mfg";
        String SLUG = "base_manufacturer.slug";
        String WEB = "base_manufacturer.website";
    }

    public static abstract interface P_COLS
    {
        String DESCRIP = "base_product.description";
        String ID = "base_product._id";
        String LOGO = "base_product.logo";
        String MFG_ID = "base_product.mfg_id";
        String NAME = "base_product.name";
        String SLUG = "base_product.slug";
    }

    public interface P_SQL_COMMANDS
    {
        String query = "SELECT base_techcategory.cat,base_techcapability.cap,base_technology.technology,base_techtypes.tech_type,base_manufacturer.mfg,base_product.name,base_product.description FROM base_techcategory INNER JOIN base_techcapability ON base_techcategory._id=base_techcapability.cat_id INNER JOIN base_technology ON base_techcapability._id=base_technology.cap_id INNER JOIN base_techtypes ON base_technology._id=base_techtypes.technology_id INNER JOIN base_version_tech_type ON base_techtypes._id=base_version_tech_type.techtypes_id INNER JOIN base_version ON base_version_tech_type.version_id=base_version._id INNER JOIN base_manufacturer ON base_version.mfg_id=base_manufacturer._id INNER JOIN base_product ON base_manufacturer._id=base_product.mfg_id ORDER BY base_product.name";
        String query_capabilities = "SELECT base_techcategory.cat,base_techcapability.cap FROM base_techcategory INNER JOIN base_techcapability ON base_techcategory._id = base_techcapability.cat_id ORDER BY base_techcapability.cap";
        String query_categories = "SELECT base_techcategory.cat FROM base_techcategory ORDER BY base_techcategory.cat ASC";
        String query_tech_types = "SELECT base_techcategory.cat,base_techcapability.cap,base_technology.technology,base_techtypes.tech_type FROM base_techcategory INNER JOIN base_techcapability ON base_techcategory._id=base_techcapability.cat_id INNER JOIN base_technology ON base_techcapability._id=base_technology.cap_id INNER JOIN base_techtypes ON base_technology._id=base_techtypes.technology_id ORDER BY base_techtypes.tech_type";
        String query_technologies = "SELECT base_techcategory.cat,base_techcapability.cap,base_technology.technology FROM base_techcategory INNER JOIN base_techcapability ON base_techcategory._id=base_techcapability.cat_id INNER JOIN base_technology ON base_techcapability._id=base_technology.cap_id ORDER BY base_technology.technology";
        String query_product_rating = "SELECT auth_user.username,base_evalscores.subcap_element_type,base_evalscores.score_boolean,base_evalscores.score_integer,base_evalscores.not_applicable FROM auth_user INNER JOIN base_evaluation ON auth_user._id=base_evaluation.user_id INNER JOIN base_version ON base_evaluation.version_id=base_version._id INNER JOIN base_product ON base_version.name_id=base_product._id INNER JOIN base_evalscores ON base_evaluation._id=base_evalscores.evaluation_id INNER JOIN base_subcapabilityelement ON base_evalscores.subcap_element_id=base_subcapabilityelement._id WHERE base_product.name=?";
        String query_product_search = "SELECT base_product.name,base_manufacturer.mfg,base_product.description FROM base_product INNER JOIN base_manufacturer ON base_product.mfg_id = base_manufacturer._id ORDER BY base_product.name ASC";
    }

    public static abstract interface TABLES
    {
        String BASE_CAP = "base_techcapability";
        String BASE_CAT = "base_techcategory";
        String BASE_MFG = "base_manufacturer";
        String BASE_P = "base_product";
        String BASE_TECH = "base_technology";
        String BASE_TECH_T = "base_techtypes";
        String BASE_V = "base_version";
        String BASE_VTECH_T = "base_version_tech_type";
    }

    public static abstract interface TECH_COLS
    {
        String BASE_CAP_ID = "base_technology.cap_id";
        String BASE_CAT_ID = "base_technology.cat_id";
        String ID = "base_technology._id";
        String TECH = "base_technology.technology";
    }

    public static abstract interface TECH_T_COLS
    {
        String BASE_CAP_ID = "base_techtypes.cap_id";
        String BASE_CAT_ID = "base_techtypes.cat_id";
        String ID = "base_techtypes._id";
        String TECH_ID = "base_techtypes.technology_id";
        String TECH_T = "base_techtypes.tech_type";
    }

    public static abstract interface VTECH_COLS
    {
        String ID = "base_version_tech_type._id";
        String TECHT_ID = "base_version_tech_type.techtypes_id";
        String VER_ID = "base_version_tech_type.version_id";
    }
    public String getDescription()
    {
        return this.description;
    }

    public String getMaker()
    {
        return this.maker;
    }

    public String getName()
    {
        return this.name;
    }

}
