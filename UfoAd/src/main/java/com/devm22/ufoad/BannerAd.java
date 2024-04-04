package com.devm22.ufoad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.devm22.ufoad.config.UFOAdConfig;
import com.devm22.ufoad.config.Utils;
import com.devm22.ufoad.holder.DatabaseBanner;
import com.devm22.ufoad.interfaces.OnBannerListener;
import com.devm22.ufoad.model.BannerModel;

import java.util.ArrayList;
import java.util.Random;

public class BannerAd extends FrameLayout {
    private static final String TAG = "BannerAd";


    private Context context;

    private int attrActionColor = Color.parseColor("#2196F3"); //default color
    private int attrTitleColor = Color.parseColor("#2196F3"); //default color
    private int attrBodyColor = Color.WHITE; //default color
    private int ANIMATION_SPEED = 3; //time on second to change Text banner.

    private ArrayList<BannerModel> bannerAdList = new ArrayList<>();

    private OnBannerListener onBannerListener;
    private final Handler handler = new Handler();


    private RelativeLayout smartBannerBody;
    private TextView adTitle, adDescription, adActionText;
    private ImageView adIcon;
    private RelativeLayout adBtnAction;
    private RelativeLayout adIconProgressBar;
    private LinearLayout adViewOne, adViewTwo;

    private int bannerIndexSelected = 0;


    public BannerAd(@NonNull Context context) {
        super(context);
        initView(null);
        this.context = context;
        initializeData();

    }

    public BannerAd(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
        this.context = context;
        initializeData();
    }

    public BannerAd(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
        this.context = context;
        initializeData();
    }

    public BannerAd(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
        this.context = context;
        initializeData();
    }


    private void initView(AttributeSet attrs) {
        //Load attributes
        final TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.BannerView, 0, 0);

        attrActionColor = typedArray.getColor(R.styleable.BannerView_banner_actionColor, attrActionColor);
        attrTitleColor = typedArray.getColor(R.styleable.BannerView_banner_titleColor, attrTitleColor);
        attrBodyColor = typedArray.getColor(R.styleable.BannerView_banner_bodyColor, attrBodyColor);
        inflateBanner();

        typedArray.recycle();

    }

    private void inflateBanner() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.small_banner, this);
    }

    private void initializeData() {
        DatabaseBanner databaseHelper = new DatabaseBanner(context);
        bannerAdList = databaseHelper.getAllBanners();




    }


    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
       // loadAd();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    @SuppressLint("SetTextI18n")
    private void initializeInflateUI() {
        smartBannerBody = findViewById(R.id.smartBannerBody);

        adTitle = findViewById(R.id.adTitle);
        adDescription = findViewById(R.id.adDescription);
        adActionText = findViewById(R.id.textAction);
        adIcon = findViewById(R.id.adIcon);
        adBtnAction = findViewById(R.id.btnAction);
        adIconProgressBar = findViewById(R.id.adIconProgressBar);
        adViewOne = findViewById(R.id.viewOne);
        adViewTwo = findViewById(R.id.viewTwo);

        applyUI();
    }

    private void applyUI() {
        if (adBtnAction != null) {
            adBtnAction.setBackgroundColor(attrActionColor);
        }

        if (adTitle != null) {
            adTitle.setTextColor(attrTitleColor);
        }

        if (adDescription != null) {
            adDescription.setTextColor(attrTitleColor);
        }

    }

    public void loadAd() {
        try {

            if (bannerAdList != null && !bannerAdList.isEmpty()) {
                int size = bannerAdList.size();
                Random random = new Random();
                int bannerIndex = random.nextInt(size);
                bannerIndexSelected = bannerIndex;
                buildBanner(bannerIndex);
            } else {
                //the ad list is empty.
                if (onBannerListener != null) {
                    onBannerListener.onBannerAdFailedToLoad("The Ad list is empty ! please check your json file.");
                }
                setVisibility(GONE);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void refreshAd(){
        try {

            if (bannerAdList != null && !bannerAdList.isEmpty()) {
                int size = bannerAdList.size();
                Random random = new Random();
                int bannerIndex = random.nextInt(size);


                bannerIndexSelected = bannerIndex;
                buildBanner(bannerIndex);
            } else {
                //the ad list is empty.
                if (onBannerListener != null) {
                    onBannerListener.onBannerAdFailedToLoad("The Ad list is empty ! please check your json file.");
                }
                setVisibility(GONE);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void buildBanner(int index) {

        initializeInflateUI();
        String banner_name = bannerAdList.get(index).getBannerName();
        String banner_description = bannerAdList.get(index).getBannerDescription();
        String banner_action_title = bannerAdList.get(index).getBannerActionTitle();
        String banner_icon = bannerAdList.get(index).getBannerIcon();
        String banner_url = bannerAdList.get(index).getBannerIcon();




        loadIcon(banner_icon);
        adTitle.setText(banner_name);
        adDescription.setText(banner_description);
        adActionText.setText(banner_action_title);


        //make animation between two content
        animation();

        adBtnAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //user clicked own banner :
                if (onBannerListener != null) {
                    onBannerListener.onBannerAdClicked();
                }
                Utils.openAdLink(context, banner_url);
            }
        });


        if (onBannerListener != null) {
            onBannerListener.onBannerAdLoaded(BannerAd.this);
        }else {
            UFOAd.showLog(BannerAd.class.getName(), "onBannerListener", "null");

        }


    }

    private void loadIcon(String icons) {
        adIconProgressBar.setVisibility(VISIBLE);
        Glide.with(context)
                .load(icons)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        adIconProgressBar.setVisibility(GONE);
                        return false;
                    }
                })
                .into(adIcon);
    }


    private void animation() {

        for (int i = 1; i < 3; i++) {

            int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animationIndexX(finalI);
                    if (finalI == 3 - 1) {
                        animation();
                    }
                }
            }, 1000L * i * ANIMATION_SPEED);
        }

    }

    private void animationIndexX(int index) {
        switch (index) {
            case 1:
                setAnimationGone(adViewTwo);
                setAnimationVisible(adViewOne);
                break;
            case 2:
                setAnimationGone(adViewOne);
                setAnimationVisible(adViewTwo);
                break;
            case 3:
                setAnimationGone(adViewTwo);
                setAnimationVisible(adViewOne);
                break;

        }
    }


    private void setAnimationVisible(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(1f);
        view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_bottom_up));
    }

    private void setAnimationGone(View view) {
        view.setVisibility(View.GONE);
        view.setAlpha(0.3f);
        view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_bottom_down));
    }





    public void setOnBannerListener(OnBannerListener onBannerListener) {
        this.onBannerListener = onBannerListener;

    }

}
