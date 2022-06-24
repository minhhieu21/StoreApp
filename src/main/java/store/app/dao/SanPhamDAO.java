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
import store.app.entity.SanPham;
import store.app.utils.XJDBC;

/**
 *
 * @author asus
 */
public class SanPhamDAO extends storeAppDAO<SanPham, String>{

    @Override
    public void insert(SanPham model) {
        String sql="INSERT INTO SanPham VALUES ([dbo].[AUTO_MaSP](), ?, ?, ?, ?, ?, ?, ?);";
        XJDBC.update(sql, 
//        model.getMaSP(), 
        model.getTenSP(), 
        model.getSoLuong(),
        model.getGiaBan(),
        model.getAnh(),
        model.getGhiChu(),
        model.getMaDM(),
        model.getSize());
    }

    @Override
    public void update(SanPham model) {
        String sql="UPDATE SanPham SET TenSP =?, SoLuong=? ,DonGiaBan=? ,Anh=? , GhiChu=? ,MaDM = ?, Size = ? WHERE MaSP = ?";
        XJDBC.update(sql, 
        model.getTenSP(), 
        model.getSoLuong(),
        model.getGiaBan(),
        model.getAnh(),
        model.getGhiChu(),
        model.getMaDM(),
        model.getSize(),
        model.getMaSP());
    }

    @Override
    public void delete(String maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP = ? ";
        XJDBC.update(sql, maSP);
    }

    @Override
    public SanPham selectById(String maSP) {
        String sql = "SELECT * FROM SanPham WHERE MaSP=?";
        List<SanPham> list = selectBySql(sql, maSP);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<SanPham> selectAll() {
        String sql = "SELECT * FROM SanPham";
        return this.selectBySql(sql);
    }

    @Override
    protected List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while(rs.next()){
                    SanPham entity=new SanPham();
                    entity.setMaSP(rs.getString("MaSP"));
                    entity.setTenSP(rs.getString("TenSP"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setGiaBan(rs.getFloat("DonGiaBan"));
                    entity.setAnh(rs.getString("Anh"));
                    entity.setGhiChu(rs.getString("GhiChu"));
                    entity.setMaDM(rs.getInt("MaDM"));
                    entity.setSize(rs.getString("Size"));
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
    
    
    public List<SanPham> selectSPByDanhMuc(int maDM){ //tìm kiếm sản phẩm theo danh mục sản phẩm
        String sql = "SELECT * FROM SanPham WHERE MaDM = ?";
        return this.selectBySql(sql, maDM);
    }
    
    public List<SanPham> selectByKeyword(String keyword){ //tìm kiếm sản phẩm theo từ khóa
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }
    
}
