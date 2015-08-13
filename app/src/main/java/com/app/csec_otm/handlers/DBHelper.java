package com.app.csec_otm.handlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.csec_otm.R;
import com.app.csec_otm.holders.EvaluationItemHolder;
import com.app.csec_otm.holders.EvaluationItemHolder.EvaluationItem;
import com.app.csec_otm.holders.IconTreeItemHolder;
import com.app.csec_otm.holders.IconTreeItemHolder.IconTreeItem;
import com.app.csec_otm.interfaces.Evaluation;
import com.app.csec_otm.interfaces.Product;
import com.app.csec_otm.search.ResultItem;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DBHelper extends SQLiteAssetHelper
{
    private static final String DATABASE_NAME = "csec.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context paramContext)
    {
        super(paramContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LinkedHashMap<String, TreeNode> PopulateFileHash()
    {
        return getProducts(getProductFiles());
    }

    public LinkedHashMap<String, TreeNode> getProductFiles()
    {
        LinkedHashMap node = new LinkedHashMap();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(Product.P_SQL_COMMANDS.query_categories, null);
        node = ProcessFileQuery(node,cursor);

        cursor = db.rawQuery(Product.P_SQL_COMMANDS.query_capabilities, null);
        node = ProcessFileQuery(node,cursor);

        cursor = db.rawQuery(Product.P_SQL_COMMANDS.query_technologies, null);
        node = ProcessFileQuery(node,cursor);

        cursor = db.rawQuery(Product.P_SQL_COMMANDS.query_tech_types, null);
        return ProcessFileQuery(node, cursor);
    }

    public LinkedHashMap<String, TreeNode> ProcessFileQuery(LinkedHashMap<String, TreeNode> node, Cursor cursor)
    {
        int count= cursor.getColumnCount();
        if (count== 1)
        {
            cursor.moveToFirst();
            do
            {
                Product product = new Product(cursor.getString(0));
                String name = product.getName();
                IconTreeItemHolder.IconTreeItem item = new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, product.getName());
                TreeNode treeNode = new TreeNode(item);
                node.put(name, treeNode);

            } while (cursor.moveToNext());
            cursor.close();
            return node;
        } else {

            cursor.moveToFirst();
            do
            {
                String parent_id = cursor.getString(0);
                for (int j = 1; j < count- 1; j++)
                {
                    parent_id += ":" + cursor.getString(j);
                }
                Product product = new Product(cursor.getString(count- 1));
                IconTreeItemHolder.IconTreeItem item = new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, product.getName());
                TreeNode treeNode = new TreeNode(item);
                node.put(parent_id + ":" + product.getName(), treeNode);
            } while (cursor.moveToNext());
            return node;
        }
    }

    public LinkedHashMap<String, TreeNode> getProducts(LinkedHashMap<String, TreeNode> node)
    {
        Cursor cursor = getReadableDatabase().rawQuery(Product.P_SQL_COMMANDS.query, null);
        cursor.moveToFirst();
        do
        {
            String parent_id = cursor.getString(0);
            for (int i= 1; i< 5; i++)
            {
                parent_id += ":" + cursor.getString(i);
            }
            Product product = new Product(cursor.getString(4), cursor.getString(5), cursor.getString(6));
            IconTreeItemHolder.IconTreeItem item = new IconTreeItemHolder.IconTreeItem(R.string.ic_file,
                    product.getName(), product.getMaker(), product.getDescription());
            TreeNode treeNode = new TreeNode(item);
            node.put(parent_id + product.getName(), treeNode);
        } while (cursor.moveToNext());
        cursor.close();
        return node;
    }

    public List<ResultItem> getSearchProducts(List<ResultItem> list) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor product_cursor = db.rawQuery(Product.P_SQL_COMMANDS.query_product_search,null);
        product_cursor.moveToFirst();
        do{
            list.add(new ResultItem(product_cursor.getString(0),product_cursor.getString(1),0,0,
                    product_cursor.getString(2)));
        }while(product_cursor.moveToNext());
        product_cursor.close();
        return list;
    }



    public LinkedHashMap<String, TreeNode> PopulateEvalRootHash(String name)
    {
        LinkedHashMap<String,TreeNode> node = new LinkedHashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(Evaluation.Evaluation_SQL_COMMANDS.query_eval_root,
                new String[]{name});
        cursor.moveToFirst();
        do
        {
            Cursor cursor1 = db.rawQuery(Evaluation.Evaluation_SQL_COMMANDS.query_user_rating,
                    new String[]{name,cursor.getString(0)});
            Double rating = Double.valueOf(String.format("%.1f", CalcRating(cursor1)));
            cursor1.close();
            Evaluation eval = new Evaluation(cursor.getString(0), rating);
            EvaluationItemHolder.EvaluationItem item = new EvaluationItemHolder.EvaluationItem(eval.getName(),
                    eval.getAverage_rating(), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            TreeNode treeNode = new TreeNode(item);
            node.put(eval.getName(), treeNode);
        } while (cursor.moveToNext());
        cursor.close();
        return node;
    }

    public LinkedHashMap<String, TreeNode> PopulateEvalHash(String name, String evaluator)
    {
        LinkedHashMap<String,TreeNode> node = new LinkedHashMap<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(Evaluation.Evaluation_SQL_COMMANDS.query_cap, null);
        node = ProcessEvalQuery(node, cursor, db, name, evaluator, cursor.getColumnCount());

        cursor = db.rawQuery(Evaluation.Evaluation_SQL_COMMANDS.query_subcap, null);
        node = ProcessEvalQuery(node, cursor, db, name, evaluator, cursor.getColumnCount());

        cursor = db.rawQuery(Evaluation.Evaluation_SQL_COMMANDS.query_subcap_element, null);
        return ProcessEvalQuery(node, cursor, db, name, evaluator, cursor.getColumnCount());
    }




    public LinkedHashMap<String, TreeNode> ProcessEvalQuery(LinkedHashMap<String, TreeNode> node,
                                                            Cursor cursor, SQLiteDatabase db,
                                                            String name, String evaluator, int count) {
        if (count == 1)
        {
            cursor.moveToFirst();
            do
            {
                String cat = cursor.getString(0);
                Cursor cursor1 = db.rawQuery(Evaluation.Evaluation_SQL_COMMANDS.query_eval_rating
                        + " AND base_capability.cap = ?", new String[] { name, evaluator, cat });
                if (cursor1.getCount() != 0)
                {
                    Double rating = Double.valueOf(String.format("%.1f", CalcRating(cursor1)));
                    Evaluation eval = new Evaluation(cat, rating);
                    EvaluationItemHolder.EvaluationItem item = new EvaluationItemHolder.EvaluationItem(
                            eval.getName(), eval.getAverage_rating(), 1);
                    TreeNode treeNode = new TreeNode(item);
                    node.put(eval.getName(), treeNode);
                }
                cursor1.close();
            } while (cursor.moveToNext());

            cursor.close();
            return node;
        } else if (count == 2){
                cursor.moveToFirst();
                do
                {
                    String cat = cursor.getString(1);
                    String parent_id = cursor.getString(0);
                    Cursor cursor1 = db.rawQuery(Evaluation.Evaluation_SQL_COMMANDS.query_eval_rating
                            + "AND base_subcapability.subcap = ?", new String[] { name, evaluator, cat });
                    if (cursor1.getCount() != 0)
                    {
                        Double rating = Double.valueOf(String.format("%.1f", CalcRating(cursor1)));
                        Evaluation eval = new Evaluation(cat, rating);
                        EvaluationItemHolder.EvaluationItem item = new EvaluationItemHolder.EvaluationItem(eval.getName()
                                ,eval.getAverage_rating(), 2);
                        TreeNode treeNode = new TreeNode(item);
                        node.put(parent_id + ":" + eval.getName(), treeNode);
                    }
                    cursor1.close();
                } while (cursor.moveToNext());
                cursor.close();
            }
        else if (count == 3){
            cursor.moveToFirst();
            do
            {
                String cat = cursor.getString(2);
                String parent_id = cursor.getString(0) + ":" + cursor.getString(1);
                Cursor cursor1 = db.rawQuery(Evaluation.Evaluation_SQL_COMMANDS.query_eval_rating
                        + "AND base_subcapabilityelement.subcap_element = ?", new String[] { name, evaluator, cat });
                if (cursor1.getCount() != 0)
                {
                    cursor1.moveToFirst();
                    do {
                        Evaluation eval = new Evaluation(cat, 0.0);
                        EvaluationItemHolder.EvaluationItem item = null;
                        if(cursor1.getInt(4)==1) {
                            item = new EvaluationItemHolder.EvaluationItem(
                                    eval.getName(), 0.0D, cursor1.getString(5), 2);
                        } else if (cursor1.getInt(1) == 0){
                            item = new EvaluationItemHolder.EvaluationItem(
                                    eval.getName(), cursor1.getInt(2), cursor1.getString(5), 0);
                        } else if (cursor1.getInt(1) == 1){
                            item = new EvaluationItemHolder.EvaluationItem(
                                    eval.getName(), cursor1.getInt(3), cursor1.getString(5), 1);
                        }
                        TreeNode treeNode = new TreeNode(item);
                        node.put(parent_id + ":" + eval.getName(), treeNode);
                    }while (cursor1.moveToNext());
                }
                cursor1.close();
            } while (cursor.moveToNext());
            cursor.close();
        }
        return node;
    }




    public int CalcProductRating(String product)
    {
        return (int)CalcRating(getReadableDatabase().rawQuery(Product.P_SQL_COMMANDS.query_product_rating, new String[] { product }));
    }

    public double CalcRating(Cursor cursor)
    {
        double int_value = 0;
        double bool_value = 0;
        double int_count = 0;
        double bool_count = 0;
        cursor.moveToFirst();
        do{
            if(cursor.getInt(4) == 0){
                switch(cursor.getInt(1)){
                    case 0:
                        bool_value += cursor.getInt(2);
                        bool_count++;
                        break;
                    case 1:
                        int_value += cursor.getInt(3);
                        int_count += 2;
                        break;
                }
            }
        }while(cursor.moveToNext());

        return (int)(Math.floor(50*((((bool_value/bool_count)*100)+((int_value/int_count)*100))/200)))/10;
    }
}
