package com.example.moustapha.gestiondescommandes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



  private   Button btnSignIn, btnSignUp;
  private   TextView txtSlogan;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp= findViewById(R.id.btnSignUp);




        btnSignIn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this,SignIn.class);
                startActivity(i);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });



    }
}
