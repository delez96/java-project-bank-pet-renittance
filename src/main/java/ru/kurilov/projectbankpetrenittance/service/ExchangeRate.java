package ru.kurilov.projectbankpetrenittance.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ExchangeRate {
    static double dollar;
    static double euro;
    static {
        try {
            Document doc = Jsoup.connect("https://bankiros.ru/currency/cbrf").get();
            dollar = Double.parseDouble(doc.getElementsByClass("currency").get(1).children().get(1).text());
            euro = Double.parseDouble(doc.getElementsByClass("currency").get(2).children().get(1).text());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static double getDollar() {
        return dollar;
    }
    public static double getEuro() {
        return euro;
    }

    public static void main(String[] args) {
        System.out.println(dollar);
        System.out.println(euro);

    }
}
