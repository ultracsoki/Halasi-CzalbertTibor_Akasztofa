package com.example.akasztofa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button buttonMinus;
    private Button buttonPlus;
    private Button buttonTipp;
    private ImageView imageviewHangman;
    private TextView textviewLetter;
    private TextView textviewWord;
    private List<String> letters;
    private List<String> words;
    private int letterCounter;
    private int randomNumber;
    private String wordLength = "";
    private int guessCount;
    private List<String> guessedLetters;
    private AlertDialog.Builder alertDialog;
    private int imageCounter;
    private int lifeCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        for (int i = 0; i < words.get(randomNumber).length(); i++) {
            wordLength += "_ ";
        }
        textviewWord.setText(wordLength);

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterCounter--;
                if (letterCounter == -1) {
                    letterCounter = letters.size() - 1;
                }
                if (guessedLetters.contains(letters.get(letterCounter))) {
                    textviewLetter.setTextColor(Color.parseColor("#000000"));
                } else {
                    textviewLetter.setTextColor(Color.parseColor("#AE031A"));
                }
                textviewLetter.setText(String.valueOf(letters.get(letterCounter)));
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterCounter++;
                if (letterCounter == letters.size()) {
                    letterCounter = 0;
                }
                if (guessedLetters.contains(letters.get(letterCounter))) {
                    textviewLetter.setTextColor(Color.parseColor("#000000"));
                } else {
                    textviewLetter.setTextColor(Color.parseColor("#AE031A"));
                }
                textviewLetter.setText(String.valueOf(letters.get(letterCounter)));
            }
        });

        buttonTipp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textviewLetter.setTextColor(Color.parseColor("#000000"));
                if(!guessedLetters.contains(letters.get(letterCounter)))
                {
                    if (words.get(randomNumber).contains(letters.get(letterCounter))) {
                        for (int i = 0; i < words.get(randomNumber).length(); i++) {
                            if (words.get(randomNumber).substring(i, i + 1).equals(letters.get(letterCounter))) {
                                char[] wordChars = wordLength.toCharArray();
                                wordChars[i * 2] = letters.get(letterCounter).charAt(0);
                                wordLength = String.valueOf(wordChars);
                                textviewWord.setText(wordLength);
                            }
                            if (!wordLength.contains("_"))
                            {
                                //felugró ablak (NYERTÉL)
                                alertDialog.setTitle("Nyertél").create().show();
                            }
                        }
                        Toast.makeText(MainActivity.this, "Jól tippeltél", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        lifeCounter--;
                        imageCounter++;
                        Toast.makeText(MainActivity.this, "Rosszul tippeltél", Toast.LENGTH_SHORT).show();
                        HashMap<String, Integer> images = new HashMap<String, Integer>();
                        images.put( "01", Integer.valueOf( R.drawable.akasztofa01 ) );
                        images.put( "02", Integer.valueOf( R.drawable.akasztofa02 ) );
                        images.put( "03", Integer.valueOf( R.drawable.akasztofa03 ) );
                        images.put( "04", Integer.valueOf( R.drawable.akasztofa04 ) );
                        images.put( "05", Integer.valueOf( R.drawable.akasztofa05 ) );
                        images.put( "06", Integer.valueOf( R.drawable.akasztofa06 ) );
                        images.put( "07", Integer.valueOf( R.drawable.akasztofa07 ) );
                        images.put( "08", Integer.valueOf( R.drawable.akasztofa08 ) );
                        images.put( "09", Integer.valueOf( R.drawable.akasztofa09 ) );
                        images.put( "10", Integer.valueOf( R.drawable.akasztofa10 ) );
                        images.put( "11", Integer.valueOf( R.drawable.akasztofa11 ) );
                        images.put( "12", Integer.valueOf( R.drawable.akasztofa12 ) );
                        images.put( "13", Integer.valueOf( R.drawable.akasztofa13 ) );
                        String correctAnswer = "";
                        if (imageCounter < 10)
                        {
                            correctAnswer = "0" + String.valueOf(imageCounter);
                        }
                        else if(imageCounter < 14)
                        {
                            correctAnswer = String.valueOf(imageCounter);
                        }
                        if (lifeCounter == 0)
                        {
                            //felugró ablak (Vesztettél)
                            alertDialog.setTitle("Vesztettél").create().show();
                        }
                        imageviewHangman.setImageResource( images.get( correctAnswer ).intValue() );
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Erre a betűre már tippeltél", Toast.LENGTH_SHORT).show();
                }
                guessedLetters.add(letters.get(letterCounter));
            }
        });
    }

    public void init() {
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonTipp = findViewById(R.id.buttonTipp);
        imageviewHangman = findViewById(R.id.imageviewHangman);
        textviewLetter = findViewById(R.id.textviewLetter);
        textviewWord = findViewById(R.id.textviewWord);
        letters = new ArrayList<>();
        UploadLettersList();
        words = new ArrayList<>();
        UploadWordsList();
        letterCounter = 0;
        Random random = new Random();
        randomNumber = random.nextInt(20);
        guessedLetters = new ArrayList<>();
        imageCounter = 0;
        lifeCounter = 13;
        alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Nyertél")
                .setMessage("Akarsz játszani mégegyet?")
                .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newGame();
                    }
                })
                .setCancelable(false);
    }

    public void newGame()
    {
        imageviewHangman.setImageResource(R.drawable.akasztofa00);
        imageCounter = 0;
        Random random = new Random();
        randomNumber = random.nextInt(20);
        guessedLetters.clear();
        lifeCounter = 13;
        wordLength = "";
        for (int i = 0; i < words.get(randomNumber).length(); i++) {
            wordLength += "_ ";
        }
        textviewWord.setText(wordLength);
        textviewLetter.setTextColor(Color.parseColor("#AE031A"));
    }

    public void UploadLettersList()
    {
        letters.add("A");
        letters.add("Á");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        letters.add("E");
        letters.add("É");
        letters.add("F");
        letters.add("G");
        letters.add("H");
        letters.add("I");
        letters.add("Í");
        letters.add("J");
        letters.add("K");
        letters.add("L");
        letters.add("M");
        letters.add("N");
        letters.add("O");
        letters.add("Ó");
        letters.add("Ö");
        letters.add("Ő");
        letters.add("P");
        letters.add("Q");
        letters.add("R");
        letters.add("S");
        letters.add("T");
        letters.add("U");
        letters.add("Ú");
        letters.add("Ü");
        letters.add("Ű");
        letters.add("V");
        letters.add("W");
        letters.add("X");
        letters.add("Y");
        letters.add("Z");
    }

    public void UploadWordsList()
    {
        words.add("JAZZ");
        words.add("KIVI");
        words.add("BÁNAT");
        words.add("EBÉD");
        words.add("HACACÁRÉ");
        words.add("KOTLÁS");
        words.add("KIVI");
        words.add("KANÁL");
        words.add("HAMU");
        words.add("VÖDÖR");
        words.add("BORS");
        words.add("FARKAS");
        words.add("RÓKA");
        words.add("DOB");
        words.add("HAJNAL");
        words.add("MÁRTÍR");
        words.add("HOMOK");
        words.add("KABIN");
        words.add("JÁTÉK");
        words.add("PAPLAN");
    }
}