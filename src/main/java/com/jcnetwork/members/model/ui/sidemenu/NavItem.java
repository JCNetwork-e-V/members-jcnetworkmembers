package com.jcnetwork.members.model.ui.sidemenu;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NavItem {

    private final Sidebar sidebar;
    private final NavGroup navGroup;
    private final NavItem superItem;

    private String label;
    private String icon;
    private String link;
    private List<NavItem> subItems = new ArrayList<>();
    private Boolean active = false;

    public NavItem(Sidebar sidebar, NavGroup navGroup, NavItem superItem, String label, String link, String icon) {
        this.sidebar = sidebar;
        this.navGroup = navGroup;
        this.superItem = superItem;
        this.label = label;
        this.icon = icon;
        this.link = link;
    }

    public NavItem addSubItem(String label, String link, String icon) {
        NavItem newSubItem = new NavItem(this.sidebar, this.navGroup, NavItem.this, label, link, icon);
        this.getSubItems().add(newSubItem);
        return newSubItem;
    }

    public NavItem and() { return this.superItem; }

    public NavGroup topLevel() { return this.navGroup; }

    public NavItem levelUp() { return this.superItem.getSuperItem(); }

    public Sidebar closeNavGroup() { return this.sidebar; }
}
