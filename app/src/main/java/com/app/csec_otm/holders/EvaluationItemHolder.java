
package com.app.csec_otm.holders;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.app.csec_otm.R;
import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;

public class EvaluationItemHolder extends TreeNode.BaseNodeViewHolder<EvaluationItemHolder.EvaluationItem>
{
    int i = 0;
    View view;

    public EvaluationItemHolder(Context paramContext)
    {
        super(paramContext);
    }

    public View createNodeView(TreeNode paramTreeNode, EvaluationItem item)
    {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        if (item.rfe == 1)
        {
            this.view = inflater.inflate(R.layout.layout_product_evaluation_root, null, false);
            TextView name = (TextView) view.findViewById(R.id.evaluation_node_text);
            TextView values = (TextView) view.findViewById(R.id.evaluation_node_value);
            name.setText("Username: \n Date: \n Version: \n AvgRating:");
            values.setText(item.text + "\n" + item.date + "\n" + item.version + "\n");
            SetAvgProRating(item.avg_rating, this.view);
            return view;
        }else if (item.rfe == 2) {
            this.view = inflater.inflate(R.layout.layout_product_evaluation_node, null, false);
            TextView name = (TextView)this.view.findViewById(R.id.eval_node_value);
            LinearLayout background = (LinearLayout)this.view.findViewById(R.id.node_background);
            name.setText(item.text);
            if (item.grey == 2) {
                background.setBackgroundColor(Color.parseColor("#e1e1e1"));
            }
            return view;
        }else if(item.rfe == 3){
            this.view = inflater.inflate(R.layout.layout_product_evaluation, null, false);
            TextView name = (TextView) view.findViewById(R.id.eval_node_value);
            TextView name2 = (TextView) view.findViewById(R.id.eval_node_name);
            TextView description = (TextView) this.view.findViewById(R.id.eval_node_description);
            Switch NA_switch = (Switch) this.view.findViewById(R.id.NA_switch);
            RadioGroup localRadioGroup = (RadioGroup) this.view.findViewById(R.id.radio_group);
            RadioButton radio1 = (RadioButton) this.view.findViewById(R.id.radio_button_1);
            RadioButton radio2 = (RadioButton) this.view.findViewById(R.id.radio_button_2);
            RadioButton radio3 = (RadioButton) this.view.findViewById(R.id.radio_button_3);

            name.setText(item.text);
            name2.setText(item.text);

            description.setText(item.ename);

            switch((int)Math.floor(item.avg_rating)){
                case 0:
                    radio1.setChecked(true);
                    break;
                case 1:
                    radio2.setChecked(true);
                    break;
                case 2:
                    radio3.setChecked(true);
                    break;
            }
            NA_switch.setChecked(false);


            if (item.bool_or_int == 0) {
                localRadioGroup.removeView(radio3);
            }

            if (item.bool_or_int == 2) {
                radio3.setChecked(true);
                NA_switch.setChecked(true);
            }

            NA_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                    compoundButton.setChecked(bool);

                }
            });
            radio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                    compoundButton.setChecked(bool);
                }
            });

            radio2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                    compoundButton.setChecked(bool);
                }
            });
            radio3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                    compoundButton.setChecked(bool);
                }
            });



        }
        return view;
    }

    public void SetAvgProRating(double ratingd, View rootView)
    {
        int rating = (int)Math.floor(ratingd);
        PrintView rating1 = (PrintView)rootView.findViewById(R.id.eval_rating1);
        PrintView rating2 = (PrintView)rootView.findViewById(R.id.eval_rating2);
        PrintView rating3 = (PrintView)rootView.findViewById(R.id.eval_rating3);
        PrintView rating4 = (PrintView)rootView.findViewById(R.id.eval_rating4);
        PrintView rating5 = (PrintView)rootView.findViewById(R.id.eval_rating5);
        if (rating < 5) {
            switch (rating) {
                case 0:
                    rating1.setIconText(R.string.ic_star_empty);
                case 1:
                    rating2.setIconText(R.string.ic_star_empty);
                case 2:
                    rating3.setIconText(R.string.ic_star_empty);
                case 3:
                    rating4.setIconText(R.string.ic_star_empty);
                case 4:
                    rating5.setIconText(R.string.ic_star_empty);
            }
        }
    }



    public static class EvaluationItem
    {
        public double avg_rating;
        public int bool_or_int;
        public String date;
        public String ename;
        public int grey;
        public int rfe;
        public boolean switcher;
        public String text;
        public String version;

        public EvaluationItem(String name, double rating, int grey)
        {
            this.text = name;
            this.avg_rating = rating;
            this.rfe = 2;
            this.grey = grey;
        }

        public EvaluationItem(String name, double rating, String description, int bool_or_int)
        {
            this.text = name;
            this.avg_rating = rating;
            this.ename = description;
            this.rfe = 3;
            this.switcher = false;
            this.bool_or_int = bool_or_int;
        }

        public EvaluationItem(String name, double rating, String date, String version, String ename)
        {
            this.text = name;
            this.avg_rating = rating;
            this.date = date;
            this.version = version;
            this.ename = ename;
            this.rfe = 1;
        }
    }
}

