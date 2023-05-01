package main;


import java.util.*;
import java.io.*;



public class Databaze {

    private Scanner sc;
    protected static HashMap <String,Film> prvkyDatabaze;
    protected static List <String> seznamHercuAnimatoru;
    private connectDB conn;

    public Databaze()
    {
        prvkyDatabaze=new HashMap<String, Film>();
        seznamHercuAnimatoru=new ArrayList<String>();
        sc=new Scanner(System.in);
        conn = new connectDB();
    }

    public void setHrany_film()
    {
        sc=new Scanner(System.in);
        System.out.println(" ");
        System.out.println("Zadejte název filmu: ");
        String nazev=sc.nextLine();
        nazev = nazev.toLowerCase();

        System.out.println(" ");
        System.out.println("Zadejte jmeno režiséra filmu: ");
        String reziser=sc.nextLine();
        reziser = reziser.toLowerCase();

        System.out.println(" ");
        System.out.println("Zadejte rok vydání filmu: ");
        int rok_vydaniPomoc=Main.Vstup_cislo(sc);
        String rok_vydani = String.valueOf(rok_vydaniPomoc);


        System.out.println(" ");
        System.out.println("Zadejte pocet herců: ");
        int pocetHercu=Main.Vstup_cislo(sc);

        List<String> herci = new ArrayList<String>();
        sc=new Scanner(System.in);
        for (int i=0; i<pocetHercu;i++){
            System.out.println(" ");
            System.out.println("Zadejte jmeno herce: ");
            String herec=sc.nextLine();
            herec = herec.toLowerCase();
            herci.add(herec);
            seznamHercuAnimatoru.add(herec);
        }


        boolean duplikat = prvkyDatabaze.containsKey(nazev);

        if (duplikat == true){
            System.out.println(" ");
            System.out.println("Film s tímto jménem se již v databázi nachází.");
        } else {

            prvkyDatabaze.put(nazev, new Hrany_film(nazev,reziser,rok_vydani,herci));

        }
    }

    public void setAnimak()
    {
        sc=new Scanner(System.in);
        System.out.println(" ");
        System.out.println("Zadejte název filmu: ");
        String nazev=sc.nextLine();
        nazev = nazev.toLowerCase();

        System.out.println(" ");
        System.out.println("Zadejte jmeno režiséra filmu: ");
        String reziser=sc.nextLine();
        reziser = reziser.toLowerCase();

        System.out.println(" ");
        System.out.println("Zadejte rok vydání filmu: ");
        int rok_vydaniPomoc=Main.Vstup_cislo(sc);
        String rok_vydani = String.valueOf(rok_vydaniPomoc);

        System.out.println(" ");
        System.out.println("Zadejte doporučený věk diváka: ");
        int doporucenyVekPomoc=Main.Vstup_cislo(sc);
        String doporucenyVek = String.valueOf(doporucenyVekPomoc);

        System.out.println(" ");
        System.out.println("Zadejte pocet animátorů: ");
        int pocetAnimatoru=Main.Vstup_cislo(sc);

        List<String> animatori = new ArrayList<String>();
        sc=new Scanner(System.in);
        for (int i=0; i<pocetAnimatoru;i++){
            System.out.println(" ");
            System.out.println("Zadejte jmeno animátora: ");
            String animator=sc.nextLine();
            animator = animator.toLowerCase();
            animatori.add(animator);
            seznamHercuAnimatoru.add(animator);
        }

        boolean duplikat = prvkyDatabaze.containsKey(nazev);

        if (duplikat){
            System.out.println(" ");
            System.out.println("Film s tímto jménem se již v databázi nachází.");
        } else {
            prvkyDatabaze.put(nazev, new Animak(nazev,reziser,rok_vydani,animatori,doporucenyVek));
        }
    }

