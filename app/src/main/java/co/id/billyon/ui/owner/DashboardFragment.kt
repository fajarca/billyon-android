package co.id.billyon.ui.owner


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import co.id.billyon.R
import co.id.billyon.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class DashboardFragment : Fragment() {

    lateinit var binding : FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container,false)
        val view = binding.root

        binding.contentEmployeeNumber.tvEmployeeNumber.text = "80"

        return view
    }

    //Cubic line chart
    //https://stackoverflow.com/questions/34181849/drawing-a-cubic-line-chart-using-mpandroid-chart
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entries = arrayListOf<Entry>()
        val entries2 = arrayListOf<Entry>()

        for (i in 1..30) {
            entries.add(Entry(i.toFloat(), (0..1000).random().toFloat()))
            entries2.add(Entry(i.toFloat(), (0..1000).random().toFloat()))
        }
        /*entries.add(Entry(10f,1000f))
        entries.add(Entry(20f,255f))
        entries.add(Entry(25f,512f))
        entries.add(Entry(30f,256f))
        entries.add(Entry(50f,900f))
        entries.add(Entry(70f,400f))
        entries.add(Entry(90f,1200f))

        entries2.add(Entry(30f,100f))
        entries2.add(Entry(70f,355f))
        entries2.add(Entry(85f,712f))
        entries2.add(Entry(90f,356f))
        entries2.add(Entry(100f,400f))
        entries2.add(Entry(110f,700f))
        entries2.add(Entry(120f,900f))*/


        val firstDataSet = LineDataSet(entries,"Nett Revenue")
        firstDataSet.color = resources.getColor(R.color.colorAccent)
        firstDataSet.valueTextColor = resources.getColor(R.color.grey_900)

        //For cubic lines:
        firstDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        //To Fill area below line, disable displayed values:
        firstDataSet.setDrawFilled(true)
        //Set fill color and line color:
        firstDataSet.setFillColor(resources.getColor(R.color.pale_green));
        firstDataSet.setColor(resources.getColor(R.color.pale_green));
        //Disable transparency (values range 0-255) and disable drawing circles on the main chart line:
        firstDataSet.setFillAlpha(255)

        val secondDataSet = LineDataSet(entries2, "Gross Revenue")
        secondDataSet.color = resources.getColor(R.color.colorPrimaryDark)
        secondDataSet.valueTextColor = resources.getColor(R.color.grey_900)
        //For cubic lines:
        secondDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        //To Fill area below line, disable displayed values:
        secondDataSet.setDrawFilled(true)
        //Set fill color and line color:
        secondDataSet.setFillColor(resources.getColor(R.color.pink_500));
        secondDataSet.setColor(resources.getColor(R.color.pink_500));
        //Disable transparency (values range 0-255) and disable drawing circles on the main chart line:
        secondDataSet.setFillAlpha(255)



        val dataSets = arrayListOf<ILineDataSet>()
        dataSets.add(firstDataSet)
       // dataSets.add(secondDataSet)

        val lineData = LineData(dataSets)
        binding.contentRevenue.lineChart.data = lineData


        //To remove horizontal grid lines:
        binding.contentRevenue.lineChart.xAxis.setDrawGridLines(false)

        //Put x axis on the bottom
        binding.contentRevenue.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        binding.contentRevenue.lineChart.description.isEnabled = false
        binding.contentRevenue.lineChart.invalidate()
    }

    fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

}
