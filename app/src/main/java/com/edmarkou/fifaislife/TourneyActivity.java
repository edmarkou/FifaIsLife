package com.edmarkou.fifaislife;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TourneyActivity extends ListActivity {

    private Button mNextMatch;
    private TextView mPlayer1;
    private TextView mPlayer2;
    private static final String PLAYER_LIST = "PLAYER_LIST";
    private static final String MATCH_LIST1 = "MATCH_LIST1";
    private static final String MATCH_LIST2 = "MATCH_LIST2";
    private ArrayList<String> mMatchList1;
    private ArrayList<String> mMatchList2;
    private ListAdapter mListAdapter;
    private ArrayList<Pair<String, String>> mMatchList;
    private Player[] mPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourney);

        mNextMatch = findViewById(R.id.nextMatchButton);
        mPlayer1 = findViewById(R.id.player1TextView);
        mPlayer2 = findViewById(R.id.player2TextView);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        ArrayList<String> mPlayerList = bundle.getStringArrayList(PLAYER_LIST);
        mMatchList1 = bundle.getStringArrayList(MATCH_LIST1);
        mMatchList2 = bundle.getStringArrayList(MATCH_LIST2);

        mPlayers = new Player[mPlayerList.size()];

        for(int i = 0; i < mPlayers.length; i++){
            mPlayers[i] = new Player(mPlayerList.get(i), 0, 0);
        }
        mMatchList = new ArrayList<>();
        for(int i = 0; i < mMatchList1.size(); i++){
            mMatchList.add(new Pair<>(mMatchList1.get(i), mMatchList2.get(i)));
        }

        mListAdapter = new ListAdapter(this, mPlayers);
        setListAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();

        mNextMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mMatchList.isEmpty()) {
                    Random random = new Random();
                    int i = random.nextInt(mMatchList.size());
                    mPlayer1.setText(mMatchList.get(i).first);
                    mPlayer2.setText(mMatchList.get(i).second);
                    mMatchList.remove(i);
                    if(mMatchList.isEmpty())
                        mNextMatch.setText("Next round");
                }else {
                    int[] winCount = new int[mPlayers.length];
                    for(int i = 0; i < mPlayers.length; i++){
                         winCount[i] = mPlayers[i].getWins();
                    }
                    Arrays.sort(winCount);
                    ArrayList<String> mQualifiedPlayers = new ArrayList<>();
                    int j = winCount.length - 1;
                    SortWinners(winCount, j, mPlayers, mQualifiedPlayers);
                    if(mQualifiedPlayers.size() < 2)
                        SortWinners(winCount, j-1, mPlayers, mQualifiedPlayers);


                    // jeigu yra daugiau nei 2 zaidejai kurie kvalifikavo i kita raunda
                    if(mQualifiedPlayers.size() > 2){

                        ArrayList<String> mQualifiedMatchList1 = new ArrayList<>();
                        ArrayList<String> mQualifiedMatchList2 = new ArrayList<>();

                        int counter = 0;
                        j = 1;
                        do {
                            for (int i = j; i < mQualifiedPlayers.size(); i++) {
                                mQualifiedMatchList1.add(mQualifiedPlayers.get(counter));
                                mQualifiedMatchList2.add(mQualifiedPlayers.get(i));
                            }
                            counter++;
                            j++;

                        } while (j != mQualifiedPlayers.size());

                        Intent intent1 = new Intent(TourneyActivity.this, TourneyActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putStringArrayList(PLAYER_LIST, mQualifiedPlayers);
                        bundle1.putStringArrayList(MATCH_LIST1, mQualifiedMatchList1);
                        bundle1.putStringArrayList(MATCH_LIST2, mQualifiedMatchList2);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                    } else {
                        Intent intent1 = new Intent(TourneyActivity.this, FinalActivity.class);
                        intent1.putStringArrayListExtra(PLAYER_LIST, mQualifiedPlayers);
                        startActivity(intent1);
                    }
                }
            }
        });

        mPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < mPlayers.length; i++){
                    if(mPlayers[i].getPlayerName().equals((mPlayer1.getText().toString()))){
                        mPlayers[i].setWins(mPlayers[i].getWins() + 1);
                    }
                }

                for(int i = 0; i < mPlayers.length; i++){
                    if(mPlayers[i].getPlayerName().equals(mPlayer2.getText().toString())){
                        mPlayers[i].setLoses(mPlayers[i].getLoses() + 1);
                    }
                }
                mPlayer1.setText("-");
                mPlayer2.setText("-");
                setListAdapter(new ListAdapter(TourneyActivity.this, SortPlayers(mPlayers)));
                mListAdapter.notifyDataSetChanged();

            }
        });

        mPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < mPlayers.length; i++){
                    if(mPlayers[i].getPlayerName().equals(mPlayer2.getText().toString())){
                        mPlayers[i].setWins(mPlayers[i].getWins() + 1);
                    }
                }

                for(int i = 0; i < mPlayers.length; i++){
                    if(mPlayers[i].getPlayerName().equals(mPlayer1.getText().toString())){
                        mPlayers[i].setLoses(mPlayers[i].getLoses() + 1);
                    }
                }
                mPlayer1.setText("-");
                mPlayer2.setText("-");
                setListAdapter(new ListAdapter(TourneyActivity.this, SortPlayers(mPlayers)));
                mListAdapter.notifyDataSetChanged();
            }
        });
    }

    public void SortWinners(int[] winCount, int j, Player[] mPlayers, ArrayList<String> mQualifiedPlayers) {

        for (int i = 0; i < mPlayers.length; i++) {
            if (winCount[j] == mPlayers[i].getWins()) {
                mQualifiedPlayers.add(mPlayers[i].getPlayerName());
            }
        }
    }

    public Player[] SortPlayers(Player[] mPlayers){
        Player player1;
        Player player2;
        boolean unsorted = true;
        do{
            unsorted = false;
            for(int i = 0; i < mPlayers.length - 1; i++){
                if(mPlayers[i].getWins() < mPlayers[i+1].getWins()){
                    player1 = mPlayers[i];
                    player2 = mPlayers[i+1];
                    mPlayers[i] = player2;
                    mPlayers[i+1] = player1;
                    unsorted = true;
                } else if(mPlayers[i].getWins() == mPlayers[i+1].getWins()){
                    if(mPlayers[i].getLoses() > mPlayers[i+1].getLoses()){
                        player1 = mPlayers[i];
                        player2 = mPlayers[i+1];
                        mPlayers[i] = player2;
                        mPlayers[i+1] = player1;
                        unsorted = true;
                    }
                }
            }
        }while(unsorted);

        return mPlayers;
    }
}
