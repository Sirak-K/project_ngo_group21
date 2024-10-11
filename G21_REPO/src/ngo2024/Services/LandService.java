/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.Services;
import java.util.HashMap;
import oru.inf.InfDB;
import oru.inf.InfException;
/**
 *
 * @author anton
 */
public class LandService {
    private InfDB idb;
    
    public LandService(InfDB idb){
        this.idb = idb; 
    }
    
    public HashMap<String, String> hamtaLandsUppgifter(String namn){
        HashMap<String, String>landsUppgifter = new HashMap<>();
        try{
            String id = idb.fetchSingle("SELECT lid FROM land where namn ='" + namn + "'"); 
            landsUppgifter = idb.fetchRow("SELECT * FROM land where lid ='" + id + "'"); 
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return landsUppgifter; 
    }
    
    public void laggTillLand(int lid, String namn, String sprak, String valuta, 
            String tidszon, String politisk_struktur, String ekonomi){
        try{
            idb.insert("INSERT INTO land VALUES(" + lid +", '" + namn +"','"+ sprak +"',"+ valuta +",'"+ tidszon +"','"+ politisk_struktur +"','"+ ekonomi +"')");
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
    }
}
