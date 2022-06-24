/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.app.entity;

import java.util.Date;

/**
 *
 * @author asus
 */
public class HoaDon {
    private String maHDBan;
    private String maNV;
    private String maKH;
    private Date ngayBan;

    public HoaDon() {
    }

    public HoaDon(String maHDBan, String maNV, String maKH, Date ngayBan) {
        this.maHDBan = maHDBan;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayBan = ngayBan;
    }

    public String getMaHDBan() {
        return maHDBan;
    }

    public void setMaHDBan(String maHDBan) {
        this.maHDBan = maHDBan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }

    
    
}
