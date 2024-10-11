/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;
/**
 *
 * @author anton
 */
public class AndraProjekt extends javax.swing.JFrame {
    private InfDB idb;
    private String valtProjekt;
    private Validering val; 

    /**
     * Creates new form AndraProjekt
     */
    public AndraProjekt(InfDB idb) {
        this.idb = idb;
        this.val = new Validering(idb); 
        initComponents();
        populeraCbStatus();
        populeraCbPrioritet();
        populeraProjektChefID();
        populeraLandNamn();
        populeraProjektNamn();
        populeraFalt(); 
        tfPid.setEditable(false);
    }
    //Metod för att populera ComboBox för status. 
    public void populeraCbStatus(){
        cbStatus.addItem("Pågående");
        cbStatus.addItem("Planerat");
        cbStatus.addItem("Avslutat");
    }
    
    //Metod för att populera ComboBox för prioritet. 
    public void populeraCbPrioritet(){
        cbPrioritet.addItem("Hög");
        cbPrioritet.addItem("Medel");
        cbPrioritet.addItem("Låg");
    }
    
    //Skapar en ArrayList och hämtar alla anställnings ID och spar dessa. Returnera dessa i en lista. 
    public ArrayList hamtaProjektChefID(){
        ArrayList<String>projektChefLista = new ArrayList<>();
        try{
            projektChefLista = idb.fetchColumn("SELECT aid FROM anstalld"); 
        }catch(InfException ex){
            System.out.println(ex.getMessage()); 
        }
        return projektChefLista; 
    }
    
    //Metod för att populera ComboBox för porjektchef. Använder ArrayList projektChefLista som skapades ovan för 
    // att hjälpa till med detta. 
    public void populeraProjektChefID(){
        ArrayList<String>projektChefLista = hamtaProjektChefID(); 
        for(String chefIdLista : projektChefLista){
            cbProjektchef.addItem(chefIdLista);
        }
    }
    
