package com.unnamed.b.atv.sample.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.holder.IconTreeItemHolder;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by Bogdan Melnychuk on 2/12/15.
 */
public class FolderStructureFragment extends Fragment {
    private AndroidTreeView tView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_default, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        TreeNode root = TreeNode.root();

        TreeNode access_control = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Access Control"));

            TreeNode authentication = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Authentication"));
            TreeNode biometric = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Biometric"));
        TreeNode smart_tokens = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Smart Tokens"));
        authentication.addChildren(biometric, smart_tokens);

        TreeNode authorization = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Authorization"));
        TreeNode access_management = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Access Management"));
        authorization.addChild(access_management);

        TreeNode boundary_prot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Boundary Protection"));
        TreeNode content_management = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Content Management"));
        TreeNode firewall = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Firewall"));
        boundary_prot.addChildren(content_management,firewall);
        access_control.addChildren(authentication,authorization,boundary_prot);


        TreeNode audit_monitoring = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Audit and Monitoring"));

        TreeNode iden_pres_rec = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Identification, Preservation, and Recovery"));
        TreeNode digital_forensics = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Digital Forensics"));
        TreeNode evidence_analysis = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Evidence Analysis"));
        TreeNode evidence_preservation = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Evidence Preservation"));
        digital_forensics.addChildren(evidence_analysis,evidence_preservation);
        iden_pres_rec.addChild(digital_forensics);

        TreeNode intrusion_detection_sys = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Intrusion Detection Systems"));
        TreeNode app_based_ids = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Application Based IDS"));
        TreeNode host_based_ids = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Host Based IDS"));
        TreeNode network_based_ids = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Network Based IDS"));
        intrusion_detection_sys.addChildren(app_based_ids,host_based_ids,network_based_ids);

        TreeNode intrusion_prev_sys = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Intrusion Prevention Systems"));
        TreeNode app_based_ips = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Application Based IPS"));
        TreeNode host_based_ips = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Host Based IPS"));

        TreeNode network_based_ips = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"Network Based IPS"));
        TreeNode ios_prevention_sys = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_drive_file,"IOS Intrusion Prevention System"));
        network_based_ips.addChild(ios_prevention_sys);

        intrusion_prev_sys.addChildren(app_based_ips,host_based_ips,network_based_ips);

        TreeNode network_analyzer = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Network Analyzer"));

        TreeNode sec_evnt_correlation = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Security Event Correlation Tools"));

        audit_monitoring.addChildren(iden_pres_rec,intrusion_detection_sys,intrusion_prev_sys,network_analyzer,sec_evnt_correlation);

        TreeNode config_management = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Configuration Management and Assurance"));
        TreeNode crypto = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Cryptography"));
        TreeNode system_info = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "System and Information Integrity"));

        root.addChildren(access_control,audit_monitoring,config_management,crypto,system_info);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        //tView.setDefaultNodeClickListener(nodeClickListener);

        containerView.addView(tView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.expandAll:
                tView.expandAll();
                break;

            case R.id.collapseAll:
                tView.collapseAll();
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }
}
