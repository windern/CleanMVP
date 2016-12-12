package com.windern.cleanmvp.presentation.note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.windern.cleanmvp.R;
import com.windern.cleanmvp.data.database.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteActivity extends AppCompatActivity implements NoteContract.View {

    private NoteContract.Presenter presenter;

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_id)
    EditText etId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        presenter = new NotePresenter(this);
    }

    @OnClick({R.id.btn_query, R.id.btn_add, R.id.btn_query_one})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                break;
            case R.id.btn_add:
                break;
            case R.id.btn_query_one:
                break;
        }
    }

    @OnClick(R.id.btn_query)
    public void query(View view) {
        presenter.loadNotes();
    }

    @OnClick(R.id.btn_add)
    public void add(View view) {
        String content = etContent.getText().toString();
        Note note = new Note();
        note.setText(content);
        note.setDate(Calendar.getInstance().getTime());
        presenter.addNote(note);
    }

    @OnClick(R.id.btn_query_one)
    public void queryOne(View view) {
        String idStr = etId.getText().toString();
        long id = 0;
        try {
            id = Long.valueOf(idStr);
            presenter.loadNote(id);
        } catch (Exception e) {
            showToast("编号转换异常");
            return;
        }

    }

    @Override
    public void showError(String tip) {
        showToast("显示错误：" + tip);
    }

    @Override
    public void showNotes(List<Note> notes) {
        List<String> listTips = new ArrayList<>();
        for (Note note : notes) {
            listTips.add(getNoteShowTip(note));
        }
        showToast(TextUtils.join("--", listTips));
    }

    @Override
    public void showNote(Note note) {
        showToast(getNoteShowTip(note));
    }

    private void showToast(String tip) {
        Toast.makeText(NoteActivity.this, tip, Toast.LENGTH_LONG).show();
    }

    private String getNoteShowTip(Note note) {
        String tip = "id:" + note.getId() + ";"
                + "text:" + note.getText() + ";"
                + "note_type:" + note.getNoteType().getText() + ";"
                + "date:" + DateUtils.formatDateTime(NoteActivity.this, note.getDate().getTime(), DateUtils.FORMAT_SHOW_TIME) + ";";
        return tip;
    }
}
