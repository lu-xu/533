package rx.dong.com.rxjoke.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.dong.com.rxjoke.R;

public class MenuRightFragment extends Fragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.menu_layout_right, container, false);
	}
}
