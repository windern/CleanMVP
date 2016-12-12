package com.windern.cleanmvp.presentation.temp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.windern.cleanmvp.R;
import com.windern.cleanmvp.data.model.Temp;
import com.windern.cleanmvp.presentation.AppApplication;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TempActivity extends AppCompatActivity implements TempContract.View{

    @Inject
    TempPresenter tempPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        ButterKnife.bind(this);

        DaggerTempComponent.builder()
                .applicationComponent(((AppApplication) getApplication()).getApplicationComponent())
                .tempModule(new TempModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showTemp(Temp temp) {
        String tip = temp.id+":"+temp.name;
        Toast.makeText(TempActivity.this,tip,Toast.LENGTH_LONG).show();
    }

    private int count = 0;

    @OnClick(R.id.btn_get_one)
    void getOne(){
        count++;
        tempPresenter.loadTemp(count);
    }


}
