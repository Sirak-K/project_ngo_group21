/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.Services;

import oru.inf.InfDB;
import oru.inf.InfException;
/**
 *
 * @author anton
 */
public class AnstalldService {
    private InfDB idb;
    private String anstalldAid;
    
    public AnstalldService(InfDB idb, String anstalldAid){
        this.idb = idb; 
        this.anstalldAid = anstalldAid; 
    }
    
    public AnstalldService(){
        
    }
    

    public void uppdateraUppgifter(String nyttFnamn, String nyttEnamn, String nyttAdress, String nyttEpost, 
            String nyttTele, String nyttAnsDatum, String nyttAvdelning){
        
        try{
        idb.update("UPDATE anstalld SET fornamn ='" + nyttFnamn + "' WHERE aid =" + anstalldAid);
        idb.update("UPDATE anstalld SET efternamn ='" + nyttEnamn + "' WHERE aid =" + anstalldAid);
        idb.update("UPDATE anstalld SET adress ='" + nyttAdress + "' WHERE aid =" + anstalldAid);
        idb.update("UPDATE anstalld SET epost ='" + nyttEpost + "' WHERE aid =" + anstalldAid);
        idb.update("UPDATE anstalld SET telefon ='" + nyttTele + "' WHERE aid =" + anstalldAid);
        idb.update("UPDATE anstalld SET anstallningsdatum ='" + nyttAnsDatum + "' WHERE aid =" + anstalldAid);
        idb.update("UPDATE anstalld SET avdelning ='" + nyttAvdelning + "' WHERE aid =" + anstalldAid);
        
        }catch(InfException ex){
            System.out.println(ex.getMessage());
            
        }
    }  
}
