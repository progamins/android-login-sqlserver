package com.example.login.connetion;

import static java.lang.Class.forName;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnetionBD {
    // Config via BuildConfig (from local.properties) — no hardcoded secrets.
    private String ip = com.example.login.BuildConfig.DB_IP;
    private String usuario = com.example.login.BuildConfig.DB_USER;
    private String password = com.example.login.BuildConfig.DB_PASSWORD;
    private String basedatos = com.example.login.BuildConfig.DB_NAME;

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