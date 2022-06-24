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
import store.app.entity.KhachHang;
import store.app.utils.XJDBC;

/**
 *
 * @author asus
 */
public class KhachHangDAO extends storeAppDAO<KhachHang, String>{

    @Override
    public void insert(KhachHang model) {
        String sql="INSERT INTO KhachHang values ([dbo].[AUTO_MaKH](), ?, ?, ?, ?, ?)";
        XJDBC.update(sql, 
//        model.getMaKH(), 
        model.getTenKH(), 
        model.getDiaChi(),
        model.getSdt(),
        model.getEmail(),
        model.isThanhVien());
    }

    @Override
    public void update(KhachHang model) {
        String sql="UPDATE KhachHang SET TenKH =? ,DiaChi=? ,Sdt=? ,Email=? , ThanhVien=? WHERE MaKH = ?";
        XJDBC.update(sql, 
        model.getTenKH(), 
        model.getDiaChi(),
        model.getSdt(),
        model.getEmail(),
        model.isThanhVien(),
        model.getMaKH());
    }

    @Override
    public void delete(String maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH = ? ";
        XJDBC.update(sql, maKH);
    }

    @Override
    public KhachHang selectById(String maKH) {
        String sql = "SELECT * FROM KhachHang WHERE MaKH=?";
        List<KhachHang> list = selectBySql(sql, maKH);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<KhachHang> selectAll() {
        String sql = "SELECT * FROM KhachHang";
        return this.selectBySql(sql);
    }

    @Override
    protected List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while(rs.next()){
                    KhachHang entity=new KhachHang();
                    entity.setMaKH(rs.getString("MaKH"));
                    entity.setTenKH(rs.getString("TenKH"));
                    entity.setDiaChi(rs.getString("DiaChi"));
                    entity.setSdt(rs.getString("Sdt"));
                    entity.setEmail(rs.getString("Email"));
                    entity.setThanhVien(rs.getBoolean("ThanhVien"));
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
    
    public List<KhachHang> selectByKeyword(String keyword){ //tìm kiếm khách hàng theo từ khóa
        String sql = "SELECT * FROM KhachHang WHERE TenKH LIKE ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }
    
    
    public void insertKHHD(KhachHang model) {
        String sql="INSERT INTO KhachHang (MaKH,TenKH,Sdt) VALUES ([dbo].[AUTO_MaKH](),?, ?)";
        XJDBC.update(sql, 
//        model.getMaKH(), 
        model.getTenKH(), 
        model.getSdt());
    }
}
