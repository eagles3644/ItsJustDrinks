package com.itsjustdrinks.itsjustdrinks.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itsjustdrinks.itsjustdrinks.R;
import com.itsjustdrinks.itsjustdrinks.utils.Constants;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                SharedPreferences.Editor editor = mPrefs.edit();
                if (user != null) {
                    editor.putBoolean(Constants.PREF_USER_AUTHENTICATED, true);
                    editor.apply();
                } else {
                    editor.putBoolean(Constants.PREF_USER_AUTHENTICATED, false);
                    editor.apply();
                }
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public static class LoginFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public LoginFragment() {
        }
        
        public static LoginFragment newInstance(int sectionNumber) {
            LoginFragment fragment = new LoginFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            Button loginGoogle = (Button) rootView.findViewById(R.id.loginGoogle);
            loginGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Will login using google...", Toast.LENGTH_SHORT).show();
                }
            });
            Button loginFacebook = (Button) rootView.findViewById(R.id.loginFacebook);
            loginFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Will login using facebook...", Toast.LENGTH_SHORT).show();
                }
            });
            Button loginTwitter = (Button) rootView.findViewById(R.id.loginTwitter);
            loginTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Will login using twitter...", Toast.LENGTH_SHORT).show();
                }
            });
            return rootView;
        }
    }

    public static class RegisterFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public RegisterFragment() {
        }

        public static RegisterFragment newInstance(int sectionNumber) {
            RegisterFragment fragment = new RegisterFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_register, container, false);
            Button registerGoogle = (Button) rootView.findViewById(R.id.registerGoogle);
            registerGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Will register using google...", Toast.LENGTH_SHORT).show();
                }
            });
            Button registerFacebook = (Button) rootView.findViewById(R.id.registerFacebook);
            registerFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Will register using facebook...", Toast.LENGTH_SHORT).show();
                }
            });
            Button registerTwitter = (Button) rootView.findViewById(R.id.registerTwitter);
            registerTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Will register using twitter...", Toast.LENGTH_SHORT).show();
                }
            });
            return rootView;
        }
    }
    
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = LoginFragment.newInstance(position+1);
                    break;
                case 1:
                    fragment = RegisterFragment.newInstance(position+1);
                    break;
                default:
                    fragment = LoginFragment.newInstance(position+1);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return Constants.LOGIN_ACTIVITY_NUM_FRAGS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return Constants.FRAG_NAME_LOGIN;
                case 1:
                    return Constants.FRAG_NAME_REGISTER;
            }
            return null;
        }
    }
}
