package com.example.zeroapp.di

import androidx.fragment.app.Fragment
import com.example.zeroapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object DeleteDialogModule {

    @Provides
    fun provideDeleteDialog(fragment: Fragment): MaterialAlertDialogBuilder {
        val materialDialog = MaterialAlertDialogBuilder(
            fragment.requireContext()
        )
            .setTitle(R.string.dialog_delete_title)
            .setMessage(R.string.dialog_delete_message)
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }

        return materialDialog

    }

}