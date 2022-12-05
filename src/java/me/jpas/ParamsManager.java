/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jpas;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Owner
 */
public class ParamsManager {

    public static Map<String, String[]> getParams(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString != null && !queryString.trim().isEmpty()) {

            String[] pageParams = request.getParameterMap().get("page");
            if (pageParams.length < 1 || pageParams[0] == null || pageParams[0].trim().isEmpty()) {
                throw new IllegalArgumentException("Error: there is no <page> param sent with request");
            }
            return request.getParameterMap();
        }

        Map<String, String[]> paramsMap = new LinkedHashMap<>();

        String pathInfo = request.getPathInfo();
        String[] params = request.getPathInfo().substring(1).split("/");
        if (pathInfo.contains("/page/")) {
            if (params.length % 2 != 0) {
                throw new IllegalArgumentException("Error: Params number in URL should be even.");
            }
            for (int i = 0; i < params.length; i = i + 2) {
                String paramName = params[i];
                String[] paramValues = params[i + 1].split(",");
                paramsMap.put(paramName, paramValues);
            }
        } else {
            for (int i = 0; i < params.length; i++) {
                String paramName = "param" + i;
                String[] paramValues = params[i].split(",");
                paramsMap.put(paramName, paramValues);
            }
        }

        return paramsMap;
    }
}
