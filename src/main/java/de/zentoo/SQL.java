package de.zentoo;

import de.zentoo.robocupanalytics.Context;
import de.zentoo.robocupanalytics.plugin.Pluginload;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by test on 14.02.15.
 */
public class SQL implements Pluginload {

    private static final Logger LOGGER = Logger.getLogger(SQL.class.getName());

    private Context context;

    public static void main(String[] args) {

    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public Parent getTabRoot() {
        Parent plugin = null;
        try {
            ResourceBundle resourceBundle = (new ResourceBundle() {
                Hashtable<String, Object> ressource = new Hashtable<String, Object>();

                public ResourceBundle setContext(Context context){
                    ressource.put("context", context);
                    return this;
                }

                @Override
                protected Object handleGetObject(String key) {
                    return ressource.get(key);
                }

                @Override
                public Enumeration<String> getKeys() {
                    return ressource.keys();
                }
            }).setContext(this.context);
            plugin = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/sql.fxml"), resourceBundle);
        } catch (IOException ex) {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "cant find controller in the classpath");
        }
        return plugin;
    }

    @Override
    public String getTabName() {
        return "SQL";
    }
}
