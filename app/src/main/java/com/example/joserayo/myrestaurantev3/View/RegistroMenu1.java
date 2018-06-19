package com.example.joserayo.myrestaurantev3.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.R;

public class RegistroMenu1 extends AppCompatActivity  {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_registro_menu1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro de Menu del Dia");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());



        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        Bundle bundle1=this.getIntent().getExtras();
        if(bundle1!=null){
            String iddato=bundle1.getString("nombre");
            String dato=bundle1.getString("idrestaurante");


        }else{


        }


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro_menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
          switch (position){
              case 0:
                  Entrada marino=new Entrada();

                  return marino;

              case 1:
                  SegundoFragment segundoFragment=new SegundoFragment();
                  return segundoFragment;

              case 2:
                  Bebidas criollo=new Bebidas();
                  return criollo;
              case 3:
                  Extras vegetariano=new Extras();
                  return vegetariano;



                  default:
                      return null;
          }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }





}
