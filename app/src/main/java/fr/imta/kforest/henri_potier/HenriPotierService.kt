package fr.imta.kforest.henri_potier

import retrofit2.Call
import retrofit2.http.GET

interface HenriPotierService {
    @GET("books")
    fun listBooks(): Call<Array<Book>>
}