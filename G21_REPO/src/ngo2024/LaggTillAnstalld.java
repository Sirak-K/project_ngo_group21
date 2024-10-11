/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;
import java.util.ArrayList;
import java.util.Random;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author anton
 */
public class LaggTillAnstalld extends javax.swing.JFrame {

    private InfDB idb;
    private Validering val; 
    /**
     * Creates new form LaggTillAnstalld
     */
    public LaggTillAnstalld(InfDB idb) {
        this.idb = idb;
        this.val = new Validering(idb); 
        initComponents();
        populeraCbTypAnstalld();
        adminInfo();
        populeraAvdelningNamn();
        tfAid.setEditable(false);
        getAnsID();
        populeraAnstallda();
        lblFelEpost.setVisible(false);
        lblFelDatum.setVisible(false);
        lblFelTele.setVisible(false);
    }

    //Metod för att populera en combobox
    public void populeraCbTypAnstalld(){
        cbTypAnstalld.addItem("Administratör");
        cbTypAnstalld.addItem("Handläggare");
    }
    
    //Metod för att gömma information
    public void adminInfo(){
        tfAid.setVisible(false);
        tfAdress.setVisible(false);
        tfAnsDatum.setVisible(false);
        tfEnamn.setVisible(false);
        tfEpost.setVisible(false);
        tfFnamn.setVisible(false);
        tfTele.setVisible(false);
        cbAvd.setVisible(false);
        lblAdress.setVisible(false);
        lblAid.setVisible(false);
        lblAnsDatum.setVisible(false);
        lblAvd.setVisible(false);
        lblEnamn.setVisible(false);
        lblEpost.setVisible(false);
        lblFnamn.setVisible(false);
        lblTele.setVisible(false);
        lblAnsOmrade.setVisible(false);
        lblMentor.setVisible(false);
        tfAnsOmrade.setVisible(false);
        cbAnstallda.setVisible(false);
    }
    
    //Metod för att visa viss information
    public void visaAdminInfo(){
        tfAid.setVisible(true);
        tfAdress.setVisible(true);
        tfAnsDatum.setVisible(true);
        tfEnamn.setVisible(true);
        tfEpost.setVisible(true);
        tfFnamn.setVisible(true);
        tfTele.setVisible(true);
        cbAvd.setVisible(true);
        lblAdress.setVisible(true);
        lblAid.setVisible(true);
        lblAnsDatum.setVisible(true);
        lblAvd.setVisible(true);
        lblEnamn.setVisible(true);
        lblEpost.setVisible(true);
        lblFnamn.setVisible(true);
        lblTele.setVisible(true);
        lblAnsOmrade.setVisible(false);
        lblMentor.setVisible(false);
        tfAnsOmrade.setVisible(false);
        cbAnstallda.setVisible(false);
    }
    
