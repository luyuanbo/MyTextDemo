package com.lus.mytextdemo.presenter;

import com.lus.mytextdemo.bean.MusicBean;
import com.lus.mytextdemo.model.MusicModel;
import com.lus.mytextdemo.view.IMusicView;

/**
 * Created by 卢总 on 2017/11/23.
 */

public class IMusicPresenter implements MusicModel.OnFinish{

    private final IMusicView iMusicView;
    private final  MusicModel musicModel;

    public IMusicPresenter(IMusicView iMusicView) {
        this.iMusicView = iMusicView;
        this.musicModel=new MusicModel();
    }

    public void getMusicUrl(String  url){
        musicModel.getMusicUrl(url);
        musicModel.setOnFinish(this);
    }

    @Override
    public void OnFinishListener(MusicBean musicBean) {
        iMusicView.getMusicData(musicBean);
    }
}
