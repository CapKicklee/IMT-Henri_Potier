package fr.imta.kforest.henri_potier

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class BookFragment: Fragment() {

    companion object {
        fun setInstance(book: Book): BookFragment {
            val args = Bundle()
            args.putParcelable("currentBook", book)

            val fragment = BookFragment()
            fragment.arguments = args

            return fragment
        }
    }
    private var bookTitle: TextView? = null
    private var bookCover: ImageView? = null
    private var bookSynopsis: TextView? = null
    private var bookPrice: TextView? = null
    private var bookIsbn: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.book_details, container, false)
        val book: Book? = arguments?.getParcelable("currentBook")

        bookTitle = view.findViewById(R.id.book_details_title)
        bookCover = view.findViewById(R.id.book_details_cover)
        bookSynopsis = view.findViewById(R.id.book_details_synopsis)
        bookPrice = view.findViewById(R.id.book_details_price)
        bookIsbn = view.findViewById(R.id.book_details_isbn)

        bookTitle?.text = book?.title
        bookCover?.let {
            Picasso.get().load(book?.cover).into(it);
        }
        var synopsis = ""
        book?.synopsis?.forEach {
            synopsis += it + "\n"
        }
        bookSynopsis?.text = synopsis
        bookPrice?.text = book?.price + " €"
        bookIsbn?.text = book?.isbn


        return view
    }


}