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
import android.widget.ViewSwitcher;

import com.app.csec_otm.R;
import com.app.csec_otm.handlers.DBHelper;
import com.app.csec_otm.holders.EvaluationItemHolder;
import com.app.csec_otm.interfaces.NodeClickedInterface;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class EvaluationFragment extends Fragment {
    private DBHelper db;
    private String evaluator;
    private NodeClickedInterface mCallback;
    private String name;
    private LinkedHashMap<String, TreeNode> node;
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
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
    {
        paramMenu.clear();
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle savedInstanceState)
    {
        this.rootView = paramLayoutInflater.inflate(R.layout.layout_evaluation, null, false);
        ViewGroup localViewGroup = (ViewGroup)this.rootView.findViewById(R.id.evaluation_container);
        Bundle b = getArguments();
        this.name = b.getString("Product Name");
        this.evaluator = b.getString("Evaluator Name");
        getActivity().setTitle(name + " Product Evaluation");
        TreeNode root = TreeNode.root();
        db = new DBHelper(getActivity().getApplicationContext());
        this.node = db.PopulateEvalHash(name, evaluator);

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

    TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener()
    {
        public void onClick(TreeNode node, Object object)
        {
            EvaluationItemHolder.EvaluationItem item = (EvaluationItemHolder.EvaluationItem) object;
            ViewSwitcher switcher;
            if (item.rfe == 3)
            {
                switcher = (ViewSwitcher)node.getViewHolder().getView().findViewById(R.id.switcher);
                switcher.showNext();

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
