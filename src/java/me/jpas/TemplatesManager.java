/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jpas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Owner
 */
public class TemplatesManager {

    private static Map<String, Document> templates = new HashMap<>();

    public static void loadTemplates() {
        URL templatesFolderUrl = TemplatesManager.class.getClassLoader().getResource("templates");
        String templatesFolderPath = null;
        try {
            templatesFolderPath = Paths.get(templatesFolderUrl.toURI()).toFile().getAbsolutePath();
            File templateFolderFile = new File(templatesFolderPath);
            File[] templateFiles = templateFolderFile.listFiles();
            for (File templateFile : templateFiles) {
                String template = readFileAsString(templateFile);
                Document doc = Jsoup.parse(template);
                String templateName = templateFile.getName().replace(".html", "");
                templates.put(templateName, doc);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(TemplatesManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static Document getTemplate(String templateName){
        return templates.get(templateName);
    }

    public static String readFileAsString(File file) throws IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

}
