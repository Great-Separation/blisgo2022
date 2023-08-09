package com.blisgo.util;

/**
 * 뷰단 sweetalert2 modal 메시지
 *
 * @author okjae
 */
public class AlertMsg {

    public AlertMsg() {
    }

    public AlertMsg(String icon, String title, String text) {
        this.icon = icon;
        this.title = title;
        this.text = text;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public enum Icons {
        success, error, warning, info, question
    }

    private String icon;
    private String title;
    private String text;
}
