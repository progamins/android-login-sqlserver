package com.example.login.connetion;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnetionBD {
    // Config via BuildConfig (from local.properties) — no hardcoded secrets.
    private String ip = com.example.login.BuildConfig.DB_IP;
    private String usuario = com.example.login.BuildConfig.DB_USER;
    private String password = com.example.login.BuildConfig.DB_PASSWORD;
    private String basedatos = com.example.login.BuildConfig.DB_NAME;

    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            // ssl=require cifra el canal (TLS) para que usuario/contraseña
            // y datos no viajen en texto plano por la red.
            String connectionURL = "jdbc:jtds:sqlserver://" + this.ip
                    + "/" + this.basedatos
                    + ";ssl=require";
            Properties props = new Properties();
            props.setProperty("user", this.usuario);
            props.setProperty("password", this.password);
            props.setProperty("loginTimeout", "10");
            DriverManager.setLoginTimeout(10);
            connection = DriverManager.getConnection(connectionURL, props);
        } catch (Exception e) {
            // Log genérico: nunca registrar credenciales ni mensajes crudos del driver
            Log.e("ConnetionBD", "No se pudo establecer la conexión con la base de datos");
        }
        return connection;
    }
}
