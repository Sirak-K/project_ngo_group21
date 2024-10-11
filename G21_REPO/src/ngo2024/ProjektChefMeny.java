package ngo2024;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import oru.inf.InfDB;
import oru.inf.InfException;

public class ProjektChefMeny extends javax.swing.JFrame implements ListSelectionListener, ItemListener {
    private InfDB databas;  // Databasanslutning
    private String anstalldAid;  // Anställd ID
    private String inloggadAnvandare; 
    private String projektID;  // Projekt ID
    private String projektPrioritet;  // Projektprioritet
    private String personalAttLaggaTillId;  // Personal ID för att lägga till
    private String personalAttTaBortId;  // Personal ID för att ta bort
    private String partnerAttLaggaTillId;  // Partner ID för att lägga till
    private String partnerAttTaBortId;  // Partner ID för att ta bort
    private Boolean arHandlaggareVald;  // Flagga för att kolla om handläggare är vald
    
    // Konstruktor
    public ProjektChefMeny(InfDB databas, String inloggadAnvandare, String anstalldAid) {
        this.databas = databas;
        this.anstalldAid = anstalldAid;
        this.inloggadAnvandare = inloggadAnvandare; 
        this.personalAttLaggaTillId = null;
        this.personalAttTaBortId = null;
        this.partnerAttLaggaTillId = null;
        this.partnerAttTaBortId = null;
        this.arHandlaggareVald = false;
        initComponents();
        populeraComboBox();
        fyllTextFaltMedProjektData();
        cbValtProjekt.addItemListener(this);
        tblVisaHandlaggare.getSelectionModel().addListSelectionListener(this); 
        tblHandlaggare.getSelectionModel().addListSelectionListener(this); 
        tblVisaPartners.getSelectionModel().addListSelectionListener(this); 
        tblVisaProjektPartnere.getSelectionModel().addListSelectionListener(this);
        listaHandlaggare();
        listaProjektPersonal();
        listaAllaPartners();
        listaProjektPartners();
        tfProjektNamn.setEditable(false);
        txtBeskrivning.setEditable(false); 
        txtDatumStart.setEditable(false);
        txtDatumSlut.setEditable(false);
        txtVisaStatus.setEditable(false);
        txtVisaKostnad.setEditable(false);
        this.setLocationRelativeTo(null);
    }
    
