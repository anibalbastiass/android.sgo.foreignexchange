package com.anibalbastias.android.foreignexchange.base.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.SavedStateViewModelFactory
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
@Singleton
open class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
            viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

inline fun <reified R : ViewModel> FragmentActivity.getViewModel(factory: ViewModelFactory): R =
        ViewModelProviders.of(this, factory)[R::class.java]


inline fun <reified R : ViewModel> Fragment.getViewModel(factory: ViewModelFactory): R =
        ViewModelProviders.of(this, factory)[R::class.java]

inline fun <reified R : ViewModel> androidx.fragment.app.FragmentActivity.getViewModel(factory: SavedStateViewModelFactory): R =
    ViewModelProviders.of(this, factory)[R::class.java]


inline fun <reified R : ViewModel> androidx.fragment.app.Fragment.getViewModel(factory: SavedStateViewModelFactory): R =
    ViewModelProviders.of(this, factory)[R::class.java]