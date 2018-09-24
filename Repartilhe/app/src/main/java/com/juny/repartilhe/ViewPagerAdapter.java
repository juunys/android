package com.juny.repartilhe;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private int[] images = {

            R.drawable.login_image_icon_one,
            R.drawable.login_image_icon_two,
            R.drawable.login_image_icon_three

    };

    public String[] slide_headings = {
            "Encontre as melhores repúblicas, kitnets e homestay!",
            "Filtre e encontre a melhor hospedagem para você!",
            "Pesquise pelos melhores e mais baratos!"
    };

    public ViewPagerAdapter(Context context) {

        this.context = context;

    }

    @Override
    public int getCount() {

        return images.length;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView headTextView = view.findViewById(R.id.headTextView);

        imageView.setImageResource(images[position]);
        headTextView.setText(slide_headings[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout) object);

    }

}
