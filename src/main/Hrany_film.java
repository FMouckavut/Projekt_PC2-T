package main;


import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class Hrany_film extends Film{


    public Hrany_film (String nazev, String reziser, String rok_vydani, List<String> herci)
    {
        super(nazev, reziser, rok_vydani, herci);
    }


    @Override
    public String getTyp() {
        return "hrany";
    }

    @Override
    public void addHodnoceni(int hodnoceni,String komentar) throws vlastniException
    {
        if (hodnoceni<1||hodnoceni>5) {
            System.out.println(" ");
            System.out.println("Zadano nespravne hodnoceni");
            System.out.println("Zadejte prosim hodnoceni v rozmezi 1 až 5");
            throw new vlastniException("");
        } else {
            TreeMap <Integer,String> celkHodDiv = new TreeMap<>();
            celkHodDiv.put(hodnoceni, komentar);
            String pomoc = celkHodDiv.toString().substring(1, celkHodDiv.toString().length() - 1);
            celkoveHodnoceniDivaku.add(pomoc);
        }
    }


    @Override
    public void Vypis() {
        System.out.println("Hraný film");
        System.out.println("Název: " + this.getNazev());
        System.out.println("Režisér: " + this.getReziser());
        System.out.println("Rok vydání: " + this.getRok_vydani());
        System.out.println("Herci: " + this.getHerciAnimatori());
    }

}
