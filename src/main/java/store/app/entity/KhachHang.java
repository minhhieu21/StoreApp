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
public class KhachHang {
    private String maKH;
    private String tenKH;
    private String diaChi;
    private String sdt;
    private String email;
    private boolean thanhVien = false; //mặc định là không 

    public KhachHang() {
    }

    @Override
    public String toString(){ //do du lieu ra combobox
        return this.tenKH;
    }
    
    @Override
    public boolean equals(Object obj){ //so sanh 
        KhachHang other = (KhachHang) obj;
        return other.getMaKH().equals(this.getMaKH());
    }
    
    public KhachHang(String maKH, String tenKH, String diaChi, String sdt, String email) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isThanhVien() {
        return thanhVien;
    }

    public void setThanhVien(boolean thanhVien) {
        this.thanhVien = thanhVien;
    }

    
    
    
}
