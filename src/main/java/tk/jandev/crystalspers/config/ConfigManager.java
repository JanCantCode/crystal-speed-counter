package tk.jandev.crystalspers.config;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigManager {

    static int counterColor = 15895042; // don't tell me there's a simpler way for white colors :)
    static int xPos;
    static int yPos;

    static String pathToConfig = FabricLoader.getInstance().getConfigDir().toString()+"crystalcounter.properties";

    public static void setCounterColor(int color) {
        counterColor = color;
    }

    public static int getCounterColor() {
        return counterColor;
    }

    public static int getX() {return xPos;}
    public static void setX(int newX) {xPos = newX;}

    public static int getY() {return yPos;}
    public static void setY(int newY) {yPos = newY;}


    public static void safe() throws IOException {
        System.out.println("safing??");

        if (!fileExistsOrInvalid()) {
            Properties newProperties = dataToProperty();
            System.out.println("[Crystal Counter] config file didnt exist.. creating one with "+newProperties.getProperty("xpos"+" "+newProperties.getProperty("ypos")+" "+newProperties.getProperty("color")));
            createFileWithProperties(newProperties);
        }

        Properties props = dataToProperty();
        createFileWithProperties(props);
    }

    public static void load() throws IOException {
        System.out.println("loading?");
        if (!fileExistsOrInvalid()) { // Incase the config file doesnt exist or is invalid, we overwrite it with another file
            Properties newProperties = dataToProperty();
            System.out.println("[Crystal Counter] config file didnt exist whilst loading, creating new one: "+newProperties.getProperty("xpos"+" "+newProperties.getProperty("ypos")+" "+newProperties.getProperty("color")));
            createFileWithProperties(newProperties);
        }

        Properties props = new Properties();

        File file = new File(pathToConfig);

        props.load(new FileInputStream(file)); // We load the properties file into the props variable

        loadFromProperty(props);
    }


    public static Properties dataToProperty() {
        Properties props = new Properties();

        props.setProperty("xpos", String.valueOf(xPos));
        props.setProperty("ypos", String.valueOf(yPos));
        props.setProperty("color", String.valueOf(counterColor));

        return props;
    }

    public static void loadFromProperty(Properties props) {
        xPos = Integer.parseInt(props.getProperty("xpos"));
        yPos = Integer.parseInt(props.getProperty("ypos"));
        counterColor = Integer.parseInt(props.getProperty("color"));
    }

    public static void createFileWithProperties(Properties props) throws IOException {
        File configFile = new File(pathToConfig);
        if (!configFile.exists()) {
            configFile.createNewFile();
        }

        props.store(new FileWriter(pathToConfig), "");
    }



    public static boolean fileExistsOrInvalid() throws IOException {
        File file = new File(pathToConfig);

        return file.exists();
    }
}
