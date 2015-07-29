package com.app.csec_otm.holders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.app.csec_otm.R;

/**
 * Created by Ryan Lehmkuhl on 7/15/15.
 */
public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private PrintView arrowView;
    private PrintView iconView;

    public IconTreeItemHolder(Context context) {
        super(context);
    }

    /**
     * Creates a file or folder node based on assigned icon
     */
    public View createNodeView(final TreeNode node, IconTreeItem value) {

        if(value.icon == R.string.ic_file){
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.layout_icon_file, null, false);
            tvValue = (TextView) view.findViewById(R.id.node_value);
            tvValue.setText(value.text);
            iconView = (PrintView) view.findViewById(R.id.icon);
            iconView.setIconText(context.getResources().getString(value.icon));
            return view;
        }
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);
        iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(value.icon));
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        return view;
    }

    /**
     * Toggles closed/open folders and icons
     */
    @Override
    public void toggle(boolean active) {
        if(arrowView != null) {
            arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
            iconView.setIconText(context.getResources().getString(active ? R.string.ic_open_folder : R.string.ic_folder));
        }
    }

    /**
     * Creates the actual Icon object
     */
    public static class IconTreeItem {
        public int icon;
        public String text;
        public String maker;
        public int rating;
        public String description;

        public IconTreeItem(int icon, String text) {
            this.icon = icon;
            this.text = text;
        }
        public IconTreeItem(int icon, String text, String maker, int rating, String description){
            this.icon = icon;
            this.text = text;
            this.maker = maker;
            this.rating = rating;
            this.description = description;
        }
    }
}