    //Metod för att visa alla information 
     public void visaHandlaggarInfo(){
        tfAid.setVisible(true);
        tfAdress.setVisible(true);
        tfAnsDatum.setVisible(true);
        tfEnamn.setVisible(true);
        tfEpost.setVisible(true);
        tfFnamn.setVisible(true);
        tfTele.setVisible(true);
        cbAvd.setVisible(true);
        lblAdress.setVisible(true);
        lblAid.setVisible(true);
        lblAnsDatum.setVisible(true);
        lblAvd.setVisible(true);
        lblEnamn.setVisible(true);
        lblEpost.setVisible(true);
        lblFnamn.setVisible(true);
        lblTele.setVisible(true);
        lblAnsOmrade.setVisible(true);
        lblMentor.setVisible(true);
        tfAnsOmrade.setVisible(true);
        cbAnstallda.setVisible(true);
    }
    
     
    //Skapar en ArrayList för att hålla alla namn på avdelningar i databasen 
    public ArrayList hamtaAvdelningNamn(){
        ArrayList<String>avdelningNamnLista = new ArrayList<>(); 
        try{
            avdelningNamnLista = idb.fetchColumn("SELECT namn FROM avdelning"); 
            
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return avdelningNamnLista; 
    }
    
    //Populerar en combobox med alla namn på avdelningar som finns i databasen
    public void populeraAvdelningNamn(){
        ArrayList<String>avdelningNamnLista = hamtaAvdelningNamn(); 
        for(String avdelningNamn : avdelningNamnLista){
            cbAvd.addItem(avdelningNamn);
        }
    }
    
    //Skapar en ArrayList för att hålla alla anställningsID som finns i databasen
    public ArrayList hamtaAnstallda(){
        ArrayList<String>anstalldLista = new ArrayList<>(); 
        try{
            anstalldLista = idb.fetchColumn("SELECT aid FROM anstalld"); 
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return anstalldLista; 
    }
    
    //Metod för att populera en combobox med alla anställningsID som finns i databasen
    public void populeraAnstallda(){
        cbAnstallda.insertItemAt("---", 0);
        ArrayList<String>anstalldLista = hamtaAnstallda();
        for(String anstalld : anstalldLista){
            cbAnstallda.addItem(anstalld); 
        }   
    }
    
    //Metod för att hämta nästa anställningsID
    public void getAnsID(){
            try{
            String nextID = idb.getAutoIncrement("anstalld", "aid");
            tfAid.setText(nextID);
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    //Metod för att slumpa fram ett nytt lösenord 
    public String nyttLosenord(){
        String karaktarer = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; 
        Random random = new Random();
        StringBuilder losenord = new StringBuilder();
        for(int i = 0; i < 7; i++){
            int index = random.nextInt(karaktarer.length());
            losenord.append(karaktarer.charAt(index));
        }
        return losenord.toString(); 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTypAnstalld = new javax.swing.JLabel();
        cbTypAnstalld = new javax.swing.JComboBox<>();
        lblAid = new javax.swing.JLabel();
        lblFnamn = new javax.swing.JLabel();
        lblEnamn = new javax.swing.JLabel();
        lblAdress = new javax.swing.JLabel();
        lblEpost = new javax.swing.JLabel();
        lblTele = new javax.swing.JLabel();
        lblAnsDatum = new javax.swing.JLabel();
        lblAvd = new javax.swing.JLabel();
        tfAid = new javax.swing.JTextField();
        tfFnamn = new javax.swing.JTextField();
        tfEnamn = new javax.swing.JTextField();
        tfAdress = new javax.swing.JTextField();
        tfEpost = new javax.swing.JTextField();
        tfTele = new javax.swing.JTextField();
        tfAnsDatum = new javax.swing.JTextField();
        cbAvd = new javax.swing.JComboBox<>();
        lblAnsOmrade = new javax.swing.JLabel();
        tfAnsOmrade = new javax.swing.JTextField();
        lblMentor = new javax.swing.JLabel();
        btnLaggtill = new javax.swing.JButton();
        cbAnstallda = new javax.swing.JComboBox<>();
        btnTillbaka = new javax.swing.JButton();
        lblFelEpost = new javax.swing.JLabel();
        lblFelDatum = new javax.swing.JLabel();
        lblFelTele = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTypAnstalld.setText("Typ av anställd");

        cbTypAnstalld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTypAnstalldActionPerformed(evt);
            }
        });

        lblAid.setText("aid");

        lblFnamn.setText("Förnamn");

        lblEnamn.setText("Efternamn");

        lblAdress.setText("Adress");

        lblEpost.setText("Epost");

        lblTele.setText("Telefon");

        lblAnsDatum.setText("Anställningsdatum");

        lblAvd.setText("Avdelning");

        lblAnsOmrade.setText("Ansvarsområde");

        lblMentor.setText("Mentor");

        btnLaggtill.setText("Lägg till");
        btnLaggtill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggtillActionPerformed(evt);
            }
        });

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        lblFelEpost.setForeground(new java.awt.Color(255, 0, 0));
        lblFelEpost.setText("felaktigt inskriven epost, skriv in i formatet \"anton.k@example.com\"");

        lblFelDatum.setForeground(new java.awt.Color(255, 51, 51));
        lblFelDatum.setText("felaktigt inskrivet datum, skriv in i formatet \"YYYY-MM-DD\"");

        lblFelTele.setForeground(new java.awt.Color(255, 0, 51));
        lblFelTele.setText("felaktigt inskrivet telefonnummer, skriv in i formatet \"XXX-XXX-XXXX\"");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTillbaka)
                                .addGap(24, 24, 24)
                                .addComponent(lblTypAnstalld))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAvd)
                                    .addComponent(lblAid)
                                    .addComponent(lblFnamn)
                                    .addComponent(lblEnamn)
                                    .addComponent(lblAdress)
                                    .addComponent(lblEpost)
                                    .addComponent(lblTele)
                                    .addComponent(lblAnsDatum)
                                    .addComponent(lblAnsOmrade)
                                    .addComponent(lblMentor))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(94, 94, 94)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfAnsDatum, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfTele, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfEpost, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfEnamn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfFnamn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfAid, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(93, 93, 93)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(tfAnsOmrade, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbTypAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbAnstallda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFelEpost)
                                    .addComponent(lblFelDatum)
                                    .addComponent(lblFelTele)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(cbAvd, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(btnLaggtill)))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTypAnstalld)
                            .addComponent(cbTypAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnTillbaka))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAid)
                    .addComponent(tfAid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFnamn)
                    .addComponent(tfFnamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEnamn)
                    .addComponent(tfEnamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAdress)
                    .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEpost)
                    .addComponent(tfEpost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelEpost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTele)
                    .addComponent(tfTele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelTele))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAnsDatum)
                    .addComponent(tfAnsDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelDatum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAvd)
                    .addComponent(cbAvd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAnsOmrade)
                    .addComponent(tfAnsOmrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMentor)
                    .addComponent(cbAnstallda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnLaggtill)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbTypAnstalldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTypAnstalldActionPerformed

        int valt = cbTypAnstalld.getSelectedIndex(); 
        if(valt == 0){
           visaAdminInfo();  
        }else{
            visaHandlaggarInfo(); 
        }
    }//GEN-LAST:event_cbTypAnstalldActionPerformed

    private void btnLaggtillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggtillActionPerformed
        int valt = cbTypAnstalld.getSelectedIndex();
        int dbID = Integer.parseInt(tfAid.getText()); 
        String dbFnamn = tfFnamn.getText(); 
        String dbEnamn = tfEnamn.getText(); 
        String dbAdress = tfAdress.getText();
        String dbEpost = tfEpost.getText(); 
        String dbTele = tfTele.getText(); 
        String dbAnsDatum = tfAnsDatum.getText();
        String nyttLosenord = nyttLosenord();
        String ansvarsOmrade = tfAnsOmrade.getText(); 
        int behorighetsNiva = 1;
        if(val.valideringDatum(dbAnsDatum) == false){
            lblFelDatum.setVisible(true);
            this.setVisible(true);
        }else if(val.hamtaEpost(dbEpost) == false){
            lblFelEpost.setVisible(true);
            this.setVisible(true);
        }else if(val.valideraAmerikansktTelefonnummer(dbTele) == false){
            lblFelTele.setVisible(true);
            this.setVisible(true);
        }else if(valt == 0 && val.valideringDatum(dbAnsDatum) == true && val.hamtaEpost(dbEpost) == true && val.valideraAmerikansktTelefonnummer(dbTele) == true){
            try{
                String valdAvd = (String) cbAvd.getSelectedItem();
                String sqlValAvd = "SELECT avdid FROM avdelning WHERE namn ='" + valdAvd +"'"; 
                String dbValAvd = idb.fetchSingle(sqlValAvd);
                idb.insert("INSERT INTO anstalld VALUES("+ dbID +", '" + dbFnamn + "','" + dbEnamn + "', '" + dbAdress + "','" + dbEpost + "','" + dbTele + "','" + dbAnsDatum + "','" + nyttLosenord + "','" + dbValAvd + "')");
                idb.insert("INSERT INTO admin VALUES("+ dbID +","+ behorighetsNiva +")");
            }catch(InfException ex){
                System.out.println(ex.getMessage());
            }
            new AdminMeny(idb).setVisible(true);
            this.dispose();
        }else if (valt == 1 && val.valideringDatum(dbAnsDatum) == true && val.hamtaEpost(dbEpost) == true && val.valideraAmerikansktTelefonnummer(dbTele) == true){
            try{
                String valdAvd = (String) cbAvd.getSelectedItem();
                String sqlValAvd = "SELECT avdid FROM avdelning WHERE namn ='" + valdAvd +"'"; 
                String dbValAvd = idb.fetchSingle(sqlValAvd);
                String valdAnstalld = (String) cbAnstallda.getSelectedItem(); 
                idb.insert("INSERT INTO anstalld VALUES("+ dbID +", '" + dbFnamn + "','" + dbEnamn + "', '" + dbAdress + "','" + dbEpost + "','" + dbTele + "','" + dbAnsDatum + "','" + nyttLosenord + "','" + dbValAvd + "')");
                if(valdAnstalld != "---"){
                idb.insert("INSERT INTO handlaggare VALUES("+ dbID +", '" + ansvarsOmrade + "','" + valdAnstalld + "')");
                }else{
                   idb.insert("INSERT INTO handlaggare VALUES("+ dbID +", '" + ansvarsOmrade + "'," + null + ")"); 
                }
            }catch(InfException ex){
                System.out.println(ex.getMessage());
            }
            new AdminMeny(idb).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnLaggtillActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new AdminMeny(idb).setVisible(true);
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
            java.util.logging.Logger.getLogger(LaggTillAnstalld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaggTillAnstalld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaggTillAnstalld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaggTillAnstalld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new LaggTillAnstalld().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLaggtill;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JComboBox<String> cbAnstallda;
    private javax.swing.JComboBox<String> cbAvd;
    private javax.swing.JComboBox<String> cbTypAnstalld;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblAid;
    private javax.swing.JLabel lblAnsDatum;
    private javax.swing.JLabel lblAnsOmrade;
    private javax.swing.JLabel lblAvd;
    private javax.swing.JLabel lblEnamn;
    private javax.swing.JLabel lblEpost;
    private javax.swing.JLabel lblFelDatum;
    private javax.swing.JLabel lblFelEpost;
    private javax.swing.JLabel lblFelTele;
    private javax.swing.JLabel lblFnamn;
    private javax.swing.JLabel lblMentor;
    private javax.swing.JLabel lblTele;
    private javax.swing.JLabel lblTypAnstalld;
    private javax.swing.JTextField tfAdress;
    private javax.swing.JTextField tfAid;
    private javax.swing.JTextField tfAnsDatum;
    private javax.swing.JTextField tfAnsOmrade;
    private javax.swing.JTextField tfEnamn;
    private javax.swing.JTextField tfEpost;
    private javax.swing.JTextField tfFnamn;
    private javax.swing.JTextField tfTele;
    // End of variables declaration//GEN-END:variables
}
