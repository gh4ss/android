package x40240.sidek.sirun.a4;

import java.util.ArrayList;

import x42040.example.inetaccess1.R;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class LocationActivity extends Activity {

    public static final String MAPS_BASE_URL = "http://maps.google.com?z=15&q=";
    
    private static final String LOGTAG = LocationActivity.class.getSimpleName();
    
    private int      locationCount;
    private String[] urls;
    private String[] locations;
    
    private WebView webView;
    private final ArrayList<String> allUrls = new ArrayList<String>();
 
    // UI Components
    //private TextView     firstnameText;
    //private TextView     lastnameText;
    //private RadioButton  maleButton;
    private RadioButton  nextLocation;
    private ProgressBar  progressBar;
    
    private Handler handler = new Handler(); // Main thread when constructed

    private long mainThreadId;
    
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        setContentView(R.layout.activity_main);
        
        //firstnameText = (TextView) findViewById(R.id.firstname_text);
        //lastnameText = (TextView) findViewById(R.id.lastname_text);
        //maleButton = (RadioButton) this.findViewById(R.id.male_radio);
        nextLocation = (RadioButton) this.findViewById(R.id.next_loc_radio);
        progressBar = (ProgressBar)  findViewById(R.id.progress_bar);
        
        feedUrl = getString(R.string.feed_url);
    }

    /*
    @SuppressLint ("SetJavaScriptEnabled")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.web_view);
        webView.setKeepScreenOn(true);
        webView.setInitialScale(100);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setWebViewClient(new MyWebViewClient());
        
        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        
        final Resources res = this.getResources();
        urls = res.getStringArray(R.array.urls);
        locations = res.getStringArray(R.array.locations);
        
        for (String url: urls)
            allUrls.add(url);
        
        final StringBuilder sb = new StringBuilder();
        for (String loc: locations) {
            sb.append(MAPS_BASE_URL);
            sb.append(loc);
            allUrls.add(sb.toString());
            sb.delete(0, sb.length());
        }
    }
    */

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onNextButtonClick (View view) {
        if (locationCount > (allUrls.size()-1))
            locationCount = 0;
        final String url = allUrls.get(locationCount);
        webView.loadUrl(url);
        locationCount++;
    }
    
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.e(LOGTAG, error.toString());
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }
    
    /* -- boiler-plate --
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	*/

}
