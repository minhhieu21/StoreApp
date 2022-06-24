/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.app.dao;

import java.util.List;

/**
 *
 * @author asus
 */
abstract public class storeAppDAO<EntityType, KeyType> { //generic
    abstract public void insert(EntityType entity);//EntityType: SanPham, NhanVien, KhachHang...
    abstract public void update(EntityType entity);
    abstract public void delete(KeyType id);//KeyType: String, Integer, Double...
    abstract public EntityType selectById(KeyType id);
    abstract public List<EntityType> selectAll();
    abstract protected List<EntityType> selectBySql(String sql, Object...args);
}
