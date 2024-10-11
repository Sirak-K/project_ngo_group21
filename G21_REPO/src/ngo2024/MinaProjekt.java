/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;


import oru.inf.InfDB;
import oru.inf.InfException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ngo2024.HandlaggareMeny;
import ngo2024.Services.ProjectServices;

public class MinaProjekt extends javax.swing.JFrame {

    private InfDB idb;
    private String inloggadAnvandare;
    private DefaultTableModel tableModel;
    private ProjectServices projectServices;
    private String anstalldAid; 
    private Validering validering;

    
    public MinaProjekt(InfDB idb, String inloggadAnvandare, String anstalldAid) {

        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;
        this.anstalldAid = anstalldAid;
        this.projectServices = new ProjectServices(idb);
        this.validering = new Validering(idb);  
        initComponents();
        initializeTableModel();
        fetchAndPopulateProjectsTable();
        
    }
    
    
    // Metod för att initiera och sätta upp JTable med rätt modell
    private void initializeTableModel() {
        tableModel = new DefaultTableModel(
            new Object[]{"Projekt ID", "Namn", "Beskrivning", "Startdatum", "Slutdatum", "Kostnad", "Status", "Prioritet", "Projektchef", "Land"}, 0
        );
        jTableMinaProjekt.setModel(tableModel);
    }
    // Fyller tabellen med data efter initiering
    private void fetchAndPopulateProjectsTable() {
        try {
            String sqlQuery = "SELECT * FROM projekt WHERE projektchef = (SELECT aid FROM anstalld WHERE epost = '" + inloggadAnvandare + "')";
            var projectsList = idb.fetchRows(sqlQuery);
            for (var project : projectsList) {
                tableModel.addRow(new Object[]{
                    project.get("pid"), project.get("projektnamn"), project.get("beskrivning"),
                    project.get("startdatum"), project.get("slutdatum"), project.get("kostnad"),
                    project.get("status"), project.get("prioritet"), project.get("projektchef"), project.get("land")
                });
            }
        } catch (InfException ex) {
            System.out.println("Error fetching projects: " + ex.getMessage());
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

        btnSparaAndringar = new javax.swing.JButton();
        jScrollPaneMinaProjekt = new javax.swing.JScrollPane();
        jTableMinaProjekt = new javax.swing.JTable();
        btnTillbaka = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSparaAndringar.setText("Spara Ändringar");
        btnSparaAndringar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaAndringarActionPerformed(evt);
            }
        });

        jTableMinaProjekt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Projekt ID", "Projektnamn", "Projektbeskrivning", "Startdatum", "Slutdatum", "Kostnad", "Status", "Prioritet", "Projektchef", "Land"
            }
        ));
        jScrollPaneMinaProjekt.setViewportView(jTableMinaProjekt);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneMinaProjekt, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(btnSparaAndringar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnTillbaka)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnTillbaka)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPaneMinaProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSparaAndringar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Spara validerade ändringarna
    private void btnSparaAndringarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaAndringarActionPerformed
 try {
        int row = jTableMinaProjekt.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Välj en rad att uppdatera.");
            return;
        }

            // Hämta projektdetaljer för rad
            String projektID = jTableMinaProjekt.getValueAt(row, 0).toString();
            String namn = jTableMinaProjekt.getValueAt(row, 1).toString();
            String beskrivning = jTableMinaProjekt.getValueAt(row, 2).toString();
            String startdatum = jTableMinaProjekt.getValueAt(row, 3).toString();
            String slutdatum = jTableMinaProjekt.getValueAt(row, 4).toString();
            String kostnad = jTableMinaProjekt.getValueAt(row, 5).toString();
            String status = jTableMinaProjekt.getValueAt(row, 6).toString();
            String prioritet = jTableMinaProjekt.getValueAt(row, 7).toString();
            String projektchef = jTableMinaProjekt.getValueAt(row, 8).toString();
            String land = jTableMinaProjekt.getValueAt(row, 9).toString();
        
            // Validera inmatningar
            if (!validering.valideraNumerisk(projektID)) {
           JOptionPane.showMessageDialog(this, "Projekt ID måste vara ett nummer.");
           return;
            }
            
            if (!validering.valideraProjektnamn(namn)) {
                      JOptionPane.showMessageDialog(this, "Ogiltigt projektnamn. Formatet ska vara 'Projekt' följt av en siffra under 1000.");
                      return;
            }
            if (!validering.valideraBeskrivning(beskrivning)) {
                JOptionPane.showMessageDialog(this, "Beskrivningen innehåller ogiltiga tecken.");
                return;
            }
            if (!validering.valideraDatum(startdatum) || !validering.valideraDatum(slutdatum)) {
             JOptionPane.showMessageDialog(this, "Ogiltigt datumformat. Formatet ska vara YYYY-MM-DD.");
             return;
            }
            if (!validering.valideraKostnad(kostnad)) {
               JOptionPane.showMessageDialog(this, "Ogiltigt kostnadsformat. Kostnaden ska vara ett decimaltal med två decimaler.");
               return;
           }
            if (!validering.valideraStatus(status)) {
                JOptionPane.showMessageDialog(this, "Ogiltig status. Tillåtna värden är: 'Pågående', 'Planerat', 'Avslutat'.");
                return;
            }
            if (!validering.valideraPrioritet(prioritet)) {
                JOptionPane.showMessageDialog(this, "Ogiltig prioritet. Tillåtna värden är: 'Hög', 'Medel', 'Låg'.");
                return;
            }
            if (!validering.valideraNumerisk(projektchef)) {
                JOptionPane.showMessageDialog(this, "Projektchef ID måste vara ett nummer.");
                return;
            }
            if (!validering.valideraNumerisk(land)) {
                JOptionPane.showMessageDialog(this, "Land ID måste vara ett nummer.");
                return;
            }

       
            projectServices.uppdateraProjekt(projektID, namn, beskrivning, startdatum, slutdatum, kostnad, status, prioritet, projektchef, land);
            JOptionPane.showMessageDialog(this, "Ändringar sparade!");
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Fel vid uppdatering: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnSparaAndringarActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new HandlaggareMeny(idb, inloggadAnvandare, anstalldAid).setVisible(true);
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
            java.util.logging.Logger.getLogger(MinaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MinaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MinaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MinaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new MinaProjekt().setVisible(true);
            }
        });
    }


    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSparaAndringar;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JScrollPane jScrollPaneMinaProjekt;
    private javax.swing.JTable jTableMinaProjekt;
    // End of variables declaration//GEN-END:variables
}
