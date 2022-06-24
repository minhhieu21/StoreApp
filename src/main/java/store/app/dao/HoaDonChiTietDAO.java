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
import store.app.entity.HoaDonChiTiet;
import store.app.utils.XJDBC;

/**
 *
 * @author asus
 */
public class HoaDonChiTietDAO extends storeAppDAO<HoaDonChiTiet, Integer>{

    @Override
    public void insert(HoaDonChiTiet model) {
        String sql="INSERT INTO HoaDonChiTiet ( MaHDBan,MaSP,SoLuong,GiamGia) VALUES (?, ?, ?, ?)";
        XJDBC.update(sql, 
        model.getMaHDBan(),
        model.getMaSP(),
        model.getSoLuong(),
        model.getGiamGia());
    }

    @Override
    public void update(HoaDonChiTiet model) {
        String sql="UPDATE HoaDonChiTiet SET MaHDBan =?, MaSP=? ,SoLuong=?, GiamGia = ? WHERE MaHDCT = ?";
        XJDBC.update(sql, 
        model.getMaHDBan(),
        model.getMaSP(),
        model.getSoLuong(),
        model.getGiamGia(),
        model.getMaHDCT());
    }

    @Override
    public void delete(Integer MaHDCT) {
        String sql = "DELETE FROM HoaDonChiTiet WHERE MaHDCT = ? ";
        XJDBC.update(sql, MaHDCT);
    }

    @Override
    public HoaDonChiTiet selectById(Integer MaHDCT) {
        String sql = "SELECT * FROM HoaDonChiTiet WHERE MaHDCT=?";
        List<HoaDonChiTiet> list = selectBySql(sql, MaHDCT);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<HoaDonChiTiet> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<HoaDonChiTiet> selectBySql(String sql, Object... args) {
         List<HoaDonChiTiet> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while(rs.next()){
                    HoaDonChiTiet entity=new HoaDonChiTiet();
                    entity.setMaHDBan(rs.getString("MaHDBan"));
                    entity.setMaSP(rs.getString("MaSP"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setGiamGia(rs.getFloat("GiamGia"));
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
    
    public List<Object[]> getHDCTByMaHDBan(String MaHD){
        String sql = "{CALL sp_HDCT_MaHD(?)}"; 
        String[] cols = {"MaHDCT","MaHDBan","TenSP","SoLuong","DonGiaBan","GiamGia","ThanhTien"};// chứa tên cột khi đổ vào table
        return this.getListOfArray(sql, cols, MaHD);//chứa kết quả câu lệnh
        }

}
