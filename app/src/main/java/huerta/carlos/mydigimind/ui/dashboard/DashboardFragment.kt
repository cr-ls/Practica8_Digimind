package huerta.carlos.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import huerta.carlos.mydigimind.R
import huerta.carlos.mydigimind.ui.Task
import huerta.carlos.mydigimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

        val btnTime: Button = root.findViewById(R.id.btnTime)

        btnTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                btnTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                root.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true
            ).show()

        }

        val btnSave = root.findViewById(R.id.btnSave) as Button
        val et_titulo = root.findViewById(R.id.TaskName) as EditText
        val checkMonday = root.findViewById(R.id.MondayCB) as CheckBox
        val checkTuesday = root.findViewById(R.id.TuesdayCB) as CheckBox
        val checkWednesday = root.findViewById(R.id.WednesdayCB) as CheckBox
        val checkThursday = root.findViewById(R.id.ThursdayCB) as CheckBox
        val checkFriday = root.findViewById(R.id.FridayCB) as CheckBox
        val checkSaturday = root.findViewById(R.id.SaturdayCB) as CheckBox
        val checkSunday = root.findViewById(R.id.SundayCB) as CheckBox

        btnSave.setOnClickListener {
            var titulo = et_titulo.text.toString()
            var time = btnTime.text.toString()
            var days = ArrayList<String>()
            val actividad = hashMapOf(
                "actividad" to et_titulo.text.toString(),
                "email" to usuario.currentUser?.email.toString(),
                "do" to checkSunday.isChecked,
                "lu" to checkMonday.isChecked,
                "ma" to checkTuesday.isChecked,
                "mi" to checkWednesday.isChecked,
                "ju" to checkThursday.isChecked,
                "vi" to checkFriday.isChecked,
                "sa" to checkSaturday.isChecked,
                "tiempo" to btnTime.toString())

            storage.collection("actividades")
                .add(actividad)
                .addOnSuccessListener {
                    Toast.makeText(root.context, "Task Agregada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(root.context, "Error: intente de nuevo", Toast.LENGTH_SHORT).show()
                }
        }

        /** btnSave.setOnClickListener {
            var title = et_titulo.text.toString()
            var time = btnTime.text.toString()
            var days = ArrayList<String>()

            if (checkMonday.isChecked)
                days.add("Monday")
            if (checkTuesday.isChecked)
                days.add("Tuesday")
            if (checkWednesday.isChecked)
                days.add("Wednesday")
            if (checkThursday.isChecked)
                days.add("Thursday")
            if (checkFriday.isChecked)
                days.add("Friday")
            if (checkSaturday.isChecked)
                days.add("Saturday")
            if (checkSunday.isChecked)
                days.add("Sunday")

            var task = Task(title, days, time)

            HomeFragment.tasks.add(task)

            Toast.makeText(root.context, "New task added", Toast.LENGTH_SHORT).show()
        } **/
        return root
    }
}