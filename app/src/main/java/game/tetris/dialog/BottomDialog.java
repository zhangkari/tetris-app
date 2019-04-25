package game.tetris.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.minmin.kari.tetris.R;

public class BottomDialog extends BaseDialog {
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_bottom;
    }

    @Override
    protected void resetStyle() {
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation);
    }

    @Override
    protected void init() {
        mView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
