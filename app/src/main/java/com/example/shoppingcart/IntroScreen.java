package com.example.shoppingcart;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shoppingcart.Model.SliderAdapter;

public class IntroScreen extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private Button next,prev,skip;
    private TextView[] mDots;

    private  int mCurrentPage;

    private SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);



        mSlideViewPager=findViewById(R.id.vwpager);
        mDotLayout=findViewById(R.id.dots);
        next=findViewById(R.id.btnNext);
        prev=findViewById(R.id.btnBack);
        skip=findViewById(R.id.btnSkip);

        sliderAdapter= new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

//        View decorView=getWindow().getDecorView();
//        int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        decorView.setSystemUiVisibility(uiOptions);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewLister);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage+1);
//                View decorView=getWindow().getDecorView();
//                int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//                decorView.setSystemUiVisibility(uiOptions);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
//                View decorView=getWindow().getDecorView();
//                int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//                decorView.setSystemUiVisibility(uiOptions);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(IntroScreen.this,login.class);
                startActivity(login);
//                View decorView=getWindow().getDecorView();
//                int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//                decorView.setSystemUiVisibility(uiOptions);
            }
        });


    }

    public void addDotsIndicator(int position){

        mDots= new TextView[2];
        mDotLayout.removeAllViews();

        for(int i=0;i<mDots.length;i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransperantWhite));

            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }
    ViewPager.OnPageChangeListener viewLister = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {


            addDotsIndicator(i);
            mCurrentPage=i;
            if(i==0){

                next.setEnabled(false);
                prev.setEnabled(false);
                prev.setVisibility(View.INVISIBLE);

                next.setText("");
                prev.setText("");
            }
            else if(i==1){
                View decorView=getWindow().getDecorView();


                next.setEnabled(true);
                prev.setEnabled(false);
                next.setVisibility(View.VISIBLE);

                next.setText("Finish");
                if(next.getText().equals("Finish")) {
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent login = new Intent(IntroScreen.this, login.class);
                            startActivity(login);

                        }
                    });
                }
                prev.setText("");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}

