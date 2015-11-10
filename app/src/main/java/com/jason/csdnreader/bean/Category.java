package com.jason.csdnreader.bean;

import com.jason.csdnreader.ui.view.treeview.TreeNodeDesc;
import com.jason.csdnreader.ui.view.treeview.TreeNodeId;
import com.jason.csdnreader.ui.view.treeview.TreeNodeLabel;
import com.jason.csdnreader.ui.view.treeview.TreeNodeLink;
import com.jason.csdnreader.ui.view.treeview.TreeNodePid;

/**
 * Created by zzmiao on 2015/11/10.
 */
public class Category {
    @TreeNodeId
    private int _id;
    @TreeNodePid
    private int pId;
    @TreeNodeLabel
    private String label;
    @TreeNodeDesc
    private String desc;
    @TreeNodeLink
    private String link;

    public Category(int _id, int pId, String label, String desc, String link) {
        super();
        this._id = _id;
        this.pId = pId;
        this.label = label;
        this.desc = desc;
        this.link = link;
    }

    @Override
    public String toString() {
        return "{_id:" + _id + ", pId:" + pId + ", label:" + label + ", desc:" + desc +", link:" + link + "}";
    }
}
