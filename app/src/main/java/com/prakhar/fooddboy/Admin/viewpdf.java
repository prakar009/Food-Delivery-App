package com.prakhar.fooddboy.Admin;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.prakhar.fooddboy.R;

import java.net.URLEncoder;

public class viewpdf extends AppCompatActivity {

    WebView fileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf);

        fileView = findViewById(R.id.viewpdf);
        fileView.getSettings().setJavaScriptEnabled(true);

        String filename = getIntent().getStringExtra("filename");
        String fileUrl = getIntent().getStringExtra("fileurl");

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("Opening....!!!");

        fileView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String encodedUrl = "";
        try {
            encodedUrl = URLEncoder.encode(fileUrl, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String fileExtension = getFileExtension(filename);
        String webViewUrl;

        if (fileExtension.equalsIgnoreCase("")) {
            webViewUrl = "http://docs.google.com/gview?embedded=true&url=" + encodedUrl;
        } else if (fileExtension.equalsIgnoreCase("xlsx") || fileExtension.equalsIgnoreCase("xls")) {
            webViewUrl = "https://view.officeapps.live.com/op/embed.aspx?src=" + encodedUrl;
        } else {
            webViewUrl = encodedUrl;
        }

        fileView.loadUrl(webViewUrl);
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex != -1) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }
}