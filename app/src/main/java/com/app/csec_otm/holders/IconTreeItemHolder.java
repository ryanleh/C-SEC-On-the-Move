package com.app.csec_otm.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.csec_otm.R;
import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.model.TreeNode.BaseNodeViewHolder;

public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem>
{
    private PrintView arrowView;
    private PrintView iconView;
    private TextView tvValue;

    public IconTreeItemHolder(Context paramContext)
    {
        super(paramContext);
    }

    public View createNodeView(TreeNode node, IconTreeItem item)
    {
        if (item.icon == R.string.ic_file)
        {
            View view = LayoutInflater.from(this.context).inflate(R.layout.layout_icon_file, null, false);
            tvValue = ((TextView)view.findViewById(R.id.node_value));
            iconView = ((PrintView)view.findViewById(R.id.icon));
            tvValue.setText(item.text);
            iconView.setIconText(item.icon);
            return view;
        }
        View view = LayoutInflater.from(this.context).inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView)view.findViewById(R.id.node_value);
        iconView = (PrintView)view.findViewById(R.id.icon);
        arrowView = (PrintView)view.findViewById(R.id.arrow_icon);
        tvValue.setText(item.text);
        iconView.setIconText(item.icon);
        arrowView.setIconText(R.string.ic_keyboard_arrow_right);
        return view;
    }

    public void toggle(boolean bool)
    {
        if (this.arrowView != null)
        {
            arrowView.setIconText(context.getResources().getString(bool ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));

        }
    }

    public static class IconTreeItem
    {
        public String description;
        public int icon;
        public String maker;
        public String text;

        public IconTreeItem(int icon, String text)
        {
            this.icon = icon;
            this.text = text;
        }

        public IconTreeItem(int icon, String text, String maker, String description)
        {
            this.icon = icon;
            this.text = text;
            this.maker = maker;
            this.description = description;
        }
    }
}

