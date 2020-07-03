package com.example.shoppingcart.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shoppingcart.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images1={
            R.drawable.cocoshells,
            R.drawable.shellscraft,


    };
    public int[] slide_images2={

            R.drawable.newspapers,
            R.drawable.papapercrafts
    };

    public String[]slide_headings={

            "We Buy!",
            "We Produce!"
    };

    public String[] slide_des={

            "       There's no such thing as 'away' When we throw anything away,it must go somewhere",
            "       If you're not buying recycled products, you're not really recycling"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        CircleImageView slideImageView1 = view.findViewById(R.id.img1);
        CircleImageView slideImageView2 = view.findViewById(R.id.img2);
        TextView slideHeading = view.findViewById(R.id.header1);
        TextView slideDesc=view.findViewById(R.id.para1);

        slideImageView1.setImageResource(slide_images1[position]);
        slideImageView2.setImageResource(slide_images2[position]);
        slideHeading.setText(slide_headings[position]);
        slideDesc.setText(slide_des[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);

        container.removeView((RelativeLayout)object);
    }
}
