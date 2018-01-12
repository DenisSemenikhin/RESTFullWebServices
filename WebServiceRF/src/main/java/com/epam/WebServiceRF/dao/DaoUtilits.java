package com.epam.WebServiceRF.dao;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaoUtilits {

    static Date stringToDate(String dateString, String patern) throws java.text.ParseException {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patern);
        date = simpleDateFormat.parse(dateString);
        return date;
    }


    static String datetoString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    static JSONObject getJsonObject(String path){
        JSONObject jsonObject = null;
        try {

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(path));
            jsonObject = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    static void writeJsonToFile(JSONObject jsonObject, String path) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonObject.toJSONString()+"\n");
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
