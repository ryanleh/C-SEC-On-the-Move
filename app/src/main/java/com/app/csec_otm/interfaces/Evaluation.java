package com.app.csec_otm.interfaces;

public class Evaluation
{
    private double average_rating;
    private String name;

    public Evaluation(String name, double rating)
    {
        this.name = name;
        this.average_rating = rating;
    }

    public double getAverage_rating()
    {
        return this.average_rating;
    }

    public String getName()
    {
        return this.name;
    }

    public static abstract interface BASE_CAP_COLS
    {
        public static final String BASE_CAP = "base_capability.cap";
        public static final String ID = "base_capability._id";
    }

    public static abstract interface ES_COLS
    {
        public static final String BOOL_S = "base_evalscores.score_boolean";
        public static final String E_ID = "base_evalscores.evaluation_id";
        public static final String ID = "base_evalscores._id";
        public static final String INT_S = "base_evalscores.score_integer";
        public static final String NA_S = "base_evalscores.not_applicable";
        public static final String SUB_EL_ID = "base_evalscores.subcap_element_id";
        public static final String SUB_EL_T = "base_evalscores.subcap_element_type";
        public static final String TEXT_S = "base_evalscores.score_text";
    }

    public static abstract interface E_COLS
    {
        public static final String AVG_S = "base_evaluation.average_score";
        public static final String COMP = "base_evaluation.completed";
        public static final String DATE = "base_evaluation.date";
        public static final String ID = "base_evaluation._id";
        public static final String STARTD = "base_evaluation.started";
        public static final String SUB = "base_evaluation.submitted";
        public static final String T_ID = "base_evaluation.technology_id";
        public static final String USR_ID = "base_evaluation.user_id";
        public static final String V_ID = "base_evaluation.version_id";
    }

    public static abstract interface Evaluation_SQL_COMMANDS
    {
        public static final String query_cap = "SELECT base_capability.cap FROM base_capability ORDER BY base_capability.cap ASC";
        public static final String query_eval_rating = "SELECT base_product.name,base_evalscores.subcap_element_type,base_evalscores.score_boolean,base_evalscores.score_integer,base_evalscores.not_applicable,base_subcapabilityelement.description FROM base_product INNER JOIN base_version ON base_product._id=base_version.name_id INNER JOIN base_evaluation ON base_version._id=base_evaluation.version_id INNER JOIN auth_user ON base_evaluation.user_id=auth_user._id INNER JOIN base_evalscores ON base_evaluation._id=base_evalscores.evaluation_id INNER JOIN base_subcapabilityelement ON base_evalscores.subcap_element_id=base_subcapabilityelement._id INNER JOIN base_subcapability ON base_subcapabilityelement.subcap_id=base_subcapability._id INNER JOIN base_capability ON base_subcapabilityelement.cap_id=base_capability._id WHERE base_product.name= ? AND auth_user.username = ?";
        public static final String query_eval_root = "SELECT DISTINCT auth_user.username,base_evaluation.date,base_version.version,base_product.name FROM base_product INNER JOIN base_version ON base_product._id = base_version.name_id INNER JOIN base_evaluation ON base_version._id = base_evaluation.version_id INNER JOIN auth_user ON base_evaluation.user_id = auth_user._id INNER JOIN base_evalscores ON base_evaluation._id = base_evalscores.evaluation_id WHERE auth_user._id = base_evaluation.user_id AND base_evaluation._id = base_evalscores.evaluation_id AND base_product.name =?";
        public static final String query_subcap = "SELECT base_capability.cap,base_subcapability.subcap FROM base_subcapability INNER JOIN base_capability ON base_subcapability.cap_id=base_capability._id ORDER BY base_subcapability.subcap ASC ";
        public static final String query_subcap_element = "SELECT base_capability.cap,base_subcapability.subcap,base_subcapabilityelement.subcap_element FROM base_subcapability INNER JOIN base_capability ON base_subcapability.cap_id=base_capability._id INNER JOIN base_subcapabilityelement ON base_capability._id=base_subcapabilityelement.cap_id WHERE base_subcapabilityelement.subcap_id=base_subcapability._id ORDER BY base_subcapabilityelement.subcap_element ASC ";
        public static final String query_user_rating = "SELECT auth_user.username,base_evalscores.subcap_element_type,base_evalscores.score_boolean,base_evalscores.score_integer,base_evalscores.not_applicable,base_evaluation.date,base_version.version,base_product.name FROM auth_user INNER JOIN base_evaluation ON auth_user._id=base_evaluation.user_id INNER JOIN base_version ON base_evaluation.version_id=base_version._id INNER JOIN base_product ON base_version.name_id=base_product._id INNER JOIN base_evalscores ON base_evaluation._id=base_evalscores.evaluation_id INNER JOIN base_subcapabilityelement ON base_evalscores.subcap_element_id=base_subcapabilityelement._id WHERE base_product.name=? AND auth_user.username=?";
    }

    public static abstract interface SUB_BASE_CAP_COLS
    {
        public static final String BASE_CAP_ID = "base_subcapability.cap_id";
        public static final String ID = "base_subcapability._id";
        public static final String SUBBASE_CAP = "base_subcapability.subcap";
    }

    public static abstract interface SUB_BASE_CAP_E_COLS
    {
        public static final String BASE_CAP_ID = "base_subcapabilityelement.cap_id";
        public static final String DES = "base_subcapabilityelement.description";
        public static final String ID = "base_subcapabilityelement._id";
        public static final String SUB_BASE_CAP_E = "base_subcapabilityelement.subcap_element";
        public static final String SUB_BASE_CAP_ID = "base_subcapabilityelement.subcap_id";
    }

    public static abstract interface TABLES
    {
        public static final String BASE_BASE_CAP = "base_capability";
        public static final String BASE_E = "base_evaluation";
        public static final String BASE_ES = "base_evalscores";
        public static final String BASE_SUB_BASE_CAP = "base_subcapability";
        public static final String BASE_SUB_BASE_CAP_E = "base_subcapabilityelement";
    }
}
