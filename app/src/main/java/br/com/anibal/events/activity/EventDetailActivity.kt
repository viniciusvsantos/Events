package br.com.anibal.events.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import br.com.anibal.events.R
import br.com.anibal.events.adapter.TabsAdapterEvent
import br.com.anibal.events.domain.Arguments
import br.com.anibal.events.database.DatabaseService
import br.com.anibal.events.domain.Setting
import br.com.anibal.events.extension.loadUrl
import br.com.anibal.events.extension.setupToolBar
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

open class EventDetailActivity : BaseActivity() {

    protected val event = Arguments.event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setupToolBar(R.id.toolbar, event.name, true)
        initViews()
        setupViewPagerTabs()
        setupFAB()
        fab.setOnClickListener { onClickFab() }
    }

    private fun initViews() {
        val urlImage = Setting.server + event.image
        appBarImage.loadUrl(urlImage)
    }

    open fun setupViewPagerTabs() {
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = TabsAdapterEvent(context, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupFAB() {
        doAsync {
            val favorite = DatabaseService.isFavorite(event)
            uiThread {
                if(favorite)
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_important))
            }
        }
    }

    private fun onClickFab() {
        doAsync {
            val favorite = DatabaseService.setFavorite(event)

            uiThread {
                if(favorite) {
                    toast(getString(R.string.txt_important))
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_important))
                } else {
                    toast(getString(R.string.txt_not_important))
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_not_important))
                }
            }
        }
    }
}
