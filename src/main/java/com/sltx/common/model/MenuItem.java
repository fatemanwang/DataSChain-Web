package com.sltx.common.model;

import java.util.List;

/**
 * menu entity
 *
 * @author Rlax
 *
 */
public class MenuItem {


    private String text;

    private String icon;

    private String href;

    private Long mark;

    private List<MenuItem> subset;

    public MenuItem(String text, String icon, String href, Long mark) {
        this.text = text;
        this.icon = icon;
        this.href = href;
        this.mark = mark;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<MenuItem> getSubset() {
        return subset;
    }

    public void setSubset(List<MenuItem> subset) {
        this.subset = subset;
    }

    public Long getMark() {
        return mark;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }
}
