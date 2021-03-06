package org.tensorflow.lite.examples.detect.camera

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.android.synthetic.main.dialog_report.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import org.tensorflow.lite.examples.detect.AnimationFab
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class VerifyImageActivity : AppCompatActivity() {
    private val api = RetrofitClient.create(FlaskApi::class.java)
    private var resultStringBreed = ""
    private var resultStringMuzzle = ""
    private var resultStringSafety = ""
    private var networkCheck = ""
    lateinit var userAddress : String

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // ?????? ????????? ???????????? ?????? ??????
    lateinit var mLastLocation: Location // ?????? ?????? ????????? ?????? ??????
    //internal lateinit var mLocationRequest: LocationRequest // ?????? ?????? ????????? ??????????????? ????????????
    internal lateinit var mLocationRequest: com.google.android.gms.location.LocationRequest // ?????? ?????? ????????? ??????????????? ????????????
    private val REQUEST_PERMISSION_LOCATION = 10

    @RequiresApi(Build.VERSION_CODES.O)
    val current = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ISO_DATE
    @RequiresApi(Build.VERSION_CODES.O)
    val formatted = current.format(formatter)

    var imageView: ImageView? = null
    var imageUri: Uri? = null
    val camRequestId = 1222
    lateinit var bodyImage : MultipartBody.Part
    var userNick = "unknown"

    var userUID : String? = null
    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database
    private val db = FirebaseFirestore.getInstance()
    lateinit var uri: Uri
    var verityValueKey : String = ""
    val nickDB = database.getReference("Nickname")
    val MY_PERMISSION_ACCESS_ALL = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_image)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

        auth = Firebase.auth
        userUID = auth.currentUser?.uid

        mLocationRequest =  LocationRequest.create().apply {

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }


        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            var permissions = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_ACCESS_ALL)
        }


        // ????????? ?????? ????????????
        nickDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //?????? ????????? ????????? ??????
                var nickData: Map<String, String> = snapshot.value as Map<String, String>
                userNick = when (nickData[userUID]) {
                    null -> "???"
                    else -> nickData[userUID]!!
                }

                Log.e("userNick22", userNick)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Error", "error nickname")
            }
        })


        val testAddress = findViewById<TextView>(R.id.tv_address)
        val testTxt = findViewById<TextView>(R.id.test)
        val btnVerify = findViewById<FloatingActionButton>(R.id.fab)
        val btnBreed = findViewById<Button>(R.id.breedGetBtn)
        val btnMuzzle = findViewById<Button>(R.id.muzzleGetBtn)
        val btnSafety = findViewById<Button>(R.id.safetyGetBtn)
        val cameraBtn = findViewById<FloatingActionButton>(R.id.fab)
        val serverBtn = findViewById<Button>(R.id.toServerBtn)
        imageView = findViewById(R.id.imageView2)

        // ?????? ???
        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(RoundedCornerTreatment()).setTopRightCornerSize(RelativeCornerSize(0.4f))
            .setTopLeftCorner(RoundedCornerTreatment()).setTopLeftCornerSize(RelativeCornerSize(0.4f))
            .build()


        testAddress.setOnClickListener {
            if (checkPermissionForLocation(this)) {
                startLocationUpdates()

            }
        }

        // ????????? ?????? ???
        cameraBtn.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val imagePath = createImage()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath)
            startActivityForResult(intent, camRequestId)
        }

        // ????????? ?????????
        serverBtn.setOnClickListener {
            sendFile("file",bodyImage)

            if (checkPermissionForLocation(this)) {
                startLocationUpdates()

            }

        }


        // ?????? ?????????
        api.getTest().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                testTxt.text = "Success"
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                testTxt.text = "Fail"
            }

        })



        // ?????? ?????? ???????????? ??????
        btnBreed.setOnClickListener {
            Log.e("resultStringBreed2", resultStringBreed)
            val intent = Intent(this, DeepLearningResultActivity::class.java)
            intent.putExtra("resultStringBreedUri", resultStringBreed)
            startActivity(intent)
        }

        // ????????? ?????? ???????????? ??????
        btnMuzzle.setOnClickListener {
            Log.e("resultStringMuzzle2", resultStringMuzzle)
            val intent = Intent(this, DeepLearningResultActivity::class.java)
            intent.putExtra("resultStringMuzzleUri", resultStringMuzzle)
            startActivity(intent)
        }

        // ?????? ?????? ???????????? ??????
        btnSafety.setOnClickListener {
            Log.e("resultStringSafety2", resultStringSafety)
            val intent = Intent(this, DeepLearningResultActivity::class.java)
            intent.putExtra("resultStringSafetyUri", resultStringSafety)
            startActivity(intent)
        }

    }


    // firebase Storage ??? ?????????
    private fun uploadImage(uri: Uri, str : String) {

        val storage = FirebaseStorage.getInstance().getReference("images/${verityValueKey}/" + str)
        storage.putFile(uri).addOnSuccessListener {
            Toast.makeText(this, "!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {

        }

    }


    // realtimeDB ??? ??????
    @RequiresApi(Build.VERSION_CODES.O)
    fun firebaseSetResult(resultBreed : Boolean, resultMuzzle : Boolean, resultSafety : Boolean) {
        val verifyRef = database.getReference("verify")
        val verifyModel = userUID?.let {
            it->
            VerityData(
                resultBreed = resultBreed,
                resultMuzzle = resultMuzzle,
                resultSafety = resultSafety,
                uid = it,
                nickname = userNick,
                date = formatted,
                address = userAddress
            )
        }
        val verifyRefPush = verifyRef.push()
        verityValueKey = verifyRefPush.key.toString()
        verifyModel?.verifyId = verifyRefPush.key.toString() //?????? ??????

        verifyRefPush.setValue(verifyModel)
        Log.d("verifyId", "${verifyModel?.verifyId}")
//        verifyRef.child(userUID!!).setValue()
    }

    fun sendFirstSetting(uri : Uri) {
        val file = File(absolutelyPath(uri, this))
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val bodyImage = MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    // ????????? ??????
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == camRequestId) {
            if (resultCode == RESULT_OK) {
                imageView!!.setImageURI(imageUri)

                val uri : Uri? = imageUri
                val file = File(absolutelyPath(uri, this))
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                bodyImage = MultipartBody.Part.createFormData("file", file.name, requestFile)

                Log.d("TAG",file.name)

                //sendFile("file",bodyImage)
            }
        }
    }

    // ????????? ??????
    private fun createImage(): Uri? {
        var uri: Uri? = null
        val resolver = contentResolver
        uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            // DCIM
            // picture
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val imgName = System.currentTimeMillis().toString()
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "$imgName.jpg")
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "My Images/")
        val finalUri = resolver.insert(uri, contentValues)
        imageUri = finalUri
        return finalUri
    }


    // ????????? ????????? ?????????
    private fun sendFile(userCd : String, image : MultipartBody.Part) {
        val service = RetrofitClient.create(FlaskApi::class.java) //???????????? ?????? ??????
        val call = service.postFile(userCd, image)!! //?????? API ?????? ??????
        val dialog = LoadingDialog(this)
        val dialogReport = ReportDialog(this)

        // ??????????????? ????????????
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        call.enqueue(object : Callback<FlaskDto> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<FlaskDto>, response: Response<FlaskDto>) {
                if (response.isSuccessful) {
                    Log.d("?????? ", "" + response.body().toString())
                    Toast.makeText(applicationContext, "?????? ??????", Toast.LENGTH_SHORT).show()

                    val resultBreed = response.body()?.result_breed
                    val resultBreedImgPath = response.body()?.result_breed_imgPath
                    val resultMuzzle = response.body()?.result_muzzle
                    val resultMuzzleImgPath = response.body()?.result_muzzle_imgPath
                    val resultSafety = response.body()?.result_safety
                    val resultSafetyImgPath = response.body()?.result_safety_imgPath

                    Log.e("resultBreedImgPath", resultBreedImgPath.toString())
                    Log.e("resultMuzzleImgPath", resultMuzzleImgPath.toString())
                    Log.e("resultSafetyImgPath", resultSafetyImgPath.toString())

                    if (resultBreedImgPath != null) {
                        resultStringBreed = resultBreedImgPath
                    }
                    if (resultMuzzleImgPath != null) {
                        resultStringMuzzle = resultMuzzleImgPath
                    }
                    if (resultSafetyImgPath != null) {
                        resultStringSafety = resultSafetyImgPath
                    }

                    val result1 = findViewById<TextView>(R.id.resultBreed)
                    // val result2 = findViewById<TextView>(R.id.result2)
                    val result3 = findViewById<TextView>(R.id.resultMuzzle)
                    // val result4 = findViewById<TextView>(R.id.result4)
                    val result5 = findViewById<TextView>(R.id.resultSafety)
                    // val result6 = findViewById<TextView>(R.id.result6)


                    // ?????? ?????????
                    val btnBreed = findViewById<Button>(R.id.breedGetBtn)
                    val btnMuzzle = findViewById<Button>(R.id.muzzleGetBtn)
                    val btnSafety = findViewById<Button>(R.id.safetyGetBtn)
                    btnBreed.isEnabled = true
                    btnMuzzle.isEnabled = true
                    btnSafety.isEnabled = true


                    if (resultBreed == true) {
                        result1.text = "??????"
                    } else result1.text = "?????????"

                    if (resultMuzzle == true) {
                        result3.text = "??????"
                    } else result3.text = "?????????"

                    if (resultSafety == true) {
                        result5.text = "??????"
                    } else result5.text = "?????????"
//                    result1.text = resultBreed.toString()
//                    // result2.text = resultBreedImgPath
//                    result3.text = resultMuzzle.toString()
//                    // result4.text = resultMuzzleImgPath
//                    result5.text = resultSafety.toString()
                    // result6.text = resultSafetyImgPath
                    Log.e("userNick", userNick)
                    // ??????????????? ?????????






                    if (resultBreed != null) {
                        if (resultMuzzle != null) {
                            if (resultSafety != null) {
                                firebaseSetResult(resultBreed, resultMuzzle, resultSafety)
                            }
                        }
                    }


                    // ????????? ?????? ?????? ????????????
                    getImageResult(resultBreedImgPath!!, "breed")

                    Handler().postDelayed({
                        getImageResult(resultMuzzleImgPath!!, "muzzle")
                    }, 1000)

                    Handler().postDelayed({
                        getImageResult(resultSafetyImgPath!!, "safety")
                    }, 1000)
//                    getImageResult(resultMuzzleImgPath!!, "muzzle")
//                    getImageResult(resultSafetyImgPath!!, "safety")

                    dialog.dismiss()


                    if (resultBreed == true && resultMuzzle == false && resultSafety == false) {
                        dialogReport.show()

                        dialogReport.report_btn.setOnClickListener {
                            Toast.makeText(applicationContext, "?????? ??????", Toast.LENGTH_SHORT).show()
                            dialogReport.dismiss()
                        }

                        dialogReport.exit_btn.setOnClickListener {
                            dialogReport.dismiss()
                        }
                    }

                } else {
                    Toast.makeText(applicationContext, "?????? ??????", Toast.LENGTH_SHORT).show()

                    // ??????????????? ?????????
                    dialog.dismiss()
                }
            }

            override fun onFailure(call: Call<FlaskDto>, t: Throwable) {
                Log.d("?????? ", t.message.toString())

                // ??????????????? ?????????
                dialog.dismiss()
            }
        })
    }


    private fun report() {

    }



    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    // ????????? ????????? ??????
    private fun getImageResult(resultString: String, value : String) {
        api.getImage(resultString)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val toString = response.body()?.byteStream()

                    val decodeStream = BitmapFactory.decodeStream(toString)
//                    val image = findViewById<ImageView>(R.id.imageView2)
//                    image.setImageBitmap(decodeStream)
                    if(decodeStream == null)
                    {
                        Log.e("Eeeeeeeeeeeeeeeeee","eeeeeeeeeeeeeeeee")
                    }
                    val imageUri1 = getImageUri(baseContext, decodeStream)
                    if (imageUri1 != null) {


                        uploadImage(imageUri1, value)
                    }

                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("?????? fail", t.message.toString())

                    // ????????? ?????? ??????
                    getImageResult(resultString!!, value)
                }
            })
    }


    // ?????? ??????
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imagePath = result.data!!.data

            val file = File(absolutelyPath(imagePath, this))
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            Log.d("TAG",file.name)
            imageView!!.setImageURI(imagePath)

            sendFile("file",body)
        }
    }

    // ????????? ??? ??????
    fun getImage(){
        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
        }
        Log.d("mediaFile","???????????? ??????")
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/* video/*"
        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
        chooserIntent.putExtra(Intent.EXTRA_TITLE,"????????? ?????? ??????????????????.")
        launcher.launch(chooserIntent)
    }

    // ???????????? ??????
    fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    // ?????? ?????? ?????? ???
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu_verity, menu)
        return true
    }

    // ?????? ??????(?????? ??????) ????????????
    private fun getAddress(position: LatLng) {
        val tttt = findViewById<TextView>(R.id.tv_address)
        val geoCoder = Geocoder(this, Locale.getDefault())
        val address =
            geoCoder.getFromLocation(position.latitude, position.longitude, 1).first()
                .getAddressLine(0)

        Log.e("Address", address)
        tttt.text = address
        userAddress = address
    }

    private fun startLocationUpdates() {

        //FusedLocationProviderClient??? ??????????????? ??????.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        // ????????? ????????? ?????? ?????? ??????????????? ???????????? ????????? ??????
        // ????????? ?????? ?????????(Looper.myLooper())?????? ??????(mLocationCallback)?????? ?????? ??????????????? ??????
        Looper.myLooper()?.let {
            mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    // ??????????????? ?????? ?????? ????????? ???????????? ??????
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // ??????????????? ?????? location ????????? onLocationChanged()??? ??????
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // ??????????????? ?????? ?????? ??????????????? ????????? ??????????????? ?????????
    fun onLocationChanged(location: Location) {
        mLastLocation = location

        Log.e("dddddd", mLastLocation.latitude.toString())
        Log.e("dddddd222", mLastLocation.longitude.toString())
        getAddress(LatLng(mLastLocation.latitude, mLastLocation.longitude))
        //text2.text = "?????? : " + mLastLocation.latitude // ?????? ??? ??????
        //text1.text = "?????? : " + mLastLocation.longitude // ?????? ??? ??????

    }


    // ?????? ????????? ????????? ???????????? ?????????
    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow ??????????????? ?????? ????????? ?????? ????????? ????????? ??????
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // ????????? ???????????? ?????? ?????? ?????? ?????????
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    // ??????????????? ?????? ?????? ??? ????????? ?????? ?????? ??????
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()

            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ ?????? ?????? ??????")
                Toast.makeText(this, "????????? ?????? ?????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        // ??????????????? ??????
        super.onStart()
        Handler().postDelayed({
            fab.show()
        }, 450)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { // ????????? ????????????
                fab.hide(AnimationFab.addVisibilityChanged)
                Handler().postDelayed({
                    finish()
                }, 300)
            }
            R.id.app_bar_album ->{
                getImage()
            }
        }
        return true
    }
}