package com.edmarkou.fifaislife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String PLAYER_LIST = "PLAYER_LIST";
    private static final String MATCH_LIST1 = "MATCH_LIST1";
    private static final String MATCH_LIST2 = "MATCH_LIST2";
    private EditText mNameLabel;
    private Button mAddButton;
    private Button mStartButton;
    private ArrayList<String> mPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameLabel = findViewById(R.id.nameLabel);
        mAddButton = findViewById(R.id.addButton);
        mStartButton = findViewById(R.id.startButton);


        mPlayerList = new ArrayList<>();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player;
                player = mNameLabel.getText().toString();
                if(!player.equals("") && !mPlayerList.contains(player)) {
                    mPlayerList.add(player);

                    Toast.makeText(MainActivity.this,
                            player + " added to the tournament", Toast.LENGTH_SHORT).show();
                }
                    mNameLabel.setText("");
            }
        });

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPlayerList.size() > 1) {
                    ArrayList<String> mMatchList1 = new ArrayList<>();
                    ArrayList<String> mMatchList2 = new ArrayList<>();
                    int counter = 0;
                    int j = 1;
                    do {
                        for (int i = j; i < mPlayerList.size(); i++) {
                            mMatchList1.add(mPlayerList.get(counter));
                            mMatchList2.add(mPlayerList.get(i));
                        }
                        counter++;
                        j++;

                    } while (j != mPlayerList.size());

                    if(mPlayerList.size() != 2) {
                        Intent intent = new Intent(MainActivity.this, TourneyActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(PLAYER_LIST, mPlayerList);
                        bundle.putStringArrayList(MATCH_LIST1, mMatchList1);
                        bundle.putStringArrayList(MATCH_LIST2, mMatchList2);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else{
                        Intent intent = new Intent(MainActivity.this, FinalActivity.class);
                        intent.putStringArrayListExtra(PLAYER_LIST, mPlayerList);
                        startActivity(intent);
                    }
                } else Toast.makeText(MainActivity.this,
                        "There are no matches ready to be played", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
