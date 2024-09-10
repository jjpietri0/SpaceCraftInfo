package hr.jjpietri.rocketlaunchinfo.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.jjpietri.rocketlaunchinfo.R
import hr.jjpietri.rocketlaunchinfo.ROCKET_LAUNCH_PROVIDER_CONTENT_URI
import hr.jjpietri.rocketlaunchinfo.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class ItemPagerAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvCrewCapacity = itemView.findViewById<TextView>(R.id.tvCrewCapacity)
        private val tvCapability = itemView.findViewById<TextView>(R.id.tvCapability)
        private val tvHumanRated = itemView.findViewById<TextView>(R.id.tvHumanRated)
        private val tvInUse = itemView.findViewById<TextView>(R.id.tvInUse)
        private val tvMaidenFlight = itemView.findViewById<TextView>(R.id.tvMaidenFlight)
        private val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)

        fun bind(item: Item) {
            tvName.text = item.name
            tvCrewCapacity.text = item.crewCapacity.toString()
            tvCapability.text = item.capability
            tvHumanRated.text = if (item.humanRated) "Yes" else "No"
            tvInUse.text = if (item.inUse) "Yes" else "Not in use"
            tvMaidenFlight.text = item.maidenFlight
            Picasso.get()
                .load(item.imageUrl)
                .error(R.drawable.ic_launcher_foreground)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)

            ivRead.setImageResource(if (item.read) R.drawable.green_flag else R.drawable.red_flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.ivRead).setOnClickListener {
            updateItem(position)
        }
        holder.bind(items[position])
    }

    private fun updateItem(position: Int) {
        val item = items[position]
        item.read = !item.read
        context.contentResolver.update(
            ContentUris.withAppendedId(ROCKET_LAUNCH_PROVIDER_CONTENT_URI, item._id!!),
            ContentValues().apply {
                put(Item::read.name, item.read)
            },
            null,
            null
        )
        notifyItemChanged(position)
    }
}