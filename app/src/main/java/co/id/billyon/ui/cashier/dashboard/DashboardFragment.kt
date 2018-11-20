package co.id.billyon.ui.cashier.dashboard


import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import co.id.billyon.R
import co.id.billyon.adapter.CategoryRecyclerAdapter
import co.id.billyon.databinding.FragmentCashierDashboardBinding
import co.id.billyon.db.entity.CategoryWithProducts
import co.id.billyon.util.REQUEST_IMAGE_CAPTURE
import co.id.billyon.util.handlers.BillyonClickHandlers
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.android.support.AndroidSupportInjection
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import javax.inject.Inject


class DashboardFragment : Fragment(), BillyonClickHandlers.Dashboard, CategoryRecyclerAdapter.OnCategoryClickListener {



    lateinit var binding: FragmentCashierDashboardBinding
    private val categoryAdapter = CategoryRecyclerAdapter(arrayListOf(), this)
    private lateinit var viewModel: DashboardViewModel
    private val TAG = DashboardFragment::class.java.simpleName
    private lateinit var ivAddCategory: ImageView
    lateinit var imageFilePath: String

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cashier_dashboard, container, false)
        binding.handlers = this
        binding.apply {
            viewmodel = viewModel
            binding.executePendingBindings()

            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = categoryAdapter
        }

        viewModel.getAllCategories()
        viewModel._categoriesProducts.observe(this, Observer { data ->
            data?.let {
                categoryAdapter.refreshData(it)
            }
        })

        viewModel.isInsertCategorySuccessful.observe(this, Observer { datas ->
            datas?.let {
                if (it) {
                    Toast.makeText(activity, "Berhasil insert", Toast.LENGTH_LONG).show()
                }
            }
        })

        return binding.root
    }

    override fun onFabAddProductPressed(view: View) {
        /* val currentTimestamp = Utils.getCurrentTimeStamp();
         val categories = arrayListOf<Category>()

         categories.add(Category("Pakaian", 1, true, true, currentTimestamp, currentTimestamp))
         *//*categories.add(Category("Pakaian Dalam", 1, true, true, currentTimestamp, currentTimestamp))
        categories.add(Category("Bawahan", 2, true, true, currentTimestamp, currentTimestamp))
        categories.add(Category("Outer", 3, true, true, currentTimestamp, currentTimestamp))
        categories.add(Category("Jacket", 4, true, true, currentTimestamp, currentTimestamp))
        categories.add(Category("Sweater", 5, true, true, currentTimestamp, currentTimestamp))*//*


        viewModel.insertAllCategories(categories)*/

        //viewModel.loadPosts()
        //viewModel.deleteAllProduct()
        /*  val currentTimestampAsId = Utils.getCurrentTimestampAsId()
          val currentTimestamp = Utils.getCurrentTimeStamp()
          val product = Products(currentTimestampAsId, 1, 1, "/haha", "Kopi Susu Keluarga", 100, 80, 12000, 8000, true,true,currentTimestamp,currentTimestamp)

          viewModel.insertProduct(product)*/

        /*  val action = DashboardFragmentDirections.actionLaunchAddProduct()
          val navController = Navigation.findNavController(view)
          navController.navigate(action)*/

        /*  val currentTimestampAsId = Utils.getCurrentTimestampAsId()
          val currentTimestamp = Utils.getCurrentTimeStamp()
          val product = Products(currentTimestampAsId, 1, 1, "/haha", "Kopi Susu Keluarga", 100, 80, 12000, 8000, true, true, currentTimestamp, currentTimestamp)

          viewModel.insertProduct(product)
  */

        //takePictureIntent()
        Dexter.withActivity(activity)
                .withPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(permissionListener)
                .withErrorListener(errorPermissionListener)
                .onSameThread()
                .check()
    }
    override fun onCategorySelected(category: CategoryWithProducts) = launchProductDetails(category)
    override fun onCategoryImageSelected(category: CategoryWithProducts) = displayCreateCategoryDialog()
    override fun onCategoryImagePressed(view: View) {

    }

    fun launchProductDetails(category: CategoryWithProducts) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        val action = DashboardFragmentDirections.actionLaunchProductList()
        action.setStoreId(category.storeId.toInt())
        action.setCategoryId(category.id.toInt())
        findNavController().navigate(action, options)
    }

    fun displayCreateCategoryDialog() {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_add_category, null, false)

        val path = "/storage/emulated/0/Android/data/co.id.billyon/files/Pictures/JPEG_20181120_050429_4034703531751475390.jpg"
        val file = File(path)
        val uri = Uri.fromFile(file)

        ivAddCategory = view.findViewById(R.id.ivAddCategory)
        Glide.with(this).load(uri).into(ivAddCategory)

        ivAddCategory.setOnClickListener { view ->
            view?.let {
                takePictureIntent()
            }
        }

        val btnAddCategory = view.findViewById<Button>(R.id.btnAddCategory)
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setView(view)
        alertDialog.show()
    }


    private fun takePictureIntent() {
        try {
            val imageFile = createImageFile()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(activity?.packageManager) != null) {
                val authorities = activity?.packageName + ".fileprovider"
                val imageUri = FileProvider.getUriForFile(context!!,authorities,imageFile!!)
                //Ask for extra output, which is the image uri of the picture taken
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        } catch (e: IOException) {
           Log.v(TAG, "Error creating the file")
            // Toast.makeText(this, "Could not create file!", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {

                    ivAddCategory.setImageBitmap(setScaledBitmap())
                    val a = imageFilePath

                }
            }

        }
    }


    val permissionListener = object : MultiplePermissionsListener {
        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
            report?.let {
                // check if all permissions are granted
                if (report.areAllPermissionsGranted()) {
                    displayCreateCategoryDialog()
                }

                //One of the required permission is denied
                if (report.isAnyPermissionPermanentlyDenied) {
                    Toast.makeText(activity, "One of the required permission is denied", Toast.LENGTH_LONG).show()
                }
            }
        }

        override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
            token?.continuePermissionRequest()
        }

    }
    val errorPermissionListener = object : PermissionRequestErrorListener {
        override fun onError(error: DexterError?) {
            Toast.makeText(activity, "Permission error ${error.toString()}", Toast.LENGTH_LONG).show()
        }

    }
    fun saveOnInternalStorage(fileName : String) {
        val file = File(activity?.filesDir, fileName)
    }
    fun loadFromIntenalStorage(fileName : String) {
        val directory = activity?.filesDir
        val file = File(directory,fileName)
    }


    @Throws(IOException::class)
    fun createImageFile() : File? {
        val timestamp  = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "JPEG_${timestamp}_"
        activity?.let {
            val storageDir : File = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            if (!storageDir.exists()) storageDir.mkdirs()
            val imageFile = File.createTempFile(fileName, ".jpg", storageDir)
            imageFilePath = imageFile.absolutePath
            return imageFile
        }

        return null

    }
    fun setScaledBitmap(): Bitmap {
        val imageViewWidth = ivAddCategory.width
        val imageViewHeight = ivAddCategory.height
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFilePath, bmOptions)
        val bitmapWidth = bmOptions.outWidth
        val bitmapHeight = bmOptions.outHeight
        val scaleFactor = Math.min(bitmapWidth/imageViewWidth, bitmapHeight/imageViewHeight)
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        return BitmapFactory.decodeFile(imageFilePath, bmOptions)
    }
}