    //Skapar en ArrayList för att hämta alla projekt för en viss anställd
    private ArrayList hamtaProjektID(){
        ArrayList<String>projektLista = new ArrayList<>(); 
        try{
        projektLista = databas.fetchColumn("SELECT pid FROM projekt WHERE projektchef =(SELECT aid FROM anstalld WHERE aid ='" + anstalldAid + "')"); 
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return projektLista; 
    }
    
    private void populeraComboBox(){
        ArrayList<String>projektLista = hamtaProjektID(); 
        for(String projekt : projektLista){
            cbValtProjekt.addItem(projekt);
        }
    }
    
    
    
    // Fyller textfälten med data för valt projekt
    private void fyllTextFaltMedProjektData(){
        try {
            String id = databas.fetchSingle("SELECT pid from projekt where pid = '" + projektID + "'"); 
            String projektNamnFraga = "SELECT projektnamn FROM projekt WHERE pid = '" + id + "'";
            tfProjektNamn.setText(databas.fetchSingle(projektNamnFraga));
            String beskrivningFraga = "SELECT beskrivning FROM projekt WHERE pid = '" + id + "'";
            txtBeskrivning.setText(databas.fetchSingle(beskrivningFraga));
            String startdatumFraga = "SELECT startdatum FROM projekt WHERE pid = '" + id + "'";
            txtDatumStart.setText(databas.fetchSingle(startdatumFraga));
            String slutdatumFraga = "SELECT slutdatum FROM projekt WHERE pid = '" + id + "'";
            txtDatumSlut.setText(databas.fetchSingle(slutdatumFraga));
            String kostnadFraga = "SELECT kostnad FROM projekt WHERE pid = '" + id + "'";
            txtVisaKostnad.setText(databas.fetchSingle(kostnadFraga));
            String statusFraga = "SELECT status FROM projekt WHERE pid = '" + id + "'";
            txtVisaStatus.setText(databas.fetchSingle(statusFraga));
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    
    // Lista alla handläggare
    private void listaHandlaggare(){
        DefaultTableModel modell = (DefaultTableModel) tblVisaHandlaggare.getModel();
        modell.setRowCount(0);
        
        String fraga = "SELECT fornamn, efternamn, aid FROM anstalld WHERE aid IN (SELECT aid FROM handlaggare) ORDER BY aid";
        ArrayList<HashMap<String, String>> allPersonal;
        
        try{
            allPersonal = databas.fetchRows(fraga);
            
            for (HashMap<String, String> enAnstalld : allPersonal) {
                String fornamn = enAnstalld.get("fornamn");
                String efternamn = enAnstalld.get("efternamn");
                String aid = enAnstalld.get("aid");
                
                modell.addRow(new Object[]{fornamn, efternamn, aid});
            }
        }
        catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Lista all personal för projektet
    private void listaProjektPersonal(){
        DefaultTableModel modell = (DefaultTableModel) tblHandlaggare.getModel();
        modell.setRowCount(0);
        
        String fraga = "SELECT fornamn, efternamn, aid FROM anstalld WHERE aid IN (SELECT aid FROM ans_proj WHERE pid = '" + projektID + "')";
        ArrayList<HashMap<String, String>> allPersonal;
        
        try{
            allPersonal = databas.fetchRows(fraga);
            
            for (HashMap<String, String> enAnstalld : allPersonal) {
                String fornamn = enAnstalld.get("fornamn");
                String efternamn = enAnstalld.get("efternamn");
                String aid = enAnstalld.get("aid");
                
                modell.addRow(new Object[]{fornamn, efternamn, aid});
            }
        }
        catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Lägg till personal till projektet
    private void laggTillPersonal() {
        String kollaID = "SELECT aid FROM anstalld WHERE aid IN (SELECT aid FROM ans_proj WHERE pid = '" + projektID + "')";
        String personalFraga = "INSERT INTO ans_proj VALUES ('" + projektID + "', '" + personalAttLaggaTillId + "')";
        ArrayList<HashMap<String, String>> projektPersonalID;
        
        try {
            projektPersonalID = databas.fetchRows(kollaID);
            Boolean idFinnsRedan = false;
            
            for (HashMap<String, String> enAnstalld : projektPersonalID){
                String aid = enAnstalld.get("aid");
                
                if (!aid.equals(personalAttLaggaTillId)){
                    idFinnsRedan = false;
                } else {
                    idFinnsRedan = true;
                }
            }
            if (!idFinnsRedan){
                databas.insert(personalFraga);
                listaProjektPersonal();
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Ta bort personal från projektet
    private void taBortPersonal(){
        String personalFraga = "DELETE FROM ans_proj WHERE pid = '" + projektID + "' AND aid = '" + personalAttTaBortId + "'";
        try {
            databas.delete(personalFraga);
            listaProjektPersonal();
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Lista alla partners
    private void listaAllaPartners() {
        String fraga = "SELECT pid, partner.namn AS namn, pid FROM partner ORDER BY pid";
        ArrayList<HashMap<String, String>> allaPartners;
   
        try{
            allaPartners = databas.fetchRows(fraga);
            
            for (HashMap<String, String> enPartner : allaPartners) {
                String namn = enPartner.get("namn");
                String partnerPid = enPartner.get("pid");
               
                DefaultTableModel modell = (DefaultTableModel) tblVisaPartners.getModel();
                modell.addRow(new Object[]{namn, partnerPid});
            }
        }
        catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Lista alla partners för projektet
    private void listaProjektPartners() {
        String fraga = "SELECT pid, partner.namn AS namn, pid FROM partner WHERE pid IN (SELECT partner_pid FROM projekt_partner WHERE pid = '" + projektID + "')";
        ArrayList<HashMap<String, String>> allaPartners;
                        
        DefaultTableModel modell = (DefaultTableModel) tblVisaProjektPartnere.getModel();
        modell.setRowCount(0);
        
        try{
            allaPartners = databas.fetchRows(fraga);
            
            for (HashMap<String, String> enPartner : allaPartners) {
                String namn = enPartner.get("namn");
                String partnerPid = enPartner.get("pid");
               
                modell.addRow(new Object[]{namn, partnerPid});
            }
        }
        catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Lägg till partner till projektet
    private void laggTillPartner(){
        String kollaID = "SELECT pid FROM partner WHERE pid IN (SELECT partner_pid FROM projekt_partner WHERE pid = '" + projektID + "')";
        String partnerFraga = "INSERT INTO projekt_partner VALUES ('" + projektID + "', '" + partnerAttLaggaTillId + "')";
        ArrayList<HashMap<String, String>> projektPartnerID;
        
        try {
            projektPartnerID = databas.fetchRows(kollaID);
            Boolean idFinnsRedan = false;
            
            for (HashMap<String, String> enAnstalld : projektPartnerID){
                String partnerID = enAnstalld.get("pid");
                
                if (!partnerID.equals(partnerAttLaggaTillId)){
                    idFinnsRedan = false;
                } else {
                    idFinnsRedan = true;
                }
            }
            if (!idFinnsRedan){
                databas.insert(partnerFraga);
                listaProjektPartners();
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Ta bort partner från projektet
    private void taBortPartner(){
        String partnerFraga = "DELETE FROM projekt_partner WHERE pid = '" + projektID + "' AND partner_pid = '" + partnerAttTaBortId + "'";
        try {
            databas.delete(partnerFraga);
            listaProjektPartners();
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            Object item = event.getItem();
            projektPrioritet = (String) item;
            String valdProjektNamn = (String) cbValtProjekt.getSelectedItem();
            try {
                String fraga = "SELECT pid FROM projekt WHERE projektnamn = '" + valdProjektNamn + "' AND projektchef = '" + anstalldAid + "'";
                projektID = databas.fetchSingle(fraga);
                fyllTextFaltMedProjektData();
                listaProjektPersonal();
                listaProjektPartners();
                listaHandlaggare();
                listaAllaPartners();
            } catch (InfException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (tblVisaHandlaggare.getSelectedRow() >= 0) {
            int selectedRow = tblVisaHandlaggare.getSelectedRow();
            String fornamn = (String) tblVisaHandlaggare.getValueAt(selectedRow, 0);
            String efternamn = (String) tblVisaHandlaggare.getValueAt(selectedRow, 1);
            String aid = (String) tblVisaHandlaggare.getValueAt(selectedRow, 2);
            
            personalAttLaggaTillId = aid;
            System.out.println(personalAttLaggaTillId);
        }
        
        if (tblHandlaggare.getSelectedRow() >= 0) {
            int selectedRow = tblHandlaggare.getSelectedRow();
            String fornamn = (String) tblHandlaggare.getValueAt(selectedRow, 0);
            String efternamn = (String) tblHandlaggare.getValueAt(selectedRow, 1);
            String aid = (String) tblHandlaggare.getValueAt(selectedRow, 2);
            
            personalAttTaBortId = aid;
            System.out.println(personalAttTaBortId);
        }
        
        if (tblVisaPartners.getSelectedRow() >= 0) {
            int selectedRow = tblVisaPartners.getSelectedRow();
            String namn = (String) tblVisaPartners.getValueAt(selectedRow, 0);
            String partnerID = (String) tblVisaPartners.getValueAt(selectedRow, 1);
            
            partnerAttLaggaTillId = partnerID;
            System.out.println(partnerAttLaggaTillId);
        }
        
        if (tblVisaProjektPartnere.getSelectedRow() >= 0) {
            int selectedRow = tblVisaProjektPartnere.getSelectedRow();
            String namn = (String) tblVisaProjektPartnere.getValueAt(selectedRow, 0);
            String partnerID = (String) tblVisaProjektPartnere.getValueAt(selectedRow, 1);
            
            partnerAttTaBortId = partnerID;
            System.out.println(partnerAttTaBortId);
        }
    }
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLaggTillPersonal = new javax.swing.JButton();
        tfProjektNamn = new javax.swing.JTextField();
        txtBeskrivning = new javax.swing.JTextField();
        txtDatumStart = new javax.swing.JTextField();
        txtDatumSlut = new javax.swing.JTextField();
        txtVisaKostnad = new javax.swing.JTextField();
        txtVisaStatus = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVisaHandlaggare = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHandlaggare = new javax.swing.JTable();
        btnStang = new javax.swing.JButton();
        btnTaBortHandlaggare = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblVisaProjektPartnere = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblVisaPartners = new javax.swing.JTable();
        btnLaggTillPartner = new javax.swing.JButton();
        lblPersonalPaProjektet = new javax.swing.JLabel();
        lblLaggTill = new javax.swing.JLabel();
        lblValdaPartners = new javax.swing.JLabel();
        lblTillganligaPartners = new javax.swing.JLabel();
        lblVisaPrio = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblPersonalPaProjektet1 = new javax.swing.JLabel();
        lblPersonalPaProjektet2 = new javax.swing.JLabel();
        lblPersonalPaProjektet3 = new javax.swing.JLabel();
        lblPersonalPaProjektet4 = new javax.swing.JLabel();
        lblPersonalPaProjektet5 = new javax.swing.JLabel();
        lblPersonalPaProjektet6 = new javax.swing.JLabel();
        cbValtProjekt = new javax.swing.JComboBox<>();
        btnStatistik = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnLaggTillPersonal.setText("Lägg till");
        btnLaggTillPersonal.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnLaggTillPersonal.setBorderPainted(false);
        btnLaggTillPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillPersonalActionPerformed(evt);
            }
        });

        tfProjektNamn.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        tfProjektNamn.setForeground(new java.awt.Color(102, 102, 102));
        tfProjektNamn.setBorder(null);
        tfProjektNamn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfProjektNamnActionPerformed(evt);
            }
        });

        txtBeskrivning.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        txtBeskrivning.setForeground(new java.awt.Color(102, 102, 102));
        txtBeskrivning.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        txtDatumStart.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        txtDatumStart.setForeground(new java.awt.Color(102, 102, 102));
        txtDatumStart.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        txtDatumStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDatumStartActionPerformed(evt);
            }
        });

        txtDatumSlut.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        txtDatumSlut.setForeground(new java.awt.Color(102, 102, 102));
        txtDatumSlut.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        txtDatumSlut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDatumSlutActionPerformed(evt);
            }
        });

        txtVisaKostnad.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        txtVisaKostnad.setForeground(new java.awt.Color(102, 102, 102));
        txtVisaKostnad.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        txtVisaKostnad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVisaKostnadActionPerformed(evt);
            }
        });

        txtVisaStatus.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        txtVisaStatus.setForeground(new java.awt.Color(102, 102, 102));
        txtVisaStatus.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        tblVisaHandlaggare.setForeground(new java.awt.Color(102, 102, 102));
        tblVisaHandlaggare.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Förnamn", "Efternamn", "ID"
            }
        ));
        tblVisaHandlaggare.setSelectionForeground(new java.awt.Color(102, 102, 102));
        jScrollPane1.setViewportView(tblVisaHandlaggare);

        tblHandlaggare.setForeground(new java.awt.Color(102, 102, 102));
        tblHandlaggare.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Förnamn", "Efternamn", "ID"
            }
        ));
        tblHandlaggare.setSelectionForeground(new java.awt.Color(102, 102, 102));
        jScrollPane2.setViewportView(tblHandlaggare);

        btnStang.setText("Spara");
        btnStang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnStang.setBorderPainted(false);
        btnStang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStangActionPerformed(evt);
            }
        });

        btnTaBortHandlaggare.setText("Ta bort");
        btnTaBortHandlaggare.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnTaBortHandlaggare.setBorderPainted(false);
        btnTaBortHandlaggare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortHandlaggareActionPerformed(evt);
            }
        });

        tblVisaProjektPartnere.setForeground(new java.awt.Color(102, 102, 102));
        tblVisaProjektPartnere.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Organisation", "ID"
            }
        ));
        tblVisaProjektPartnere.setSelectionForeground(new java.awt.Color(102, 102, 102));
        jScrollPane3.setViewportView(tblVisaProjektPartnere);

        tblVisaPartners.setForeground(new java.awt.Color(102, 102, 102));
        tblVisaPartners.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Organisation", "ID"
            }
        ));
        tblVisaPartners.setSelectionForeground(new java.awt.Color(102, 102, 102));
        jScrollPane4.setViewportView(tblVisaPartners);

        btnLaggTillPartner.setText("Lägg till");
        btnLaggTillPartner.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnLaggTillPartner.setBorderPainted(false);
        btnLaggTillPartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillPartnerActionPerformed(evt);
            }
        });

        lblPersonalPaProjektet.setText("Beskrivning");

        lblLaggTill.setText("Lägg till personal");

        lblValdaPartners.setText("Partners på projektet");

        lblTillganligaPartners.setText("Tillgängliga partners");

        lblVisaPrio.setText("Välj projekt");

        jButton1.setText("Ta bort partner");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblPersonalPaProjektet1.setText("Namn:");

        lblPersonalPaProjektet2.setText("Status");

        lblPersonalPaProjektet3.setText("Personal på projektet");

        lblPersonalPaProjektet4.setText("Slutdatum");

        lblPersonalPaProjektet5.setText("Startdatum");

        lblPersonalPaProjektet6.setText("Kostnad");

        cbValtProjekt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbValtProjektActionPerformed(evt);
            }
        });

        btnStatistik.setText("Visa min statistik");
        btnStatistik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatistikActionPerformed(evt);
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
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTaBortHandlaggare, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLaggTillPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLaggTill, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btnStang, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(btnLaggTillPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(236, 236, 236))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(170, 170, 170)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTillganligaPartners, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblValdaPartners, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPersonalPaProjektet3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnTillbaka)
                .addGap(165, 165, 165)
                .addComponent(lblVisaPrio, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbValtProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblPersonalPaProjektet1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfProjektNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblPersonalPaProjektet, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblPersonalPaProjektet5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDatumStart, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPersonalPaProjektet2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPersonalPaProjektet6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPersonalPaProjektet4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(57, 57, 57)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVisaStatus)
                            .addComponent(txtDatumSlut)
                            .addComponent(txtVisaKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnStatistik)
                .addGap(109, 109, 109))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblVisaPrio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbValtProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnTillbaka))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPersonalPaProjektet1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfProjektNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPersonalPaProjektet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnStatistik)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPersonalPaProjektet5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDatumStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDatumSlut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPersonalPaProjektet4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVisaStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPersonalPaProjektet2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPersonalPaProjektet6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVisaKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPersonalPaProjektet3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValdaPartners))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTaBortHandlaggare, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLaggTill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTillganligaPartners)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLaggTillPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLaggTillPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnStang)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        tfProjektNamn.getAccessibleContext().setAccessibleName("");
        txtVisaKostnad.getAccessibleContext().setAccessibleName("kostnad");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDatumStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDatumStartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDatumStartActionPerformed

    private void btnLaggTillPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillPersonalActionPerformed
        laggTillPersonal();
           
    }//GEN-LAST:event_btnLaggTillPersonalActionPerformed

    private void btnLaggTillPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillPartnerActionPerformed
        laggTillPartner();
    }//GEN-LAST:event_btnLaggTillPartnerActionPerformed

    private void btnStangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStangActionPerformed
        new HandlaggareMeny(databas, inloggadAnvandare, anstalldAid).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnStangActionPerformed

    private void btnTaBortHandlaggareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortHandlaggareActionPerformed
        taBortPersonal();
    }//GEN-LAST:event_btnTaBortHandlaggareActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
