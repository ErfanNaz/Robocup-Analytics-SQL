package de.zentoo.uicontroll;

import de.zentoo.robocupanalytics.Context;
import de.zentoo.robocupanalytics.entity.Show;
import de.zentoo.robocupanalytics.event.action.Action;
import de.zentoo.robocupanalytics.event.action.ControlAction;
import de.zentoo.robocupanalytics.event.handler.ControlListenerHandler;
import de.zentoo.robocupanalytics.event.listener.ControlActionListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Erfan on 17.02.15.
 */
public class Sqlcontroller implements Initializable, ControlActionListener{

    private Context context;

    private ControlListenerHandler handler;

    @FXML
    private Label label_sqlOut;

    @FXML
    private TextArea textArea_sqlInput;

    private void sqlExample(Connection con, String sqlquery) throws SQLException {
        Statement stmt = null;
        String query = sqlquery;
        try {
            String sqlResult = "";
            stmt = con.createStatement();
            if(query.toLowerCase().startsWith("update")){
                stmt.executeUpdate(query);
                return;
            }
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                String row = "";
                for(int i = 1; i <= columnCount; i++){
                    String name = rsmd.getColumnName(i);
                    String value = rs.getString(name);
                    row += name + " " + value + "\t";
                }
                sqlResult += row + "\n";
            }
            label_sqlOut.setText(sqlResult);
        } catch (SQLException ex ) {
            label_sqlOut.setText(ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    @FXML
    private void action_sql(ActionEvent event){
        label_sqlOut.setText(textArea_sqlInput.getText());
        Connection connection = context.getOrm().getConnection();
        String sqlInput = textArea_sqlInput.getText();
        if(sqlInput == "") return;
        try {
            sqlExample(connection, sqlInput);
        } catch (SQLException ex) {
            label_sqlOut.setText(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void action_reload(ActionEvent event){
        List<Show> alleShows = null;
        try {
            alleShows = context.getOrm().getShowDao().queryForAll();
            context.getPlayService().setShowList(alleShows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        context = (Context) resources.getObject("context");
        handler = context.getListenerHandler();
        handler.addListener(this);
    }

    @Override
    public void onAction(Action action) {

    }

    @Override
    public void onAction(ControlAction action) {

    }
}
