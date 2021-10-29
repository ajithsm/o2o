package com.photos.scanner;

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

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    private ZXingScannerView scannerView;
    private TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scannerView = (ZXingScannerView) findViewById(R.id.zxscan);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        scannerView.setResultHandler(MainActivity.this);
                        scannerView.startCamera();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        Toast.makeText(MainActivity.this, "You must accept this permission", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    }
                })
                .check();
    }
    public void onPause(){
        System.out.println("Reached OnPause");
        super.onPause();
    }
    public void onResume(){
        System.out.println("Reached OnResume");
        scannerView.setResultHandler(MainActivity.this);
        scannerView.startCamera();
        super.onResume();
    }

    protected void onDestroy()
    {
        System.out.println("Reached OnDestroy");
        scannerView.stopCamera();
        super.onDestroy();
   }
    @Override
    public void handleResult(Result rawResult) {
        System.out.println("Reached OnhandleReslt");
        Intent browse = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://www.ajio.com/search/?text="+rawResult.getText()));
        startActivity( browse );

//        ProcessPhoenix.triggerRebirth(getApplicationContext());
    }


}