package bartr.underdogz.hackfest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import bartr.underdogz.hackfest.account.LoginActivity;


public class AccountFragment extends Fragment {

    private static final String TAG = "AccountFragment";
    private WebView webView;

    public String url = "https://bartr-discuss.firebaseapp.com/add-post";

    private FirebaseAuth.AuthStateListener mAuthStateListener;


    private Button mSignOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mSignOut = (Button) view.findViewById(R.id.sign_out);

        setupFirebaseListener();

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to sign out the user.");
                FirebaseAuth.getInstance().signOut();
            }
        });
        webView = (WebView) view.findViewById(R.id.webview2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

// you need to setWebViewClient for forcefully open in your webview
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                view.loadUrl(url);
                return true;
            }
        });


        return view;
    }

    private void setupFirebaseListener(){
        Log.d(TAG, "setupFirebaseListener: setting up the auth state listener.");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }else{
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                    Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }
}





















