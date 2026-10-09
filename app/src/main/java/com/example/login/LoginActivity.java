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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginActivity extends AppCompatActivity {
    EditText usuario, clave;
    TextView lblregistrar;
    Button btningresar;
    private boolean loginEnProgreso = false;

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
                // Evita doble click / múltiples intentos simultáneos
                if (loginEnProgreso) return;
                if (!validarCampos()) return;
                loginEnProgreso = true;
                btningresar.setEnabled(false);
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

    /**
     * Validación de entrada ANTES de tocar la base de datos:
     * - Campos obligatorios no vacíos
     * - Longitud máxima para acotar el input
     * - Solo caracteres seguros (letras, números y . _ -) en el usuario
     * La contraseña solo se acota en longitud; su contenido se envía parametrizado.
     */
    private boolean validarCampos() {
        String u = usuario.getText().toString().trim();
        String p = clave.getText().toString();

        if (u.isEmpty() || p.isEmpty()) {
            Toast.makeText(this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (u.length() > 50 || p.length() > 128) {
            Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!u.matches("[A-Za-z0-9._-]+")) {
            Toast.makeText(this, "Usuario inválido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                // Consulta parametrizada (PreparedStatement) — previene inyección SQL.
                String sql = "SELECT IdUsuario, NombreUsuario FROM Usuarios WHERE NombreUsuario = ? AND Clave = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, usuario.getText().toString().trim());
                ps.setString(2, clave.getText().toString());
                ResultSet rs = ps.executeQuery();

                boolean existe = rs.next();
                rs.close();
                ps.close();

                if (existe) {
                    mensaje = "Acceso Exitoso";
                    return true;
                } else {
                    // Mensaje genérico: no revela si el usuario existe
                    mensaje = "Error en el usuario o contraseña";
                    return false;
                }
            } catch (Exception e) {
                // Se registra el detalle solo en Log del servidor de desarrollo;
                // al usuario se le muestra un mensaje genérico (sin datos internos).
                Log.e("LoginError", "Fallo en la autenticación");
                mensaje = "No se pudo iniciar sesión. Inténtelo más tarde.";
                return false;
            } finally {
                try {
                    if (con != null) con.close();
                } catch (Exception ignored) {
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            loginEnProgreso = false;
            btningresar.setEnabled(true);
            Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            if (success) {
                Intent menu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(menu);
            }
            usuario.setText("");
            clave.setText("");
        }
    }
}
