/*
 * Copyright (C) 2014 Maigret Aurelien / Colin Julien
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.qinder.layout;

import fr.qinder.R;
import android.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class DrawerLayoutActivity extends ActionBarActivity implements OnClickListener {

    private DrawerLayout mDrawerLayout = null;
    private Fragment mNavigationFragment = null;
    private int mDrawerLayoutContent = 0;
    private SparseArray<String> mMapListener = new SparseArray<String>();
    private ActionBarDrawerToggle mDrawerToggle;

    protected void addListenerDrawerNavigation(int id, String className) {
        mMapListener.put(id, className);
        mNavigationFragment.getView().findViewById(id).setOnClickListener(this);
    }

    protected void setContentFragment(String className) {
        DrawerLayoutFragment fragment;
        try {
            fragment = (DrawerLayoutFragment) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            fragment = null;
        } catch (IllegalAccessException e) {
            fragment = null;
        } catch (ClassNotFoundException e) {
            fragment = null;
        }
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(mDrawerLayoutContent, fragment).commit();
            getSupportActionBar().setTitle(fragment.getTitle());
        }
    }

    @Override
    public void onClick(View v) {
        if (mMapListener.get(v.getId()) != null) {
            setContentFragment(mMapListener.get(v.getId()));
            mDrawerLayout.closeDrawer(mNavigationFragment.getView());
        }
    }

    protected void setConfigDrawerLayout(int idToolbar, int idDrawerLayout, int idDrawerLayoutNavigationFragment, int idDrawerLayoutContent, String defaultClassName) {
        Toolbar toolbar = (Toolbar) findViewById(idToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(idDrawerLayout);
        mNavigationFragment = getFragmentManager().findFragmentById(idDrawerLayoutNavigationFragment);
        mDrawerLayoutContent = idDrawerLayoutContent;

        setContentFragment(defaultClassName);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
