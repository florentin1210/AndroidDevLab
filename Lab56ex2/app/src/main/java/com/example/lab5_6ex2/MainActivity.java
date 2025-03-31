package com.example.lab5_6ex2;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntroductionFragment introFragment = new IntroductionFragment();
        Ch1Fragment ch1Fragment = new Ch1Fragment();
        Ch2Fragment ch2Fragment = new Ch2Fragment();
        Ch3Fragment ch3Fragment = new Ch3Fragment();
        Ch4Fragment ch4Fragment = new Ch4Fragment();

        loadFragment(introFragment);

        Button introBtn = findViewById(R.id.introductionButton);
        Button ch1Btn = findViewById(R.id.ch1Button);
        Button ch2Btn = findViewById(R.id.ch2Button);
        Button ch3Btn = findViewById(R.id.ch3Button);
        Button ch4Btn = findViewById(R.id.ch4Button);

        introBtn.setOnClickListener(v -> loadFragment(introFragment));
        ch1Btn.setOnClickListener(v -> loadFragment(ch1Fragment));
        ch2Btn.setOnClickListener(v -> loadFragment(ch2Fragment));
        ch3Btn.setOnClickListener(v -> loadFragment(ch3Fragment));
        ch4Btn.setOnClickListener(v -> loadFragment(ch4Fragment));
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}
