/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.app.utils;

import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author asus
 */
public class Validator {
    public static void validateEmty(JTextField field, StringBuilder sb, String errorMessage){
        if (field.getText().equals("")) {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.red);
            field.requestFocus();
        }else{
            field.setBackground(Color.white);
        }
    }
    
    
    public static void validateEmtyPW(JPasswordField field, StringBuilder sb, String errorMessage){
        String password = new String(field.getPassword());
        if (password.equals("")) {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.red);
            field.requestFocus();
        }else{
            field.setBackground(Color.white);
        }
    }
}
