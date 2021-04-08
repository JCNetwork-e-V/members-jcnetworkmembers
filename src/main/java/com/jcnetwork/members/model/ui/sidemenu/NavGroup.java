package com.jcnetwork.members.model.ui.sidemenu;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NavGroup {

    private final Sidebar sidebar;

    private String header;
    private List<NavItem> navItems = new ArrayList<>();

    public NavGroup(Sidebar sidebar) {
        this.sidebar = sidebar;
    }
    public NavGroup(Sidebar sidebar, String header) {
        this.sidebar = sidebar;
        this.header = header;
    }

    public NavItem addNavItem(String label, String link, String icon) {
        NavItem newNavItem = new NavItem(this.sidebar, NavGroup.this,null, label, link, icon);
        this.getNavItems().add(newNavItem);
        return newNavItem;
    }

    public Sidebar and() {
        return this.sidebar;
    }
}
