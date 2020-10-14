package com.example.fingerspeedgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextview;
    private TextView aThousandTextView;
    private Button tapTapButton;


    private CountDownTimer CountDownTimer;

    private long initaialContDownINMillis = 60000;
    private int timerInterval = 1000;
    private int remainingTime =60;
    private  int aThousand = 10;

    private final String REMAINING_TIME_KEY = "remaining time key";
    private final String A_THOUSAND_KEY ="a thousand key";


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(REMAINING_TIME_KEY,remainingTime);
        outState.putInt(A_THOUSAND_KEY,aThousand);
        CountDownTimer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timerTextview = findViewById(R.id.txtTimer);
        aThousandTextView = findViewById(R.id.txtAThousand);
        tapTapButton = findViewById(R.id.btnTap);
        aThousandTextView.setText(aThousand + "");

        if (savedInstanceState != null) {
            remainingTime = savedInstanceState.getInt(REMAINING_TIME_KEY);
            aThousand = savedInstanceState.getInt(A_THOUSAND_KEY);

            restoreTheGame();
        }

        tapTapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aThousand--;
                aThousandTextView.setText(aThousand + "");

                if (remainingTime > 0 && aThousand <= 0) {
                    Toast.makeText(MainActivity.this, "Coingratulation You Have win The Game", Toast.LENGTH_LONG).show();

                    showAlert("Congress You Have win", "Would You Like To PLay The Game Again");


                }
            }
        });

        if (savedInstanceState == null) {
            CountDownTimer = new CountDownTimer(initaialContDownINMillis, timerInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTime = (int) millisUntilFinished / 1000;
                    timerTextview.setText(remainingTime + "");

                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, "Coundown Finish", Toast.LENGTH_LONG);


                    showAlert("You Have Lose The Game", "Would You Like To PLay The Game Again");

                }
            };

            CountDownTimer.start();
        }
    }

    private void restoreTheGame() {
        int restoredRemainingTime = remainingTime;
        int restoredAthousand = aThousand;

        timerTextview.setText(restoredRemainingTime+"");
        aThousandTextView.setText(restoredAthousand+"");

        CountDownTimer = new CountDownTimer(remainingTime*1000,timerInterval*1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int) millisUntilFinished /1000;
                timerTextview.setText(remainingTime+"");
                if (remainingTime > 0 && aThousand <= 0) {
                    Toast.makeText(MainActivity.this, "Coingratulation You Have win The Game", Toast.LENGTH_LONG).show();

                    showAlert("Congress You Have win", "Would You Like To PLay The Game Again");


                }
            }

            @Override
            public void onFinish() {
                showAlert("You Have Lose The Game", "Would You Like To PLay The Game Again");
            }
        };
        CountDownTimer.start();
    }

    private void showAlert(String title, String mess)
    {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(mess)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetTheGame();
                    }
                })
                .show();
            alertDialog.setCancelable(false);


    }
    private void resetTheGame()
    {
        if(CountDownTimer !=null) {
            CountDownTimer.cancel();
            CountDownTimer = null;
        }
        aThousand =1000;
        aThousandTextView.setText(aThousand+"");

        timerTextview.setText(remainingTime+"");

        CountDownTimer = new CountDownTimer(initaialContDownINMillis,timerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int) millisUntilFinished/1000;
                timerTextview.setText(remainingTime+"");
                if (remainingTime > 0 && aThousand <= 0) {
                    Toast.makeText(MainActivity.this, "Coingratulation You Have win The Game", Toast.LENGTH_LONG).show();

                    showAlert("Congress You Have win", "Would You Like To PLay The Game Again");


                }
            }

            @Override
            public void onFinish() {
                showAlert("You Have Lose The Game", "Would You Like To PLay The Game Again");
            }
        };
        CountDownTimer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() ==R.id.info_item)
        {
            Toast.makeText(MainActivity.this,"The Version Of The Game Is "+BuildConfig.VERSION_NAME, Toast.LENGTH_LONG).show();
        }
        return  true;
    }
}