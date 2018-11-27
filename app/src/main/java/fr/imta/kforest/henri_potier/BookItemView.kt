package fr.imta.kforest.henri_potier

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class BookItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    var titleView: TextView? = null
    var imageView: ImageView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        titleView = findViewById(R.id.book_list_title)
        imageView = findViewById(R.id.book_list_cover)
    }
}