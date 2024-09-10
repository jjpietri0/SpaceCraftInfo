package hr.jjpietri.rocketlaunchinfo.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.jjpietri.rocketlaunchinfo.ItemPagerActivity
import hr.jjpietri.rocketlaunchinfo.POSITION
import hr.jjpietri.rocketlaunchinfo.R
import hr.jjpietri.rocketlaunchinfo.ROCKET_LAUNCH_PROVIDER_CONTENT_URI
import hr.jjpietri.rocketlaunchinfo.framework.startActivity
import hr.jjpietri.rocketlaunchinfo.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ItemAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        fun bind(item: Item) {
            tvTitle.text = item.name
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.rocketlaunch)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnLongClickListener {
            val item = items[position]
            context.contentResolver.delete(
                ContentUris.withAppendedId(ROCKET_LAUNCH_PROVIDER_CONTENT_URI, item._id!!),
                null, null
            )
            File(item.picturePath).delete()
            items.removeAt(position)
            notifyDataSetChanged()
            true
        }

        holder.itemView.setOnClickListener {
            context.startActivity<ItemPagerActivity>(POSITION, position)

        }

        holder.bind(items[position])
    }
}