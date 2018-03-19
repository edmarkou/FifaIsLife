package com.edmarkou.fifaislife;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FinalActivity extends AppCompatActivity {

    private static final String PLAYER_LIST = "PLAYER_LIST";
    private TextView mFinalist1;
    private TextView mFinalist2;
    private TextView mWinnerTextView;
    private TextView mWinner;
    private Button mRestart;
    private ConstraintLayout layout;
    private Context context = FinalActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Intent intent = this.getIntent();
        ArrayList<String> mFinalists = intent.getStringArrayListExtra(PLAYER_LIST);

        mFinalist1 = findViewById(R.id.Finalist1);
        mFinalist2 = findViewById(R.id.Finalist2);
        mWinnerTextView = findViewById(R.id.vsTextView);
        mWinner = findViewById(R.id.WinnerTextView);
        mRestart = findViewById(R.id.Restart);
        layout = findViewById(R.id.background);

        mFinalist1.setText(mFinalists.get(0));
        mFinalist2.setText(mFinalists.get(1));
        mWinner.setVisibility(View.GONE);
        mRestart.setVisibility(View.GONE);

        mFinalist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFinalist1.setVisibility(View.INVISIBLE);
                mFinalist2.setVisibility(View.GONE);
                mWinner.setVisibility(View.VISIBLE);
                mWinnerTextView.setVisibility(View.GONE);
                mWinner.setText(mFinalist1.getText().toString() + " won!");
                mRestart.setVisibility(View.VISIBLE);
                layout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.fifacup_background));
            }
        });

        mFinalist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFinalist1.setVisibility(View.GONE);
                mFinalist2.setVisibility(View.GONE);
                mWinnerTextView.setVisibility(View.GONE);
                mWinner.setVisibility(View.VISIBLE);
                mWinner.setText(mFinalist2.getText().toString() + " won!");
                mRestart.setVisibility(View.VISIBLE);
                layout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.fifacup_background));
            }
        });

        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FinalActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

}
