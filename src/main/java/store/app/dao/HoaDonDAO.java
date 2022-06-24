/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import store.app.entity.HoaDon;
import store.app.utils.XJDBC;

/**
 *
 * @author asus
 */
public class HoaDonDAO extends storeAppDAO<HoaDon, String>{

    @Override
    public void insert(HoaDon model) {
        String sql="INSERT INTO HoaDon VALUES ([dbo].[AUTO_MaHD](), ?, ?, ?)";
        XJDBC.update(sql, 
//        model.getMaHDBan(),
        model.getMaNV(),
        model.getNgayBan(),
        model.getMaKH());
    }

    @Override
    public void update(HoaDon model) {
        String sql="UPDATE HoaDon SET MaNV =?, NgayBan=? ,MaKH=? WHERE MaHDBan = ?";
        XJDBC.update(sql, 
        model.getMaNV(),
        model.getNgayBan(),
        model.getMaKH(),
        model.getMaHDBan());
    }

    @Override
    public void delete(String maHD) {
        String sql = "DELETE FROM HoaDon WHERE MaHDBan = ? ";
        XJDBC.update(sql, maHD);
    }

    @Override
    public HoaDon selectById(String maHD) {
        String sql = "SELECT * FROM HoaDOn WHERE MaHDBan=?";
        List<HoaDon> list = selectBySql(sql, maHD);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<HoaDon> selectAll() {
        String sql = "SELECT * FROM HoaDon";
        return this.selectBySql(sql);
    }

    @Override
    protected List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while(rs.next()){
                    HoaDon entity=new HoaDon();
                    entity.setMaHDBan(rs.getString("MaHDBan"));
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setNgayBan(rs.getDate("NgayBan"));
                    entity.setMaKH(rs.getString("MaKH"));
                    list.add(entity);
                }
            }
            finally{
                rs.getStatement().getConnection().close();
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    public List<HoaDon> selectByKhachHang(String MaKH){ //tìm kiếm Khóa học theo chuyên đề 
        String sql = "select hd.MaHDBan, MaNV, NgayBan, MaKH, SUM(sp.DonGiaBan*ct.SoLuong-ct.GiamGia) as ThanhTien from HoaDon hd \n" +
                "inner join HoaDonChiTiet ct \n" +
                "on ct.MaHDBan = hd.MaHDBan\n" +
                "inner join SanPham sp \n" +
                "on sp.MaSP = ct.MaSP\n" +
                "where hd.MaKH = ? \n" +
                "GROUP BY hd.MaHDBan,MaNV, NgayBan, MaKH";
        return this.selectBySql(sql, MaKH);
    }
    
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
    
    public List<Object[]> getHoaDon(){
        String sql = "{CALL sp_HoaDon}"; 
        String[] cols = {"MaHDBan","MaNV","NgayBan","MaKH","TongTien"};// chứa tên cột khi đổ vào table
        return this.getListOfArray(sql, cols);//chứa kết quả câu lệnh
        }
    
    public List<Object[]> getHoaDonTheoNgay(String Ngay){
       String sql = "{CALL sp_HoaDonTheoNgay(?)}"; 
        String[] cols = {"MaHDBan","MaNV","NgayBan","MaKH","TongTien"};// chứa tên cột khi đổ vào table
        return this.getListOfArray(sql, cols, Ngay);//chứa kết quả câu lệnh
        }
    
    public List<Object[]> getHoaDonTheoKH(String maKH){
        String sql = "{CALL sp_HoaDonKhachHang(?)}"; 
        String[] cols = {"MaHDBan","MaNV","NgayBan","MaKH","TongTien"};// chứa tên cột khi đổ vào table
        return this.getListOfArray(sql, cols, maKH);//chứa kết quả câu lệnh
        }
}
