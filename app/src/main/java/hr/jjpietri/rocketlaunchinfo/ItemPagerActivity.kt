package hr.jjpietri.rocketlaunchinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.jjpietri.rocketlaunchinfo.adapter.ItemPagerAdapter
import hr.jjpietri.rocketlaunchinfo.databinding.ActivityItemPagerBinding
import hr.jjpietri.rocketlaunchinfo.framework.fetchItems
import hr.jjpietri.rocketlaunchinfo.model.Item

const val POSITION = "hr.jjpietri.rocketlaunchinfo.item_pos"

class ItemPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemPagerBinding

    private lateinit var items: MutableList<Item>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPager() {
        items = fetchItems()
        position = intent.getIntExtra(POSITION, position)
        binding.viewPager.adapter = ItemPagerAdapter(this, items)
        binding.viewPager.currentItem = position
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}