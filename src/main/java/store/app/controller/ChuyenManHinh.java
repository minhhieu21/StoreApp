/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.app.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import store.app.beans.DanhMucBeans;
import store.app.ui.HoaDon_panel;
import store.app.ui.Home_panel;
import store.app.ui.KhachHang_panel;
import store.app.ui.NhanVien_panel;
import store.app.ui.SanPham_panel;


/**
 *
 * @author asus
 */
public class ChuyenManHinh {
    private JPanel root;
    private String kindSelect = "";
    
    private List<DanhMucBeans> listItem = null;
    
    public ChuyenManHinh(JPanel jpnroot){
        this.root = jpnroot;
    }
    
    public void setView(JPanel jpnItem, JLabel jlbItem){
        kindSelect = "TrangChu";
        
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new Home_panel());
//        root.validate();
//        root.repaint();
    }
    
    public void setEvent(List<DanhMucBeans> listItem){
        this.listItem = listItem;
        
        for (DanhMucBeans item : listItem) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener{
        private JPanel node;
        private String kind;
        
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }
        
        

        @Override
        public void mouseClicked(MouseEvent e) {
            switch(kind){
                case "SanPham":
                    node = new SanPham_panel();
                    break;
                case "NhanVien":
                    node = new NhanVien_panel();
                    break;
                case "KhachHang":
                    node = new KhachHang_panel();
                    break; 
                case "HoaDon":
                    node = new HoaDon_panel();
                    break;
                default:
                    node = new Home_panel();
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
//            root.repaint();
            setChangeBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {//khi click v??o menu th?? s??? c?? hi???u ???ng nh??y m??u ch???n
            kindSelect = kind;
            jpnItem.setBackground(new Color(7, 153, 146));
            jlbItem.setBackground(new Color(7, 153, 146));
            jlbItem.setForeground(Color.yellow);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {// khi r?? v??o menu th?? s??? ?????i m??u
            jpnItem.setBackground(Color.white);
        }

        @Override
        public void mouseExited(MouseEvent e) { //khi tho??t r?? chu???t ??? menu
            if (!kindSelect.equalsIgnoreCase(kind)) {
                jpnItem.setBackground( new Color(246,185,59));
                jlbItem.setBackground(Color.black);
            }
        }
        
        private void setChangeBackground(String kind){
            for (DanhMucBeans item : listItem) {
                if (item.getKind().equalsIgnoreCase(kind)) {
                    item.getJlb().setBackground(Color.white);
                    item.getJpn().setBackground(Color.white); // khi click v??o menu th?? s??? ?????i m??u panel
                    jlbItem.setForeground(Color.black);
                }else{
                    item.getJlb().setBackground(new Color(246,185,59)); //kh click v??o menu th?? s??? gi??? nguy??n m??u l??c ban ?????u
                    item.getJpn().setBackground(new Color(246,185,59));
                }
            }
        }
    
    }
}
