package com.example.walletmanagementws.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class CurrentTimestampUtil {

    public static XMLGregorianCalendar getCurrentTimestamp() {
        try {
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            
            XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar();
            
            return now;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
}