package com.example.zulqarnain.libraryapp.Admin;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.zulqarnain.libraryapp.R;
import com.example.zulqarnain.libraryapp.ui.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Admin");
        auth = FirebaseAuth.getInstance();
        mViewPager= (ViewPager) findViewById(R.id.admin_view_pager);
        AdminPagerAdapter adapter = new AdminPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }
    public class AdminPagerAdapter extends FragmentStatePagerAdapter {

        public  AdminPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new AdminDashBoardFragment();

            } else if (position == 1) {
                return new AddBookFragement();
            }
            return null;
        }

        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "Book dashboard";
            else
                return "Job Portal";


        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

