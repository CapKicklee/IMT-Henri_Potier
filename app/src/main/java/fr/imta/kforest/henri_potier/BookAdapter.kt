package fr.imta.kforest.henri_potier

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class BookAdapter(private val books: List<Book>, private val listener : (book: Book) -> Unit) : RecyclerView.Adapter<BookViewHolder>() {
    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val bookListTitle = holder.bookItemView.findViewById<TextView>(R.id.book_list_title)
        val bookListCover = holder.bookItemView.findViewById<ImageView>(R.id.book_list_cover)

        bookListTitle.text = books[position].title
        bookListCover?.let {
            Picasso.get().load(books[position].cover).into(it)
        }

        holder.bookItemView.setOnClickListener {
            listener(books[position])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        // create a new view
        val bookDetails = LayoutInflater.from(parent.context)
               .inflate(R.layout.item_view_book, parent, false) as BookItemView

        return BookViewHolder(bookDetails)
    }

}

class BookViewHolder(val bookItemView: BookItemView) : RecyclerView.ViewHolder(bookItemView)