taBortPartner();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tfProjektNamnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfProjektNamnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfProjektNamnActionPerformed

    private void txtDatumSlutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDatumSlutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDatumSlutActionPerformed

    private void txtVisaKostnadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVisaKostnadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVisaKostnadActionPerformed

    private void cbValtProjektActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbValtProjektActionPerformed
        String hamtaProjekt = (String) cbValtProjekt.getSelectedItem();
        projektID = hamtaProjekt; 
        fyllTextFaltMedProjektData();
        listaProjektPersonal();
        listaProjektPartners();
    }//GEN-LAST:event_cbValtProjektActionPerformed

    private void btnStatistikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatistikActionPerformed
        new Statistik(databas, inloggadAnvandare, anstalldAid).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnStatistikActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new HandlaggareMeny(databas, inloggadAnvandare, anstalldAid).setVisible(true);
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
            java.util.logging.Logger.getLogger(ProjektChefMeny.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjektChefMeny.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjektChefMeny.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjektChefMeny.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new ProjektChefMeny().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLaggTillPartner;
    private javax.swing.JButton btnLaggTillPersonal;
    private javax.swing.JButton btnStang;
    private javax.swing.JButton btnStatistik;
    private javax.swing.JButton btnTaBortHandlaggare;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JComboBox<String> cbValtProjekt;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblLaggTill;
    private javax.swing.JLabel lblPersonalPaProjektet;
    private javax.swing.JLabel lblPersonalPaProjektet1;
    private javax.swing.JLabel lblPersonalPaProjektet2;
    private javax.swing.JLabel lblPersonalPaProjektet3;
    private javax.swing.JLabel lblPersonalPaProjektet4;
    private javax.swing.JLabel lblPersonalPaProjektet5;
    private javax.swing.JLabel lblPersonalPaProjektet6;
    private javax.swing.JLabel lblTillganligaPartners;
    private javax.swing.JLabel lblValdaPartners;
    private javax.swing.JLabel lblVisaPrio;
    private javax.swing.JTable tblHandlaggare;
    private javax.swing.JTable tblVisaHandlaggare;
    private javax.swing.JTable tblVisaPartners;
    private javax.swing.JTable tblVisaProjektPartnere;
    private javax.swing.JTextField tfProjektNamn;
    private javax.swing.JTextField txtBeskrivning;
    private javax.swing.JTextField txtDatumSlut;
    private javax.swing.JTextField txtDatumStart;
    private javax.swing.JTextField txtVisaKostnad;
    private javax.swing.JTextField txtVisaStatus;
    // End of variables declaration//GEN-END:variables
}
