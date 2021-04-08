package com.jcnetwork.members.model.ui.sidemenu;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Sidebar {

    private List<NavGroup> navGroups = new ArrayList<>();

    public NavGroup addNavGroup() {
        NavGroup newNavGroup = new NavGroup(Sidebar.this);
        this.getNavGroups().add(newNavGroup);
        return newNavGroup;
    }

    public NavGroup addNavGroup(String header) {
        NavGroup newNavGroup = new NavGroup(Sidebar.this, header);
        this.getNavGroups().add(newNavGroup);
        return newNavGroup;
    }

    public void setActiveLinks(String path){

        List<NavGroup> navGroups = this.getNavGroups();
        for(NavGroup navGroup : navGroups){
            for(NavItem navItem : navGroup.getNavItems()) {
                setActiveNavItems(navItem, path);
            }
        }
        this.setNavGroups(navGroups);
    }

    private Boolean setActiveNavItems(NavItem navItem, String path) {

        Boolean containsActive = navItem.getLink().equals(path);

        if (!containsActive){
            for(NavItem subItem : navItem.getSubItems()){
                if (!containsActive) containsActive = setActiveNavItems(subItem, path);
            }
        }

        if(containsActive) {
            navItem.setActive(true);
        }

        return containsActive;
    }
}
