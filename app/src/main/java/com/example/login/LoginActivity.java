package com.example.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.connetion.ConnetionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    EditText usuario, clave;
    TextView lblregistrar;
    Button btningresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usuario = findViewById(R.id.txtusuario);
        clave = findViewById(R.id.txtclave);
        lblregistrar = findViewById(R.id.lblregistrar);
        btningresar = findViewById(R.id.btningresar);

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginTask().execute();
            }
        });

        lblregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(getApplicationContext(), RegistrarActivity.class);
                startActivity(menu);
            }
        });
    }

    private class LoginTask extends AsyncTask<Void, Void, Boolean> {
        String mensaje = null;

        @Override
        protected Boolean doInBackground(Void... voids) {
            ConnetionBD instanceConnection = new ConnetionBD();
            Connection con = instanceConnection.connect();

            if (con == null) {
                mensaje = "Verifique su conexión";
                return false;
            }

            try {
                String sql = "SELECT * FROM Usuarios WHERE NombreUsuario = '" + usuario.getText().toString() + "' AND Clave='" + clave.getText().toString() + "'";
                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    mensaje = "Acceso Exitoso";
                    return true;
                } else {
                    mensaje = "Error en el usuario o contraseña";
                    return false;
                }
            } catch (Exception e) {
                Log.e("Error DE CONEXION:", e.getMessage());
                mensaje = "Error de conexión: " + e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            if (success) {
                Intent menu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(menu);
                usuario.setText("");
                clave.setText("");
            } else {
                usuario.setText("");
                clave.setText("");
            }
        }
    }
}
