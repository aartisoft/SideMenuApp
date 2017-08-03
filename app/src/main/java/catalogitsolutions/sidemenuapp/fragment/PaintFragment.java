package catalogitsolutions.sidemenuapp.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import catalogitsolutions.sidemenuapp.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class PaintFragment extends Fragment implements ScreenShotable {

    public static final String PAINT = "Paint";

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    View v;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_paint, container, false);
        return v;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                PaintFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