    public void saveToDb()
    {
        conn.pripoj();
        conn.dropTables();
        conn.createTableHraneFilmy();
        conn.createTableAnimovaneFilmy();

        for (Map.Entry<String, Film> entry : prvkyDatabaze.entrySet()) {
            Object film = entry.getValue();

            if ((((Film) film).getTyp()).equals("hrany")){
                String nazev = ((Film) film).getNazev();
                String reziser =((Film) film).getReziser();
                String rok_vydani =((Film) film).getRok_vydani();
                List<String> herci = new ArrayList<String>(((Film) film).getHerciAnimatori());
                List<String> hodnoceni = new ArrayList<String>(((Film) film).getHodnoceni());

                conn.insertHranyFilm(nazev,reziser,rok_vydani,herci,hodnoceni);

            } else if ((((Film) film).getTyp()).equals("animovany")) {
                String nazev = ((Film) film).getNazev();
                String reziser =((Film) film).getReziser();
                String rok_vydani =((Film) film).getRok_vydani();
                String doporucVek =((Animak) film).getDoporucenyVek();
                List<String> animat = new ArrayList<String>(((Film) film).getHerciAnimatori());
                List<String> hodnoceni = new ArrayList<String>(((Film) film).getHodnoceni());

                conn.insertAnimak(nazev,reziser,rok_vydani,doporucVek,animat,hodnoceni);
            }
        }
        conn.odpoj();
    }

    public void loadFromDb()
    {
        conn.pripoj();
        conn.selectAnim();
        conn.selectHrane();
        conn.odpoj();

    }


   public void getDatabaze()
    {
        int pocetPrvku=prvkyDatabaze.size();
        if (pocetPrvku>0) {
            for (Map.Entry<String, Film> entry : prvkyDatabaze.entrySet()) {
                Object filmy = entry.getValue();
                ((Film) filmy).Vypis();
                System.out.println(" ");
            }
        } else {
            System.out.println("Databáze filmů je prázdná");
        }
    }

    public void smazFilm(String nazev)
    {

        boolean x=prvkyDatabaze.containsKey(nazev);

        if (x) {
            Object film = prvkyDatabaze.get(nazev);
            List <String> herciAnimatori=((Film) film).getHerciAnimatori();
            for (int i = 0; i < herciAnimatori.size(); i++) {
                String herecAnimator = herciAnimatori.get(i);
                seznamHercuAnimatoru.remove(herecAnimator);
            }
            prvkyDatabaze.remove(nazev);
        } else {
            System.out.println("Databáze neobsahuje tento film");
        }
    }

    public void upravFilm()
    {
        System.out.println("Zadejte jméno filmu, který chcete upravit: ");
        String nazev=sc.nextLine();
        nazev = nazev.toLowerCase();
        boolean x=prvkyDatabaze.containsKey(nazev);

        if (x) {
            getFilm(nazev);
            Object film = prvkyDatabaze.get(nazev);
            String typFilmu=((Film) film).getTyp();
            smazFilm(nazev);
            System.out.println("Zadejte nové parametry filmu, který jste vybrali k úpravě: ");
            if (typFilmu.equals("hrany")) {
                setHrany_film();
            } else {
                setAnimak();
            }

        } else {
            System.out.println("Databáze neobsahuje tento film");
        }
    }

    public void getFilm(String nazev)
    {
        boolean x=prvkyDatabaze.containsKey(nazev);

        if (x) {
            System.out.println(" ");
            Object film = prvkyDatabaze.get(nazev);
            ((Film) film).Vypis();
            System.out.println(" ");
            List<String> pomocList = ((Film) film).getHodnoceni();
            System.out.println("Hodnocení diváků: ");
            for (int i = 0; i < pomocList.size(); i++) {
                System.out.println(pomocList.get(i));
            }
            //((Film) film).printHodnoceni();
        } else {
            System.out.println("Databáze neobsahuje tento film");
        }

    }

    public void getHerciVeVicFilmech() {
        List<String> pomocnyList=seznamHercuAnimatoru;
        System.out.println("Herci nebo animátoři, kteří se podíleli na více filmech: ");
        for (int i = 0; i < pomocnyList.size(); i++){
            String polozka=pomocnyList.get(i);
            int vyskyt = Collections.frequency(pomocnyList, polozka);
            if (vyskyt > 1) {
                List<String> seznamDanychFilmu=FilmyJednohoHerce(polozka);
                System.out.println(polozka+" hraje v těchto filmech: "+ seznamDanychFilmu);
                pomocnyList.removeAll(Collections.singleton(polozka));
            }
        }
    }

