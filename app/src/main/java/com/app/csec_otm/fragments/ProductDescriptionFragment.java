package com.app.csec_otm.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.csec_otm.R;
import com.app.csec_otm.holders.EvaluationItemHolder;
import com.app.csec_otm.interfaces.ProductClickedInterface;
import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

/**
 * Created by Ryan Lehmkuhl on 7/15/15.
 */
public class ProductDescriptionFragment extends Fragment {
    private AndroidTreeView tView;
    private LinkedHashMap<Integer,TreeNode> node = new LinkedHashMap<>();
    ProductClickedInterface mCallback;

    public ProductDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_description, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.evaluation_container);

        getActivity().setTitle("Product Description");
        Bundle b = getArguments();
        TextView product_named = (TextView) rootView.findViewById(R.id.product_name);
        TextView product_maker = (TextView) rootView.findViewById(R.id.product_maker);
        TextView product_description = (TextView) rootView.findViewById(R.id.product_description);
        product_named.setText(b.getString("Product Name"));
        product_maker.setText("From " + b.getString("Product Maker"));
        product_description.setText(b.getString("Product Description"));
        SetRating(b.getInt("Rating"), rootView);


        TreeNode root = TreeNode.root();
        node = MakeNodes(node);

        //method to add children based on id
        for(int key : node.keySet()){
            if(key < 10){
                root.addChild(node.get(key));

            }
            else if(key > 10) {
                int parent_key = (key / 10);
                (node.get(parent_key)).addChild(node.get(key));
            }
        }
        tView = new AndroidTreeView(getActivity(),root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(EvaluationItemHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);
        containerView.addView(tView.getView());
        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }


        return rootView;
    }

    private LinkedHashMap<Integer,TreeNode> MakeNodes(LinkedHashMap<Integer,TreeNode> node){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("evaluation_data.txt")));
            String line;
            while((line = reader.readLine()) != null){
                if(!line.isEmpty() || line.length() != 0){
                    String[] var = line.split(":");
                    int id = Integer.parseInt(var[0]);
                    String name = var[1];
                    String fof = var[2];
                    if(fof.equals("true")) {
                    }
                    else{
                        float rating = Float.parseFloat(var[3]);
                        node.put(id, new TreeNode(new EvaluationItemHolder.EvaluationItem(name,rating)));
                    }
                }
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }
    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            EvaluationItemHolder.EvaluationItem item = (EvaluationItemHolder.EvaluationItem) value;
            //if(item.icon == R.string.ic_file){
             //   mCallback.onProductClicked(item);
            //}
        }
    };


    public void SetRating(int rating, View root){
        PrintView star1 = (PrintView) root.findViewById(R.id.product_rating1);
        PrintView star2 = (PrintView) root.findViewById(R.id.product_rating2);
        PrintView star3 = (PrintView) root.findViewById(R.id.product_rating3);
        PrintView star4 = (PrintView) root.findViewById(R.id.product_rating4);
        PrintView star5 = (PrintView) root.findViewById(R.id.product_rating5);

        while(rating < 5){
            switch(rating) {
                case 0:
                    star1.setIconText(R.string.ic_star_empty);
                    rating++;
                case 1:
                    star2.setIconText(R.string.ic_star_empty);
                    rating++;
                case 2:
                    star3.setIconText(R.string.ic_star_empty);
                    rating++;
                case 3:
                    star4.setIconText(R.string.ic_star_empty);
                    rating++;
                case 4:
                    star5.setIconText(R.string.ic_star_empty);
                    rating++;
            }
        }
    }
}

//update product, add product version, evaluate the latest version