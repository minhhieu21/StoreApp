/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.app.ui;

import java.awt.Color;
import static java.awt.Color.white;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import store.app.dao.DanhMucSpDAO;
import store.app.dao.SanPhamDAO;
import store.app.entity.DanhMucSP;
import store.app.entity.SanPham;
import store.app.utils.MsgBox;
import store.app.utils.XImage;
import store.app.utils.XJDBC;

/**
 *
 * @author asus
 */
public class SanPham_panel extends javax.swing.JPanel {

    /**
     * Creates new form NhanVien_panel
     */
    public SanPham_panel() {
        initComponents();
        init();
    }
    
    JFileChooser FileChooser = new JFileChooser("D:\\Du an 1\\store-app\\src\\main\\images");
    DanhMucSpDAO dmdao = new DanhMucSpDAO();
    String strHinhAnh = null;
    
        void viewTable(){
        tbSanPham.getColumnModel().getColumn(0).setPreferredWidth(40);
        tbSanPham.getColumnModel().getColumn(1).setPreferredWidth(160);
        tbSanPham.getColumnModel().getColumn(2).setPreferredWidth(80);
        tbSanPham.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbSanPham.getColumnModel().getColumn(4).setPreferredWidth(80);
        tbSanPham.getColumnModel().getColumn(5).setPreferredWidth(60);
        tbSanPham.getColumnModel().getColumn(6).setPreferredWidth(60);
        tbSanPham.getColumnModel().getColumn(7).setPreferredWidth(60);
        tbSanPham.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
        
    void fillTableDmSP(){
    //lấy list nhanVien từ CSDL điền vào bảng
    //LƯU Ý: CHỈ TRƯỞNG PHÒNG MỚI XEM ĐƯỢC MẬT KHẨU CỦA NGƯỜI KHÁC
        DefaultTableModel model = (DefaultTableModel) tbDmSP.getModel();
        model.setRowCount(0);
        try {
            List<DanhMucSP> list = dmdao.selectAll();
            for (int i = 0; i < list.size(); i++) {
                DanhMucSP dmsp = list.get(i);
                model.addRow(new Object[]{
                i + 1,
                dmsp.getMaDM(),
                dmsp.getTenDM(),
            });
            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    
    DanhMucSP getFormDanhMucSP(){    
    //lấy thông tin trên form cho vào đt danh mục sản phẩm
    //return nhanVien
    
        DanhMucSP dmsp = new DanhMucSP();
        dmsp.setTenDM(txtTenDM_DmSP.getText());
        return dmsp;
    }
    
    DanhMucSP getFormDanhMucSP1(){    
    //lấy thông tin trên form cho vào đt danh mục sản phẩm
    //return nhanVien
    
        DanhMucSP dmsp = new DanhMucSP();
        dmsp.setMaDM(Integer.valueOf(txtMaDm_Dmsp.getText()));
        dmsp.setTenDM(txtTenDM_DmSP.getText());
        return dmsp;
    }
    
    void setFormDanhMucSP(DanhMucSP dmsp) {
        txtMaDm_Dmsp.setText(String.valueOf(dmsp.getMaDM()));
        txtTenDM_DmSP.setText(dmsp.getTenDM());
    }
    
    void insertDmSP(){
    //lấy thông tin trên form để
    //thêm danh mục sp vào CSDL
          DanhMucSP model = getFormDanhMucSP();
          try {
              dmdao.insert(model);
              this.fillTableDmSP();
              this.fillComboBoxDmSP();
              MsgBox.showMessageDialog(this, "Thêm mới danh mục sản phẩm thành công");
              txtMaDm_Dmsp.setText("");
              txtTenDM_DmSP.setText("");
          } catch (Exception e) {
              MsgBox.showMessageDialog(this, "Thêm mới danh mục sản phẩm thất bại !");
              e.printStackTrace();
          }
    }
    void updateDmSP(){
    //lấy thông tin trên form để
    //cập nhật danh mục sp theo maDM
          DanhMucSP model = getFormDanhMucSP1();
          try {
              dmdao.update(model);
              this.fillTableDmSP();
              this.fillComboBoxDmSP();
              MsgBox.showMessageDialog(this, "Cập nhật danh mục sản phẩm thành công");
              txtMaDm_Dmsp.setText("");
              txtTenDM_DmSP.setText("");
          } catch (Exception e) {
              MsgBox.showMessageDialog(this, "Cập nhật danh mục sản phẩm thất bại !");
              e.printStackTrace();
          }
      }
      
      void deleteDmSP(){
    //lấy maDm trên form, xóa danh mục sp theo maDm
    //xóa trắng form
          if (MsgBox.showConfirmDialog(this, "Bạn chắc chắn xóa Danh mục sản phẩm này")) {
              try {
                    String maDm = txtMaDm_Dmsp.getText();
                    dmdao.delete(maDm);
                    this.fillTableDmSP();
                    this.fillComboBoxDmSP();
                    MsgBox.showMessageDialog(this, "Xóa danh mục sản phẩm thành công");
                    txtMaDm_Dmsp.setText("");
                    txtTenDM_DmSP.setText("");
                  }catch (Exception e) {
                    MsgBox.showMessageDialog(this, "Không thể xóa danh mục sản phẩm");   
              }
          }
          
    }
    
   
    
    public void LayDuLieuSanPhamTheoDanhMucSP (String MaDM) {
        String cautruyvan = " select MaSP,SanPham.TenSP,DanhMucSP.TenDanhMuc\n" +
        " as TenDanhMuc,SanPham.Size from SanPham\n" +
        " inner join DanhMucSP on DanhMucSP.MaDM = SanPham.MaDM where DanhMucSP.MaDM = " + MaDM;
        ResultSet rs = XJDBC.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã Sản Phẩm", "Tên sản phẩm", "Loại sản phẩm", "Size"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbSP_DmSP.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[5];
                item[0] = c;
                item[1] = rs.getString("MaSP");
                item[2] = rs.getString("TenSP");
                item[3] = rs.getString("TenDanhMuc");
                item[4] = rs.getString("Size");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgpThanhVien = new javax.swing.ButtonGroup();
        lblTitle = new javax.swing.JLabel();
        tab = new javax.swing.JTabbedPane();
        pnlCapNhat = new javax.swing.JPanel();
        lblMaSP = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        lblDGB = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        lblSoLuong = new javax.swing.JLabel();
        lblGhiChu = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblAnh = new javax.swing.JLabel();
        lblDanhMuc = new javax.swing.JLabel();
        cboDanhMuc = new javax.swing.JComboBox<>();
        txtTenSP = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtSize = new javax.swing.JTextField();
        lblKichCo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        panelTimKiem = new javax.swing.JPanel();
        lblTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        pnlDanhMuc = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbDmSP = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtMaDm_Dmsp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTenDM_DmSP = new javax.swing.JTextField();
        themDM = new javax.swing.JButton();
        xoaDM = new javax.swing.JButton();
        suaDM = new javax.swing.JButton();
        Mới = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbSP_DmSP = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(30, 55, 153));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("QUẢN LÍ SẢN PHẨM");

        pnlCapNhat.setBackground(new java.awt.Color(255, 255, 255));
        pnlCapNhat.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblMaSP.setText("Mã sản phẩm :");

        lblTenSP.setText("Tên sản phẩm :");

        lblDGB.setText("Đơn giá bán :");

        txtMaSP.setEditable(false);
        txtMaSP.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaSP.setForeground(new java.awt.Color(255, 0, 0));

        lblSoLuong.setText("Số lượng :");

        lblGhiChu.setText("Ghi chú :");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        btnThem.setBackground(new java.awt.Color(246, 185, 59));
        btnThem.setForeground(new java.awt.Color(30, 55, 153));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/them.png"))); // NOI18N
        btnThem.setText("Save");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(246, 185, 59));
        btnSua.setForeground(new java.awt.Color(30, 55, 153));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/sua.png"))); // NOI18N
        btnSua.setText("Edit");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(246, 185, 59));
        btnXoa.setForeground(new java.awt.Color(30, 55, 153));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/delete-button.png"))); // NOI18N
        btnXoa.setText("Delete");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(246, 185, 59));
        btnMoi.setForeground(new java.awt.Color(30, 55, 153));
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/newww.png"))); // NOI18N
        btnMoi.setText("New");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(246, 185, 59));
        btnFirst.setForeground(new java.awt.Color(30, 55, 153));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/last.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(246, 185, 59));
        btnPrev.setForeground(new java.awt.Color(30, 55, 153));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/back.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(246, 185, 59));
        btnNext.setForeground(new java.awt.Color(30, 55, 153));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/next.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(246, 185, 59));
        btnLast.setForeground(new java.awt.Color(30, 55, 153));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/first.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("ẢNH SẢN PHẨM ");

        lblAnh.setBackground(new java.awt.Color(30, 55, 153));
        lblAnh.setText("Ảnh sản phẩm ");
        lblAnh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        lblDanhMuc.setText("Danh mục sản phẩm :");

        cboDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDanhMucActionPerformed(evt);
            }
        });

        txtGiaBan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaBanKeyReleased(evt);
            }
        });

        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });

        lblKichCo.setText("Kích cỡ :");

        jLabel1.setText("Vui lòng nhập đầy đủ thông tin sản phẩm");

        jLabel2.setText("trước khi lưu");

        javax.swing.GroupLayout pnlCapNhatLayout = new javax.swing.GroupLayout(pnlCapNhat);
        pnlCapNhat.setLayout(pnlCapNhatLayout);
        pnlCapNhatLayout.setHorizontalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDGB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTenSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(74, 74, 74))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaSP)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDanhMuc)
                                    .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                                        .addComponent(lblKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(13, 13, 13))
                                    .addComponent(txtSize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtGiaBan)
                            .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong)
                            .addComponent(txtTenSP)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSua)
                                .addGap(4, 4, 4)
                                .addComponent(btnXoa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMoi)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(62, 62, 62)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                        .addComponent(lblAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(lblGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(169, 169, 169))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        pnlCapNhatLayout.setVerticalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblMaSP)
                        .addGap(5, 5, 5)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTenSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDanhMuc)
                            .addComponent(lblKichCo))
                        .addGap(4, 4, 4)
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(lblDGB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(lblSoLuong)
                        .addGap(4, 4, 4)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblGhiChu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addGap(3, 3, 3)
                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast)
                    .addComponent(btnFirst)
                    .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem)
                        .addComponent(btnSua)
                        .addComponent(btnXoa)
                        .addComponent(btnMoi)))
                .addGap(25, 25, 25))
        );

        tab.addTab("CẬP NHẬT", pnlCapNhat);

        pnlDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ SP", "TÊN SP", "SL TỒN KHO", "GIÁ BÁN", "ẢNH ", "GHI CHÚ", "DANH MỤC", "SIZE"
            }
        ));
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbSanPham);

        panelTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        panelTimKiem.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTimKiem.setText("TÌM KIẾM SẢN PHẨM :");

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelTimKiemLayout = new javax.swing.GroupLayout(panelTimKiem);
        panelTimKiem.setLayout(panelTimKiemLayout);
        panelTimKiemLayout.setHorizontalGroup(
            panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTimKiemLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(lblTimKiem)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTimKiemLayout.setVerticalGroup(
            panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTimKiemLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTimKiem))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
        );

        tab.addTab("DANH SÁCH", pnlDanhSach);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tbDmSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã danh mục SP", "Tên danh mục SP"
            }
        ));
        tbDmSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDmSPMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbDmSP);

        jLabel5.setText("Mã danh mục :");

        txtMaDm_Dmsp.setEditable(false);

        jLabel6.setText("Tên danh mục");

        themDM.setBackground(new java.awt.Color(246, 185, 59));
        themDM.setForeground(new java.awt.Color(30, 55, 153));
        themDM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/them.png"))); // NOI18N
        themDM.setText("Thêm");
        themDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themDMActionPerformed(evt);
            }
        });

        xoaDM.setBackground(new java.awt.Color(246, 185, 59));
        xoaDM.setForeground(new java.awt.Color(30, 55, 153));
        xoaDM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/delete-button.png"))); // NOI18N
        xoaDM.setText("Xóa");
        xoaDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaDMActionPerformed(evt);
            }
        });

        suaDM.setBackground(new java.awt.Color(246, 185, 59));
        suaDM.setForeground(new java.awt.Color(30, 55, 153));
        suaDM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/sua.png"))); // NOI18N
        suaDM.setText("Sửa");
        suaDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaDMActionPerformed(evt);
            }
        });

        Mới.setBackground(new java.awt.Color(246, 185, 59));
        Mới.setForeground(new java.awt.Color(30, 55, 153));
        Mới.setIcon(new javax.swing.ImageIcon(getClass().getResource("/store/app/icons/newww.png"))); // NOI18N
        Mới.setText("Mới");
        Mới.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MớiActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("DANH MỤC SẢN PHẨM ");

        tbSP_DmSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã  SP", "Tên  SP", "Danh mục", "Size"
            }
        ));
        jScrollPane5.setViewportView(tbSP_DmSP);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("SẢN PHẨM ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaDm_Dmsp, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtTenDM_DmSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(themDM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(xoaDM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(suaDM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Mới, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(21, 21, 21))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(313, 313, 313)
                        .addComponent(jLabel7)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaDm_Dmsp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenDM_DmSP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(themDM)
                            .addComponent(suaDM))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(xoaDM)
                            .addComponent(Mới)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlDanhMucLayout = new javax.swing.GroupLayout(pnlDanhMuc);
        pnlDanhMuc.setLayout(pnlDanhMucLayout);
        pnlDanhMucLayout.setHorizontalGroup(
            pnlDanhMucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlDanhMucLayout.setVerticalGroup(
            pnlDanhMucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tab.addTab("DANH MỤC SẢN PHẨM", pnlDanhMuc);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tab))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void MớiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MớiActionPerformed
        txtMaDm_Dmsp.setText("");
        txtTenDM_DmSP.setText("");
    }//GEN-LAST:event_MớiActionPerformed

    private void suaDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaDMActionPerformed
        this.updateDmSP();
    }//GEN-LAST:event_suaDMActionPerformed

    private void xoaDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaDMActionPerformed
        this.deleteDmSP();
    }//GEN-LAST:event_xoaDMActionPerformed

    private void themDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themDMActionPerformed
        if (txtTenDM_DmSP.getText().isEmpty()) {
            MsgBox.showMessageDialog(this, "Bạn chưa nhập tên danh mục");
            return;
        }else{
            this.insertDmSP();
        }
    }//GEN-LAST:event_themDMActionPerformed

    private void tbDmSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDmSPMouseClicked
        int viTriDongVuaBam = tbDmSP.getSelectedRow();
        txtMaDm_Dmsp.setText(tbDmSP.getValueAt(viTriDongVuaBam, 1).toString());
        txtTenDM_DmSP.setText(tbDmSP.getValueAt(viTriDongVuaBam, 2).toString());

        LayDuLieuSanPhamTheoDanhMucSP(txtMaDm_Dmsp.getText());
        
    }//GEN-LAST:event_tbDmSPMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        this.fillTableSP();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void tbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMouseClicked
        if (evt.getClickCount()==1) {
            this.row = tbSanPham.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tbSanPhamMouseClicked

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
        if (txtSoLuong.getText().isEmpty()) {
            return;
        }
        try {
            int soluong =Integer.parseInt(txtSoLuong.getText());
            if (soluong <= 0) {
                MsgBox.showErrorDialog(this, "Số lượng sản phẩm phải lớn hơn 0", "THÔNG BÁO");
                return ;
            }
        } catch (Exception e) {
            MsgBox.showErrorDialog(this, "Phải là số", "THÔNG BÁO");
            return ;
        }
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void txtGiaBanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaBanKeyReleased
        if (txtGiaBan.getText().isEmpty()) {
            return;
        }
        try {
            float dongia =Float.parseFloat(txtGiaBan.getText());

            if (dongia <= 0) {
                MsgBox.showErrorDialog(this, "Giá sản phẩm phải lớn 0", "THÔNG BÁO");
                return ;
            }
        } catch (Exception e) {
            MsgBox.showErrorDialog(this, "Phải là số", "THÔNG BÁO");
            return ;
        }
    }//GEN-LAST:event_txtGiaBanKeyReleased

    private void cboDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDanhMucActionPerformed
        this.chonDMSanPham();
    }//GEN-LAST:event_cboDanhMucActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        this.chonAnh();
    }//GEN-LAST:event_lblAnhMouseClicked

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        this.deleteSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (MsgBox.showConfirmDialog(this, "Bạn chắc chắn muốn cập nhật sản phẩm này ??")) {
            this.updateSP();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateForm()) {
            if (checkNullHinh()) {
                if (checkSo()) {
                    this.insertSP();
                }
            }
        }else{
            MsgBox.showMessageDialog(this, "Bạn chưa nhập đầy đủ thông tin !");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    void chonDMSanPham() {
        try {
            DanhMucSP dmsp = (DanhMucSP) cboDanhMuc.getSelectedItem(); 
            cboDanhMuc.setToolTipText(String.valueOf(dmsp.getMaDM()));
        } catch (Exception e) {
            
        }       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Mới;
    private javax.swing.ButtonGroup bgpThanhVien;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblDGB;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblKichCo;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelTimKiem;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhMuc;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JButton suaDM;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tbDmSP;
    private javax.swing.JTable tbSP_DmSP;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JButton themDM;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaDm_Dmsp;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSize;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenDM_DmSP;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JButton xoaDM;
    // End of variables declaration//GEN-END:variables

    
    void init(){
        fillTableDmSP();
        fillTableSP();
        fillComboBoxDmSP();
        viewTableSP_DMSP();
        viewTable();
    }
    
    
    SanPhamDAO spdao = new SanPhamDAO();
    
    
    SanPham getForm(){    
    //lấy thông tin trên form cho vào đt sản phẩm
    //return sản phẩm
    
        SanPham sp = new SanPham();
        sp.setMaSP(txtMaSP.getText());
        sp.setTenSP(txtTenSP.getText());
        sp.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        sp.setSize(txtSize.getText());
        sp.setGiaBan(Float.valueOf(txtGiaBan.getText()));
        sp.setAnh(lblAnh.getToolTipText());    //lấy tên hình
        sp.setMaDM(Integer.valueOf(cboDanhMuc.getToolTipText()));
        sp.setGhiChu(txtGhiChu.getText());
        return sp;
        
    }
    
    //lấy thông tin từ đt sản phẩm đưa lên form
    //lưu ý lấy hình ảnh từ thư mục images đưa lên form theo tenFile lấy từ SanPham
    void setForm(SanPham sp) {//đưa dữ liệu lên form
        cboDanhMuc.setToolTipText(String.valueOf(sp.getMaDM()));    

        txtMaSP.setText(sp.getMaSP());
        txtTenSP.setText(sp.getTenSP());
        txtSoLuong.setText(String.valueOf(sp.getSoLuong()));
        txtSize.setText(sp.getSize());
        txtGiaBan.setText(String.valueOf(sp.getGiaBan()));
        // load hinh anh
        lblAnh.setToolTipText(sp.getAnh());
        if (sp.getAnh()!= null) {
            lblAnh.setText("");
            lblAnh.setIcon(XImage.readLogo(sp.getAnh()));
            /*
            ImageIcon readLogo(String tenFile) đọc file trong thư mục logos theo tên file trả về ImageIcon
            void setIcon(ImageIcon icon) set Icon cho lbl
            */
        }else{
            lblAnh.setIcon(XImage.readLogo("D:\\Du an 1\\store-app\\src\\main\\images\\noImage.png"));
        }
        cboDanhMuc.getModel().setSelectedItem(dmdao.selectById(String.valueOf(sp.getMaDM()))); 
        txtGhiChu.setText(sp.getGhiChu());


    }
    int row = -1; //chứa vị trí hiện hành của nhân viên
    void updateStatus(){  //cập nhật trạng thái form
          boolean edit = (this.row >= 0); //đang chọn một hàng nào đó ->> trạng thái edit
          boolean first = (this.row == 0); //đang ở bản ghi đầu tiên 
          boolean last = (this.row == tbSanPham.getRowCount() - 1); // đang ở bản ghi cuối cùng
          
          //trạng thái form
          txtMaSP.setEditable(!edit);
          btnThem.setEnabled(!edit);
          btnSua.setEnabled(edit);
          btnXoa.setEnabled(edit);
          
          //trạng thái điều hướng 
          btnFirst.setEnabled(edit && !first);
          btnPrev.setEnabled(edit && !first);
          btnNext.setEnabled(edit && !last);
          btnLast.setEnabled(edit && !last);
    }
    
    void first(){
          this.row = 0;
          this.edit();
          tbSanPham.setRowSelectionInterval(row, row);
    }
    
    void prev(){
          if (this.row > 0) {
              this.row--;
              this.edit();
              tbSanPham.setRowSelectionInterval(row, row);

          }
    }
      
    void next(){
           if (this.row < tbSanPham.getRowCount() - 1) {
              this.row++;
              this.edit();
              tbSanPham.setRowSelectionInterval(row, row);

          } 
    }
    
    void last(){
           this.row = tbSanPham.getRowCount() -1 ;
           this.edit();
           tbSanPham.setRowSelectionInterval(row, row);

    }
    
    void edit(){//điền thông tin đt sản phẩm lên form (theo vị trí row)
        try {
            String masp = (String) tbSanPham.getValueAt(this.row, 0);
            SanPham model = spdao.selectById(masp);
            if (model != null) {
                this.setForm(model);
                this.updateStatus();
                tab.setSelectedIndex(0);
            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }  
    }
    
    void fillTableSP(){
    //lấy list nhanVien từ CSDL điền vào bảng
    //LƯU Ý: CHỈ TRƯỞNG PHÒNG MỚI XEM ĐƯỢC MẬT KHẨU CỦA NGƯỜI KHÁC
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            List<SanPham> list = spdao.selectByKeyword(keyword);
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getSoLuong(),
                    sp.getGiaBan(),
                    sp.getAnh(),
                    sp.getGhiChu(),
                    sp.getMaDM(),
                    sp.getSize()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    
    void fillComboBoxDmSP() { //đổ dữ liệu danh mục sản phẩm lên combobox
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDanhMuc.getModel(); //kết nối model với cbo
        model.removeAllElements();   //xóa toàn bộ item của cbo
        try {
            List<DanhMucSP> list = dmdao.selectAll();
            for (DanhMucSP dmsp : list) {
                model.addElement(dmsp);    //thêm đối tượng (Object) vào model
            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }
 
    void insertSP(){
    //lấy thông tin trên form để
    //thêm sản phẩm vào CSDL
          SanPham model = getForm();
          try {
              spdao.insert(model);
              this.fillTableSP();
              this.clearForm();
              MsgBox.showMessageDialog(this, "Thêm mới sản phẩm thành công");
          } catch (Exception e) {
              MsgBox.showMessageDialog(this, "Thêm mới sản phẩm thất bại !");
              e.printStackTrace();
          }
      }

      void updateSP(){
    //lấy thông tin trên form để
    //cập nhật nhanVien theo maKH
          SanPham model = getForm();
          try {
              spdao.update(model);
              this.fillTableSP();
              this.clearForm();
              MsgBox.showMessageDialog(this, "Cập khách sản phẩm thành công");
          } catch (Exception e) {
              MsgBox.showMessageDialog(this, "Cập nhật sản phẩm thất bại !");
              e.printStackTrace();
          }
      }
      
    void deleteSP(){
    //lấy sản phẩm trên form, xóa sản phẩm theo maSP
    //xóa trắng form
              String maSP = txtMaSP.getText();
              if  (MsgBox.showConfirmDialog(this, "Bạn thực sự muốn xóa sản phẩm này ?")) {
                  try {
                    spdao.delete(maSP);
                    this.fillTableSP();
                    this.clearForm();
                    MsgBox.showMessageDialog(this, "Xóa sản phẩm thành công !");
                  }catch (Exception e) {
                    MsgBox.showMessageDialog(this, "Không thể xóa sản phẩm !"); 
                    return;
              }
        }      
    }
      
    void clearForm(){ //xóa trắng form
        this.setForm(new SanPham());
        lblAnh.setIcon(null);
        this.row = -1;
        this.updateStatus();
        txtGhiChu.setText("");
        txtMaSP.setBackground(white);
        txtMaSP.setEditable(false);
    }

    private boolean validateForm() {
        if (txtTenSP.getText().isEmpty() || txtSoLuong.getText().isEmpty() || 
            txtSize.getText().isEmpty() || txtGiaBan.getText().isEmpty() ) {
            return false;
        }
        return true;
    }
    
    public boolean checkNullHinh(){
        if(lblAnh.getToolTipText()!=null){
            return true;
        }else{
            MsgBox.showMessageDialog(this, "Không được để trống hình.");
            return false;
        }
    }
    
    public boolean checkSo(){
            try {
                int soluong =Integer.parseInt(txtSoLuong.getText());
                float dongia =Float.parseFloat(txtGiaBan.getText());
                
                if (dongia <= 0) {
                    MsgBox.showErrorDialog(this, "Giá sản phẩm không được âm", "THÔNG BÁO");
                    return false;
                }           
                if (soluong <= 0) {
                    MsgBox.showErrorDialog(this, "Số lượng sản phẩm phải lớn hơn 0", "THÔNG BÁO");
                    return false;
                }        
            } catch (Exception e) {
                MsgBox.showErrorDialog(this, "Phải là số", "THÔNG BÁO");
                return false;
            }
            return true;
        }
    
    void chonAnh() {
        if (FileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { //nếu người dùng đã chọn đc file
            File file = FileChooser.getSelectedFile();    //lấy file người dùng chọn
            FileChooser.setDialogTitle("Chọn ảnh");
            if (XImage.saveLogo(file)) {  //sao chép file đã chọn thư mục logos
                // Hiển thị hình lên form
                lblAnh.setText("");
                lblAnh.setIcon(XImage.readLogo(file.getName())); //file.getName(); lấy tên của file
                //ImageIcon readLogo(String tenFile) đọc file trong thư mục logos theo tên file trả về ImageIcon
                //void setIcon(ImageIcon icon) set Icon cho lbl
                lblAnh.setToolTipText(file.getName());
            }
        }
    }
    
    public boolean checkTrungMa(JTextField txt) {//kiểm tra trùng mã danh mục sản phẩm
        txt.setBackground(white);
        if (spdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            txt.setBackground(Color.pink);
            MsgBox.showMessageDialog(this,"Mã sản phẩm "+ txt.getText()+ " đã tồn tại");
            return false;
        }
    }
    
    void viewTableSP_DMSP(){
        tbSP_DmSP.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbSP_DmSP.getColumnModel().getColumn(1).setPreferredWidth(70);
        tbSP_DmSP.getColumnModel().getColumn(2).setPreferredWidth(270);
        tbSP_DmSP.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbSP_DmSP.getColumnModel().getColumn(4).setPreferredWidth(90);
        tbSP_DmSP.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
      
}
