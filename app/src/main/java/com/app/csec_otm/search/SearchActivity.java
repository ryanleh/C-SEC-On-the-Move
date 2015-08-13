package com.app.csec_otm.search;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.csec_otm.handlers.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity controls the maint flow for the Custom Searchable behaviour. Its onCreate method
 * initialize the main UI components - such as the app bar and result list (RecyclerView). It should
 * be called through an intent and it's responses are also sent as intents
 */
public class SearchActivity extends AppCompatActivity {
    // CONSTANTS
    public static final int VOICE_RECOGNITION_CODE = 1;

    // UI ELEMENTS
    private RecyclerView searchResultList;
    private EditText searchInput;
    private RelativeLayout voiceInput;
    private RelativeLayout dismissDialog;
    private ImageView micIcon;

    private String query;
    private DBHelper db;
    private List<ResultItem> products;
    private SearchAdapter all_products;
    private List<ResultItem> products_filtered;

    private String searchableActivity = "com.app.csec_otm.activities.SingleFragmentActivity";
    private Boolean isRecentSuggestionsProvider = Boolean.TRUE;

    // Activity Callbacks __________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(com.app.csec_otm.R.layout.custom_searchable);
        this.getWindow().setStatusBarColor(getResources().getColor(com.app.csec_otm.R.color.textPrimaryColor));

        this.query = "";
        this.searchResultList = (RecyclerView) this.findViewById(com.app.csec_otm.R.id.cs_result_list);
        this.searchInput = (EditText) this.findViewById(com.app.csec_otm.R.id.custombar_text);
        this.voiceInput = (RelativeLayout) this.findViewById(com.app.csec_otm.R.id.custombar_mic_wrapper);
        this.dismissDialog = (RelativeLayout) this.findViewById(com.app.csec_otm.R.id.custombar_return_wrapper);
        this.micIcon = (ImageView) this.findViewById(com.app.csec_otm.R.id.custombar_mic);
        this.micIcon.setSelected(Boolean.FALSE);

        // Initialize result list
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        searchResultList.setLayoutManager(linearLayoutManager);

        this.db = new DBHelper(this);
        products = new ArrayList<>();
        all_products = new SearchAdapter(db.getSearchProducts(products));
        searchResultList.setAdapter(all_products);

        this.searchInput.setMaxLines(1);

        implementSearchTextListener();
        implementDismissListener();
        implementVoiceInputListener();
        implementResultListOnItemClickListener();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case VOICE_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchInput.setText(text.get(0));
                }
                break;
            }
        }
    }

    // Sends an intent with the typed query to the searchable Activity
    private void sendSuggestionIntent(ResultItem item) {
        try {
            Intent sendIntent = new Intent(this, Class.forName(searchableActivity));
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            Bundle b = new Bundle();
            b.putParcelable("Suggestion", item);

            sendIntent.putExtras(b);
            startActivity(sendIntent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Sends an intent with the typed query to the searchable Activity
    private void sendSearchIntent () {
        try {
            Intent sendIntent = new Intent(this, Class.forName(searchableActivity));
            sendIntent.setAction(Intent.ACTION_SEARCH);
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            if(!products_filtered.isEmpty()){
                sendIntent.putExtra(SearchManager.QUERY, products_filtered.get(0));
            } else {
                sendIntent.putExtra(SearchManager.QUERY, products.get(0));
            }
            startActivity(sendIntent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Listeners implementation ____________________________________________________________________
    private void implementSearchTextListener() {
        // Gets the event of pressing search button on soft keyboard
        TextView.OnEditorActionListener searchListener = new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sendSearchIntent();
                }
                return true;
            }
        };

        searchInput.setOnEditorActionListener(searchListener);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (!"".equals(searchInput.getText().toString())) {
                    query = searchInput.getText().toString();
                    products_filtered = new ArrayList<>();
                    products_filtered = performSearch(products,query);
                    SearchAdapter adapter = new SearchAdapter(products_filtered);
                    searchResultList.setAdapter(adapter);
                    setClearTextIcon();
                } else {
                    searchResultList.setAdapter(all_products);
                    setMicIcon();
                }
            }

            private List<ResultItem> performSearch(List<ResultItem> products, String query) {

                // First we split the query so that we're able
                // to search word by word (in lower case).
                String[] queryByWords = query.toLowerCase().split("\\s+");

                // Empty list to fill with matches.
                List<ResultItem> products_filtered = new ArrayList<>();

                // Go through initial releases and perform search.
                for (ResultItem product : products) {
                    String product_queries = (product.getHeader() + " " + product.getSubHeader()).toLowerCase();
                    for (String word : queryByWords) {
                        int numberOfMatches = queryByWords.length;
                        if (product_queries.contains(word)) {
                            numberOfMatches--;
                        } else {
                            break;
                        }
                        if (numberOfMatches == 0) {
                            products_filtered.add(product);
                        }

                    }

                }

                return products_filtered;
            }

            // DO NOTHING
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            // DO NOTHING
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    // Finishes this activity and goes back to the caller
    private void implementDismissListener () {
        this.dismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Implements speech-to-text
    private void implementVoiceInputListener () {
        this.voiceInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (micIcon.isSelected()) {
                    searchInput.setText("");
                    query = "";
                    micIcon.setSelected(Boolean.FALSE);
                    micIcon.setImageResource(com.app.csec_otm.R.drawable.mic_icon);
                } else {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");

                    com.app.csec_otm.search.SearchActivity.this.startActivityForResult(intent, VOICE_RECOGNITION_CODE);
                }
            }
        });
    }

    // Sends intent to searchableActivity with the selected result item
    private void implementResultListOnItemClickListener () {
        searchResultList.addOnItemTouchListener(new RecyclerViewOnItemClickListener(this,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ResultItem clickedItem = ((SearchAdapter) searchResultList.getAdapter()).getItem(position);
                        sendSuggestionIntent(clickedItem);
                    }
                }));
    }



    // UI __________________________________________________________________________________________

    // Set X as the icon for the right icon in the app bar
    private void setClearTextIcon () {
        micIcon.setSelected(Boolean.TRUE);
        micIcon.setImageResource(com.app.csec_otm.R.drawable.delete_icon);
        micIcon.invalidate();
    }

    // Set the micrphone icon as the right icon in the app bar
    private void setMicIcon () {
        micIcon.setSelected(Boolean.FALSE);
        micIcon.setImageResource(com.app.csec_otm.R.drawable.mic_icon);
        micIcon.invalidate();
    }
}

