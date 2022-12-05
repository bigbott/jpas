/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.model;

import demo.data.Smartphone;
import me.jpas.JpasModel;

/**
 *
 * @author Owner
 */
public class SmartphoneModel implements JpasModel{

    public Smartphone getSmartphoneById(String id) { 
        Smartphone phone = new Smartphone();
        phone.title = "ZTE nubia Z40S Pro";
        phone.releaseDate = "2022, July 26";
        phone.memory = "18GB 1024GB";
        phone.processor = "Qualcomm SM8475 Snapdragon 8 Plus Gen 1";
        phone.display = "Amoled 6.67 inch, 144 Hz";
        phone.battery = "Li-Po 5000 Mah, fast charging 80 W";
        phone.camera = "64Mp";
        phone.os = "Android 12, MyOS 12";
        
        return phone;

    }

}
