package ngo2024;


import java.util.ArrayList;
import java.util.HashMap;
import oru.inf.InfDB;
import oru.inf.InfException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author tom-l
 */
public class ProjektHandlaggare extends javax.swing.JFrame {

    
    private InfDB idb;
    private String inLoggadEPost;
    private String anstalldAid;
    private String avdelningsID; 
    
    /**
     * Creates new form testProjekt
     */
    public ProjektHandlaggare(InfDB idb, String inLoggadEPost, String anstalldAid) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.anstalldAid = anstalldAid; 
        this.idb = idb;
        this.inLoggadEPost = inLoggadEPost;
        setUpInfo();   
    }
    
    //Här listas samtliga projekt för handläggare
    public void hamtaAvdelningsID(){
        try{
        String hamtatAvdelningsID = idb.fetchSingle("SELECT avdelning FROM anstalld WHERE aid='" + anstalldAid + "'");
        avdelningsID = hamtatAvdelningsID; 
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
    }
    
            private void setUpInfo () {
            String SQLFraga = "select pid, projektnamn, beskrivning, startdatum, slutdatum, kostnad, status, prioritet, projektchef, land "
                            + "from ngo_2024.projekt inner join anstalld "
                            + "on projekt.projektchef = anstalld.aid "
                            + "order by status";
        
        try {
        ArrayList<HashMap<String, String>> lista = idb.fetchRows(SQLFraga);
        
        for (int i = 0; i < lista.size(); i++) {
            taVisningsRuta.append("ProjektID: " + lista.get(i).get("pid") + "\n" + "Projektnamn: " + lista.get(i).get("projektnamn") + "\n" + "Beskrivning: "
                        + lista.get(i).get("beskrivning") + "\n" + "Startdatum: " + lista.get(i).get("startdatum") + "\n" + "Slutdatum: "
                        + lista.get(i).get("slutdatum") + "\n" + "Kostnad: " + lista.get(i).get("kostnad") + "\n" + "Status: "
                        + lista.get(i).get("status") + "\n" + "Prioritet: " + lista.get(i).get("prioritet") + "\n" + "Projektchef: "
                        + lista.get(i).get("projektchef") + "\n" + "Land: " + lista.get(i).get("land") 
                        + "\n" + "---------------------------------------- \n");


}

    } catch (InfException ex) {
                ex.getMessage();
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

        lblProjekt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taVisningsRuta = new javax.swing.JTextArea();
        btnFiltreraProjekt = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblProjekt.setText("Projekt");

        taVisningsRuta.setColumns(20);
        taVisningsRuta.setRows(5);
        jScrollPane1.setViewportView(taVisningsRuta);

        btnFiltreraProjekt.setText("Filtrera Projekt");
        btnFiltreraProjekt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltreraProjektActionPerformed(evt);
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFiltreraProjekt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTillbaka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(504, 504, 504)
                        .addComponent(lblProjekt)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjekt)
                    .addComponent(btnTillbaka))
                .addGap(8, 8, 8)
                .addComponent(btnFiltreraProjekt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        
        this.setVisible(false);
        new HandlaggareMeny(idb, inLoggadEPost, anstalldAid).setVisible(true);
        
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void btnFiltreraProjektActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltreraProjektActionPerformed
        //// Här finns möjligheten att filtrera projekt som gäller för den avdelning den anställda är tilldelad
            String SQLFraga = "select pid, projektnamn, beskrivning, startdatum, slutdatum, kostnad, status, prioritet, projektchef, land "
            + "from ngo_2024.projekt inner join ngo_2024.anstalld "
            + "on projekt.projektchef = + aid "
            + "where avdelning = " + anstalldAid 
            + " order by status";

        try {
        ArrayList<HashMap<String, String>> lista = idb.fetchRows(SQLFraga);
                    taVisningsRuta.setText("");

        for (int i = 0; i < lista.size(); i++) {
            taVisningsRuta.append("ProjektID: " + lista.get(i).get("pid") + "\n" + "Projektnamn: " + lista.get(i).get("projektnamn") + "\n" + "Beskrivning: "
                        + lista.get(i).get("beskrivning") + "\n" + "Startdatum: " + lista.get(i).get("startdatum") + "\n" + "Slutdatum: "
                        + lista.get(i).get("slutdatum") + "\n" + "Kostnad: " + lista.get(i).get("kostnad") + "\n" + "Status: "
                        + lista.get(i).get("status") + "\n" + "Prioritet: " + lista.get(i).get("prioritet") + "\n" + "Projektchef: "
                        + lista.get(i).get("projektchef") + "\n" + "Land: " + lista.get(i).get("land") 
                        + "\n" + "---------------------------------------- \n");    

    }
        } catch (InfException ex) {
                ex.getMessage();
}


            
    
    }//GEN-LAST:event_btnFiltreraProjektActionPerformed

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
            java.util.logging.Logger.getLogger(ProjektHandlaggare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjektHandlaggare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjektHandlaggare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjektHandlaggare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
    //            new ProjektHandlaggare().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltreraProjekt;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblProjekt;
    private javax.swing.JTextArea taVisningsRuta;
    // End of variables declaration//GEN-END:variables

}
