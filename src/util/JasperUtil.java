package util;

import database.DatabaseConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

public class JasperUtil {
    /*public static void loadReport(String report){
        try {
            JasperDesign design = JRXmlLoader.load(JasperUtil.class.getResourceAsStream("/report/"+report+".jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DatabaseConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    public static void emptyReport(String report) {
        generateReport(report,null, new JREmptyDataSource(1));
    }

    public static void sqlReport(String report) {
        try {
            generateReport(report, null, DatabaseConnection.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void beanArrayReport(String report, LinkedList<Object> list) {
        generateReport(report, null, new JRBeanArrayDataSource(list.toArray()));
    }

    public static void beanArrayReport(String report, Map<String,Object> parameters, LinkedList<Object> list) {
        generateReport(report, parameters, new JRBeanArrayDataSource(list.toArray()));
    }

    public static void sqlReport(String report, Map<String,Object> parameters) {
        try {
            generateReport(report, parameters, DatabaseConnection.getInstance().getConnection());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void generateReport(String report, Map<String, Object> parameters, JRDataSource dataSource){
        try {
            JasperDesign design = JRXmlLoader.load(JasperUtil.class.getResourceAsStream("/report/"+report+".jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void generateReport(String report, Map<String, Object> parameters, Connection connection){
        try {
            JasperDesign design = JRXmlLoader.load(JasperUtil.class.getResourceAsStream("/report/"+report+".jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }


}
