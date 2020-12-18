package com.brulesoft.alquifor.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brulesoft.alquifor.MainActivity;
import com.brulesoft.alquifor.MisComentariosActivity;
import com.brulesoft.alquifor.R;
import com.brulesoft.alquifor.RegistroActivity;
import com.brulesoft.alquifor.api.MethodComentarios;
import com.brulesoft.alquifor.api.MethodUsuarios;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.data.LoginDataSource;
import com.brulesoft.alquifor.models.Comentario;
import com.brulesoft.alquifor.models.LoginCredenciales;
import com.brulesoft.alquifor.models.RespuestaLogin;
import com.brulesoft.alquifor.models.Usuario;
import com.brulesoft.alquifor.ui.login.LoginViewModel;
import com.brulesoft.alquifor.ui.login.LoginViewModelFactory;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button botonRegistrarse = findViewById(R.id.botonRegistrarse);
        botonRegistrarse.setBackgroundColor(Color.rgb(217, 127, 213));

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MethodUsuarios service = RetrofitClient.getRetrofitInstance().create(MethodUsuarios.class);
                Call<RespuestaLogin> call = service.loginIn(new LoginCredenciales(usernameEditText.getText().toString(),
                                                            passwordEditText.getText().toString()));
                call.enqueue(new Callback<RespuestaLogin>() {

                    @Override
                    public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                        if(response.isSuccessful()){
                            try {
                                loadingProgressBar.setVisibility(View.VISIBLE);
//                                String token = response.body().string();
                                String token = response.body().getToken();
                                SharedPreferences preferences = getSharedPreferences("ALQUIFOR", Context.MODE_PRIVATE);
                                preferences.edit().putString("TOKEN", token).apply();
                                String email = usernameEditText.getText().toString().trim();
                                preferences.edit().putString("EMAIL", email).apply();

                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Algo ha salido mal", Toast.LENGTH_SHORT).show();
                    }

                });





//                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
            }
        });

        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}