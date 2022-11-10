package com.example.zeroapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.zeroapp.util.mainActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseBindingFragment<T : ViewBinding>
    (protected val inflater: (LayoutInflater, ViewGroup?, Boolean) -> T) : Fragment() {

    protected var binding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = this.inflater(inflater, container, false)
        return binding?.root
    }


    fun showSnackBar(
        anchorView: View = mainActivity!!.bottomNavView!!,
        stringResId: Int,
        duration: Int = Snackbar.LENGTH_SHORT
    ) {
        Snackbar.make(binding!!.root, stringResId, duration)
            .setAnchorView(anchorView)
            .show()
    }

    fun showSnackBarByString(
        anchorView: View = mainActivity!!.bottomNavView!!,
        string: String,
        duration: Int = Snackbar.LENGTH_SHORT
    ) {
        Snackbar.make(binding!!.root, string, duration)
            .setAnchorView(anchorView)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
