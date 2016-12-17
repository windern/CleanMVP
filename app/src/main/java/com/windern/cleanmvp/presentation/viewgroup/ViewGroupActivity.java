package com.windern.cleanmvp.presentation.viewgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.windern.cleanmvp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewGroupActivity extends AppCompatActivity {

    @BindView(R.id.dragLayout)
    DragLayout dragLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        ButterKnife.bind(this);

        String relations = createRelations(dragLayout.getChildCount());
        dragLayout.setRelations(relations);
        dragLayout.invalidate();
    }

    public String createRelations(int count) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                String line = String.valueOf(i) + DragLayout.WORD_SPLIT_LINE_POINT + String.valueOf(j);
                list.add(line);
            }
        }
        String relations = TextUtils.join(DragLayout.WORD_SPLIT_LINE, list);
        return relations;
    }
}
