/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oru.inf.InfDB;
import oru.inf.InfException; 
/**
 *
 * @author anton
 */
public class Validering {
     private InfDB idb;

    public Validering(InfDB idb) {
        this.idb = idb;
    }
    
    //Validering för att hämta admin
    public boolean hamtaAdmin(String mail){
      boolean validering = false;
      String sqlqury = "SELECT EXISTS (SELECT 1 FROM admin WHERE aid = (SELECT aid FROM anstalld WHERE epost = '"+mail+"')" +") AS Admin;";
      try{
          String resultat = idb.fetchSingle(sqlqury);
      if (resultat.equals("1")){
           validering = true;     
        }
      }catch(InfException ex){
          System.out.println(ex.getMessage());
      }
         
      return validering;
   }
    //Validering för att hämta projektchef
    public boolean hamtaProjektchef(String mail) {
    boolean validering = false;
    String sqlqury = "SELECT EXISTS (SELECT 1 FROM projekt WHERE projektchef = (SELECT aid FROM anstalld WHERE epost = '" + mail + "')) AS Projektchef;";
    try {
        String resultat = idb.fetchSingle(sqlqury);
        if (resultat.equals("1")) {
            validering = true;
        }
    } catch (InfException ex) {
        System.out.println(ex.getMessage());
    }

    return validering;
    }
    
    //Validering för att hämta handläggare
    public boolean hamtaHandlaggare(String mail) {
    boolean validering = false;
    String sqlqury = "SELECT EXISTS (SELECT 1 FROM handlaggare WHERE aid = (SELECT aid FROM anstalld WHERE epost = '" + mail + "')) AS Handlaggare;";
    try {
        String resultat = idb.fetchSingle(sqlqury);
        if (resultat.equals("1")) {
            validering = true;
        }
    } catch (InfException ex) {
        System.out.println(ex.getMessage());
    }

    return validering;
    }
    
