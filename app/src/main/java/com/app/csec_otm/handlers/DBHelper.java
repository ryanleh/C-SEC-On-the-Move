package com.app.csec_otm.handlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ViewDebug;

import com.app.csec_otm.R;
import com.app.csec_otm.holders.IconTreeItemHolder;
import com.app.csec_otm.interfaces.Product;
import com.app.csec_otm.interfaces.ProductFile;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.unnamed.b.atv.model.TreeNode;

import java.util.LinkedHashMap;


/**
 * Created by ryanleh on 7/28/15.
 */
public class DBHelper extends SQLiteAssetHelper{

    private static final String DATABASE_NAME = "csec.db";
    private static final int DATABASE_VERSION = 1;

    private static final String query_categories = "SELECT " + ProductFile.CATEGORIES_COLUMNS.ID + "," +
            ProductFile.CATEGORIES_COLUMNS.CAT + " FROM " + ProductFile.TABLES.CATEGORIES +
            " ORDER BY " + ProductFile.CATEGORIES_COLUMNS.CAT + " ASC";

    private static final String query_capabilities = "SELECT " + ProductFile.CAPABILITIES_COLUMNS.ID + "," +
            ProductFile.CAPABILITIES_COLUMNS.CAP + "," + ProductFile.CATEGORIES_COLUMNS.CAT + "," +
            ProductFile.CATEGORIES_COLUMNS.ID + " FROM " + ProductFile.TABLES.CATEGORIES +
            " INNER JOIN " + ProductFile.TABLES.CAPABILITIES + " ON " + ProductFile.CATEGORIES_COLUMNS.ID
            + " = " + ProductFile.CAPABILITIES_COLUMNS.CAT_ID + " ORDER BY " + ProductFile.CAPABILITIES_COLUMNS.CAP;

    private static final String query_technologies = "SELECT " + ProductFile.TECHNOLOGIES_COLUMNS.ID + "," +
            ProductFile.TECHNOLOGIES_COLUMNS.TECHNOLOGY + "," + ProductFile.CATEGORIES_COLUMNS.CAT +
            "," + ProductFile.CATEGORIES_COLUMNS.ID + "," + ProductFile.CAPABILITIES_COLUMNS.CAP +
            "," + ProductFile.CAPABILITIES_COLUMNS.ID + " FROM " + ProductFile.TABLES.CATEGORIES +
            " INNER JOIN " + ProductFile.TABLES.CAPABILITIES + " ON " + ProductFile.CATEGORIES_COLUMNS.ID
            + "=" + ProductFile.CAPABILITIES_COLUMNS.CAT_ID + " INNER JOIN " + ProductFile.TABLES.TECHNOLOGIES
            + " ON " + ProductFile.CAPABILITIES_COLUMNS.ID + "=" + ProductFile.TECHNOLOGIES_COLUMNS.CAP_ID
            + " ORDER BY " + ProductFile.TECHNOLOGIES_COLUMNS.TECHNOLOGY;

    private static final String query_tech_types = "SELECT " + ProductFile.TECH_TYPES_COLUMNS.ID
            + "," + ProductFile.TECH_TYPES_COLUMNS.TECH_TYPE + "," + ProductFile.CATEGORIES_COLUMNS.CAT +
            "," + ProductFile.CATEGORIES_COLUMNS.ID + "," + ProductFile.CAPABILITIES_COLUMNS.CAP +
            "," + ProductFile.CAPABILITIES_COLUMNS.ID + "," + ProductFile.TECHNOLOGIES_COLUMNS.TECHNOLOGY
            + "," + ProductFile.TECHNOLOGIES_COLUMNS.ID + " FROM " + ProductFile.TABLES.CATEGORIES
            + " INNER JOIN " + ProductFile.TABLES.CAPABILITIES + " ON " + ProductFile.CATEGORIES_COLUMNS.ID
            + "=" + ProductFile.CAPABILITIES_COLUMNS.CAT_ID + " INNER JOIN " + ProductFile.TABLES.TECHNOLOGIES
            + " ON " + ProductFile.CAPABILITIES_COLUMNS.ID + "=" + ProductFile.TECHNOLOGIES_COLUMNS.CAP_ID
            + " INNER JOIN " + ProductFile.TABLES.TECH_TYPES + " ON " + ProductFile.TECHNOLOGIES_COLUMNS.ID
            + "=" + ProductFile.TECH_TYPES_COLUMNS.TECHNOLOGY_ID + " ORDER BY " + ProductFile.TECH_TYPES_COLUMNS.TECH_TYPE;



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }

    public LinkedHashMap<String,TreeNode> PopulateHash(){
        LinkedHashMap<String,TreeNode> node = getProductFiles();
        node = getProducts(node);
        return node;
    }

    public LinkedHashMap<String,TreeNode> getProductFiles(){
        LinkedHashMap<String,TreeNode> node = new LinkedHashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        ProductFile file;
        String parent_id;

        Cursor cursor = db.rawQuery(query_categories, null);
        if(cursor.moveToFirst()) {
            do {
                file = new ProductFile(cursor.getInt(0),cursor.getString(1), null);
                node.put(file.getId(),new TreeNode(new IconTreeItemHolder.IconTreeItem(
                        R.string.ic_folder,file.getName())));
            } while (cursor.moveToNext());
        }
        cursor.close();

        cursor = db.rawQuery(query_capabilities, null);
        if(cursor.moveToFirst()) {
            do {
                file = new ProductFile(cursor.getInt(0),cursor.getString(1), cursor.getString(2)
                        + cursor.getInt(3));
                node.put(file.getParent_id() + ":" + file.getId(),new TreeNode(
                        new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,file.getName())));
            } while (cursor.moveToNext());
        }

        cursor = db.rawQuery(query_technologies, null);
        if(cursor.moveToFirst()) {
            do {
                parent_id = cursor.getString(2) + cursor.getString(3) + ":" + cursor.getString(4)
                        + cursor.getString(5);
                file = new ProductFile(cursor.getInt(0),cursor.getString(1), parent_id);
                node.put(file.getParent_id() + ":" + file.getId(),new TreeNode(
                        new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,file.getName())));
            } while (cursor.moveToNext());
        }

        cursor = db.rawQuery(query_tech_types, null);
        if(cursor.moveToFirst()) {
            do {
                parent_id = cursor.getString(2) + cursor.getString(3) + ":" + cursor.getString(4)
                        + cursor.getString(5) + ":" + cursor.getString(6) + cursor.getString(7);
                file = new ProductFile(cursor.getInt(0),cursor.getString(1), parent_id);
                node.put(file.getParent_id() + ":" + file.getId(),new TreeNode(
                        new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,file.getName())));
            } while (cursor.moveToNext());
        }

        return node;
    }

    public LinkedHashMap<String,TreeNode> getProducts(LinkedHashMap<String,TreeNode> node){

        return node;
    }





    /**public Cursor getEmployees() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"_id", "cat"};
        String sqlTables = "base_techcategory";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        return c;
    }

    public void add(TechCategory contact){
        SQLiteDatabase db = this.getWritableDatabase();
        int id = 7;
        String cat = "hello";
        String slug = "idk";
        String CreateHello ="INSERT INTO base_techcategory (_id, cat, slug) " +
                "VALUES ('" + id + "', '" + cat + "', '"  + slug + "')";
        db.execSQL(CreateHello);
        db.close(); // Closing database connection
    }**/
}
