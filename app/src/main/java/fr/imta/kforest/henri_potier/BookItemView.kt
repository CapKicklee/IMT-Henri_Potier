package fr.imta.kforest.henri_potier

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class BookItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    var titleView: TextView? = null
    var priceView: TextView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        titleView = findViewById(R.id.book_title)
        priceView = findViewById(R.id.book_price)
    }
}