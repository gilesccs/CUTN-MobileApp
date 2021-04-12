package sg.edu.smu.cs461.cutn_mobileapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.emitters.StreamEmitter
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import www.sanju.motiontoast.MotionToast


class Rewards : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private var running = false
    private var totalSteps = 0f
    private var prevTotal = 0f
    private var currentVoucher = "ASZ213SA"
    private var voucherPool : List<String> = listOf("SZEA331A", "ZSESA3AC", "ASE31ASP","DEE113A3")
    private val CHANNEL_ID = "channelID"
    private val CHANNEL_NAME = "channelName"
    private lateinit var NOT_MANAGER: NotificationManagerCompat
    private lateinit var NOTI: Notification
    val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
                val permission = arrayOf(Manifest.permission.ACTIVITY_RECOGNITION)
                requestPermissions(permission, 1313)
            }
        }
        createNotificationChannel()

        val notiIntent = Intent(this, Rewards::class.java)
        val pendingNotiIntent = TaskStackBuilder.create(this).run{
            addNextIntentWithParentStack(notiIntent)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        NOTI = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText("You have successfully earn a reward. Claim them now!")
                .setContentTitle("Congratulations!")
                .setSmallIcon(R.drawable.ic_baseline_beenhere_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingNotiIntent)
                .setAutoCancel(true)
                .build()

        NOT_MANAGER = NotificationManagerCompat.from(this)

        goBackHomePage()
        loadData()
        resetRewards()
        copyTextToClipboard()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        replaceVoucher()
    }

    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT).apply{
                lightColor = Color.BLUE
                enableLights(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun replaceVoucher(){
        val sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val savedCode = sharedPreferences.getString("discountCode","DLEABZ21")

        if (savedCode != null) {
            Log.i("test",savedCode)
        }
        if (savedCode != null) {
            currentVoucher = savedCode
        }

        val curr = findViewById<TextView>(R.id.voucherCode)
        curr.text = currentVoucher
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode == 1313 && (grantResults[0] == PackageManager.PERMISSION_DENIED)){
//            Toast.makeText(this,"Please accept the permission to continue!", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if(stepSensor == null) {

//            Toast.makeText(this,"Pedometer might not work in your device!", Toast.LENGTH_LONG).show()
            MotionToast.createColorToast(this,
                "",
                "Pedometer might not work in your device!",
                MotionToast.TOAST_INFO,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this,R.font.helvetica_regular))
        }else{
            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(running){
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - prevTotal.toInt()
            if(currentSteps >= 20){
                congratulations()
            }
            val curr = findViewById<TextView>(R.id.currentStepsView)
            curr.text = ("$currentSteps")

            val circularProgressBar = findViewById<CircularProgressBar>(R.id.stepprogressbar)
            circularProgressBar.apply{
                setProgressWithAnimation(currentSteps.toFloat())
            }
        }
    }

    fun copyTextToClipboard() {
        val btn = findViewById<Button>(R.id.copyBtn)
        val curr = findViewById<TextView>(R.id.voucherCode)

        btn.setOnClickListener{
            val textToCopy = curr.text
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", textToCopy)
            clipboardManager.setPrimaryClip(clipData)
//            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
            MotionToast.createColorToast(this,
                "Copied!",
                "Text copied to clipboard",
                MotionToast.TOAST_INFO,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this,R.font.helvetica_regular))
        }


    }


    fun congratulations(){
        val gz = findViewById<TextView>(R.id.congratsMsg)
        if(gz.visibility == View.INVISIBLE){
            showMsg()
            NOT_MANAGER.notify(NOTIFICATION_ID, NOTI)
            val viewKonfetti = findViewById<KonfettiView>(R.id.viewKonfetti)
            viewKonfetti.build()
                    .addColors(Color.BLUE, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(1000L)
                    .addShapes(Shape.Square, Shape.Circle)
                    .addSizes(Size(12, 5F))
                    .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
                    .streamFor(particlesPerSecond = 200, emittingTime = 5000L)
        }
//        val viewKonfetti = findViewById<KonfettiView>(R.id.viewKonfetti)
//        viewKonfetti.build()
//            .addColors(Color.BLUE, Color.MAGENTA)
//            .setDirection(0.0, 359.0)
//            .setSpeed(1f, 5f)
//            .setFadeOutEnabled(true)
//            .setTimeToLive(1000L)
//            .addShapes(Shape.Square, Shape.Circle)
//            .addSizes(Size(12, 5F))
//            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
//            .streamFor(particlesPerSecond = 50, emittingTime = 5000L)
    }

    fun resetRewards(){
        val btn = findViewById<Button>(R.id.resetBtn)
        val curr = findViewById<TextView>(R.id.currentStepsView)
        btn.setOnClickListener{
//            Toast.makeText(this,"Hold this to reset your rewards! Make sure to copy the discount code",Toast.LENGTH_LONG).show()
            MotionToast.createColorToast(this,
                "",
                "Hold this to reset your rewards! Make sure to copy the discount code!",
                MotionToast.TOAST_INFO,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this,R.font.helvetica_regular))
        }

        btn.setOnLongClickListener{
            val viewKonfetti = findViewById<KonfettiView>(R.id.viewKonfetti)
            viewKonfetti.reset()
            showMsg()
            getRandVoucher()
            replaceVoucher()
            resetSharedPref()
            prevTotal = totalSteps
            curr.text = 0.toString()
            saveData()
            true
        }
    }


    fun goBackHomePage(){
        val btn = findViewById<Button>(R.id.goBack)
        btn.setOnClickListener{
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1",prevTotal)
        editor.apply()
    }

    private fun resetSharedPref(){
        val sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1", 0f)
        editor.apply()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1",0f)
        Log.d("MainActivity","$savedNumber")
        prevTotal = savedNumber
    }

    fun showMsg(){
        val gz = findViewById<TextView>(R.id.congratsMsg)
        if(gz.visibility == View.VISIBLE){
            gz.visibility = View.INVISIBLE
        }else{
            gz.visibility = View.VISIBLE
        }

        val discount = findViewById<TextView>(R.id.discountHeader)
        if(discount.visibility == View.VISIBLE){
            discount.visibility = View.INVISIBLE
        }else{
            discount.visibility = View.VISIBLE
        }

        val vouchercode = findViewById<TextView>(R.id.voucherCode)
        if(vouchercode.visibility == View.VISIBLE){
            vouchercode.visibility = View.INVISIBLE
        }else{
            vouchercode.visibility = View.VISIBLE
        }

        val reset = findViewById<TextView>(R.id.resetBtn)
        if(reset.visibility == View.VISIBLE){
            reset.visibility = View.INVISIBLE
        }else{
            reset.visibility = View.VISIBLE
        }

        val copyBtn = findViewById<Button>(R.id.copyBtn)
        if(copyBtn.visibility == View.VISIBLE){
            copyBtn.visibility = View.INVISIBLE
        }else{
            copyBtn.visibility = View.VISIBLE
        }
    }

    fun getRandVoucher(){
        var rnds = (0..3).random()
        var currentChoice = voucherPool[rnds]
        while(currentChoice == currentVoucher){
            rnds = (0..3).random()
            currentChoice = voucherPool[rnds]
        }
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("discountCode",currentChoice)
        editor.apply()
    }
}