package com.apps.o2o;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import o2o.R;

public class Scanning extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    private ZXingScannerView scannerView;
    private TextView txtResult;
    private String store;
    private String getUrl(String store, String barcode)
    {
        String url;
        switch (store){
            case "relianceTrends":
                url = "https://www.ajio.com/search/?text="+ barcode;
                break;
            case "maxFashions":
                url = "https://www.maxfashion.in/in/en/search?q="+ barcode;
                break;
            case "westside":
                url = "https://www.westside.com/search?type=product&q="+ barcode;
                break;
            case "lifestyle":
                url = "https://www.lifestylestores.com/p/"+ barcode;
                break;
            default:
                url = "";
                break;
        }
        return url;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
        scannerView = (ZXingScannerView) findViewById(R.id.zxscan);
        store = getIntent().getExtras().getString("store");
        Toast.makeText(getApplicationContext(), store, Toast.LENGTH_SHORT).show();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        scannerView.setResultHandler(Scanning.this);
                        scannerView.startCamera();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        Toast.makeText(Scanning.this, "You must accept this permission", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    }
                })
                .check();
    }
    public void onPause(){
        super.onPause();
    }
    public void onResume(){
        scannerView.setResultHandler(Scanning.this);
        scannerView.startCamera();
        super.onResume();
    }

    protected void onDestroy()
    {
        scannerView.stopCamera();
        super.onDestroy();
   }
    @Override
    public void handleResult(Result rawResult) {
        String url = getUrl(store, rawResult.getText());
        Intent browse = new Intent( Intent.ACTION_VIEW, Uri.parse(url));
        startActivity( browse );
    }
}