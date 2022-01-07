package oltest.appmt.cryptocurrency

import android.app.Activity
import android.graphics.Color
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import oltest.appmt.cryptocurrency.Common
import oltest.appmt.cryptocurrency.ILoadMore
import oltest.appmt.cryptocurrency.CoinModel
import oltest.appmt.cryptocurrency.R
import kotlinx.android.synthetic.main.activity_coin.view.*
import java.lang.StringBuilder

class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var coinIcon = itemView.coinIcon
    var coinSymbol = itemView.coinSymbol
    var coinName = itemView.coinName
    var coinPrice = itemView.priceUsd
    var low = itemView.low
    var hight = itemView.hight
    var priceChange = itemView.price_change
}

class CoinAdapter(recyclerView: RecyclerView, internal var activity: Activity, var items: List<CoinModel>) : RecyclerView.Adapter<CoinViewHolder>() {

    internal var loadMore: ILoadMore? = null
    var isLoading: Boolean = false
    var visibleThreshold = 5
    var lastVisibleItem: Int = 0
    var totalItemCount: Int = 0

    init {
        val linearLayout = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayout.itemCount
                lastVisibleItem = linearLayout.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (loadMore != null)
                        loadMore!!.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    fun setLoadMore(loadMore: ILoadMore) {
        this.loadMore = loadMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.activity_coin, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coinModel = items.get(position)

        val item = holder as CoinViewHolder

        item.coinName.text = coinModel.name
        item.coinSymbol.text = coinModel.symbol
        item.coinPrice.text = coinModel.current_price
        item.low.text = coinModel.low_24h + "%"
        item.hight.text = coinModel.high_24h + "%"
        item.priceChange.text = coinModel.price_change_24h + "%"

        Picasso.with(activity.baseContext)
            .load(StringBuilder(Common.imageUrl)
                .append(coinModel.symbol!!.toLowerCase())
                .append(".png")
                .toString())
            .into(item.coinIcon)

        //Set color
        item.low.setTextColor(if (coinModel.low_24h!!.contains("-"))
            Color.parseColor("#FF0000")
        else
            Color.parseColor("#32CD32")
        )

        item.hight.setTextColor(if (coinModel.high_24h!!.contains("-"))
            Color.parseColor("#FF0000")
        else
            Color.parseColor("#32CD32")
        )

        item.priceChange.setTextColor(if (coinModel.price_change_24h!!.contains("-"))
            Color.parseColor("#FF0000")
        else
            Color.parseColor("#32CD32")
        )

    }

    fun  setLoaded(){
        isLoading = false
    }

    fun updateData(coinModels: List<CoinModel>)
    {
        this.items = coinModels
        notifyDataSetChanged()
    }

}