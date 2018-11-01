package com.example.moustapha.gestiondescommandes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moustapha.gestiondescommandes.Common.Common;
import com.example.moustapha.gestiondescommandes.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {


    EditText edtPhone,edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


       edtPassword = (MaterialEditText)findViewById(R.id.edtpassword);
       edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        //initialisation de firebase
        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user= database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog= new ProgressDialog(SignIn.this);
                mDialog.setMessage("Pleaz Waiting...");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //verifier si un l'user n'existe pas dans la base données


                        if(dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {

                            //get user i,formation
                            mDialog.dismiss();

                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                          //  user.setPhone(edtPhone.getText().toString());

                            if (user.getPassword().equals(edtPassword.getText().toString()))
                            {
                                Toast.makeText(SignIn.this, "Connexion Reussie !!", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(SignIn.this,Accueil.class);
                                Common.currentUser=user;
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Echec de la Connexion !!!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "user n'existe pas dans la base ", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
