package main;

import java.lang.*;
//import java.io.*;
import java.util.*;
import main.vlastniException;

public class Main {


    public static int menu;
    public static String nazev;
    public static String Vstup_druh (Scanner sc)
    {
        String retezec = sc.next();

        String malyRetezec = retezec.toLowerCase();

        try {
            if (!malyRetezec.equals("a") && !malyRetezec.equals("b")) {
                throw new vlastniException("Zadán neplatný vstup!");
                }
        } catch (vlastniException e) {
            System.out.println(e.getMessage());
            System.out.println("Zadejte prosim písmeno u možnosti, kterou chcete zvolit");
            malyRetezec = Vstup_druh(sc);
        }
        return malyRetezec;
    }

    public static int Vstup_cislo (Scanner sc)
    {
        int cislo;
        try
        {
            cislo = sc.nextInt();
            if (menu==1 && (cislo<0 || cislo>10)) {
                throw new vlastniException("Zadán neplatný vstup!");
            }
        } catch (vlastniException e) {
            System.out.println(e.getMessage());
            System.out.println("Zadejte prosim cislo u možnosti, kterou chcete zvolit");
            cislo = Vstup_cislo(sc);
        }
        catch(Exception e)
        {
            System.out.println("Zadán neplatný vstup!");
            System.out.println("Zadejte prosim cislo možnosti, kterou chcete zvolit");
            sc.nextLine();
            cislo = Vstup_cislo(sc);
        }
        return cislo;
    }

    public static void main(String[] args) {

        boolean loop=true;
        Databaze databazeFilmu=new Databaze();
        Scanner sc = new Scanner(System.in);
        databazeFilmu.loadFromDb();

        while(loop) {

            sc = new Scanner(System.in);
            menu=1;

            System.out.println(" ");
            System.out.println("Databáze filmů");
            System.out.println("Vyberte požadovanou akci: ");
            System.out.println(" ");
            System.out.println("1) Přidání nového filmu");
            System.out.println("2) Upravení filmu");
            System.out.println("3) Smazání filmu");
            System.out.println("4) Přidání hodnocení danému filmu");
            System.out.println("5) Výpis všech filmů");
            System.out.println("6) Vyhledání konkrétního filmu");
            System.out.println("7) Výpis herců nebo animátorů, kteří se podíleli na více než jednom filmu");
            System.out.println("8) Výpis všech filmů, které obsahují konkrétního herce nebo animátora");
            System.out.println("9) Uložení informace o vybraném filmu (dle jeho názvu) do souboru");
            System.out.println("10) Načtení všech informací o daném filmu ze souboru");
            System.out.println("0) Ukončení programu");
            System.out.println(" ");


            int volba=Vstup_cislo(sc);
            menu=0;
            switch (volba)
            {
                case 1:
                    System.out.println("Vyberte druh filmu (Animovaný - a  /  Hraný - b): ");
                    String druh = Vstup_druh(sc);

                    if (druh.equals("a")){
                        databazeFilmu.setAnimak();
                    } else if (druh.equals("b")){
                        databazeFilmu.setHrany_film();
                    }
                    break;

                case 2:
                    System.out.println(" ");
                    databazeFilmu.upravFilm();
                    break;

                case 3:
                    System.out.println(" ");
                    System.out.println("Zadejte jméno filmu, který chcete smazat: ");
                    sc = new Scanner(System.in);
                    nazev=sc.nextLine();
                    databazeFilmu.smazFilm(nazev);
                    break;

                case 4:
                    databazeFilmu.addHodnoceniFilmu();
                    break;

                case 5:
                    System.out.println(" ");
                    databazeFilmu.getDatabaze();
                    break;

                case 6:
                    System.out.println(" ");
                    System.out.println("Zadejte jméno filmu, který chcete vyhledat: ");
                    sc = new Scanner(System.in);
                    nazev=sc.nextLine();
                    databazeFilmu.getFilm(nazev);
                    break;

                case 7:
                    databazeFilmu.getHerciVeVicFilmech();
                    break;

                case 8:
                    System.out.println("Zadejte jméno herce nebo animátora, jehož filmy chcete vyhledat: ");
                    sc = new Scanner(System.in);
                    String herec=sc.nextLine();
                    List<String> seznamDanychFilmu=databazeFilmu.FilmyJednohoHerce(herec);
                    System.out.println("Hledaný herec nebo animátor se podílel na těchto filmech: "+ seznamDanychFilmu);
                    break;

                case 9:
                    if (databazeFilmu.ulozDoSouboru()) {
                        System.out.println(" ");
                        System.out.println("Film uložen");
                    }else {
                        System.out.println(" ");
                        System.out.println("Film nebylo možno uložit");
                    }
                    break;

                case 10:

                    if (databazeFilmu.nactiZeSouboru()) {
                        System.out.println(" ");
                        System.out.println("Film načten");
                    } else {
                        System.out.println(" ");
                        System.out.println("Film nebylo možno načíst");
                    }
                    break;

                case 0:
                    databazeFilmu.saveToDb();
                    loop=false;
                    break;
                default:
                    System.out.println("Pokud vidíte tuto zprávu, tak se něco pokazilo =(");
                    System.out.println("Jestli to dál normálně funguje, buďte za to rádi. =)");
                    System.out.println("Pokud ne, tak to zkuste restartovat =o");
                    break;
            }



        }
    }
}