    //Skapar en ArrayList och hämtar alla namn på länder och spar dessa. Returnerar dessa i en lista. 
    public ArrayList hamtaLandNamn(){
        ArrayList<String>landNamnLista = new ArrayList<>(); 
        try{
            landNamnLista = idb.fetchColumn("SELECT namn FROM land");
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return landNamnLista; 
    }
    
    //Metod för att populera ComboBox för namn på länder. Använder ArrayList landNamnLista som skapades ovan för
    // att hjälpa till med detta. 
    public void populeraLandNamn(){
        ArrayList<String>landNamnLista = hamtaLandNamn();
        for(String landLista : landNamnLista){
            cbLand.addItem(landLista);
        }
    }
    
    //Skapar en ArrayList och hämtar alla projektnamn och spar dessa. >Returnera dessa i en lista. 
    public ArrayList hamtaProjektNamn(){
        ArrayList<String>projektNamnLista = new ArrayList<>();
        try{
            projektNamnLista = idb.fetchColumn("SELECT projektnamn FROM projekt"); 
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return projektNamnLista; 
    }
    
    //Metod för att populera Combobox för alla projektnamn. Använder ArrayList projektNamnLista som skapades ovan för
    // att hjälpa till med detta. 
    public void populeraProjektNamn(){
        ArrayList<String>projektNamnLista = hamtaProjektNamn(); 
        for(String projektLista : projektNamnLista){
            cbValtProjekt.addItem(projektLista);
        }
    }
    
    public void populeraFalt(){
        try{
            String id = idb.fetchSingle("SELECT pid FROM projekt WHERE projektnamn ='" + valtProjekt + "'");
            String sqlPid = idb.fetchSingle("SELECT pid FROM projekt WHERE pid ='" + id + "'");
            String sqlProjektNamn = idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid ='" + id + "'");
            String sqlBeskrivning = idb.fetchSingle("SELECT beskrivning FROM projekt WHERE pid ='" + id + "'");
            String sqlStartdatum = idb.fetchSingle("SELECT startdatum FROM projekt WHERE pid ='" + id + "'");
            String sqlSlutdatum = idb.fetchSingle("SELECT slutdatum FROM projekt WHERE pid ='" + id + "'");
            String sqlKostnad = idb.fetchSingle("SELECT kostnad FROM projekt WHERE pid ='" + id + "'");
            tfPid.setText(sqlPid);
            tfProjektnamn.setText(sqlProjektNamn);
            tfBeskrivning.setText(sqlBeskrivning);
            tfStartdatum.setText(sqlStartdatum);
            tfSlutdatum.setText(sqlSlutdatum); 
            tfKostnad.setText(sqlKostnad);
        }catch(InfException ex){
            System.out.println(ex.getMessage());
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

        lblVal = new javax.swing.JLabel();
        cbValtProjekt = new javax.swing.JComboBox<>();
        lblPid = new javax.swing.JLabel();
        lblProjektnamn = new javax.swing.JLabel();
        lblBeskrivning = new javax.swing.JLabel();
        lblStartdatum = new javax.swing.JLabel();
        lblSlutdatum = new javax.swing.JLabel();
        lblKostnad = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblPrioritet = new javax.swing.JLabel();
        lblProjektchef = new javax.swing.JLabel();
        lblLand = new javax.swing.JLabel();
        tfPid = new javax.swing.JTextField();
        tfProjektnamn = new javax.swing.JTextField();
        tfBeskrivning = new javax.swing.JTextField();
        tfStartdatum = new javax.swing.JTextField();
        tfSlutdatum = new javax.swing.JTextField();
        tfKostnad = new javax.swing.JTextField();
        cbStatus = new javax.swing.JComboBox<>();
        cbPrioritet = new javax.swing.JComboBox<>();
        cbProjektchef = new javax.swing.JComboBox<>();
        cbLand = new javax.swing.JComboBox<>();
        btnSpara = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblVal.setText("Välj projekt");

        cbValtProjekt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbValtProjektActionPerformed(evt);
            }
        });

        lblPid.setText("ProjektID");

        lblProjektnamn.setText("Projektnamn");

        lblBeskrivning.setText("Beskrivning");

        lblStartdatum.setText("Startdatum");

        lblSlutdatum.setText("Slutdatum");

        lblKostnad.setText("Kostnad");

        lblStatus.setText("Status");

        lblPrioritet.setText("Prioritet");

        lblProjektchef.setText("Projektchef");

        lblLand.setText("Land");

        btnSpara.setText("Spara ändringar");
        btnSpara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaActionPerformed(evt);
            }
        });

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(lblVal))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProjektnamn)
                                    .addComponent(lblPid)
                                    .addComponent(lblBeskrivning)
                                    .addComponent(lblStartdatum)
                                    .addComponent(lblSlutdatum)
                                    .addComponent(lblKostnad)
                                    .addComponent(lblStatus)
                                    .addComponent(lblPrioritet)
                                    .addComponent(lblProjektchef)
                                    .addComponent(lblLand))))
                        .addGap(86, 86, 86)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfPid, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbValtProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfProjektnamn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(1, 1, 1)))
                            .addComponent(cbPrioritet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbProjektchef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(btnSpara))
                    .addComponent(btnTillbaka))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnTillbaka)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVal)
                    .addComponent(cbValtProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPid)
                    .addComponent(tfPid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektnamn)
                    .addComponent(tfProjektnamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBeskrivning)
                    .addComponent(tfBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStartdatum)
                    .addComponent(tfStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSlutdatum)
                    .addComponent(tfSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKostnad)
                    .addComponent(tfKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrioritet)
                    .addComponent(cbPrioritet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektchef)
                    .addComponent(cbProjektchef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLand)
                    .addComponent(cbLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSpara)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbValtProjektActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbValtProjektActionPerformed
         String val = (String) cbValtProjekt.getSelectedItem(); 
         valtProjekt = val;
         populeraFalt(); 
    }//GEN-LAST:event_cbValtProjektActionPerformed

    private void btnSparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaActionPerformed
        if(val.valideringDatum(tfStartdatum.getText()) == false){
            JOptionPane.showMessageDialog(this, "Datum behöver vara i rätt format, YYYY-MM-DD");
        }else if(val.valideringDatum(tfSlutdatum.getText()) == false){
            JOptionPane.showMessageDialog(this, "Datum behöver vara i rätt format, YYYY-MM-DD");
        }else if(val.valideraNumerisk(tfKostnad.getText()) == false){
            JOptionPane.showMessageDialog(this, "Kostnaden behöver vara i rätt format, ange enbart siffror utan decimaler");
        }else if(val.valideringDatum(tfStartdatum.getText()) == true && val.valideringDatum(tfSlutdatum.getText()) == true && val.valideraNumerisk(tfKostnad.getText()) == true){
        int taBortRuta = JOptionPane.showConfirmDialog(null, "Är du säker", "meddelande", JOptionPane.YES_NO_OPTION);
        if(taBortRuta == JOptionPane.YES_OPTION){
            try{
                String id = idb.fetchSingle("SELECT pid FROM projekt WHERE projektnamn ='" + valtProjekt + "'"); 
                idb.update("UPDATE projekt SET projektnamn ='" + tfProjektnamn.getText() + "' WHERE pid =" + id);
                idb.update("UPDATE projekt SET beskrivning ='" + tfBeskrivning.getText() + "' WHERE pid =" + id);
                idb.update("UPDATE projekt SET startdatum ='" + tfStartdatum.getText() + "' WHERE pid =" + id);
                idb.update("UPDATE projekt SET slutdatum ='" + tfSlutdatum.getText() + "' WHERE pid =" + id);
                idb.update("UPDATE projekt SET kostnad ='" + tfKostnad.getText() + "' WHERE pid =" + id);
                idb.update("UPDATE projekt SET status ='" + cbStatus.getSelectedItem() + "' WHERE pid =" + id);
                idb.update("UPDATE projekt SET prioritet ='" + cbPrioritet.getSelectedItem() + "' WHERE pid =" + id);
                idb.update("UPDATE projekt SET projektchef ='" + cbProjektchef.getSelectedItem() + "' WHERE pid =" + id);
                idb.update("UPDATE projekt SET projektnamn ='" + tfProjektnamn.getText() + "' WHERE pid =" + id);
                String valtLand = (String) cbLand.getSelectedItem(); 
                String dbLand = idb.fetchSingle("SELECT lid FROM land WHERE namn ='" + valtLand + "'");
                idb.update("UPDATE projekt SET land ='" + dbLand + "' WHERE pid =" + id);
                new Projekt(idb).setVisible(true);
                this.dispose();
            }catch(InfException ex){
                System.out.println(ex.getMessage());
            }
        }else{
            new AndraProjekt(idb).setVisible(true);
            this.dispose();
        }
        }
    }//GEN-LAST:event_btnSparaActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new Projekt(idb).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AndraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AndraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AndraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AndraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AndraProjekt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSpara;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JComboBox<String> cbLand;
    private javax.swing.JComboBox<String> cbPrioritet;
    private javax.swing.JComboBox<String> cbProjektchef;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JComboBox<String> cbValtProjekt;
    private javax.swing.JLabel lblBeskrivning;
    private javax.swing.JLabel lblKostnad;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblPid;
    private javax.swing.JLabel lblPrioritet;
    private javax.swing.JLabel lblProjektchef;
    private javax.swing.JLabel lblProjektnamn;
    private javax.swing.JLabel lblSlutdatum;
    private javax.swing.JLabel lblStartdatum;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblVal;
    private javax.swing.JTextField tfBeskrivning;
    private javax.swing.JTextField tfKostnad;
    private javax.swing.JTextField tfPid;
    private javax.swing.JTextField tfProjektnamn;
    private javax.swing.JTextField tfSlutdatum;
    private javax.swing.JTextField tfStartdatum;
    // End of variables declaration//GEN-END:variables
}
