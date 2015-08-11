package com.app.csec_otm.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.csec_otm.R;
import com.app.csec_otm.handlers.DBHelper;
import com.app.csec_otm.holders.EvaluationItemHolder;
import com.app.csec_otm.interfaces.NodeClickedInterface;
import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.LinkedHashMap;

/**
 * Created by Ryan Lehmkuhl on 7/15/15.
 */

public class ProductDescriptionFragment extends Fragment {
    private DBHelper db;
    private NodeClickedInterface mCallback;
    private LinkedHashMap<String, TreeNode> node;
    private View rootView;
    private AndroidTreeView tView;


    public void SetAvgProRating(String product, View rootView)
    {
        DBHelper db = new DBHelper(getActivity().getApplicationContext());
        int i = db.CalcProductRating(product);
        PrintView star1 = (PrintView)rootView.findViewById(R.id.product_rating1);
        PrintView star2 = (PrintView)rootView.findViewById(R.id.product_rating2);
        PrintView star3 = (PrintView)rootView.findViewById(R.id.product_rating3);
        PrintView star4 = (PrintView)rootView.findViewById(R.id.product_rating4);
        PrintView star5 = (PrintView)rootView.findViewById(R.id.product_rating5);
        if (i < 5)
        {
            switch (i)
            {
                case 0:
                    star1.setIconText(R.string.ic_star_empty);
                case 1:
                    star2.setIconText(R.string.ic_star_empty);
                case 2:
                    star3.setIconText(R.string.ic_star_empty);
                case 3:
                    star4.setIconText(R.string.ic_star_empty);
                case 4:
                    star5.setIconText(R.string.ic_star_empty);
            }
        }
    }

    public void onAttach(Activity paramActivity)
    {
        super.onAttach(paramActivity);
        try
        {
            this.mCallback = ((NodeClickedInterface)paramActivity);
            return;
        }
        catch (ClassCastException localClassCastException1)
        {
            ClassCastException localClassCastException2 = new ClassCastException(paramActivity.toString());
            throw localClassCastException2;
        }
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
    {
        paramMenu.clear();
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup rootViewGroup, Bundle savedInstanceState)
    {
        getActivity().setTitle("Product Description");
        rootView = paramLayoutInflater.inflate(R.layout.product_description, null, false);
        ViewGroup localViewGroup = (ViewGroup)this.rootView.findViewById(R.id.evaluation_container);
        TextView product_name = (TextView)this.rootView.findViewById(R.id.product_name);
        TextView product_maker = (TextView)this.rootView.findViewById(R.id.product_maker);
        TextView product_description = (TextView)this.rootView.findViewById(R.id.product_description);

        Bundle b = getArguments();
        String name = b.getString("Product Name");
        product_name.setText(name);
        product_maker.setText("From " + b.getString("Product Maker"));
        product_description.setText("''" + b.getString("Product Description") + "''");
        SetAvgProRating(name, this.rootView);
        TreeNode root = TreeNode.root();
        this.db = new DBHelper(getActivity().getApplicationContext());
        this.node = db.PopulateEvalRootHash(name);

        for(String key : node.keySet()) {
            String[] split = key.split(":");
            if (split.length == 1) {
                root.addChild(node.get(key));
            } else {
                String parent_id = split[0];
                for (int i = 1; i < (split.length - 1); i++) {
                    parent_id += ":" + split[i];
                }
                node.get(parent_id).addChild(node.get(key));
            }
        }

        tView = new AndroidTreeView(getActivity(), root);
        this.tView.setDefaultAnimation(true);
        this.tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        this.tView.setDefaultViewHolder(EvaluationItemHolder.class);
        this.tView.setDefaultNodeClickListener(this.nodeClickListener);
        localViewGroup.addView(this.tView.getView());
        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
        return rootView;
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener()
    {
        public void onClick(TreeNode paramAnonymousTreeNode, Object paramAnonymousObject)
        {
            EvaluationItemHolder.EvaluationItem localEvaluationItem = (EvaluationItemHolder.EvaluationItem)paramAnonymousObject;
            ProductDescriptionFragment.this.mCallback.onEvaluationClicked(localEvaluationItem);
        }
    };


    public void onDestroyView()
    {
        if (this.rootView.getParent() != null) {
            ((ViewGroup)this.rootView.getParent()).removeView(this.rootView);
        }
        super.onDestroyView();
    }

    public void onSaveInstanceState(Bundle paramBundle)
    {
        super.onSaveInstanceState(paramBundle);
        paramBundle.putString("tState", this.tView.getSaveState());
    }
}

