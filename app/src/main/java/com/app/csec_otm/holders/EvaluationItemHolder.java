package com.app.csec_otm.holders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.csec_otm.R;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by ryanleh on 7/21/15.
 */
public class EvaluationItemHolder extends TreeNode.BaseNodeViewHolder<EvaluationItemHolder.EvaluationItem> {

    public EvaluationItemHolder(Context context) {
        super(context);
        }

    @Override
    public View createNodeView(final TreeNode node, EvaluationItemHolder.EvaluationItem value){
        if(value.eof){
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.layout_product_evaluation, null, false);
            TextView text = (TextView) view.findViewById(R.id.evaluation_node_value);
            TextView rating = (TextView) view.findViewById(R.id.rating);
            text.setText(value.text);
            rating.setText("Rating: "+value.rating);
            return view;
        }
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_product_evaluation, null, false);
        TextView text = (TextView) view.findViewById(R.id.evaluation_node_value);
        TextView rating = (TextView) view.findViewById(R.id.rating);
        text.setText(value.text);
        rating.setText("Avg Rating: " + value.avg_rating);
        return view;
        }

    public static class EvaluationItem{
        public String text;
        public int rating;
        public float avg_rating;
        public boolean eof;

        public EvaluationItem(String text, float avg_rating){
            this.text = text;
            this.avg_rating=avg_rating;
            this.eof = false;
        }
        public EvaluationItem(String text, int rating){
            this.text = text;
            this.rating = rating;
            this.eof = true;
        }

    }
}