    public List<String> FilmyJednohoHerce(String herec)
    {
        int pocetPrvku=prvkyDatabaze.size();
        if (pocetPrvku>0) {
            List<String> filmyDanehoHerce = new ArrayList<String>();
            for (Map.Entry<String, Film> entry : prvkyDatabaze.entrySet()) {
                List<String> pomocnyList = new ArrayList<String>();
                Object filmy = entry.getValue();
                pomocnyList = ((Film) filmy).getHerciAnimatori();
                System.out.println(" ");
                if (pomocnyList.contains(herec)){
                    filmyDanehoHerce.add(((Film) filmy).getNazev());
                }
            }
            if (filmyDanehoHerce.isEmpty()){
                System.out.println("Hledaný herec nehraje v žádném filmu v databázi");
            } else {
                return filmyDanehoHerce;

            }
        } else {
            System.out.println("Databáze filmů je prázdná");
        }
        return null;
    }

    public void addHodnoceniFilmu() {

        System.out.println(" ");
        System.out.println("Zadejte jméno filmu, který chcete ohodnotit: ");
        sc = new Scanner(System.in);
        String nazev=sc.nextLine();
        nazev = nazev.toLowerCase();
        boolean x=prvkyDatabaze.containsKey(nazev);

        if (x) {
            Object film = prvkyDatabaze.get(nazev);

            System.out.println(" ");
            System.out.println("Přejete si zadat slovní hodnocení? (ano - a / ne - b) ");
            String komentar=Main.Vstup_druh(sc);

            if (komentar.equals("a")){
                System.out.println(" ");
                System.out.println("Zadejte váš komentář: ");
                sc=new Scanner(System.in);
                komentar=sc.nextLine();
            } else if (komentar.equals("b")){
                komentar=" - ";
            }

            chybaPriHodnoceni(komentar,film);

        } else {
            System.out.println("Databáze neobsahuje tento film");
        }
    }
    public void chybaPriHodnoceni(String komentar,Object film) {

        System.out.println(" ");
        System.out.println("Zadejte vaše bodové hodnocení filmu: ");
        int hodnoceni=Main.Vstup_cislo(sc);

        try {
            ((Film) film).addHodnoceni(hodnoceni,komentar);
        } catch (vlastniException e) {
            System.out.println(" ");
            chybaPriHodnoceni(komentar,film);
        }
    }



    public boolean ulozDoSouboru()
    {
        System.out.println("Zadejte název filmu, který chcete uložit: ");
        sc=new Scanner(System.in);
        String nazev=sc.nextLine();
        nazev = nazev.toLowerCase();
        boolean x=prvkyDatabaze.containsKey(nazev);

        if (x) {
            System.out.println(" ");
            Object film = prvkyDatabaze.get(nazev);
            String typFilmu=((Film) film).getTyp();

            try {
                FileWriter fw = new FileWriter(nazev+".txt");
                BufferedWriter out = new BufferedWriter(fw);
                out.write(typFilmu);
                out.newLine();
                out.write(((Film) film).getNazev());
                out.newLine();
                out.write(((Film) film).getReziser());
                out.newLine();
                out.write(((Film) film).getRok_vydani());

                if (typFilmu.equals("animovany")) {
                    out.newLine();
                    out.write(((Animak) film).getDoporucenyVek());
                }

                out.newLine();
                out.write(((Film) film).getHerciAnimatori().toString());
                out.newLine();
                out.write(((Film) film).getHodnoceni().toString());
                out.close();
                fw.close();
            }
            catch (IOException e) {
                System.out.println(" ");
                System.out.println("Soubor nelze vytvorit");
                return false;
            }
            return true;
        } else {
            System.out.println(" ");
            System.out.println("Databáze neobsahuje tento film");
            return false;
        }
    }

