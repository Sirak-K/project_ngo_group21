/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ngo2024;

/**
 *
 * @author SSIRA
 */

import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import oru.inf.InfDB;
import oru.inf.InfException;

public class NGO2024 {

    private static InfDB idb;


    public static void main(String[] args) {

        FlatCarbonIJTheme.setup(); 
        try {
            idb = new InfDB("ngo_2024", "3306", "dbAdmin2024", "dbAdmin2024PW");
            new Inloggning(idb).setVisible(true);
        } 
        catch(InfException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
    
}

