package com.app.csec_otm.interfaces;

public class pInterf
{
    public static abstract interface AUTH_COLS
    {
        public static final String ID = "auth_user._id";
        public static final String NAME = "auth_user.username";
    }

    public static abstract interface TABLES
    {
        public static final String AUTH_USERS = "auth_user";
        public static final String BASE_VERSION = "base_version";
    }

    public static abstract interface V_COLS
    {
        public static final String ID = "base_version._id";
        public static final String LIC_T = "base_version.lic_type";
        public static final String LIC_TM = "base_version.lic_term";
        public static final String MFG_ID = "base_version.mfg_id";
        public static final String NAME_ID = "base_version.name_id";
        public static final String PRC = "base_version.price";
        public static final String REL_D = "base_version.rel_date";
        public static final String REL_T = "base_version.rel_type";
        public static final String SLUG = "base_version.slug";
        public static final String V = "base_version.version";
        public static final String WP = "base_version.whitepaper";
    }
}
