package com.app.csec_otm.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.csec_otm.handlers.DBHelper;
import com.app.csec_otm.interfaces.Product;
import com.app.csec_otm.interfaces.ProductFile;
import com.unnamed.b.atv.model.TreeNode;
import com.app.csec_otm.R;
import com.app.csec_otm.holders.IconTreeItemHolder;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.app.csec_otm.interfaces.ProductClickedInterface;

import java.util.LinkedHashMap;

/**
 * Created by Ryan Lehmkuhl on 7/8/15.
 */
public class ProductFolderStructureFragment extends Fragment{
    private AndroidTreeView tView;
    private View rootView;
    private LinkedHashMap<String, TreeNode> node = new LinkedHashMap<>();
    private ProductClickedInterface mCallback;
    private DBHelper db;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Reads from file to populate LinkedHashMap (TODO: read from server)
     * Constructs TreeNode View from LinkedHashMap and passes to View
     **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Product Search");
        //check to see if the fragment has already been made as not to wipe state
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.folder_structure, null, false);
            ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
            TreeNode root = TreeNode.root();
            db = new DBHelper(getActivity().getApplicationContext());
            node = db.PopulateHash();
            //method to add children based on id
            for(String key : node.keySet()) {
                String[] split = key.split(":");
                if (split.length == 1) {
                    root.addChild(node.get(key));
                } else {
                    String parent_id = split[0];
                    int num = 1;

                    for (int i = split.length; i > 2; i--) {
                        parent_id += ":" + split[num];
                        num++;
                    }
                    node.get(parent_id).addChild(node.get(key));
                }
            }
            tView = new AndroidTreeView(getActivity(),root);
            tView.setDefaultAnimation(true);
            tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
            tView.setDefaultViewHolder(IconTreeItemHolder.class);
            tView.setDefaultNodeClickListener(nodeClickListener);
            containerView.addView(tView.getView());
            if (savedInstanceState != null) {
                String state = savedInstanceState.getString("tState");
                if (!TextUtils.isEmpty(state)) {
                    tView.restoreState(state);
                }
            }
        }
        return rootView;
    }

    /**
     * Expands and collapses the nodes when the arrow icon is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return true;
    }

    /**
     * Starts a new Fragment when a file is clicked
     */
    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            if(item.icon == R.string.ic_file){
                mCallback.onProductClicked(item);
            }
        }
    };




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interfaces. If not, it throws an exception
        try {
            mCallback = (ProductClickedInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    

}
