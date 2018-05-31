package org.esiea.alahyane_barres.youmovies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.Menu;

import java.text.DateFormat;
import java.util.Date;

import static org.esiea.alahyane_barres.youmovies.R.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        TextView tv_dn = (TextView) findViewById(id.Date_now);
        String now = DateUtils.formatDateTime(getApplicationContext(), (new Date()).getTime(), DateFormat.FULL);
        tv_dn.setText(now);

        Button btn_bm = (Button) findViewById(id.Best_movie);
        btn_bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getString(string.best_movie_text), Toast.LENGTH_LONG).show();
            }
        });

        /*Button btn_hw2 = (Button)findViewById(id.button_text2);
        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Voulez vous vraiment quitter ?")
                        .setTitle("Quitter")
                        .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                closeContextMenu();
                            };
                   );

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        } );*/

        final Button movieButton = (Button) findViewById(id.Research_movie);
        movieButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Movies.class);
                startActivity(intent);
            }
        });

        final Button contactButton = (Button) findViewById(id.Contact);
        contactButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Contact.class);
                startActivity(intent);


            }
        });}

        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.Settings) {
                Toast.makeText(getApplicationContext(), getString(R.string.Do_you_want_to_change_the_settings), Toast.LENGTH_SHORT).show();
            } else if (id == R.id.Help) {
                Toast.makeText(getApplicationContext(), getString(R.string.Do_you_need_help), Toast.LENGTH_SHORT).show();
            }
            return super.onOptionsItemSelected(item);
        }

        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }

/** Bouton fesont appel à GoogleMaps */
        public void GoogleMaps(View v) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Batman")));
        }


    }