package fr.imta.kforest.henri_potier

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.ClassCastException

class BookList : Fragment() {

    companion object {
        private val BOOKS = "books"
    }

    private var viewAdapter: RecyclerView.Adapter<*>? = null
    private var viewManager: RecyclerView.LayoutManager? = null
    private var listener: OnBookClickListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.book_list, container, false) as RecyclerView

        viewManager = LinearLayoutManager(view.context)

        val books = arguments?.getParcelableArray(BOOKS)?.toList() as List<Book>
        viewAdapter = BookAdapter(books) { book -> onItemClick(book)}

        view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnBookClickListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnBookClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun onItemClick(book: Book) {
        listener?.onClick(book)
    }

    interface OnBookClickListener {
        fun onClick(book: Book)
    }

}