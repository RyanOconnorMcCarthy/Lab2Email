package com.example.lab2email;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.security.SecureRandom;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText password;
    EditText email;
    EditText pnumber;
    EditText userCode;


    String generatedCode;

    Button getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.editTextText);
        password = findViewById(R.id.editTextTextPassword);
        email = findViewById(R.id.editTextTextEmailAddress2);
        pnumber = findViewById(R.id.editTextPhone);
        userCode = findViewById(R.id.editTextCode);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);




            return insets;

        });
    }

    public void sendCode(View view){
        SecureRandom random = new SecureRandom();
        generatedCode = String.format(
                java.util.Locale.US,
                "%06d",
                random.nextInt(1000000)
        );

        String user = name.getText().toString();
        String emailAddress = email.getText().toString();

        String subject = "Account Validation Code";
        String body = "Hello " + user+ "Your account validation code is: "+generatedCode+ "Enter this code in the app to validate your account.";

        Uri Email = Uri.parse(
                "mailto:" + Uri.encode(emailAddress)
                        + "?subject=" + Uri.encode(subject)
                        + "&body=" + Uri.encode(body)
        );

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Email);


        startActivity(Intent.createChooser(
            intent, "Send validation email"));
    }

    public void buttonPress(View view) {
        String user = name.getText().toString().trim();
        String code = userCode.getText().toString().trim();
        Intent intent = new Intent(
                MainActivity.this,
                Message.class
        );

        if(code.equals(generatedCode)){
            intent.putExtra("name", user);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Incorrect Code", Toast.LENGTH_SHORT).show();
        }




    }
}