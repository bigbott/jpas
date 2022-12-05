/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.jpas.JpasModel;
import me.jpas.JpasView;
import org.jsoup.nodes.Document;

/**
 *
 * @author Owner
 */
public class ErrorView implements JpasView{

    @Override
    public String createHtml(HttpServletRequest request, HttpServletResponse response, Map<String, String[]> params, Document doc, JpasModel model) {
    
    
        return doc.toString();
    }
    
}
