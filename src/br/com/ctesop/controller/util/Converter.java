/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ctesop.controller.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class Converter {
    public static final LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return LocalDate.parse(sdf.format(date), dtf);
    }
    
    public static Date converterData(LocalDate data) throws ParseException{
         if (data == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(data.toString());
    
    }
    
    public static Date converterData(String data) throws ParseException{
         if (data == null || data.isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(data);
    
    }

    public static String formatarData(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    
}
