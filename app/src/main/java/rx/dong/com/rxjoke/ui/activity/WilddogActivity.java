package rx.dong.com.rxjoke.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.dong.com.rxjoke.R;
import rx.dong.com.rxjoke.ui.BaseActivity;

/**
 * Created by xulu on 2016/8/26.
 */
public class WilddogActivity extends BaseActivity{
    @Bind(R.id.textView)
    TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.wilddog_test);
        ButterKnife.bind(this);
    }
}
