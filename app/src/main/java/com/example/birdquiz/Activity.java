package com.example.birdquiz;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.ComponentCallbacks2;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.KeyEventDispatcher;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatCallback;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

interface Activity extends LayoutInflater.Factory2, Window.Callback, KeyEvent.Callback, View.OnCreateContextMenuListener, ComponentCallbacks2, LifecycleOwner, KeyEventDispatcher.Component, ViewModelStoreOwner, ActivityCompat.OnRequestPermissionsResultCallback, ActivityCompat.RequestPermissionsRequestCodeValidator, AppCompatCallback, TaskStackBuilder.SupportParentable, ActionBarDrawerToggle.DelegateProvider, View.OnClickListener {
    @Override
    void onClick(View view);

    void onDialogPositiveClick(int selectedValue);

    void onDialogNegativeClick();
}
