package co.gstreak.app;

import com.reactnativenavigation.NavigationActivity;


public class MainActivity extends NavigationActivity {

    @Override
    protected void addDefaultSplashLayout() {
        setContentView(getLayoutInflater().inflate(R.layout.splash_layout, null));
    }
}
