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
        public static final String BASE_CAP = "base_techcapability.cap";
        public static final String BASE_CAT_ID = "base_techcapability.cat_id";
        public static final String ID = "base_techcapability._id";
    }

    public static abstract interface BASE_CAT_COLS
    {
        public static final String BASE_CAT = "base_techcategory.cat";
        public static final String ID = "base_techcategory._id";
    }

    public static abstract interface MFG_COLS
    {
        public static final String ID = "base_manufacturer._id";
        public static final String MFG = "base_manufacturer.mfg";
        public static final String SLUG = "base_manufacturer.slug";
        public static final String WEB = "base_manufacturer.website";
    }

    public static abstract interface P_COLS
    {
        public static final String DESCRIP = "base_product.description";
        public static final String ID = "base_product._id";
        public static final String LOGO = "base_product.logo";
        public static final String MFG_ID = "base_product.mfg_id";
        public static final String NAME = "base_product.name";
        public static final String SLUG = "base_product.slug";
    }

    public static abstract interface P_SQL_COMMANDS
    {
        public static final String query = "SELECT base_techcategory.cat,base_techcapability.cap,base_technology.technology,base_techtypes.tech_type,base_manufacturer.mfg,base_product.name,base_product.description FROM base_techcategory INNER JOIN base_techcapability ON base_techcategory._id=base_techcapability.cat_id INNER JOIN base_technology ON base_techcapability._id=base_technology.cap_id INNER JOIN base_techtypes ON base_technology._id=base_techtypes.technology_id INNER JOIN base_version_tech_type ON base_techtypes._id=base_version_tech_type.techtypes_id INNER JOIN base_version ON base_version_tech_type.version_id=base_version._id INNER JOIN base_manufacturer ON base_version.mfg_id=base_manufacturer._id INNER JOIN base_product ON base_manufacturer._id=base_product.mfg_id ORDER BY base_product.name";
        public static final String query_capabilities = "SELECT base_techcategory.cat,base_techcapability.cap FROM base_techcategory INNER JOIN base_techcapability ON base_techcategory._id = base_techcapability.cat_id ORDER BY base_techcapability.cap";
        public static final String query_categories = "SELECT base_techcategory.cat FROM base_techcategory ORDER BY base_techcategory.cat ASC";
        public static final String query_tech_types = "SELECT base_techcategory.cat,base_techcapability.cap,base_technology.technology,base_techtypes.tech_type FROM base_techcategory INNER JOIN base_techcapability ON base_techcategory._id=base_techcapability.cat_id INNER JOIN base_technology ON base_techcapability._id=base_technology.cap_id INNER JOIN base_techtypes ON base_technology._id=base_techtypes.technology_id ORDER BY base_techtypes.tech_type";
        public static final String query_technologies = "SELECT base_techcategory.cat,base_techcapability.cap,base_technology.technology FROM base_techcategory INNER JOIN base_techcapability ON base_techcategory._id=base_techcapability.cat_id INNER JOIN base_technology ON base_techcapability._id=base_technology.cap_id ORDER BY base_technology.technology";
        public static final String query_product_rating = "SELECT auth_user.username,base_evalscores.subcap_element_type,base_evalscores.score_boolean,base_evalscores.score_integer,base_evalscores.not_applicable FROM auth_user INNER JOIN base_evaluation ON auth_user._id=base_evaluation.user_id INNER JOIN base_version ON base_evaluation.version_id=base_version._id INNER JOIN base_product ON base_version.name_id=base_product._id INNER JOIN base_evalscores ON base_evaluation._id=base_evalscores.evaluation_id INNER JOIN base_subcapabilityelement ON base_evalscores.subcap_element_id=base_subcapabilityelement._id WHERE base_product.name=?";

    }

    public static abstract interface TABLES
    {
        public static final String BASE_CAP = "base_techcapability";
        public static final String BASE_CAT = "base_techcategory";
        public static final String BASE_MFG = "base_manufacturer";
        public static final String BASE_P = "base_product";
        public static final String BASE_TECH = "base_technology";
        public static final String BASE_TECH_T = "base_techtypes";
        public static final String BASE_V = "base_version";
        public static final String BASE_VTECH_T = "base_version_tech_type";
    }

    public static abstract interface TECH_COLS
    {
        public static final String BASE_CAP_ID = "base_technology.cap_id";
        public static final String BASE_CAT_ID = "base_technology.cat_id";
        public static final String ID = "base_technology._id";
        public static final String TECH = "base_technology.technology";
    }

    public static abstract interface TECH_T_COLS
    {
        public static final String BASE_CAP_ID = "base_techtypes.cap_id";
        public static final String BASE_CAT_ID = "base_techtypes.cat_id";
        public static final String ID = "base_techtypes._id";
        public static final String TECH_ID = "base_techtypes.technology_id";
        public static final String TECH_T = "base_techtypes.tech_type";
    }

    public static abstract interface VTECH_COLS
    {
        public static final String ID = "base_version_tech_type._id";
        public static final String TECHT_ID = "base_version_tech_type.techtypes_id";
        public static final String VER_ID = "base_version_tech_type.version_id";
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