     // Validering av e-post
    public boolean hamtaEpost(String ePost) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (ePost == null) {
            return false;
        }
        return pattern.matcher(ePost).matches();
    }

     //Validering av datum
    public boolean valideringDatum(String datum) {
        String datePattern = "^(?:\\d{4})-(?:0[1-9]|1[0-2])-(?:0[1-9]|[1-2][0-9]|3[0-1])$";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(datum);
        
        if (matcher.matches()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate.parse(datum, formatter);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }
        return false;
    }
    
    // Valideringsmetod 1
    // Validering av numeriska fält som projektID
    public boolean valideraNumerisk(String data) {
        return data.matches("\\d+");  // Kontrollerar om data enbart består av siffror
    }
    
    // Valideringsmetod 2
    // Validering av numeriska strängar (avdelningsnummer etc.)
    public boolean valideraNumeriskStrang(String data) {
        return data.matches("^\\d+$");
    }
    
    // Valideringsmetod 3
    // Validering av beskrivningar som innehåller bokstäver, mellanslag och nummer
    public boolean valideraBeskrivning(String data) {
        return data.matches("^[a-zA-Z0-9\\s]+$");
    }
    
    // Valideringsmetod 5
    // Validering av lösenord (med krav på minst 8 tecken)
    public boolean valideraLosenord(String losenord) {
        return losenord.matches("^[A-Za-z\\d]{8,}$");
    }
    
    // Valideringsmetod 6
    // Validering av internationella telefonnummer
    public boolean valideraInternationelltTelefonnummer(String telefon) {
        String telefonRegex = "^\\+\\d{10}$";
        Pattern pattern = Pattern.compile(telefonRegex);
        if (telefon == null) {
            return false;
        }
        return pattern.matcher(telefon).matches();
    }
    
    // Valideringsmetod 7
    // Validering av telefonnummer (Amerikanskt format: XXX-XXX-XXXX)
    public boolean valideraAmerikansktTelefonnummer(String telefon) {
        String telefonRegex = "^\\d{3}-\\d{3}-\\d{4}$";
        Pattern pattern = Pattern.compile(telefonRegex);
        if (telefon == null) {
            return false;
        }
        return pattern.matcher(telefon).matches();
    }
    
    // Valideringsmetod 8
    // Validering av adresser (inklusive siffror, bokstäver och adressymboler)
    public boolean valideraAdress(String adress) {
        String adressRegex = "^[0-9a-zA-ZäöåÄÖÅ\\s.,'-]+$";
        Pattern pattern = Pattern.compile(adressRegex);
        if (adress == null) {
            return false;
        }
        return pattern.matcher(adress).matches();
    }
    
    // Valideringsmetod 9
    // Validering av förnamn (inklusive bindestreck och svenska tecken)
    public boolean valideraFornamn(String fornamn) {
        String fornamnRegex = "^[a-zA-ZäöåÄÖÅ\\s-]+$";
        Pattern pattern = Pattern.compile(fornamnRegex);
        if (fornamn == null) {
            return false;
        }
        return pattern.matcher(fornamn).matches();
    }
    
    // Valideringsmetod 10
    // Validering av efternamn (inklusive bindestreck och apostrofer)
    public boolean valideraEfternamn(String efternamn) {
        String efternamnRegex = "^[a-zA-ZäöåÄÖÅ\\-']+$";
        Pattern pattern = Pattern.compile(efternamnRegex);
        if (efternamn == null) {
            return false;
        }
        return pattern.matcher(efternamn).matches();
    }

    // Valideringsmetod 12
    // Validering av projektnamn (format: "Projekt" följt av en siffra under 1000)
    public boolean valideraProjektnamn(String projektnamn) {
    String projektnamnRegex = "^Projekt [1-9]\\d{0,2}$";
    Pattern pattern = Pattern.compile(projektnamnRegex);
        if (projektnamn == null) {
        return false;
    }
    return pattern.matcher(projektnamn).matches();
    }
    
    // Valideringsmetod 13
    // Validering av kostnader (format: decimaltal med två decimaler)
    public boolean valideraKostnad(String kostnad) {
    String kostnadRegex = "^\\d+\\.\\d{2}$";
    Pattern pattern = Pattern.compile(kostnadRegex);
        if (kostnad == null) {
        return false;
    }
    return pattern.matcher(kostnad).matches();
    }
    
    // Valideringsmetod 14
    // Validering av valuta (format: decimaltal med fyra decimaler)
    public boolean valideraValuta(String kostnad) {
    String kostnadRegex = "^\\d+\\.\\d{4}$";
    Pattern pattern = Pattern.compile(kostnadRegex);
        if (kostnad == null) {
        return false;
    }
    return pattern.matcher(kostnad).matches();
    }

    // Validering av datum
    public boolean valideraDatum(String datum) {
        String datePattern = "^(?:\\d{4})-(?:0[1-9]|1[0-2])-(?:0[1-9]|[1-2][0-9]|3[0-1])$";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(datum);

        if (matcher.matches()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate.parse(datum, formatter);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }
        return false;
    }
    // Valideringsmetod för Status
    public boolean valideraStatus(String status) {
        List<String> giltigaStatusar = Arrays.asList("Pågående", "Planerat", "Avslutat");
        if (giltigaStatusar.contains(status)) {
            return true;
        } else {
            // Konvertera första bokstaven till stor bokstav och kontrollera igen
            String formattedStatus = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
            return giltigaStatusar.contains(formattedStatus);
        }
    }
    
    // Valideringsmetod för Prioritet
    public boolean valideraPrioritet(String prioritet) {
        List<String> giltigaPrioriteter = Arrays.asList("Hög", "Medel", "Låg");
        if (giltigaPrioriteter.contains(prioritet)) {
            return true;
        } else {
            // Konvertera första bokstaven till stor bokstav och kontrollera igen
            String formattedPrioritet = prioritet.substring(0, 1).toUpperCase() + prioritet.substring(1).toLowerCase();
            return giltigaPrioriteter.contains(formattedPrioritet);
        }
    }
}

   