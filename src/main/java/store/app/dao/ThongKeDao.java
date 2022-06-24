/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.app.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import store.app.utils.XJDBC;

/**
 *
 * @author asus
 */
public class ThongKeDao {
    private List<Object[]> getListOfArray(String sql, String[] cols, Object...arg){
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = XJDBC.query(sql, arg);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list; //danh sách mảng ->> filltable
        } catch (Exception e) {
            throw new RuntimeException();
        }

        }
    
    public List<Object[]> getDoanhThuTheoThang(int thang){
        String sql = "{CALL sp_DoanhThuTheoThang(?)}";
        String[] cols = {"TongHoaDonDaBan","SPDaBan","DoanhThu","HDThapNhat","HDCaoNhat","TrungBinh"};
        return this.getListOfArray(sql, cols, thang);
            
        }
    
    public List<Object[]> getDoanhThuTheoNgay(String ngay){
        String sql = "{CALL sp_DoanhThuTheoNgay(?)}";
        String[] cols = {"TongHoaDonDaBan","SPDaBan","DoanhThu","HDThapNhat","HDCaoNhat","TrungBinh"};
        return this.getListOfArray(sql, cols, ngay);
            
        }
    
    public List<Object[]> getSanPhamTheoNgay(String ngay){
        String sql = "{CALL sp_ThongKeSanPhamDaBanTheoNgay(?)}";
        String[] cols = {"MaHDBan","MaSP","TenSP","SoLuong","TenDanhMuc","ThanhTien"};
        return this.getListOfArray(sql, cols, ngay);
            
        }
    
    
    public List<Object[]> getSanPhamTheoThang(int thang){
        String sql = "{CALL sp_ThongKeSanPhamDaBanTheoThang(?)}";
        String[] cols = {"MaHDBan","MaSP","TenSP","SoLuong","TenDanhMuc","ThanhTien"};
        return this.getListOfArray(sql, cols, thang);
            
        }
    
    public List<Object[]> getSanPhamKhBanDuocTheoThang(int thang){
        String sql = "{CALL sp_KhBanDuocThang(?)}";
        String[] cols = {"MaSP","TenSP","DonGiaBan","LoaiSP","SLTonKho"};
        return this.getListOfArray(sql, cols, thang);
            
        }
    
    public List<Object[]> getSanPhamKhBanDuocTheoNgay(String ngay){
        String sql = "{CALL sp_KhBanDuocNgay(?)}";
        String[] cols = {"MaSP","TenSP","DonGiaBan","LoaiSP","SLTonKho"};
        return this.getListOfArray(sql, cols, ngay);
            
        }
    
    public List<Object[]> getSPBanChayTheoNgay(String ngay){
        String sql = "{CALL sp_Top5SanPham(?)}";
        String[] cols = {"MaSP","TenSP","DonGiaBan","LoaiSP","SoLuongDaBan"};
        return this.getListOfArray(sql, cols, ngay);
            
        }
    public List<Object[]> getSPBanChayTheoThang(int thang){
        String sql = "{CALL sp_Top5SanPhamTheoThang(?)}";
        String[] cols = {"MaSP","TenSP","DonGiaBan","LoaiSP","SoLuongDaBan"};
        return this.getListOfArray(sql, cols, thang);
            
        }
    
    public List<Object[]> getNhanVienCoDonNgay(String ngay){
        String sql = "{CALL sp_NhanVien_CoDonNgay(?)}";
        String[] cols = {"MaHDBan","SoLuongSP","MaNV","TenNV","NgayBan","TongTien"};
        return this.getListOfArray(sql, cols, ngay);
            
        }
    public List<Object[]> getNhanVienCoDonThang(int thang){
        String sql = "{CALL sp_NhanVien_CoDonThang(?)}";
        String[] cols = {"MaHDBan","SoLuongSP","MaNV","TenNV","NgayBan","TongTien"};
        return this.getListOfArray(sql, cols, thang);
            
        }
    
    public List<Object[]> getNhanVienThang(int thang){
        String sql = "{CALL sp_NhanVienThang(?)}";
        String[] cols = {"MaNV","TenNV","SoHDBanDuoc","SPDaBan","TongTienBanDuoc"};
        return this.getListOfArray(sql, cols, thang);
            
        }
    public List<Object[]> getNhanVienNgay(String ngay){
        String sql = "{CALL sp_NhanVienNgay(?)}";
        String[] cols = {"MaNV","TenNV","SoHDBanDuoc","SPDaBan","TongTienBanDuoc"};
        return this.getListOfArray(sql, cols, ngay);
            
        }
}
