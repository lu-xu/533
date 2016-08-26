package rx.dong.com.rxjoke.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.dong.com.rxjoke.R;
import rx.dong.com.rxjoke.ui.activity.WilddogActivity;

public class MenuLeftFragment extends Fragment {
    @Bind(R.id.leftfra_ln1)
    RelativeLayout left_ln1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menu, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    //    private RelativeLayout ln1;
    private void initView(View view) {
//        ln1 = view.findViewById(R.id.)
    }


    @OnClick(R.id.leftfra_ln1)
    void IntentTo() {
        startActivity(new Intent(getActivity(), WilddogActivity.class));
//        Toast.makeText(getActivity(), "我被点击了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
