package org.viesure.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtil {

    /**
     * Loads a property file from the resources package with the given name
     * @param device the name of the property file
     * @return the loaded properties for the given device
     */
    public static Properties loadDeviceProperty(String device) {
        FileInputStream inputStream;
        Properties properties = new Properties();

        String propertiesFilePath = String.format(System.getProperty("user.dir")+"/src/main/resources/properties/%s.properties", device);

        try {
            inputStream = new FileInputStream(propertiesFilePath);
            properties.load(inputStream);

        } catch (Exception e) {
            String errorMessage = "Property file not found or invalid for path: " + propertiesFilePath + "\n" + e.getLocalizedMessage();

            AllureLogger.saveTextLog(errorMessage);
            System.out.println(errorMessage);
        }
        return properties;
    }

    /**
     * Moves the current allure report's history folder
     * Into the result'S history folder
     * So trends can be viewed
     */
    public static void moveAllureReportHistory() {
        File sourceDirectory = new File(System.getProperty("user.dir") + "/allure-report/history");
        File destinationDirectory = new File(System.getProperty("user.dir") + "/allure-results/history");

//        try {
//            FileUtils.deleteDirectory(destinationDirectory);
//        } catch (IOException e) {
//            System.out.println("Deleting history directory failed");
//            e.printStackTrace();
//        }

        try {
            FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
        } catch (IOException e) {
            System.out.println("Copying the history file failed");
            e.printStackTrace();
        }
    }
}
