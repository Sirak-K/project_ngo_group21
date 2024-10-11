/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.Services;

import oru.inf.InfDB;
import oru.inf.InfException;

public class ProjectServices {
    private InfDB idb;
    private InfException ex;

    public ProjectServices(InfDB idb) {
        this.idb = idb;
    }

    public void uppdateraProjekt(String projektID, 
            String namn, String beskrivning, String startdatum, 
            String slutdatum, String kostnad, String status, 
            String prioritet, String projektchef, String land) throws InfException {
        String sqlQuery = "UPDATE projekt SET "
                   + "projektnamn = '" + namn + "', "
                   + "beskrivning = '" + beskrivning + "', "
                   + "startdatum = '" + startdatum + "', "
                   + "slutdatum = '" + slutdatum + "', "
                   + "kostnad = '" + kostnad + "', "
                   + "status = '" + status + "', "
                   + "prioritet = '" + prioritet + "', "
                   + "projektchef = '" + projektchef + "', "
                   + "land = '" + land + "' "
                   + "WHERE pid = '" + projektID + "'";
        idb.update(sqlQuery);
    }
}
