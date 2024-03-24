package com.example.tugasday05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText namaEditText, jumlahEditText, kodeBarangEditText;
    private RadioButton emasRadioButton, perakRadioButton, biasaRadioButton;
    private Button prosesButton;
    private Map<String, Integer> hargaBarangMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namaEditText = findViewById(R.id.namaEditText);
        jumlahEditText = findViewById(R.id.jumlahEditText);
        kodeBarangEditText = findViewById(R.id.kodeBarangEditText);
        emasRadioButton = findViewById(R.id.emasRadioButton);
        perakRadioButton = findViewById(R.id.perakRadioButton);
        biasaRadioButton = findViewById(R.id.biasaRadioButton);
        prosesButton = findViewById(R.id.prosesButton);

        hargaBarangMap.put("PCO", 2730551);
        hargaBarangMap.put("AA5", 9999999);
        hargaBarangMap.put("SGS", 12999999);

        emasRadioButton.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            if (emasRadioButton.isChecked()) {
                perakRadioButton.setChecked(false);
                biasaRadioButton.setChecked(false);
            }
        }
        });

        perakRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (perakRadioButton.isChecked()) {
                    emasRadioButton.setChecked(false);
                    biasaRadioButton.setChecked(false);
                }
            }
        });biasaRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (biasaRadioButton.isChecked()) {
                    emasRadioButton.setChecked(false);
                    perakRadioButton.setChecked(false);
                }
            }
        });

        prosesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesTransaksi();
            }
        });
    } private void prosesTransaksi() {
        String nama = namaEditText.getText().toString().trim();
        int jumlahBarang;
        try {
            jumlahBarang = Integer.parseInt(jumlahEditText.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, getText(R.string.masukkanJumlahBarangYangValid), Toast.LENGTH_SHORT).show();
            return;
        }

        String jenisMembership = "";
        if (emasRadioButton.isChecked()) {
            jenisMembership = "Emas";
        } else if (perakRadioButton.isChecked()) {
            jenisMembership = "Perak";
        } else if (biasaRadioButton.isChecked()) {
            jenisMembership = "Biasa";
        }
        String kodeBarang = kodeBarangEditText.getText().toString().toUpperCase().trim(); // Mengubah menjadi huruf kapital
        int hargaBarang = 0;

        // Validasi kode barang dan ambil harga sesuai kode barang
        if (hargaBarangMap.containsKey(kodeBarang)) {
            hargaBarang = hargaBarangMap.get(kodeBarang);
        } else {
            Toast.makeText(this, getText(R.string.kodeBarangTidakValid), Toast.LENGTH_SHORT).show();
            return;
        }

        int jumlahHarga = jumlahBarang * hargaBarang;

        // Berikan diskon harga sebesar 100 ribu jika total harga melebihi 10 juta
        if (jumlahHarga > 10000000) {
            jumlahHarga -= 100000;
        } // Format harga ke format mata uang Rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String hargaFormatted = formatRupiah.format(hargaBarang);

        Intent intent = new Intent(this, TransaksiActivity.class);
        intent.putExtra("nama", nama);
        intent.putExtra("jumlahBarang", jumlahBarang);
        intent.putExtra("jenisMembership", jenisMembership);
        intent.putExtra("kodeBarang", kodeBarang);
        intent.putExtra("hargaBarang", hargaFormatted);
        intent.putExtra("jumlahHarga", jumlahHarga);
        startActivity(intent);
    }
}






