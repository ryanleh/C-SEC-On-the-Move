package com.app.csec_otm.interfaces;

import com.app.csec_otm.holders.EvaluationItemHolder;
import com.app.csec_otm.holders.IconTreeItemHolder;

public abstract interface NodeClickedInterface
{
    void onEvaluationClicked(EvaluationItemHolder.EvaluationItem paramEvaluationItem);

    void onProductClicked(IconTreeItemHolder.IconTreeItem paramIconTreeItem);
}
