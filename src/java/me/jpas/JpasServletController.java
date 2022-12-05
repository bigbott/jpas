/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jpas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.jpas.utils.CorsUtil;
import org.jsoup.nodes.Document;

/**
 *
 * @author Owner
 */
public class JpasServletController extends HttpServlet{
    
    public void init(ServletConfig config) throws ServletException {
      
            super.init(config);            
            TemplatesManager.loadTemplates();
           
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
               
        ServletContext context = getServletContext(); 
        Map<String, String[]> paramsMap = null;
        try {
             paramsMap = ParamsManager.getParams(request);
        } catch (Exception e) {
            sendErrorResponse(request, response, paramsMap, context, e.getMessage());
        }
        
        String templateName = paramsMap.get("page") == null ? paramsMap.get("param0")[0] : paramsMap.get("page")[0];
        
        JpasView view = getView(context, templateName);
        JpasModel model = getModel(context, templateName);
        
        Document doc = TemplatesManager.getTemplate(templateName);
  
        String html = "";
        try {
            html = view.createHtml(request, response, paramsMap, doc, model);
        } catch (Exception ex) {
            Logger.getLogger(JpasServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sendResponse(response, html);
        
    }
    
    private void sendResponse(HttpServletResponse response, String html) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        CorsUtil.addCorsHeaders(response);
        PrintWriter writer = response.getWriter();
        writer.print(html);
        writer.close();
    }

    private JpasView getView(ServletContext context, String templateName) {
        
        String className = getClassName(templateName);
        
        String packageName = getInitParameter("viewPackage");
        
        String viewClassFullName = packageName + "." + className + "View";
        JpasView view = (JpasView) context.getAttribute(viewClassFullName);
        if (view == null) {
            try {
                Class viewClass = Class.forName(viewClassFullName);
                view = (JpasView) viewClass.newInstance();
                context.setAttribute(viewClassFullName, view);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return view;
    }
    
    private JpasModel getModel(ServletContext context,String templateName) {
        
        String className = getClassName(templateName);
        
        String packageName = getInitParameter("modelPackage");
        
        String modelClassFullName = packageName + "." + className + "Model";
        JpasModel model = (JpasModel) context.getAttribute(modelClassFullName);
        if (model == null) {
            try {
                Class modelClass = Class.forName(modelClassFullName);
                model = (JpasModel) modelClass.newInstance();
                context.setAttribute(modelClassFullName, model);
            } catch (Exception ex) {
                ex.printStackTrace();
                
                return null;
            }
        }
        return model;
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, 
                                                   Map<String, String[]> paramsMap, ServletContext context, String message) throws IOException {
        String templateName = "error";
        
        JpasView view = getView(context, templateName);
        JpasModel model = null;
        
        Document doc = TemplatesManager.getTemplate(templateName);
         
        
        String html = "";
        try {
            html = view.createHtml(request, response, paramsMap, doc, model);
        } catch (Exception ex) {
            Logger.getLogger(JpasServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sendResponse(response, html);
    }

    private String getClassName(String templateName) {
        String className = "";
        if (templateName.contains("-")) {
            String[] pathData = templateName.split("-");

            for (String word : pathData) {
                className += Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            }
            return className;
        }

        className = Character.toUpperCase(templateName.charAt(0)) + templateName.substring(1);
        return className;
    }
}
