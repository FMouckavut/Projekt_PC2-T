package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class connectDB {
    private Connection conn;
    public boolean pripoj() {
        conn= null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:FilmDB.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void odpoj() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
/*
    public boolean createTableFilmy()
    {
        if (conn==null)
            return false;
        String sql = "CREATE TABLE IF NOT EXISTS Filmy (" + "id integer PRIMARY KEY," + "jmeno varchar(255) NOT NULL,"+ "rodneCislo bigint, " + "popis varchar(50), " + "plat real" + ");";
        //(" + "nazev TEXT PRIMARY KEY NOT NULL," + "reziser TEXT NOT NULL," + "rok INT NOT NULL, " + "herci TEXT NOT NULL, " + "hodnoceni TEXT NOT NULL," + "slovniHodnoceni TEXT NULL DEFAULT '-'" + ");";
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public void insertFilm(String jmeno, long RC, String popis,float plat ) {
        String sql = "INSERT INTO zamestnanci(jmeno,rodneCislo,popis,plat) VALUES(?,?,?,?)";
        try {
            PreparedStatement prepstmnt = conn.prepareStatement(sql);
            prepstmnt.setString(1, jmeno);
            prepstmnt.setLong(2, RC);
            prepstmnt.setString(3, popis);
            prepstmnt.setFloat(4, plat);
            prepstmnt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAll(){
        String sql = "SELECT id, jmeno,rodneCislo, popis, plat FROM zamestnanci";
        try {
            Statement stmnt  = conn.createStatement();
            ResultSet res_set    = stmnt.executeQuery(sql);
            while (res_set.next()) {
                System.out.println(res_set.getInt("id") +  "\t" +
                        res_set.getString("jmeno") + "\t" +
                        res_set.getLong("rodneCislo") + "\t" +
                        res_set.getString("popis") + "\t" +
                        res_set.getLong("plat"));
            }
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        }
    }*/

    public boolean createTableHraneFilmy()
    {
        if (conn==null){return false;}
        String sql = "CREATE TABLE IF NOT EXISTS HraneFilmy (" + "nazev TEXT PRIMARY KEY NOT NULL," + "reziser TEXT NOT NULL," + "rok_vydani TEXT NOT NULL, " + "herci TEXT NOT NULL, " + " hodnoceni TEXT NOT NULL);";
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean createTableAnimovaneFilmy()
    {
        if (conn==null){return false;}
        String sql = "CREATE TABLE IF NOT EXISTS AnimovaneFilmy (" + "nazev TEXT PRIMARY KEY NOT NULL," + "reziser TEXT NOT NULL," + "rok_vydani TEXT NOT NULL, " + "doporuceny_vek TEXT NOT NULL, " + " animatori TEXT NOT NULL, " + "hodnoceni TEXT NOT NULL);";
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void insertHranyFilm(String nazev, String reziser, String rok_vydani, List<String> herciStr, List<String> hodnocStr) {
        String sql = "INSERT INTO HraneFilmy(nazev,reziser,rok_vydani,herci,hodnoceni) VALUES(?,?,?,?,?)";
        try {
            String herci=herciStr.toString();
            herci = herci.substring(1, herci.length() - 1);

            String hodnoceni=hodnocStr.toString();
            hodnoceni = hodnoceni.substring(1, hodnoceni.length() - 1);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nazev);
            pstmt.setString(2, reziser);
            pstmt.setString(3, rok_vydani);
            pstmt.setString(4, herci);
            pstmt.setString(5,hodnoceni);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertAnimak(String nazev, String reziser, String rok_vydani, String doporucenyVek, List<String> animatStr, List<String> hodnocStr) {
        String sql = "INSERT INTO AnimovaneFilmy(nazev,reziser,rok_vydani,doporuceny_vek,animatori,hodnoceni) VALUES(?,?,?,?,?,?)";
        try {
            String animatori=animatStr.toString();
            animatori = animatori.substring(1, animatori.length() - 1);

            String hodnoceni=hodnocStr.toString();
            hodnoceni = hodnoceni.substring(1, hodnoceni.length() - 1);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nazev);
            pstmt.setString(2, reziser);
            pstmt.setString(3, rok_vydani);
            pstmt.setString(4, doporucenyVek);
            pstmt.setString(5, animatori);
            pstmt.setString(6,hodnoceni);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void selectHrane(){
        String SQL = "SELECT nazev,reziser,rok_vydani,herci,hodnoceni FROM HraneFilmy";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            Databaze databazeFilmu=new Databaze();
            while (rs.next()) {
                String nazev = rs.getString("nazev");
                String reziser = rs.getString("reziser");
                String rok_vydani = rs.getString("rok_vydani");
                String herci = rs.getString("herci");
                String hodnoceni = rs.getString("hodnoceni");

                List <String> herciAnimatori = new ArrayList<String>(Arrays.asList(herci.split(",\\s*")));
                List <String> seznamHercuAnimatoru = new ArrayList<String>();
                List <String> hod = new ArrayList<String>(Arrays.asList(hodnoceni.split(",\\s*")));
                
                for (int i=0; i<(herciAnimatori.size());i++){seznamHercuAnimatoru.add(herciAnimatori.get(i));}
                for (int i=0; i<(herciAnimatori.size());i++){Databaze.seznamHercuAnimatoru.add(herciAnimatori.get(i));}

                Hrany_film film = new Hrany_film(nazev,reziser,rok_vydani,seznamHercuAnimatoru);
                ((Hrany_film) film).setCelkoveHodnoceniDivaku(hod);

                Databaze.prvkyDatabaze.put(nazev, film);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAnim(){
        String sql = "SELECT nazev,reziser,rok_vydani,doporuceny_vek,animatori,hodnoceni FROM AnimovaneFilmy";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Databaze databazeFilmu=new Databaze();
            while (rs.next()) {
                String nazev = rs.getString("nazev");
                String reziser = rs.getString("reziser");
                String rok_vydani = rs.getString("rok_vydani");
                String doporucenyVek = rs.getString("doporucenyVek");
                String animatori = rs.getString("animatori");
                String hodnoceni = rs.getString("hodnoceni");

                List <String> herciAnimatori = new ArrayList<String>(Arrays.asList(animatori.split(",\\s*")));
                List <String> seznamHercuAnimatoru = new ArrayList<String>();
                List <String> hod = new ArrayList<String>(Arrays.asList(hodnoceni.split(",\\s*")));

                for (int i=0; i<(herciAnimatori.size());i++){seznamHercuAnimatoru.add(herciAnimatori.get(i));}
                for (int i=0; i<(herciAnimatori.size());i++){Databaze.seznamHercuAnimatoru.add(herciAnimatori.get(i));}

                Animak film = new Animak(nazev,reziser,rok_vydani,seznamHercuAnimatoru,doporucenyVek);
                ((Animak) film).setCelkoveHodnoceniDivaku(hod);

                Databaze.prvkyDatabaze.put(nazev, film);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropTables()
    {
        String sql = "DROP TABLE HraneFilmy";
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "DROP TABLE AnimovaneFilmy";
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
