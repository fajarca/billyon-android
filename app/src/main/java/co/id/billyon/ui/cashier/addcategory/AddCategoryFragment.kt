package co.id.billyon.ui.cashier.addcategory

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import co.id.billyon.R
import co.id.billyon.databinding.FragmentAddCategoryBinding
import co.id.billyon.db.entity.Category
import co.id.billyon.ui.cashier.dashboard.DashboardFragment
import co.id.billyon.util.REQUEST_IMAGE_CAPTURE
import co.id.billyon.util.Utils
import dagger.android.support.AndroidSupportInjection
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddCategoryFragment : Fragment() {

    private lateinit var viewModel: AddCategoryViewModel
    private lateinit var binding: FragmentAddCategoryBinding
    private var imageFilePath = ""
    private val TAG = AddCategoryFragment::class.java.simpleName

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_category, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddCategoryViewModel::class.java)
        binding.vm = viewModel
        binding.fragment = this
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isValid.observe(this,
                Observer {
                    it?.let {
                        if (it) {
                            val timestamp = Utils.getCurrentTimeStamp()
                            val categoryName = binding.etCategoryName.text.toString().trim()
                            val category = Category(categoryName, imageFilePath, 2, true, true, timestamp, timestamp)
                            viewModel.insertCategory(category)
                        }
                    }
                })
        viewModel.data.observe(this,
                Observer {
                    it?.let {
                        if (!it.error) {
                            findNavController().popBackStack()
                        }

                    }
                }
        )
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    fun takePicture() {
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
        val imageViewWidth = binding.ivAddCategory.width
        val imageViewHeight = binding.ivAddCategory.height
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {

                    binding.ivAddCategory.setImageBitmap(setScaledBitmap())

                }
            }

        }
    }
}
