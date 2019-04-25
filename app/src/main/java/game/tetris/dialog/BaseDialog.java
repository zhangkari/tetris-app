package game.tetris.dialog;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import game.tetris.utils.Logs;

public class BaseDialog extends DialogFragment {
    private static final String TAG = "BaseDialog";

    protected View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        return inflater.inflate(getLayoutId(), parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        mView = view;
        init();
    }

    protected void init() {

    }

    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onStart() {
        super.onStart();
        resetStyle();
    }

    protected void resetStyle() {
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            Logs.d(TAG, e.getMessage());
        }
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        try {
            return super.show(transaction, tag);
        } catch (Exception e) {
            Logs.d(TAG, e.getMessage());
        }
        return 0;
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }
}
