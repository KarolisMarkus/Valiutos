/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.eis.valiutukursai.main;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lt.eis.valiutukursai.data.Currency;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Karolis
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Iveskite pirma data formatu yyyy-MM-dd: ");
        LocalDate date1 = dateInput(sc);
        System.out.println("Iveskite antra data formatu yyyy-MM-dd: ");
        LocalDate date2 = dateInput(sc);

        NodeList nl1 = createNodeList(date1);
        NodeList nl2 = createNodeList(date2);

        Set<String> l = new HashSet<>();
        System.out.println("Iveskite valiutu kodus: ");
        Boolean b = true;
        while (b) {
            System.out.println("Iveskite valiutu kodus arba zodi 'end' jeigu norite baigti:");
            String s = sc.nextLine();
            if (!"end".equalsIgnoreCase(s)) {
                if (s.length() != 3) {
                    System.out.println("Valitu kodai yra triju raizdiu. Prasome ivesti kita.");
                } else {
                    l.add(s);
                }
            } else {
                b = false;
            }
        }

        for (String code : l) {
            Boolean check = true;
            Currency c = new Currency();
            for (int temp = 0; temp < nl1.getLength(); temp++) {
                Node node1 = nl1.item(temp);
                Node node2 = nl2.item(temp);
                if (node1.getNodeType() == Node.ELEMENT_NODE && node2.getNodeType() == Node.ELEMENT_NODE) {
                    Element element1 = (Element) node1;
                    if (element1.getElementsByTagName("valiutos_kodas").item(0).getTextContent().equalsIgnoreCase(code.toUpperCase())) {
                        c = getCurrency(element1, nl2);
                        System.out.println("");
                        System.out.println(c.getPavadinimas());
                        System.out.println(c.getValiutosKodas());
                        System.out.println(c.getData() + " santykis buvo: ");
                        System.out.println(c.getSantykis1());
                        System.out.println(c.getData2() + " santykis buvo: ");
                        System.out.println(c.getSantykis2());
                        System.out.println("Skirtumas pateiktomis dienomis yra: " + c.getBendrasSantykis());
                        System.out.println("");
                        check = false;
                        break;
                    }

                }
            }
            if (check) {
                System.out.println("Valiutos kodas " + code + " neegzistuoja.");
            }
        }
        sc.close();
    }

    public static LocalDate dateInput(Scanner sc) {
        String sDate;
        LocalDate date = null;
        sDate = sc.nextLine();
        LocalDate ldTest = LocalDate.of(2014, 10, 1);
        LocalDate ldNow = LocalDate.now();
        try {
            date = LocalDate.parse(sDate);
        } catch (Exception e) {
            System.out.println("Blogai ivesta data");
            return dateInput(sc);
        }
        if (date.isBefore(ldTest)) {
            System.out.println("Sios ir ankstesniu datu duomenu nera, duomenys rodomi tik nuo 2014 m. rugsejo 30 d. ");
            date = dateInput(sc);
            return date;
        }
        if (date.isAfter(ldNow)) {
            System.out.println("Si data yra velsne negu siandien.");
            date = ldNow;
            return date;
        }
        return date;
    }

    public static NodeList createNodeList(LocalDate date) {
        NodeList nList = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            System.setProperty("http.agent", "Chrome");
            Document doc = dBuilder.parse(new URL("https://www.lb.lt/lt/currency/daylyexport/?xml=1&class=Eu&type=day&date_day=" + date.toString()).openStream());
            doc.getDocumentElement().normalize();
            nList = doc.getElementsByTagName("item");
        } catch (Exception ex) {
            System.out.println("nepavyko atsiusti failo");
        }
        if (nList.getLength() <= 1) {
            return (createNodeList(date.minusDays(1)));
        }
        return nList;
    }

    public static Currency getCurrency(Element e, NodeList nl) {
        Currency c = new Currency();
        c.setPavadinimas(e.getElementsByTagName("pavadinimas").item(0).getTextContent());
        c.setValiutosKodas(e.getElementsByTagName("valiutos_kodas").item(0).getTextContent());
        c.setData(LocalDate.parse(e.getElementsByTagName("data").item(0).getTextContent()));
        c.setSantykis1(new BigDecimal(e.getElementsByTagName("santykis").item(0).getTextContent().replace(",", ".")));
        c.setSantykis2(getSecondSantykis(nl, c.getValiutosKodas()));
        c.setData2(getSecondDate(nl));
        return c;
    }

    public static BigDecimal getSecondSantykis(NodeList nl, String vk) {
        for (int temp = 1; temp < nl.getLength(); temp++) {
            Node node = nl.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                if (e.getElementsByTagName("valiutos_kodas").item(0).getTextContent().equalsIgnoreCase(vk)) {
                    String sSantykis = e.getElementsByTagName("santykis").item(0).getTextContent().replace(",", ".");
                    BigDecimal santykis = new BigDecimal(sSantykis);
                    return santykis;
                }
            }
        }
        return null;
    }

    public static LocalDate getSecondDate(NodeList nl) {
        LocalDate d = null;
        Node n = nl.item(1);
        if (n.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) n;
            d = LocalDate.parse(e.getElementsByTagName("data").item(0).getTextContent());
            return d;
        }
        return d;
    }
}
