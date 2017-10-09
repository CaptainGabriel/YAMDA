package com.dev.moviedb.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Based upon {@link LifecycleLoggingFragment}.
 */
public class LifecycleLogListFragment extends ListFragment {

  protected final String TAG = getClass().getSimpleName();

  /** {@inheritDoc} */
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Log.d(TAG, "onAttach() - the fragment is being attached to its context");
  }

  /** {@inheritDoc} */
  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    if(savedInstanceState != null) {
      // The fragment is being re-created.
      Log.d(TAG, "onCreate(): fragment re-created");

    } else {
      // The fragment is being created anew.
      Log.d(TAG, "onCreate(): fragment created anew");
    }
  }
  /** {@inheritDoc} */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = super.onCreateView(inflater, container, savedInstanceState);
    Log.d(TAG, "onCreateView()");
    return view;
  }

  /** {@inheritDoc} */
  @Override
  public void onStart(){
    super.onStart();
    Log.d(TAG, "onStart()");
  }

  /** {@inheritDoc} */
  @Override
  public void onResume(){
    super.onResume();
    Log.d(TAG, "onResume()");
  }

  /** {@inheritDoc} */
  @Override
  public void onPause(){
    super.onPause();
    Log.d(TAG, "onPause()");
  }

  /** {@inheritDoc} */
  @Override
  public void onStop(){
    super.onStop();
    Log.d(TAG, "onStop()");
  }

  /** {@inheritDoc} */
  @Override
  public void onDestroy(){
    Log.d(TAG, "onDestroy()");
    super.onDestroy();
  }

  /** {@inheritDoc} */
  @Override
  public void onDetach() {
    super.onDetach();
    Log.d(TAG, "onDetach()");
  }
}
