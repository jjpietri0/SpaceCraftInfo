package hr.jjpietri.rocketlaunchinfo.factory

import android.content.Context
import hr.jjpietri.rocketlaunchinfo.dao.RocketLaunchSqlHelper

fun getRocketLaunchRepository(context: Context?) = RocketLaunchSqlHelper(context)