package com.lus.mytextdemo.utils;

import com.lus.mytextdemo.bean.MusicBean;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 卢总 on 2017/11/23.
 */

public interface APIService {

    @GET
    Observable<MusicBean> getMusicBean(@Url String url);
}
