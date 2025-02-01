package com.example.stitchwave.view.tdm;

public class StyleTM implements Comparable<StyleTM>{
    private String style_id;
    private String size;
    private Integer qty;
    private String employee_id;
    private String stock_id;

    public StyleTM(String style_id, String size, Integer qty, String employee_id, String stock_id) {
        this.style_id = style_id;
        this.size = size;
        this.qty = qty;
        this.employee_id = employee_id;
        this.stock_id = stock_id;
    }

    public String getStyle_id() {
        return style_id;
    }

    public void setStyle_id(String style_id) {
        this.style_id = style_id;
    }

    public String getSize(){
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQty(){
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getEmployee_id(){
       return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    @Override
    public int compareTo(StyleTM o) {
        return style_id.compareTo(o.getStyle_id());
    }

    @Override
    public String toString() {
        return "StyleTM{" +
                "style_id='" + style_id + '\'' +
                ", size='" + size + '\'' +
                ", qty=" + qty +
                ", employee_id='" + employee_id + '\'' +
                ", stock_id='" + stock_id + '\'' +
                '}';
    }
}
