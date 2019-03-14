package sk.itsovy.persons;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Date;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {

        /*SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-mm-dd");
        Date date1 = null;
        try {
            date1 = dateformat1.parse("1998-08-11");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Person person1 = new Person("Tom","Varga",date1,"190310/0012");
        Database db1 = new Database();
        db1.insertNewPerson(person1);*/

        File readFile = new File("/Users/tomvarga/desktop/text.txt");
        FileWriter fileWriter = new FileWriter("/Users/tomvarga/desktop/result.txt");

        try {

            //overenie ci to je cislo rodne
            //ci ma 11 cisiel
            //cislo z rodneho cisla porovnat s cislom v datume

            Scanner sc = new Scanner(readFile);
            Scanner sc2 = null;

            try {

                sc2 = new Scanner(new File("/Users/tomvarga/desktop/text.txt"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (sc2.hasNext()) {

                //premenne pre string
                Scanner s2 = new Scanner(sc2.nextLine());
                String Str = sc.nextLine();

                //premenna pre birth number
                String bnum = Str.split(" ")[2];
                //odstranenie lomky zo stringu s čislom
                String bnumRaw =  bnum.replace("/", "");
                //parsnutie stringu na čislo int
                long bnumLong = Long.parseLong(bnumRaw);
                int bnumCompare = Integer.parseInt(bnumRaw.substring(3,4));
                int bnumCompare2 = Integer.parseInt(bnumRaw.substring(4,6));
                int bnumCompare3 = Integer.parseInt(bnumRaw.substring(0,2));
                /*int bnumCompare3 = ;*/

                //premenna pre date
                String bdate = Str.split(" ")[3];
                //odstranenie bodky zo stringu s čislom
                String bdateRaw =  bdate.replace(".", "");
                //parsnutie stringu na čislo int
                int bmonth = Integer.parseInt(bdateRaw.substring(3,4));
                int bday = Integer.parseInt(bdateRaw.substring(0,2));
                int byear = Integer.parseInt(bdateRaw.substring(6,8));

                //System.out.println(bnumLong);
                //System.out.println(bnumCompare);
                //System.out.println(byear);
                //System.out.println(bmonthLong);
                //System.out.println(bday);
                //System.out.println(bnumCompare2);
                //System.out.println(bnumCompare3);

                String fname = Str.split("\\s+")[0];
                String lname = Str.split("\\s+")[1];
                String rc = Str.split("\\s+")[2];
                String date = Str.split("\\s+")[3].replace(".","-");

                while (s2.hasNext()) {
                    String s = s2.next();

                    //System.out.println(s);
                    //hotove overenie pre rodne cislo a delenie 11 cisiel
                    if(s.matches("^[0-9]{2}[0,1,5,6][0-9]{3}\\/?[0-9]{3,4}$") && bnumLong%11 == 0 &&
                    bnumCompare == bmonth && bnumCompare2 == bday && bnumCompare3 == byear){
                        //fileWriter.write("\n"+Str);
                        //System.out.println(fname);

                        System.out.println(date);

                        SimpleDateFormat dateformat1 = new SimpleDateFormat("dd-MM-yyyy");
                        Date date1 = null;
                        try {
                            date1 = dateformat1.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        System.out.println(date1);

                        Person person1 = new Person(fname,lname,date1,bnum);
                        Database db1 = new Database();
                        db1.insertNewPerson(person1);
                        //System.out.println(db1.selectBySurname("Jarna").getSurname());
                        //System.out.println(db1.selectByBNum("010513/1235").getSurname());
                        //db1.selectWomenNumber();

                        /*List <Person> persons = db1.selectAllMen();

                        for(int i=1; i<persons.size();i++){
                            System.out.println(persons.get(i).getName()+" "+persons.get(i).getSurname());
                        }*/

                        /*List <Person> persons2 = db1.selectAllAdult();

                        for(int i=1; i<persons2.size();i++) {
                            System.out.println(persons2.get(i).getName() + " " + persons2.get(i).getSurname());

                        }*/

                       // Set <String> persons2 = db1.selectAllFirstName();

                        /*for(int i=1; i<persons2.size();i++) {
                            System.out.println(persons2);

                        }*/

                        //System.out.println(persons2);
                        /*for(String ccc: persons2){
                            System.out.println(ccc);
                        }*/

                        List <Person> persons = db1.selectAll();

                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document doc = docBuilder.newDocument();

                        Element rootPersons = doc.createElement("Persons");
                        doc.appendChild(rootPersons);

                        for(int i=1; i<persons.size();i++) {

                            System.out.println(persons.get(i).getName() + " " + persons.get(i).getSurname());

                            Element person = doc.createElement("Person");
                            rootPersons.appendChild(person);

                            Element name = doc.createElement("FirstName");
                            name.appendChild(doc.createTextNode(persons.get(i).getName()));// tu meno
                            person.appendChild(name);

                            Element lastname = doc.createElement("LastName");
                            lastname.appendChild(doc.createTextNode(persons.get(i).getSurname()));// tu priezvisko
                            person.appendChild(lastname);

                            Element dnar = doc.createElement("Date");
                            dnar.appendChild(doc.createTextNode(persons.get(i).getDob().toString()));// tu date
                            person.appendChild(dnar);

                            Element birthnum = doc.createElement("BirthNumber");
                            birthnum.appendChild(doc.createTextNode(persons.get(i).getBnum()));// tu bnum
                            person.appendChild(birthnum);

                        }

                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File("file.xml"));

                        transformer.transform(source, result);

                        System.out.println("File saved!");

                    }
                }
            }

            sc.close();
            fileWriter.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

}