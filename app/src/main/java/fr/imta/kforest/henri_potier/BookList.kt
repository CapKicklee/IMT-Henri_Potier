package fr.imta.kforest.henri_potier

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BookList : Fragment() {

    private var viewAdapter: RecyclerView.Adapter<*>? = null
    private var viewManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val listView = inflater.inflate(R.layout.list_view_book, container, false) as RecyclerView

        viewManager = LinearLayoutManager(listView.context)
        val books = arguments?.getParcelableArray("books")?.toList() as List<Book>
        viewAdapter = BookAdapter(books)

        listView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

}