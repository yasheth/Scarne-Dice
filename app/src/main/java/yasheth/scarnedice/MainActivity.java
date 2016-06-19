package yasheth.scarnedice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //4 global variables to store value of user and computer scores
    int uoverall, ucurrent, coverall, ccurrent;

    //Random is used to generate a random number
    Random rand=new Random();

    //TextViews and ImageView to implement our logic on the components in UI
    TextView uscorer, cscorer, current;
    ImageView cov;

    //To store strings of scorecard of user and computer
    String udefault, cdefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uscorer=(TextView)findViewById(R.id.uscore);
        cscorer=(TextView)findViewById(R.id.cscore);
        current=(TextView)findViewById(R.id.current);
        cov=(ImageView)findViewById(R.id.imgdice);
        udefault= (String)uscorer.getText();
        cdefault=(String)cscorer.getText();
    }

    //Handler for onclick events
    public void onclick(View v) {

        //if ROLL button clicked
        if (v.getId() == R.id.btnroll) {

            //user current score stored after dice roll
            ucurrent = diceRoll(ucurrent,6,1);

            //if user rolls 0, no updation and turn switches to computer
            if (ucurrent == 0) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        computerTurn();
                    }
                }, 1000);
            }
            //Update value of current score
            String update = "Current Score is : " + String.valueOf(ucurrent);
            current.setText(update);
        }

        //if HOLD button clicked
        if(v.getId()==R.id.btnhold){
            //update user overall value, change current to 0 and update scorecard
            uoverall=uoverall+ucurrent;
            ucurrent=0;
            String update=udefault+String.valueOf(uoverall);
            uscorer.setText(update);

            //check after updating overall score if condition of winning is satisfied for user
            if(uoverall>coverall && uoverall>=100){
                Toast toast=Toast.makeText(getApplicationContext(), "You Win Congratulations! Keep Beating.", Toast.LENGTH_SHORT);
                toast.show();
                reset();
            }

            //switch control to computer
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override public void run() {computerTurn();}}, 1000);
        }

        //if RESET button clicked
        if(v.getId()==R.id.btnreset){
            reset();
        }

    }

    //the reset helper logic
    public void reset(){
        //making all global variables 0
        uoverall=0;
        ucurrent=0;
        ccurrent=0;
        coverall=0;

        //updating scorecard to default
        uscorer.setText(udefault);
        cscorer.setText(cdefault);
        current.setText("");

        //Toast so user knows about new game (optional)
        Toast toast=Toast.makeText(getApplicationContext(), "New Game! Good Luck!", Toast.LENGTH_SHORT);
        toast.show();
        cov.setImageResource(R.drawable.dice);

    }


    public int diceRoll(int value,int max,int min){

        //Logic so that integer is between 1 and 6
        int randomNum = rand.nextInt(max - min) + min;
        value=value+randomNum;

        //if number rolled is 1 set user current score to 0
        if(randomNum==1)
        {
            value=0;
            current.setText("");
            cov.setImageResource(R.drawable.dice1);
        }

        //if number other than 1, just update photo to corresponding dice
        else if(randomNum==2)
            cov.setImageResource(R.drawable.dice2);

        else if(randomNum==3)
            cov.setImageResource(R.drawable.dice3);

        else if(randomNum==4)
            cov.setImageResource(R.drawable.dice4);

        else if(randomNum==5)
            cov.setImageResource(R.drawable.dice5);

        else if(randomNum==6)
            cov.setImageResource(R.drawable.dice6);

        return value;
    }

    public void computerTurn(){

        //disabling ROLL, HOLD and RESET buttons
        Button btn = (Button) findViewById(R.id.btnhold);
        btn.setEnabled(false);
        Button btn1 = (Button) findViewById(R.id.btnroll);
        btn1.setEnabled(false);
        Button btn2 = (Button) findViewById(R.id.btnreset);
        btn2.setEnabled(false);


        //computer rolls dice
        ccurrent=diceRoll(ccurrent,6,2);

        //Update value of current score
        String update="Current Score is : "+String.valueOf(ccurrent);
        current.setText(update);


        if(ccurrent>=13){

            //update overall value, change current to 0 and update scorecard
            current.setText("");
            coverall=coverall+ccurrent;
            ccurrent=0;
            update=cdefault+String.valueOf(coverall);
            cscorer.setText(update);

            //check after updating overall score if condition of winning is satisfied for computer
            if(coverall>uoverall && coverall>=100){
                Toast toast=Toast.makeText(getApplicationContext(), "Computer Wins! Try Again Loser!", Toast.LENGTH_SHORT);
                toast.show();
                reset();
            }

            //switch ROLL, HOLD and RESET buttons back ON
            btn2.setEnabled(true);
            btn1.setEnabled(true);
            btn.setEnabled(true);

            //Toast to let user know its his turn now
            Toast toast=Toast.makeText(getApplicationContext(), "Your Turn!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override public void run() {computerTurn();}}, 1000);
        }

    }
}
