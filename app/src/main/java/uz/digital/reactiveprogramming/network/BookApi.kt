package uz.digital.reactiveprogramming.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import uz.digital.reactiveprogramming.model.BookListDTO

interface BookApi {
    @GET("volumes")
    fun searchBook(@Query("q") query: String): Observable<BookListDTO>
}