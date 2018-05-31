package org.esiea.alahyane_barres.youmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Contact extends AppCompatActivity {


    String phone_number = "0609157467";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);






        Button btn_call = (Button)findViewById(R.id.Call);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(Intent.ACTION_CALL_BUTTON, Uri.parse("tel:" + phone_number)));
            }
        });
    }
}
