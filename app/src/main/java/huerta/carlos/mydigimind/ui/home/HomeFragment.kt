package huerta.carlos.mydigimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import huerta.carlos.mydigimind.R
import huerta.carlos.mydigimind.ui.Task

class HomeFragment : Fragment() {
    private var adaptador: AdaptadorTareas? = null
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth

    companion object {
        var tasks = ArrayList<Task>()
        var first = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        tasks = ArrayList()
        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

        fillTasks()
        if (!tasks.isEmpty()) {
            var gridView: GridView = root.findViewById(R.id.gridView)

            adaptador = AdaptadorTareas(root.context, tasks)
            gridView.adapter = adaptador
        }

        /** if (first){
        fillTasks()
        first = false
        }

        adaptador = AdaptadorTareas(root.context, tasks)

        val gridView: GridView = root.findViewById(R.id.gridView)

        gridView.adapter = adaptador **/

        return root
    }

    fun fillTasks() {
        storage.collection("actividades")
            .whereEqualTo("email", usuario.currentUser?.email)
            .get()
            .addOnSuccessListener {
                it.forEach {
                    var dias = ArrayList<String>()
                    if (it.getBoolean("lu") == true) dias.add("Monday")
                    if (it.getBoolean("ma") == true) dias.add("Tuesday")
                    if (it.getBoolean("mi") == true) dias.add("Wednesday")
                    if (it.getBoolean("ju") == true) dias.add("Thursday")
                    if (it.getBoolean("vi") == true) dias.add("Friday")
                    if (it.getBoolean("sa") == true) dias.add("Saturday")
                    if (it.getBoolean("do") == true) dias.add("Sunday")
                    tasks!!.add(Task(it.getString("actividad")!!, dias, it.getString("tiempo")!!))
                }
                /** adaptador = AdaptadorTareas(context!!, tasks)
                gridview.adapter = adaptador **/
                // Ni idea que paso en esta parte, no me dejo hacer lo que queria.
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error, intente de nuevo", Toast.LENGTH_SHORT).show()
            }

        /** tasks.add(Task("Practice 1", arrayListOf("Tuesday"), "17:30"))
        tasks.add(Task("Practice 2", arrayListOf("Monday", "Sunday"), "17:30"))
        tasks.add(Task("Practice 3", arrayListOf("Wednesday"), "17:30"))
        tasks.add(Task("Practice 4", arrayListOf("Saturday"), "11:00"))
        tasks.add(Task("Practice 5", arrayListOf("Friday"), "13:00"))
        tasks.add(Task("Practice 6", arrayListOf("Thursday"), "10:40"))
        tasks.add(Task("Practice 7", arrayListOf("Monday"), "12:00")) **/
    }

    private class AdaptadorTareas : BaseAdapter {
        var tasks = ArrayList<Task>()
        var contexto: Context? = null

        constructor(contexto: Context, tasks: ArrayList<Task>) {
            this.contexto = contexto
            this.tasks = tasks
        }

        override fun getCount(): Int {
            return tasks.size
        }

        override fun getItem(p0: Int): Any {
            return tasks[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var task = tasks[p0]
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.task_view, null)

            var tv_title: TextView = vista.findViewById(R.id.tv_title)
            var tv_time: TextView = vista.findViewById(R.id.tv_time)
            var tv_days: TextView = vista.findViewById(R.id.tv_days)

            tv_title.setText(task.title)
            tv_time.setText(task.time)
            tv_days.setText(task.days.toString())

            return vista
        }

    }
}