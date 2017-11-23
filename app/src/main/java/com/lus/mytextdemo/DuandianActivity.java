package com.lus.mytextdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.lus.mytextdemo.bean.UrlBean;
import com.lus.mytextdemo.downlaod.DownloadUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DuandianActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private int max;
    private DownloadUtil mDownloadUtil;

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.delete)
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duandian);
        ButterKnife.bind(this);

        //注册事件
        EventBus.getDefault().register(this);
      //  String urlString = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";

        //如果需要全屏可放开
         /*View rootView = getLayoutInflater().from(this).inflate(R.layout.simple_player_view_player, null);
        setContentView(rootView);*/
        String url = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
        new PlayerView(this)
                .setTitle("什么")
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .forbidTouch(false)
                .setPlaySource(url)
                .startPlay();
    }
    /**
     * 订阅者处理粘性事件
     *
     */
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void ononMoonStickyEvent(UrlBean urlBean){
        String localPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/local";
        mDownloadUtil = new DownloadUtil(2, localPath, "ss.png", urlBean.getUrl(),
                this);
        mDownloadUtil.setOnDownloadListener(new DownloadUtil.OnDownloadListener() {

            @Override
            public void downloadStart(int fileSize) {
                // TODO Auto-generated method stub
                Log.w(TAG, "fileSize::" + fileSize);
                max = fileSize;
                mProgressBar.setMax(fileSize);
            }

            @Override
            public void downloadProgress(int downloadedSize) {
                // TODO Auto-generated method stub
                Log.w(TAG, "Compelete::" + downloadedSize);
                mProgressBar.setProgress(downloadedSize);
                textView.setText((int) downloadedSize * 100 / max + "%");
            }

            @Override
            public void downloadEnd() {
                // TODO Auto-generated method stub
                Log.w(TAG, "ENd");
            }
        });

    }

    @OnClick({R.id.start, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                mDownloadUtil.start();
                break;
            case R.id.delete:
                mDownloadUtil.pause();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }
}
