package com.example.login.connetion;

import static java.lang.Class.forName;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnetionBD {
    private String ip = "[REDACTADO — credencial filtrada y rotada]";
    private String usuario = "[REDACTADO]";
    private String password = "[REDACTADO]";
    private String basedatos = "Login";

    @SuppressLint("NewApi")
    public Connection connect() {
        Connection connection = null;
        String connectionURL = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + this.ip + "/" + this.basedatos + ";user=" + this.usuario + ";password=" + this.password + ";";
            connection = DriverManager.getConnection(connectionURL);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error de conexión SQL:", e.getMessage());
        }
    return connection;
    }
}