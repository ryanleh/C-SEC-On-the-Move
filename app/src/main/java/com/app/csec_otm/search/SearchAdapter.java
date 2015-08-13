package com.app.csec_otm.search;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Fills the result line with the passed cursor
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ResultItem> dataSet;

    // Constructors ________________________________________________________________________________
    public SearchAdapter (List<ResultItem> dataSet) {
        this.dataSet = dataSet;
    }

    // Callbacks ___________________________________________________________________________________
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(com.app.csec_otm.R.layout.custom_searchable_row_details, parent, false);

        ViewHolder holder = new ViewHolder(view);

        // UI configuration
            android.view.ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CustomSearchableInfo.getResultItemHeight();
            view.setLayoutParams(params);


        // Change layout based on the user option of one or two-lines mode
        if (!CustomSearchableInfo.getIsTwoLineExhibition()) {
            holder.subHeader.setVisibility(TextView.GONE);
            holder.header.setTypeface(Typeface.DEFAULT);
            view.invalidate();
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.header.setText(dataSet.get(position).getHeader());
        viewHolder.subHeader.setText(dataSet.get(position).getSubHeader());
        viewHolder.leftIcon.setImageResource(dataSet.get(position).getLeftIcon());
        viewHolder.rightIcon.setImageResource(dataSet.get(position).getRightIcon());
    }

    // Getters and Setters _________________________________________________________________________
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public ResultItem getItem (Integer position) {
        return dataSet.get(position);
    }

    // View Holder _________________________________________________________________________________
    private class ViewHolder extends RecyclerView.ViewHolder {

        public TextView header;
        public TextView subHeader;
        public ImageView leftIcon;
        public ImageView rightIcon;

        public ViewHolder (View v) {
            super (v);

            this.header = (TextView) v.findViewById(com.app.csec_otm.R.id.rd_header_text);
            this.subHeader = (TextView) v.findViewById(com.app.csec_otm.R.id.rd_sub_header_text);
            this.leftIcon = (ImageView) v.findViewById(com.app.csec_otm.R.id.rd_left_icon);
            this.rightIcon = (ImageView) v.findViewById(com.app.csec_otm.R.id.rd_right_icon);
        }
    }
}
