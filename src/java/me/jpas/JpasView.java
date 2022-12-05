/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jpas;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.nodes.Document;

/**
 *
 * @author Owner
 */
public interface JpasView {
    String createHtml(HttpServletRequest request, HttpServletResponse response, Map<String, String[]> params, Document doc, JpasModel model);
}
