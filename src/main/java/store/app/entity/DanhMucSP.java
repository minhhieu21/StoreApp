/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.app.entity;

/**
 *
 * @author asus
 */
public class DanhMucSP {
    private int maDM;
    private String tenDM;

    @Override
    public String toString(){ //do du lieu ra combobox
        return this.tenDM;
    }

    public DanhMucSP() {
    }

    public DanhMucSP(int maDM, String tenDM) {
        this.maDM = maDM;
        this.tenDM = tenDM;
    }

    public int getMaDM() {
        return maDM;
    }

    public void setMaDM(int maDM) {
        this.maDM = maDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    

    

    

    
    
}
