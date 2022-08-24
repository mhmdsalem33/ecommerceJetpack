package mhmd.salem.coffe.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mhmd.salem.coffe.Room.DrinksDatabase

class DrinksDetailsViewModelFactory(
    val db  : DrinksDatabase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DrinksDetailsViewModel(db) as T
    }
}