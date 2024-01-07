package com.example.isimsehir;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;
import java.util.ArrayList;
import java.util.Random;

public class TimerGame extends AppCompatActivity {

    private TextView textViewCityInfo,textViewCity,textViewTotalScore;

    private String[] cities={"Abu Dabi", "Amsterdam", "Ankara", "Antalya", "Atina", "Auckland", "Barcelona", "Berlin", "Birmingham", "Bordeaux", "Boston", "Brasilia", "Brüksel", "Buenos Aires", "Busan", "Cape Town", "Casablanca", "Chengdu", "Chicago", "Cologne", "Curitiba", "Dallas", "Delhi", "Dresden", "Dubai", "Dublin", "Durban", "Edinburg", "Florence", "Glasgow", "Granada", "Guangzhou", "Helsinki", "Hong Kong", "Houston", "Istanbul", "Jakarta", "Johannesburg", "Kahire", "Karachi", "Kiev", "Kuala Lumpur", "Lagos", "Lahore", "Lima", "Lisbon", "Londra", "Los Angeles", "Lyon", "Madrid", "Manchester", "Marseille", "Melbourne", "Meksiko City", "Miami", "Milano", "Montreal", "Moskova", "Mumbai", "Nairobi", "New York City", "Nice", "Osaka", "Oslo", "Paris", "Philadelphia", "Porto", "Porto Alegre", "Pretoria", "Prag", "Rio de Janeiro", "Riyad", "Salvador", "San Francisco", "Santiago", "Seul", "Shanghai", "Sidney", "Singapur", "St Petersburg", "Stockholm", "Tokyo", "Toronto", "Valencia", "Varşova", "Vancouver", "Vienna", "Zürih"};

    private Random randomcities,randomletter;
    private int randomcitiesNumber,randomletternumber,firstLetterNumber;
    private String takenCities,citieslength,editTxtTakenGuess;
    private ArrayList<Character>cityletters;
    EditText editTextGuess;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;

    private float maxScore=100.0f,deductedScore,totalScore=0,sectionTotalScore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_game);
        textViewCityInfo=(TextView)findViewById(R.id.textViewCityInfo);
        textViewCity=(TextView)findViewById(R.id.textViewCity);
        textViewTotalScore=(TextView)findViewById(R.id.textViewTotalScore);
        editTextGuess = (EditText) findViewById(R.id.editTextGuess);

        timerTextView = findViewById(R.id.timerTextView);
        startCountdown();

        randomletter=new Random();
        randomValueChoose();
    }
    private void startCountdown() {
        countDownTimer = new CountDownTimer(10000, 1000) { // 60 seconds countdown
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(formatTime(millisUntilFinished));
            }

            public void onFinish() {
                timerTextView.setText("Countdown Finished");
                Toast.makeText(getApplicationContext(),"Time is Up!",Toast.LENGTH_LONG).show();
                Button myButton = findViewById(R.id.buttonGuessIt);
                myButton.setEnabled(false);
            }
        }.start();
    }

    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void buttonGuesit(View v){

        editTxtTakenGuess=editTextGuess.getText().toString();


        if (!TextUtils.isEmpty(editTxtTakenGuess)){
            if (editTxtTakenGuess.equals(takenCities)){
                sectionTotalScore+=totalScore;
                Toast.makeText(getApplicationContext(),"Correct !",Toast.LENGTH_SHORT).show();
                textViewTotalScore.setText("Total section score: "+sectionTotalScore);

                editTextGuess.setText("");
                randomValueChoose();
            }
            else
                System.out.println("Wrong !");
        }else
            System.out.println("This place can't remain empty!");
    }

    public void buttonGiveletter(View v){
        if (cityletters.size()>0){
            randomTakeNumber();
            totalScore -= deductedScore;
            Toast.makeText(getApplicationContext(),"Remaining points= "+totalScore,Toast.LENGTH_SHORT).show();
        }else
            System.out.println("No have letters!");

    }

    @SuppressLint("MissingInflatedId")
    private void randomValueChoose(){
        citieslength="";


        randomcities=new Random();
        randomcitiesNumber=randomcities.nextInt(cities.length);
        takenCities=cities[randomcitiesNumber];
        textViewCityInfo.setText(takenCities.length()+" Letter City");


        if(takenCities.length() >= 5 && takenCities.length()<=7)
            firstLetterNumber=1;
        else if (takenCities.length()>=8 && takenCities.length()<10)
            firstLetterNumber=2;
        else if (takenCities.length()>=10)
            firstLetterNumber=3;
        else
            firstLetterNumber=0;



        for (int i=0;i<takenCities.length();i++){
            if (i<takenCities.length()-1)
                citieslength+="_ ";
            else
                citieslength+="_";
        }

        textViewCity.setText(citieslength);
        cityletters=new ArrayList<>();

        for(char c:takenCities.toCharArray())
            cityletters.add(c);

        for (int c=0;c<firstLetterNumber;c++){
            randomTakeNumber();


            deductedScore = maxScore / cityletters.size();
            totalScore=maxScore;


        }
    }

    private void randomTakeNumber(){
        randomletternumber=randomletter.nextInt(cityletters.size());
        //String text = editTextGuess.getText().toString();
        String[] textLetters = textViewCity.getText().toString().split(" ");
        char[] takenLetters=takenCities.toCharArray();

        for (int i=0;i<takenCities.length();i++){
            if (textLetters[i].equals("_")&&takenLetters[i]==cityletters.get(randomletternumber)){
                textLetters[i]=String.valueOf(cityletters.get(randomletternumber));
                citieslength="";

                for (int j=0;j<takenCities.length();j++){
                    if (j==i)
                        citieslength+=textLetters[j]+" ";
                    else if (j<takenCities.length()-1)
                        citieslength+=textLetters[j]+" ";
                    else
                        citieslength+=textLetters[j];
                }

                break;
            }
        }
        textViewCity.setText(citieslength);
        cityletters.remove(randomletternumber);
    }
}
