package com.lus.mytextdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lus.mytextdemo.adapter.XRAdapter;
import com.lus.mytextdemo.bean.MusicBean;
import com.lus.mytextdemo.presenter.IMusicPresenter;
import com.lus.mytextdemo.view.IMusicView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMusicView {

    @BindView(R.id.home_xrcv)
    XRecyclerView homeXrcv;

    String url="v1/restserver/ting?method=baidu.ting.billboard.billList&type=1&size=10&offset=0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //加布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeXrcv.setLayoutManager(layoutManager);
        IMusicPresenter iMusicPresenter = new IMusicPresenter(this);
        iMusicPresenter.getMusicUrl(url);
        //设置上拉和下拉监听
        homeXrcv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
              /*  //refresh data here
                //刷新时清空
                curr=0;
                list.clear();
                getData(API.TYPE_HOME,curr);
                //刷新控件
                homeXrcv.refreshComplete();*/
            }

            @Override
            public void onLoadMore() {
                // load more data here
                //分页加载时使用
                 /*   curr++;
                getData(API.TYPE_HOME,curr);
                xr.refreshComplete();*/
            }
        });

        //加载数据

       /* getData(API.TYPE_HOME,curr);*/
    }

    @Override
    public void getMusicData(MusicBean musicBean) {
        XRAdapter xrAdapter = new XRAdapter(musicBean, MainActivity.this);
        homeXrcv.setAdapter(xrAdapter);
    }
}
