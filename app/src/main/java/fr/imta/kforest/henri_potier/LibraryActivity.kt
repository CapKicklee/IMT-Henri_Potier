package fr.imta.kforest.henri_potier

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.FrameLayout
import android.widget.LinearLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.*

class LibraryActivity : AppCompatActivity() {

    private var books: Array<Book>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)
        Timber.plant(Timber.DebugTree())
        Timber.i("Coucou")


        val landscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        // Plant logger cf. Android Timber
        val containerFrameLayout = findViewById<LinearLayout>(R.id.containerFrameLayout)
        val listBooksFrameLayout = findViewById<FrameLayout>(R.id.listBooksFrameLayout)
        if (landscape) {
            containerFrameLayout.orientation = LinearLayout.HORIZONTAL
            listBooksFrameLayout.visibility = FrameLayout.VISIBLE
        } else {
            containerFrameLayout.orientation = LinearLayout.VERTICAL
            listBooksFrameLayout.visibility = FrameLayout.GONE
        }

        if (books != null) {
            val bundle = Bundle()
            bundle.putParcelableArray("books", books)

            val bookList = BookList()
            bookList.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .replace(R.id.listBooksFrameLayout, bookList)
                    .commit()
        } else {
            getBooks()
        }
    }

    private fun getBooks() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // Create a service
        val api = retrofit.create(HenriPotierService::class.java)

        // Calling book API
        api.listBooks().enqueue(object : Callback<Array<Book>> {

            override fun onResponse(call: Call<Array<Book>>?, response: Response<Array<Book>>) {
                response.body()?.forEach {
                    Timber.i(it.title)
                }

                books = response.body()
                val bundleToSave = Bundle()
                bundleToSave.putParcelableArray("books", books)

                val bookListFragment = BookList()
                bookListFragment.arguments = bundleToSave

                supportFragmentManager.beginTransaction()
                        .replace(R.id.listBooksFrameLayout, bookListFragment)
                        .commit()

                Timber.i("Coucou")

            }

            override fun onFailure(call: Call<Array<Book>>?, t: Throwable?) {
                Timber.e("FAILURE \n%s\ncall: %s", t.toString(), call)
            }

        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArray("books", books)
        //outState.putParcelable(BOOK_SELECTED, selected)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        books = savedInstanceState.getParcelableArray("books") as Array<Book>
        //selected = savedInstanceState.getParcelable(BOOK_SELECTED)
    }
}
