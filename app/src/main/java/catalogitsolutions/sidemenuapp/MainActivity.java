package catalogitsolutions.sidemenuapp;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import catalogitsolutions.sidemenuapp.fragment.BuildingFragment;
import catalogitsolutions.sidemenuapp.fragment.BookFragment;
import catalogitsolutions.sidemenuapp.fragment.PaintFragment;
import catalogitsolutions.sidemenuapp.fragment.CaseFragment;
import catalogitsolutions.sidemenuapp.fragment.ShopFragment;
import catalogitsolutions.sidemenuapp.fragment.PartyFragment;
import catalogitsolutions.sidemenuapp.fragment.MovieFragment;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MainActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private BuildingFragment contentFragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentFragment = new BuildingFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(MainActivity.this, list, contentFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(BuildingFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(BuildingFragment.BUILDING, R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(BookFragment.BOOK, R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(PaintFragment.PAINT, R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(CaseFragment.CASE, R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ShopFragment.SHOP, R.drawable.icn_5);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(PartyFragment.PARTY, R.drawable.icn_6);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(MovieFragment.MOVIE, R.drawable.icn_7);
        list.add(menuItem7);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {

        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {


        switch (slideMenuItem.getName()) {
            case BuildingFragment.CLOSE:
                return screenShotable;

            case BuildingFragment.BUILDING:
                BuildingFragment fragment = new BuildingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                Toast.makeText(this, "Building", Toast.LENGTH_SHORT).show();
                return replaceFragment(fragment, position);

            case BookFragment.BOOK:
                BookFragment fragment1 = new BookFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment1).commit();
                Toast.makeText(this, "Book", Toast.LENGTH_SHORT).show();
                return replaceFragment(fragment1, position);

            case PaintFragment.PAINT:
                PaintFragment fragment2 = new PaintFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2).commit();
                Toast.makeText(this, "Paint", Toast.LENGTH_SHORT).show();
                return replaceFragment(fragment2, position);

            case CaseFragment.CASE:
                CaseFragment fragment3 = new CaseFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment3).commit();
                Toast.makeText(this, "Case", Toast.LENGTH_SHORT).show();
                return replaceFragment(fragment3, position);

            case ShopFragment.SHOP:
                ShopFragment fragment4 = new ShopFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment4).commit();
                Toast.makeText(this, "Shop", Toast.LENGTH_SHORT).show();
                return replaceFragment(fragment4, position);

            case PartyFragment.PARTY:
                PartyFragment fragment5 = new PartyFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment5).commit();
                Toast.makeText(this, "Party", Toast.LENGTH_SHORT).show();
                return replaceFragment(fragment5, position);

            case MovieFragment.MOVIE:
                MovieFragment fragment6 = new MovieFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment6).commit();
                Toast.makeText(this, "Movie", Toast.LENGTH_SHORT).show();
                return replaceFragment(fragment6, position);

            default:
                return replaceFragment(screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
