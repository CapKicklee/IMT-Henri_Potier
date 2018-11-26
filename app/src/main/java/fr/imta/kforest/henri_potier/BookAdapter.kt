package fr.imta.kforest.henri_potier

import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class BookAdapter(private val books: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val titleView = holder.bookItemView.findViewById<TextView>(R.id.book_title)
        val imageView = holder.bookItemView.findViewById<ImageView>(R.id.book_cover)
        val priceView = holder.bookItemView.findViewById<TextView>(R.id.book_price)

        titleView.text = books[position].title
        priceView.text = books[position].price.toString() + "€"
        imageView?.let {
            Picasso.get().load(books[position].cover).into(it)
        }

    }

    class BookViewHolder(val bookItemView: BookItemView) : RecyclerView.ViewHolder(bookItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        // create a new view
        val bookDetails = LayoutInflater.from(parent.context)
               .inflate(R.layout.item_view_book, parent, false) as BookItemView
        // set the view's size, margins, paddings and layout parameters

        return BookViewHolder(bookDetails)
    }

}