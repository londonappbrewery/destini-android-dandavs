package com.londonappbrewery.destini;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // TODO: Steps 4 & 8 - Declare member variables here:
    private Button mTopButton;
    private Button mBottomButton;
    private TextView mTextView;

    private int mStoryIndex;
    private int mStory;
    private int mTopButtonIndex;
    private int mBottomButtonIndex;

    private StoryProgress[] mStoryBank = new StoryProgress[] {
            new StoryProgress(R.string.T1_Story, 1),
            new StoryProgress(R.string.T1_Ans1, 2),
            new StoryProgress(R.string.T1_Ans2, 3),
            new StoryProgress(R.string.T2_Story, 4),
            new StoryProgress(R.string.T2_Ans1, 5),
            new StoryProgress(R.string.T2_Ans2, 6),
            new StoryProgress(R.string.T3_Story, 7),
            new StoryProgress(R.string.T3_Ans1, 8),
            new StoryProgress(R.string.T3_Ans2, 9),
            new StoryProgress(R.string.T4_End, 10),
            new StoryProgress(R.string.T5_End, 11),
            new StoryProgress(R.string.T6_End, 12)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mStoryIndex = savedInstanceState.getInt("StoryIndex");
        }else
        {
            mStoryIndex = 0;
            mTopButtonIndex = 1;
            mBottomButtonIndex = 2;
        }

        // TODO: Step 5 - Wire up the 3 views from the layout to the member variables:
        mTopButton = (Button) findViewById(R.id.buttonTop);
        mBottomButton = (Button) findViewById(R.id.buttonBottom);
        mTextView = (TextView) findViewById(R.id.storyTextView);

        mStory = mStoryBank[mStoryIndex].getStoryID();
        mTextView.setText(mStory);

        // TODO: Steps 6, 7, & 9 - Set a listener on the top button:

        mTopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkStoryPath(1);
                updateStory();
            }
        });

        // TODO: Steps 6, 7, & 9 - Set a listener on the bottom button:


        mBottomButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkStoryPath(2);
                updateStory();
            }
        });
    }

    private void checkStoryPath(int userSelection){
        int ChosenAnswer = mStoryBank[mStoryIndex].isAnswer();

        //Log.d("destini", "User Selection = " + userSelection);

        if (userSelection == 1)
        {
            //Log.d("destini", "Story index = " + mStoryIndex);
            if (mStoryIndex == 0 || mStoryIndex == 3)
            {
                mStoryIndex = 5;
                mTopButtonIndex = 7;
                mBottomButtonIndex = 8;
            }
            else if (mStoryIndex == 6)
            {
                    mStoryIndex = 10;
                    End_Of_Story();
            }
        }
        else
        {
            Log.d("destini", "Story index = " + mStoryIndex);
            if (mStoryIndex == 0)
            {
                mStoryIndex = 2;
                mTopButtonIndex = 4;
                mBottomButtonIndex = 5;
            }else if (mStoryIndex == 3){
                mStoryIndex = 8;
                End_Of_Story();
            }else if (mStoryIndex == 6)
            {
                mStoryIndex = 9;
                End_Of_Story();
            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt("StoryIndex", mStoryIndex);
        super.onSaveInstanceState(outState);
    }

    private void End_Of_Story()
    {
        mTopButton.setVisibility(View.GONE);
        mBottomButton.setVisibility(View.GONE);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("End Of Story");
        alert.setCancelable(false);
        alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                finish();
            }
        });
        alert.show();
    }

    private void updateStory(){
        mStoryIndex = (mStoryIndex + 1) % mStoryBank.length;

        if (mStoryIndex == 0) {
            End_Of_Story();
        }

        mStory = mStoryBank[mStoryIndex].getStoryID();
        mTextView.setText(mStory);
        mStory = mStoryBank[mTopButtonIndex].getStoryID();
        mTopButton.setText(mStory);
        mStory = mStoryBank[mBottomButtonIndex].getStoryID();
        mBottomButton.setText(mStory);
    }

}
