package com.example.czirjktibor.reminderapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.czirjktibor.reminderapp.R;
import com.example.czirjktibor.reminderapp.entitys.Color;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorSelectorFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_color_selector, container, false);

        ButterKnife.bind(this, fragment);

        return fragment;
    }

    @OnClick(R.id.blue_button)
    protected void blueColorSelected(){
        sendResult(Color.BLUE);
    }

    @OnClick(R.id.red_button)
    protected void redColorSelected(){
        sendResult(Color.RED);
    }

    @OnClick(R.id.yellow_button)
    protected void yellowColorSelected(){
        sendResult(Color.YELLOW);
    }

    @OnClick(R.id.green_button)
    protected void greenColorSelected(){
        sendResult(Color.GREEN);
    }

    @OnClick(R.id.orange_button)
    protected void orangeColorSelected(){
        sendResult(Color.ORANGE);
    }

    @OnClick(R.id.cyan_button)
    protected void cyanColorSelected(){
        sendResult(Color.CYAN);
    }

    private void sendResult(Color color) {

        if( getTargetFragment() == null ) {
            return;
        }

        Intent intent = EditorFragment.newIntent(color);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }
}
