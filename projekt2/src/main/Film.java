package main;
import java.util.*;

public abstract class Film {

        protected String nazev;
        protected String reziser;
        protected String rok_vydani;
        protected List<String> herciAnimatori;
        protected List<String> celkoveHodnoceniDivaku;

        public Film (String nazev, String reziser, String rok_vydani, List<String> herciAnimatori) {
            this.nazev = nazev;
            this.reziser = reziser;
            this.rok_vydani = rok_vydani;
            this.herciAnimatori = herciAnimatori;
            this.celkoveHodnoceniDivaku = new ArrayList<>();
        }

        public String getNazev() {return nazev;}

        public String getReziser() {return reziser;}

        public String getRok_vydani() {return rok_vydani;}

        public List<String> getHerciAnimatori() {return herciAnimatori;}

        public List <String> getHodnoceni() {return celkoveHodnoceniDivaku;}

        public void setCelkoveHodnoceniDivaku(List<String> celkoveHodnoceniDivaku) {
            this.celkoveHodnoceniDivaku = celkoveHodnoceniDivaku;
        }


        public abstract void addHodnoceni (int hodnoceni,String komentar) throws vlastniException;
        public abstract String getTyp();
        public abstract void Vypis();


    }