/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.view;

import demo.data.Smartphone;
import demo.model.SmartphoneModel;
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
public class SmartphoneView implements JpasView {
    @Override
    public String createHtml(HttpServletRequest request, HttpServletResponse response, Map<String, String[]> params, Document doc, JpasModel model) {

        String smartphoneId = null;
        if (params.containsKey("page")){ //url contains parameters names - Link 1 and Link 2
            smartphoneId = params.get("id")[0];
        } else {                        //url does not contains parameter names - Link 3
            smartphoneId = params.get("param1")[0];
        }
        
        Smartphone phone = ((SmartphoneModel) model).getSmartphoneById(smartphoneId);

        doc.select("head title").first().text(phone.title);

        doc.select("#title h2").first().text(phone.title);
        doc.select("#releaseDate h2").first().text(phone.releaseDate);
        doc.select("#memory h2").first().text(phone.memory);

        doc.select("#processor span").first().text(phone.processor);
        doc.select("#display span").first().text(phone.display);
        doc.select("#battery span").first().text(phone.battery);
        doc.select("#camera span").first().text(phone.camera);
        doc.select("#os span").first().text(phone.os);

        return doc.toString();
    }
}
