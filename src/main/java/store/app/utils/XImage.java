/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.app.utils;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author asus
 */
public class XImage {
    /**
     * Ảnh biểu tượng của ứng dụng xuất hiện trên mọi cửa sổ
     * @return 
     */
    public static final Image APP_ICON;
    static{
    // Tải biểu tượng ứng dụng
    String file = "/store/app/icons/icon.png";
    APP_ICON = new ImageIcon(XImage.class.getResource(file)).getImage();
    }
    
    /**
     * Sao chép file logo chuyên đề vào thư mục logo
     * @param src là đối tượng file ảnh
     */
    
    public static boolean saveLogo(File file){
     File dir = new File("D:\\Du an 1\\store-app\\src\\main\\images");  //khai báo thư mục logos ngang hàng với src
     // Tạo thư mục nếu chưa tồn tại
     if(!dir.exists()){
        dir.mkdirs();
     }
     File newFile = new File(dir, file.getName());
     try {
         // Copy vào thư mục logos (đè nếu đã tồn tại)
         Path source = Paths.get(file.getAbsolutePath());
         Path destination = Paths.get(newFile.getAbsolutePath());
         Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
         return true;
     }
     catch (Exception ex) {
        return false;
     }
 }
    
    /**
     * Đọc hình ảnh logo chuyên đề
     * @param filename là tên file logo
     * @return ảnh đọc trước
     */
    public static ImageIcon readLogo(String fileName){
    File path = new File("D:\\Du an 1\\store-app\\src\\main\\images", fileName);
    return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT)); 
    }
    
    public static ImageIcon readLogoHDCT(String fileName){
    File path = new File("D:\\Du an 1\\store-app\\src\\main\\images", fileName);
    return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT)); 
    }
}
