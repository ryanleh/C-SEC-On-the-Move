package com.app.csec_otm.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.csec_otm.handlers.DBHelper;
import com.unnamed.b.atv.model.TreeNode;
import com.app.csec_otm.R;
import com.app.csec_otm.holders.IconTreeItemHolder;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.app.csec_otm.interfaces.NodeClickedInterface;

import java.util.LinkedHashMap;

/**
 * Created by Ryan Lehmkuhl on 7/8/15.
 */

public class ProductFolderStructureFragment extends Fragment {
    private DBHelper db;
    private NodeClickedInterface mCallback;
    public LinkedHashMap<String, TreeNode> node;
    private View rootView;
    private AndroidTreeView tView;


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
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle savedInstanceState)
    {
        getActivity().setTitle("Product Search");

        if (this.rootView == null)
        {
            this.rootView = paramLayoutInflater.inflate(R.layout.folder_structure, null, false);
            ViewGroup localViewGroup = (ViewGroup)this.rootView.findViewById(R.id.container);

            TreeNode root = TreeNode.root();
            DBHelper localDBHelper = new DBHelper(getActivity().getApplicationContext());
            this.db = localDBHelper;
            this.node = this.db.PopulateFileHash();

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
            this.tView.setDefaultViewHolder(IconTreeItemHolder.class);
            this.tView.setDefaultNodeClickListener(this.nodeClickListener);
            localViewGroup.addView(this.tView.getView());
            if (savedInstanceState != null) {
                String state = savedInstanceState.getString("tState");
                if (!TextUtils.isEmpty(state)) {
                    tView.restoreState(state);
                }
            }
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        final EditText editText = (EditText) menu.findItem(
                R.id.action_search).getActionView();
        editText.addTextChangedListener(textWatcher);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                editText.clearFocus();
                return true; // Return true to expand action view
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };

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
