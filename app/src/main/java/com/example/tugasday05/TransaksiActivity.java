package com.example.tugasday05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.Locale;

public class TransaksiActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private TextView transaksiTextView;
    private TextView thanksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        transaksiTextView = findViewById(R.id.transaksiTextView);
        thanksTextView = findViewById(R.id.thanksTextView);
        Button shareButton = findViewById(R.id.shareButton);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        int jumlahBarang = intent.getIntExtra("jumlahBarang", 0);
        String jenisMembership = intent.getStringExtra("jenisMembership");
        String kodeBarang = intent.getStringExtra("kodeBarang");
        String hargaBarang = intent.getStringExtra("hargaBarang");
        int jumlahHarga = intent.getIntExtra("jumalhHarga", 0);
        double diskonMember = intent.getDoubleExtra("potonganPelanggan", 0); // Mengambil diskon member dari intent

        welcomeTextView.setText(getText(R.string.selamatDatang) + " " + nama + "\n" + getText(R.string.tipePelanggan) + " " + jenisMembership);

        String transaksiDetail = getText(R.string.transaksiHariIni) +  "\n" +
                getText(R.string.kodeBarang) + " " + kodeBarang + "\n" +
                getText(R.string.namaBarang) + " " + getNamaBarang(kodeBarang) + "\n" +
                getText(R.string.jumlahBarang) + " " + jumlahBarang + "\n" +
                getText(R.string.harga) + " " + hargaBarang + "\n" +
                getText(R.string.jumlahHarga) + " " + formatRupiah(jumlahHarga) + "\n";

        double diskonPersen = 0;
        switch (jenisMembership) {
            case "Emas":
                diskonPersen = 0.1; // Diskon 10%
                break;
            case "Perak":
                diskonPersen = 0.05; // Diskon 5%
                break;
            case "Biasa":
                diskonPersen = 0.02; // Diskon 2%
                break;
            default:
                break;
        }
        double diskonMemberValue = jumlahHarga * diskonPersen;
        transaksiDetail += getText(R.string.potonganPelanggan) + " " + formatRupiah((int) diskonMemberValue) + "\n";

        // Jika total harga melebihi 10 juta, tambahkan diskon harga
        if (jumlahHarga > 10000000) {
            int discountHarga = 100000;
            transaksiDetail += getText(R.string.potonganHarga) + " " + formatRupiah(discountHarga) + "\n";
            jumlahHarga -= discountHarga;
        }

        int totalBayar = (int) (jumlahHarga - diskonMemberValue);
        transaksiDetail += getText(R.string.jumlahBayar) + " " + formatRupiah(totalBayar) + "\n";

        transaksiTextView.setText(transaksiDetail);

        thanksTextView.setText(getText(R.string.terimakasihTelahBerbelanjaDisini));

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transaksi = welcomeTextView.getText().toString() + "\n" +
                        transaksiTextView.getText().toString() + "\n" +
                        thanksTextView.getText().toString();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, transaksi);
                startActivity(Intent.createChooser(shareIntent, getText(R.string.bagikanTransaksi)));
            }
        });
    }


    private String getNamaBarang(String kodeBarang) {
        switch (kodeBarang) {
            case "PCO":
                return "Poco M3";
            case "AA5":
                return "Acer Aspire 5";
            case "SGS":
                return "Samsung Galaxy S";
            default:
                return "Unknown";
        }
    }

    private String formatRupiah(int harga) {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatRupiah.format(harga);
    }
}
