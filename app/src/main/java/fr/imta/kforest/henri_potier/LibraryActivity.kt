package fr.imta.kforest.henri_potier

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.LinearLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class LibraryActivity : AppCompatActivity(), BookList.OnBookClickListener {

    private var landscapeMode: Boolean = false
    private var booklist: Array<Book>? = null
    private var selected: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)
        landscapeMode = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val containerLayout = findViewById<LinearLayout>(R.id.containerLayout)
        val bookDetailsFrame = findViewById<FrameLayout>(R.id.book_details_frame)
        if (landscapeMode) {
            containerLayout.orientation = LinearLayout.HORIZONTAL
            bookDetailsFrame.visibility = FrameLayout.VISIBLE
        } else {
            containerLayout.orientation = LinearLayout.VERTICAL
            bookDetailsFrame.visibility = FrameLayout.GONE
        }

        Timber.plant(Timber.DebugTree())

        if (booklist != null) {
            val bundle = Bundle()
            bundle.putParcelableArray("books", booklist)

            val bookList = BookList()
            bookList.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .replace(R.id.list_books_frame, bookList)
                    .commit()

            selected?.let {
                onClick(it)
            }
        } else {
            getBooks()
        }
    }

    private fun getBooks() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(HenriPotierService::class.java)

        api.listBooks().enqueue(object : Callback<Array<Book>> {
            override fun onFailure(call: Call<Array<Book>>, t: Throwable) {
                Timber.i("Something went wrong while fetching books : " + t.message)
            }

            override fun onResponse(call: Call<Array<Book>>,
                                    response: Response<Array<Book>>)   {
                booklist = response.body()

                val bundle = Bundle()
                val bookList = BookList()

                bundle.putParcelableArray("books", booklist)
                bookList.arguments = bundle

                supportFragmentManager.beginTransaction()
                        .replace(R.id.list_books_frame, bookList)
                        .commit()

                selected?.apply {
                    onClick(this)
                }
            }
        })
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArray("books", booklist)
        outState.putParcelable("book", selected)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        booklist = savedInstanceState.getParcelableArray("books") as Array<Book>
        selected = savedInstanceState.getParcelable("book")
    }

    override fun onClick(book: Book) {
        selected = book
        val fragment = BookFragment.setInstance(book)

        if (landscapeMode) {
            supportFragmentManager.beginTransaction()
                    .replace(if (landscapeMode) R.id.book_details_frame else R.id.list_books_frame , fragment)
                    .commit()
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(if (landscapeMode) R.id.book_details_frame else R.id.list_books_frame , fragment)
                    .addToBackStack(BookFragment::class.java.name)
                    .commit()
        }

    }
}
