package com.app.csec_otm.activities;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.csec_otm.R;
import com.app.csec_otm.fragments.EvaluationFragment;
import com.app.csec_otm.fragments.ProductFolderStructureFragment;
import com.app.csec_otm.fragments.ProductDescriptionFragment;
import com.app.csec_otm.holders.EvaluationItemHolder;
import com.app.csec_otm.holders.IconTreeItemHolder;
import com.app.csec_otm.interfaces.NodeClickedInterface;

import com.app.csec_otm.search.CustomSearchableInfo;
import com.app.csec_otm.search.ResultItem;
import com.app.csec_otm.search.SearchActivity;

import javax.xml.transform.Result;


/**
 * Created by Ryan Lehmkuhl on 7/8/15.
 */


public class SingleFragmentActivity extends AppCompatActivity implements NodeClickedInterface{
    private boolean SearchToF;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView listView;

    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
        getMenuInflater().inflate(R.menu.menu, paramMenu);
        this.SearchToF = false;
        return true;
    }

    private void addDrawerItems()
    {
        String[] arrayOfString = { "Expand all", "Collapse all", "Add product", "Add evaluation", "Sign out" };
        ArrayAdapter localArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayOfString);
        this.mDrawerList.setAdapter(localArrayAdapter);
        ListView localListView = this.mDrawerList;
        AdapterView.OnItemClickListener local1 = new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
            {
                Toast.makeText(SingleFragmentActivity.this, "Option clicked", Toast.LENGTH_LONG).show();
            }
        };
        localListView.setOnItemClickListener(local1);
    }

    private void setupDrawer()
    {
        ActionBarDrawerToggle local2 = new ActionBarDrawerToggle(this, this.mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close)
        {
            public void onDrawerClosed(View paramAnonymousView)
            {
                super.onDrawerClosed(paramAnonymousView);
                SingleFragmentActivity.this.invalidateOptionsMenu();
            }

            public void onDrawerOpened(View paramAnonymousView)
            {
                super.onDrawerOpened(paramAnonymousView);
                SingleFragmentActivity.this.invalidateOptionsMenu();
            }
        };
        this.mDrawerToggle = local2;
        this.mDrawerToggle.setDrawerIndicatorEnabled(true);
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
    }


    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    public void onConfigurationChanged(Configuration paramConfiguration)
    {
        super.onConfigurationChanged(paramConfiguration);
        this.mDrawerToggle.onConfigurationChanged(paramConfiguration);
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_single_fragment);
        this.mDrawerList = ((ListView)findViewById(R.id.navList));
        this.mDrawerLayout = ((DrawerLayout)findViewById(R.id.drawer_layout));
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ProductFolderStructureFragment localProductFolderStructureFragment = new ProductFolderStructureFragment();
        FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
        localFragmentTransaction.add(R.id.fragment_container, localProductFolderStructureFragment);
        localFragmentTransaction.commit();

        CustomSearchableInfo.setTransparencyColor(Color.parseColor("#ccFFFFFF"));

        Intent intent = getIntent();
        handleIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()) || Intent.ACTION_VIEW.equals(intent.getAction())) {
            Bundle bundle = this.getIntent().getExtras();
            ResultItem receivedItem = bundle.getParcelable("Suggestion");
            if (receivedItem != null) {
                IconTreeItemHolder.IconTreeItem item = new IconTreeItemHolder.IconTreeItem(R.string.ic_file,
                        receivedItem.getHeader(),receivedItem.getSubHeader(),receivedItem.getDescription());
                onProductClicked(item);
            }
        }
    }



    public void onEvaluationClicked(EvaluationItemHolder.EvaluationItem paramEvaluationItem)
    {
        EvaluationFragment localEvaluationFragment = new EvaluationFragment();
        Bundle localBundle = new Bundle();
        localBundle.putString("Product Name", paramEvaluationItem.ename);
        localBundle.putString("Evaluator Name", paramEvaluationItem.text);
        localEvaluationFragment.setArguments(localBundle);
        FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
        localFragmentTransaction.replace(R.id.fragment_container, localEvaluationFragment);
        localFragmentTransaction.addToBackStack(null);
        localFragmentTransaction.commit();
    }

    public void onProductClicked(IconTreeItemHolder.IconTreeItem paramIconTreeItem)
    {
        ProductDescriptionFragment localProductDescriptionFragment = new ProductDescriptionFragment();
        Bundle localBundle = new Bundle();
        localBundle.putString("Product Name", paramIconTreeItem.text);
        localBundle.putString("Product Maker", paramIconTreeItem.maker);
        localBundle.putString("Product Description", paramIconTreeItem.description);
        localProductDescriptionFragment.setArguments(localBundle);
        FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
        localFragmentTransaction.replace(R.id.fragment_container, localProductDescriptionFragment);
        localFragmentTransaction.addToBackStack(null);
        localFragmentTransaction.commit();
    }
    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
        int id = paramMenuItem.getItemId();

        if (id == R.id.action_search) {
            // Calls Custom Searchable Activity
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);

            return true;
        } else if (this.mDrawerToggle.onOptionsItemSelected(paramMenuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(paramMenuItem);
    }

    protected void onPostCreate(Bundle paramBundle)
    {
        super.onPostCreate(paramBundle);
        this.mDrawerToggle.syncState();
    }


}
