package com.example.projekt2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLiczbaKostek;
    private GridLayout gridKostki;
    private TextView textViewWyniki;

    private final List<Integer> wylosowaneKostki = new ArrayList<>();
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextLiczbaKostek = findViewById(R.id.editTextLiczbaKostek);
        Button buttonLosuj = findViewById(R.id.buttonLosuj);
        gridKostki = findViewById(R.id.gridKostki);
        textViewWyniki = findViewById(R.id.textViewWyniki);
        Button buttonRosnaco = findViewById(R.id.buttonRosnaco);
        Button buttonMalejaco = findViewById(R.id.buttonMalejaco);

        buttonLosuj.setOnClickListener(v -> losujKostki());
        buttonRosnaco.setOnClickListener(v -> sortujRosnaco());
        buttonMalejaco.setOnClickListener(v -> sortujMalejaco());
    }

    private void losujKostki() {
        String inputText = editTextLiczbaKostek.getText().toString().trim();
        if (TextUtils.isEmpty(inputText)) {
            Toast.makeText(this, "Wprowadź liczbę kostek", Toast.LENGTH_SHORT).show();
            return;
        }

        int liczbaKostek;
        try {
            liczbaKostek = Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Nieprawidłowa wartość", Toast.LENGTH_SHORT).show();
            return;
        }

        if (liczbaKostek <= 0 || liczbaKostek > 100) {
            Toast.makeText(this, "Liczba kostek musi być z zakresu od 1 do 100", Toast.LENGTH_SHORT).show();
            return;
        }

        wylosowaneKostki.clear();
        for (int i = 0; i < liczbaKostek; i++) {
            wylosowaneKostki.add(random.nextInt(6) + 1);
        }

        wyswietlKostki();
        obliczIWyswietlWyniki();
    }

    private void wyswietlKostki() {
        gridKostki.removeAllViews();
        for (int i = 0; i < wylosowaneKostki.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText(String.valueOf(wylosowaneKostki.get(i)));
            tv.setTextSize(18);
            tv.setPadding(16, 16, 16, 16);
            tv.setGravity(Gravity.CENTER);
            gridKostki.addView(tv);
        }
    }

    private void obliczIWyswietlWyniki() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wylosowaneKostki.size(); i++) {
            sb.append(wylosowaneKostki.get(i));
            if (i < wylosowaneKostki.size() - 1) {
                sb.append(", ");
            }
        }
        textViewWyniki.setText(sb.toString());
    }

    private void sortujRosnaco() {
        if (wylosowaneKostki.isEmpty()) {
            Toast.makeText(this, "Najpierw należy wylosować kostki", Toast.LENGTH_SHORT).show();
            return;
        }
        sortujRosnacoAlgorytm(wylosowaneKostki);
        wyswietlKostki();
        obliczIWyswietlWyniki();
    }

    private void sortujMalejaco() {
        if (wylosowaneKostki.isEmpty()) {
            Toast.makeText(this, "Najpierw należy wylosować kostki", Toast.LENGTH_SHORT).show();
            return;
        }
        sortujMalejacoAlgorytm(wylosowaneKostki);
        wyswietlKostki();
        obliczIWyswietlWyniki();
    }

    private void sortujRosnacoAlgorytm(List<Integer> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.get(j) > lista.get(j + 1)) {
                    int temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }

    private void sortujMalejacoAlgorytm(List<Integer> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.get(j) < lista.get(j + 1)) {
                    int temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }
}
