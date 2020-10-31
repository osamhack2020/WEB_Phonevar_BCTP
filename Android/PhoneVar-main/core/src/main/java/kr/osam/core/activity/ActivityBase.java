package kr.osam.core.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import kr.osam.core.progressdialog.CustomProgressDialog;
import kr.osam.core.progressdialog.IProgressDialog;


public class ActivityBase extends AppCompatActivity implements IProgressDialog {
    private final String LOTTIE_FILE_NAME = "lottie_army.json";
    private FragmentManager fragmentManager;
    private CustomProgressDialog customProgressDialog;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    @Override
    public void showProgressDialog(@Nullable String message) {
        hideProgress();
        customProgressDialog = new CustomProgressDialog(context, getSupportFragmentManager(),LOTTIE_FILE_NAME, message);
        customProgressDialog.execute();
    }

    @Override
    public void showProgressDialog(@StringRes int resId) {
        hideProgress();
        customProgressDialog = new CustomProgressDialog(context, getSupportFragmentManager(), LOTTIE_FILE_NAME, context.getResources().getString(resId));
        customProgressDialog.execute();

    }

    @Override
    public void hideProgressDialog() {
        hideProgress();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgress();
    }

    private void hideProgress() {
        if (customProgressDialog != null) {
            customProgressDialog.cancel(false);
            customProgressDialog = null;
        }
    }
}
