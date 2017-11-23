package com.lus.mytextdemo.model;

import android.util.Log;

import com.lus.mytextdemo.bean.MusicBean;
import com.lus.mytextdemo.utils.API;
import com.lus.mytextdemo.utils.APIService;
import com.lus.mytextdemo.utils.LoggingInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 卢总 on 2017/11/23.
 */

public class MusicModel implements IMusicModel{

    private OnFinish onFinish;

    //定义一个接口
    public interface OnFinish{
        void OnFinishListener(MusicBean musicBean);
    }
    //添加设置方法
    public void setOnFinish(OnFinish finish){

        this.onFinish=finish;
    }


    @Override
    public void getMusicUrl(String url) {

        //创建拦截器
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
        //构建retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.TYPE_URL)//添加url
                .addConverterFactory(GsonConverterFactory.create())//设置Gson工厂模式
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava适配器工厂模式
                .client(client)//添加拦截器
                .build();

        //通过动态代理得到网络接口对象
        APIService apiService = retrofit.create(APIService.class);
        //得到Observable被观察者
        Observable<MusicBean> homeBean = apiService.getMusicBean(url);
        homeBean.subscribeOn(Schedulers.io())//subscribeOn()主要改变的是订阅的线程，即call()执行的线程;//IO线程做耗时操作
                .observeOn(AndroidSchedulers.mainThread())//observeOn()主要改变的是发送的线程，即onNext()执行的线程。//在主线程更新UI
                //订阅观察者
                .subscribe(new Observer<MusicBean>() {

                    private List<MusicBean.SongListBean> song_list;

                    //完成时执行
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity+++++++++", "onCompleted: ");
                    }

                    //异常
                    @Override
                    public void onError(Throwable e) {
                        Log.d("MainActivity", "onError: ");

                    }

                    //加载中
                    @Override
                    public void onNext(MusicBean musicBean) {
                        Log.d("MainActivity", "onNext: ");
                        song_list = musicBean.getSong_list();
                        for (int i = 0; i < song_list.size(); i++) {
                            Log.i("xxx", song_list.get(i).getAlbum_title());
                        }
                        //通过接口方法拿到数据
                        onFinish.OnFinishListener(musicBean);

                    }
                });
    }

}

