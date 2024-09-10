package hr.jjpietri.rocketlaunchinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.jjpietri.rocketlaunchinfo.api.SpaceCraftWorker
import hr.jjpietri.rocketlaunchinfo.databinding.ActivitySplashScreenBinding
import hr.jjpietri.rocketlaunchinfo.framework.applyAnimation
import hr.jjpietri.rocketlaunchinfo.framework.callDelayed
import hr.jjpietri.rocketlaunchinfo.framework.getBooleanPreference
import hr.jjpietri.rocketlaunchinfo.framework.isOnline
import hr.jjpietri.rocketlaunchinfo.framework.setBooleanPreference
import hr.jjpietri.rocketlaunchinfo.framework.startActivity

private const val DELAY = 3000L

const val DATA_IMPORTED = "hr.jjpietri.rocketlaunchinfo.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        binding.ivSplash.applyAnimation(R.anim.fade)
        binding.tvSplash.applyAnimation(R.anim.blink)
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) {
                startActivity<HostActivity>()
            }
        } else {
            if (isOnline()) {
                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(SpaceCraftWorker::class.java)
                    )
                }
            } else {
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) {
                    finish()
                }
            }
        }
    }

}