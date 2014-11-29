package fr.qinder.layout;

import fr.qinder.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class DrawerLayoutActivity extends Activity implements OnClickListener {

    private DrawerLayout mDrawerLayout = null;
    private Fragment mNavigationFragment = null;
    private int mDrawerLayoutContent = 0;
    private SparseArray<String> mMapListener = new SparseArray<String>();
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    protected void addListenerDrawerNavigation(int id, String className) {
        mMapListener.put(id, className);
        mNavigationFragment.getView().findViewById(id).setOnClickListener(this);
    }

    protected void setContentFragment(String className) {
        Fragment fragment;
        try {
            fragment = (Fragment) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            fragment = null;
        }
        getFragmentManager().beginTransaction().replace(mDrawerLayoutContent, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        if (mMapListener.get(v.getId()) != null) {
            setContentFragment(mMapListener.get(v.getId()));
            mDrawerLayout.closeDrawer(mNavigationFragment.getView());
        }
    }

    protected void setConfigDrawerLayout(int idDrawerLayout, int idDrawerLayoutNavigationFragment, int idDrawerLayoutContent, String defaultClassName) {
        mDrawerLayout = (DrawerLayout) findViewById(idDrawerLayout);
        mNavigationFragment = getFragmentManager().findFragmentById(idDrawerLayoutNavigationFragment);
        mDrawerLayoutContent = idDrawerLayoutContent;
        setContentFragment(defaultClassName);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    protected Fragment getDrawerLayoutNavigationFragment() {
        return mNavigationFragment;
    }

}
