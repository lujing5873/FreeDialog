package com.example.freedialog;

import java.util.Objects;

public class SelectModel {
    private String text;
    private Boolean select;

    public SelectModel(String text, Boolean select) {
        this.text = text;
        this.select = select;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectModel that = (SelectModel) o;
        return Objects.equals(text, that.text) && Objects.equals(select, that.select);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, select);
    }
}
