package simple.example.izzulislamapi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnMacOS,btnUbuntu,btnFreeBSD,btnCentos, btnOracle, btnCromeOS, btnWindows;
    TextView txtSistemOperasi,txtDevloper,txtWebsite, txtDeskrisi;
    ImageView logo;
    FloatingActionButton btnRefresh;
    View lyCurrency;
    ProgressBar loadingIndicator;
    private String OS = "MacOS";
    JSONObject dataOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialisasiView();
        getDataSistemOperasi();
    }

    private void inisialisasiView() {
        txtSistemOperasi = findViewById(R.id.txtSistemOperasi);
        txtSistemOperasi = findViewById(R.id.txtSistemOperasi);
        lyCurrency = findViewById(R.id.lyCurrency);
        logo = findViewById(R.id.logo);
        txtDevloper = findViewById(R.id.txtDevloper);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtDeskrisi = findViewById(R.id.txtDeskripsi);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        logo = findViewById(R.id.logo);

        btnMacOS = findViewById(R.id.btnMacOS);
        btnMacOS.setOnClickListener(view -> showDataSistemOperasi("MacOS"));

        btnUbuntu = findViewById(R.id.btnUbuntu);
        btnUbuntu.setOnClickListener(view -> showDataSistemOperasi("Ubuntu Linux"));

        btnFreeBSD = findViewById(R.id.btnFreeBSD);
        btnFreeBSD.setOnClickListener(view -> showDataSistemOperasi("FreeBSD"));

        btnCentos = findViewById(R.id.btnCentos);
        btnCentos.setOnClickListener(view -> showDataSistemOperasi("CentOS"));

        btnOracle = findViewById(R.id.btnOracle);
        btnOracle.setOnClickListener(view -> showDataSistemOperasi("Oracle Solaris"));

        btnCromeOS = findViewById(R.id.btnCromeOS);
        btnCromeOS.setOnClickListener(view -> showDataSistemOperasi("ChromeOS"));

        btnWindows = findViewById(R.id.btnWindows);
        btnWindows.setOnClickListener(view -> showDataSistemOperasi("Microsoft Windows"));

        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(view -> getDataSistemOperasi());
    }

    private void getDataSistemOperasi() {
        loadingIndicator.setVisibility(View.VISIBLE);
        String baseURL = "https://ewinsutriandi.github.io/mockapi/operating_system.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MAIN",response.toString());
                        dataOS= response;
                        showDataSistemOperasi(OS);
                        loadingIndicator.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingIndicator.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Gagal mengambil data",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void showDataSistemOperasi(String OS) {
        this.OS = OS;
        // tampilkan nama framework terpilih
        txtSistemOperasi.setText(OS);
        try { // try catch untuk antisipasi error saat parsing JSON
            // tampilkan data framework
            JSONObject data = dataOS.getJSONObject(OS);
            txtDevloper.setText(data.getString("developer"));
            String link = data.getString("website");
            txtWebsite.setLinksClickable(true);
            txtWebsite.setMovementMethod(LinkMovementMethod.getInstance());
            txtWebsite.setText(Html.fromHtml(link));
            txtDeskrisi.setText(data.getString("description"));

            String imgUrl = data.getString("logo_url");
            Glide.with(this)
                    .load(imgUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .fitCenter()
                    .into(logo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
