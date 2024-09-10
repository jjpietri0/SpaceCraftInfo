package hr.jjpietri.rocketlaunchinfo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.jjpietri.rocketlaunchinfo.framework.setBooleanPreference
import hr.jjpietri.rocketlaunchinfo.framework.startActivity

class RocketLaunchReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //FOREGROUND
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()
    }
}