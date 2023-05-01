package main;

import java.util.List;
import java.util.TreeMap;

public class Animak extends Film{

    private String doporucenyVek;
    public Animak (String nazev, String reziser, String rok_vydani, List<String> animatori, String doporucenyVek)
    {
        super(nazev, reziser, rok_vydani, animatori);
        this.doporucenyVek=doporucenyVek;
    }

    public String getDoporucenyVek() {return doporucenyVek;}

    @Override
    public String getTyp() {
        return "animovany";
    }

    @Override
    public void addHodnoceni (int hodnoceni,String komentar) throws vlastniException
    {
        if (hodnoceni<1||hodnoceni>10) {
            System.out.println(" ");
            System.out.println("Zadano nespravne hodnoceni");
            System.out.println("Zadejte prosim hodnoceni v rozmezi 1 až 10");
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
        System.out.println("Animovaný film");
        System.out.println("Název: " + this.getNazev());
        System.out.println("Režisér: " + this.getReziser());
        System.out.println("Rok vydání: " + this.getRok_vydani());
        System.out.println("Doporučený věk diváka: " + this.getDoporucenyVek());
        System.out.println("Animatoři: " + this.getHerciAnimatori());
    }
}
