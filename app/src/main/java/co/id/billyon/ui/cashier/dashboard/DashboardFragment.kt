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
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import co.id.billyon.R
import co.id.billyon.adapter.CategoryRecyclerAdapter
import co.id.billyon.databinding.FragmentCashierDashboardBinding
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.CategoryWithProducts
import co.id.billyon.util.REQUEST_IMAGE_CAPTURE
import co.id.billyon.util.Utils
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


class DashboardFragment : Fragment(), CategoryRecyclerAdapter.OnCategoryClickListener {


    lateinit var binding: FragmentCashierDashboardBinding
    private val categoryAdapter = CategoryRecyclerAdapter(arrayListOf(), this)
    private lateinit var viewModel: DashboardViewModel
    private val TAG = DashboardFragment::class.java.simpleName
    private lateinit var ivAddCategory: ImageView
    lateinit var imageFilePath: String

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cashier_dashboard, container, false)
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

        viewModel.addCategoryMessage.observe(this, Observer { Toast.makeText(activity, it, Toast.LENGTH_LONG).show() })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_dashboard, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_add_category -> {
                Dexter.withActivity(activity)
                        .withPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(permissionListener)
                        .withErrorListener(errorPermissionListener)
                        .onSameThread()
                        .check()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCategorySelected(category: CategoryWithProducts) = launchProductDetails(category)
    override fun onCategoryImageSelected(category: CategoryWithProducts) = displayCreateCategoryDialog()

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

        ivAddCategory = view.findViewById(R.id.ivAddCategory)
        val btnAddCategory = view.findViewById<Button>(R.id.btnAddCategory)
        val etCategoryName = view.findViewById<TextView>(R.id.etCategoryName)


        ivAddCategory.setOnClickListener { takePictureIntent() }

        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setView(view)
        val dialog = alertDialog.create()
        dialog.show()

        btnAddCategory.setOnClickListener {
            val timestamp = Utils.getCurrentTimeStamp()
            val categoryName = etCategoryName.text.toString().trim()
            val category = Category(categoryName, imageFilePath, 2, true, true, timestamp, timestamp)
            viewModel.insertCategory(category)
            dialog.dismiss()
        }
    }


    private fun takePictureIntent() {
        try {
            val imageFile = createImageFile()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(activity?.packageManager) != null) {
                val authorities = activity?.packageName + ".fileprovider"
                val imageUri = FileProvider.getUriForFile(context!!, authorities, imageFile!!)
                //Ask for extra output, which is the image uri of the picture taken
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error creating the image file")
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {

                    ivAddCategory.setImageBitmap(setScaledBitmap())

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

    @Throws(IOException::class)
    fun createImageFile(): File? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "JPEG_${timestamp}_"
        activity?.let {
            val storageDir: File = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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
        val scaleFactor = Math.min(bitmapWidth / imageViewWidth, bitmapHeight / imageViewHeight)
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        return BitmapFactory.decodeFile(imageFilePath, bmOptions)
    }
}