    public boolean nactiZeSouboru() {

        System.out.println("Zadejte název souboru (.txt), odkud chcete film načíst (bez přípony): ");
        sc=new Scanner(System.in);
        String nazevSouboru=sc.nextLine();
        nazevSouboru = nazevSouboru.toLowerCase();
        boolean duplikat = prvkyDatabaze.containsKey(nazevSouboru);
        if (duplikat){
            System.out.println(" ");
            System.out.println("Film s tímto jménem se již v databázi nachází.");
            return false;
        } else {

            String nazev = "";
            String reziser= "";
            String rok_vydani= "";
            String doporucenyVek= "";
            List<String> herciAnimatori = null;
            String herAnim = "";
            List<String> hodnoceni = null;
            String hod = "";

            try {
                File soubor = new File(nazevSouboru+".txt");
                sc = new Scanner(soubor);
                int x=0;
                int y=2;

                while (sc.hasNextLine()) {
                    String line=sc.nextLine();
                    if (line.equals("hrany")&&x==0){
                        y=0;
                    } else if (line.equals("animovany")&&x==0){
                        y=1;
                    }

                    if (y==2){
                        System.out.println("Soubor se nepodařilo načíst");
                        System.out.println("Zkontrolujte, zda soubor obsahuje všechny potřebné parametry");
                        return false;
                    }

                    if (x==4&& y==0){
                        x++;
                    }

                    if (x==1){
                        nazev=line;
                    } else if (x==2) {
                        reziser=line;
                    }else if (x==3) {
                        rok_vydani=line;
                    }else if (x==4 && y==1) {
                        doporucenyVek=line;
                    }else if (x==5) {
                        herAnim=line;
                        herAnim = herAnim.substring(1, herAnim.length() - 1);
                        herciAnimatori = new ArrayList<String>(Arrays.asList(herAnim.split(",\\s*")));
                        for (int i=0; i<(herciAnimatori.size());i++){seznamHercuAnimatoru.add(herciAnimatori.get(i));}
                    }else if (x==6) {
                        hod=line;
                        hod = hod.substring(1, hod.length() - 1);
                        hodnoceni = new ArrayList<String>(Arrays.asList(hod.split(",\\s*")));
                    }
                    x++;
                }

                sc.close();
                if ((nazev=="" || reziser=="" || rok_vydani=="" || herAnim=="") && y==0){
                    System.out.println("Soubor se nepodařilo načíst");
                    System.out.println("Zkontrolujte, zda soubor obsahuje všechny potřebné parametry");
                    return false;
                } else if ((nazev=="" || reziser=="" || rok_vydani=="" || doporucenyVek==""|| herAnim=="") && y==1){
                    System.out.println("Soubor se nepodařilo načíst");
                    System.out.println("Zkontrolujte, zda soubor obsahuje všechny potřebné parametry");
                    return false;
                }

                if (y==0){
                    prvkyDatabaze.put(nazevSouboru, new Hrany_film(nazev,reziser,rok_vydani,herciAnimatori));
                }else{
                    prvkyDatabaze.put(nazevSouboru, new Animak(nazev,reziser,rok_vydani,herciAnimatori,doporucenyVek));
                }

                Object film = prvkyDatabaze.get(nazevSouboru);
                ((Film) film).setCelkoveHodnoceniDivaku(hodnoceni);

            } catch (FileNotFoundException e) {
                System.out.println(" ");
                System.out.println("Soubor nenalezen");
                System.out.println("Zkontrolujte zda je soubor ve stejné složce jako program a zda jeho název obsahuje pouze malá písmena");
                return false;
            }
            return true;
        }
    }
/*
        try {
            fr = new FileReader(nazevSouboru);
            in = new BufferedReader(fr);
            String line=in.readLine();
            String oddelovac = "[ ]+";
            String[] castiTextu = line.split(oddelovac);

            if (castiTextu.length!=2)
                return false;
            int pocetPrvku=Integer.parseInt(castiTextu[1]);
            //prvkyDatabaze=new Hrany_film[pocetPrvku];
            for (int i=0;i<pocetPrvku;i++)
            {
                line=in.readLine();
                castiTextu = line.split(oddelovac);
                if (castiTextu.length==3)
                {
                    prvkyDatabaze[i]=new Hrany_film(castiTextu[0], Integer.parseInt(castiTextu[1]));
                    prvkyDatabaze[i].setStudijniPrumer(Float.parseFloat(castiTextu[2]));
                }
            }
        }
        catch (IOException e) {
            System.out.println("Soubor  nelze otev��t");
            return false;
        }
        catch (NumberFormatException e) {
            System.out.println("Chyba integrity dat v souboru");
            return false;
        } catch (vlastniException e) {
            System.out.println("Nespravna data v souboru");
            return false;
        }
        finally
        {
            try
            {	if (in!=null)
            {
                in.close();
                fr.close();
            }
            }
            catch (IOException e) {
                System.out.println("Soubor nelze zavrit");
                return false;
            }
        }

        return true;
    }*/
/*
    public String[] nactiFilm(String jmenoSouboru) {
        FileReader fr=null;
        BufferedReader in=null;
        try {
            fr = new FileReader(jmenoSouboru);
            in = new BufferedReader(fr);
            String radek=in.readLine();
            String[] vysledek = radek.split(";",-1);
            return vysledek;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

}