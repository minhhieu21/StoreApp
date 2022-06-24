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
import store.app.entity.DanhMucSP;
import store.app.utils.XJDBC;

/**
 *
 * @author asus
 */
public class DanhMucSpDAO extends storeAppDAO<DanhMucSP, String>{

    @Override
    public void insert(DanhMucSP model) {
        String sql="INSERT INTO DanhMucSP ( TenDanhMuc) VALUES ( ?)";
        XJDBC.update(sql, 
        model.getTenDM());
    }

    @Override
    public void update(DanhMucSP model) {
        String sql="UPDATE DanhMucSP SET TenDanhMuc=? WHERE MaDM=?";
        XJDBC.update(sql, 
        model.getTenDM(), 
        model.getMaDM());
    }

    @Override
    public void delete(String maDM) {
        String sql = "DELETE FROM DanhMucSP WHERE MaDM = ? ";
        XJDBC.update(sql, maDM);
    }

    @Override
    public DanhMucSP selectById(String maDM) {
        String sql = "SELECT * FROM DanhMucSP WHERE MaDM=?";
        List<DanhMucSP> list = selectBySql(sql, maDM);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<DanhMucSP> selectAll() {
        String sql = "Select * from DanhMucSP";
        return this.selectBySql(sql);
    }

    @Override
    protected List<DanhMucSP> selectBySql(String sql, Object... args) {
        List<DanhMucSP> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJDBC.query(sql, args);
                while(rs.next()){
                    DanhMucSP entity=new DanhMucSP();
                    entity.setMaDM(rs.getInt("MaDM"));
                    entity.setTenDM(rs.getString("TenDanhMuc"));
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
    
}
