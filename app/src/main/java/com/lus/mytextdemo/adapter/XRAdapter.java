package com.lus.mytextdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lus.mytextdemo.DuandianActivity;
import com.lus.mytextdemo.R;
import com.lus.mytextdemo.bean.MusicBean;
import com.lus.mytextdemo.bean.UrlBean;
import com.lus.mytextdemo.utils.GlideImaGlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 卢总 on 2017/11/23.
 */

public class XRAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MusicBean musicBean;
    Context mcontext;
    ArrayList<String> mlist;


    public XRAdapter(MusicBean musicBean, Context mcontext) {
        this.musicBean = musicBean;
        this.mcontext = mcontext;
    }

    //枚举类型
    private enum Item_Type {

        Typeone, Typetwo

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Item_Type.Typeone.ordinal()) {
            View mView = LayoutInflater.from(mcontext).inflate(R.layout.recycle_item_a, null);
            ViewHolderA viewHolder = new ViewHolderA(mView);
            return viewHolder;

        } else if (viewType == Item_Type.Typetwo.ordinal()) {

            View mView = LayoutInflater.from(mcontext).inflate(R.layout.recycle_item_b, null);
            ViewHolderB viewHolder = new ViewHolderB(mView);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //判断条目类型
        if (holder instanceof ViewHolderA) {
            mlist = new ArrayList<>();
            MusicBean.BillboardBean billboard = musicBean.getBillboard();
            mlist.add(billboard.getPic_s192());
            mlist.add(billboard.getPic_s210());
            mlist.add(billboard.getPic_s444());
            mlist.add(billboard.getPic_s640());


            //设置banner图片加载器
            ((ViewHolderA) holder).mbanner.setImageLoader(new GlideImaGlideImageLoader());
            ((ViewHolderA) holder).mbanner.setImages(mlist);

            ((ViewHolderA) holder).mbanner.start();
            ((ViewHolderA) holder).mbanner.setOnBannerListener(new OnBannerListener() {
                 @Override
                 public void OnBannerClick(int position) {
                     String s = mlist.get(position);
                     //事件发布者发布事件
                     EventBus.getDefault().postSticky(new UrlBean(s));
                      mcontext.startActivity(new Intent(mcontext, DuandianActivity.class));

                 }
             });

        } else if (holder instanceof ViewHolderB) {
            //给子布局的控件传值
           ((ViewHolderB) holder).myImageView.setImageURI(musicBean.getSong_list().get(position).getPic_big());
           ((ViewHolderB) holder).xvbTv.setText(musicBean.getSong_list().get(position).getAlbum_title());

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    //得到viewType类型 返回值赋值给onCreateViewHolder的参数
    @Override
    public int getItemViewType(int position) {
        // return super.getItemViewType(position);

        //根据下标判断类型返回布局
        if (position == 0) {
            return Item_Type.Typeone.ordinal();
        } else if (position == 1) {
            return Item_Type.Typetwo.ordinal();
        }
        return -1;


    }

    class ViewHolderA extends RecyclerView.ViewHolder {
        public Banner mbanner;

        public ViewHolderA(View itemView) {
            super(itemView);
            mbanner = (Banner) itemView.findViewById(R.id.banner);

        }
    }


    class ViewHolderB extends RecyclerView.ViewHolder {
        //得到控件
        @BindView(R.id.my_image_view)
        SimpleDraweeView myImageView;
        @BindView(R.id.xvb_tv)
        TextView xvbTv;

        public ViewHolderB(View itemView) {
            super(itemView);
            //初始化ButterKnife
            ButterKnife.bind(this, itemView);

        }
    }
}
