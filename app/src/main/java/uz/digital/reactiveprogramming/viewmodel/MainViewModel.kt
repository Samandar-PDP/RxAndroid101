package uz.digital.reactiveprogramming.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uz.digital.reactiveprogramming.model.BookListDTO
import uz.digital.reactiveprogramming.model.RetrofitInstance

class MainViewModel : ViewModel() {
    private val _liveData: MutableLiveData<BookListDTO> = MutableLiveData()
    val liveData: LiveData<BookListDTO> get() = _liveData

    fun searchBook(query: String) {
        val response = RetrofitInstance.provideBookApi()
        response.searchBook(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBoolList())
    }

    private fun getBoolList(): Observer<BookListDTO> {
        return object : Observer<BookListDTO> {
            override fun onSubscribe(d: Disposable) {
                Log.d("ViewModel", "onSubscribe: $d")
            }

            override fun onNext(t: BookListDTO) {
                _liveData.postValue(t)
            }

            override fun onError(e: Throwable) {
                Log.d("ViewModel", "onError: ${e.message}")
            }

            override fun onComplete() {
                Log.d("ViewModel", "onComplete: ")
            }
        }
    }
}