package com.example.practicadelsdksupabaselistview

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.practicadelsdksupabaselistview.Adapters.AlumnoAdapter
import com.example.practicadelsdksupabaselistview.Models.Alumno
import com.example.practicadelsdksupabaselistview.Models.Materia
import com.example.practicadelsdksupabaselistview.Services.SupabaseManager
import ec.edu.uteq.app.utils.SupabaseErrorHandler
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class mainContenedores : AppCompatActivity() {
    private var globalMaterias = ArrayList<Materia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_contenedores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val actvMaterias = findViewById<AutoCompleteTextView>(R.id.actvListaMaterias)
        val actvListaNiveles =   findViewById<AutoCompleteTextView>(R.id.actvListaNiveles)
        val lvAlumnos = findViewById<ListView>(R.id.lvAlumnos)

        // Asignamos el adaptador de niveles por código por si el XML está fallando
        val nivelesArray = resources.getStringArray(R.array.niveles)
        val adapterNiveles = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nivelesArray)
        actvListaNiveles.setAdapter(adapterNiveles)

        actvListaNiveles.setOnItemClickListener {
                parent, view, position, id ->
            
            Toast.makeText(this@mainContenedores, "Cargando materias de nivel ${position+1}...", Toast.LENGTH_SHORT).show()


            val semestreTexto = nivelesArray[position]
            val nivelInt = when(semestreTexto.lowercase()) {
                "primero" -> 1
                "segundo" -> 2
                "tercero" -> 3
                "cuarto" -> 4
                "quinto" -> 5
                "sexto" -> 6
                "séptimo", "septimo" -> 7
                "octavo" -> 8
                "noveno" -> 9
                "décimo", "decimo" -> 10
                else -> position + 1
            }

            actvMaterias.setText("")
            val lstMaterias = ArrayList<String>()
            lifecycleScope.launch {
                try {
                    val listaMaterias = ArrayList(
                        SupabaseManager.client
                            .from("materias")
                            .select {
                                filter {
                                    eq("nivel", nivelInt)
                                }
                                order("nombre", Order.ASCENDING)
                            }
                            .decodeList<Materia>()
                    )


                    globalMaterias = listaMaterias

                    for (materia in listaMaterias) {
                        lstMaterias.add(materia.nombre ?: "")
                    }
                    android.util.Log.d("SUPABASE_DEBUG", "Materias encontradas: ${lstMaterias.size}")
                    runOnUiThread {
                        Toast.makeText(this@mainContenedores, "Se encontraron ${lstMaterias.size} materias", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    android.util.Log.e("SUPABASE_DEBUG", "Error cargando materias: ${e.message}", e)
                    runOnUiThread {
                        SupabaseErrorHandler.show(this@mainContenedores, e)
                    }
                    lstMaterias.clear()
                } finally {
                    val adapter = ArrayAdapter(
                        this@mainContenedores,
                        android.R.layout.simple_spinner_dropdown_item,
                        lstMaterias
                    )

                    actvMaterias.setAdapter(adapter)

                }
            }

        }

        actvMaterias.setOnItemClickListener {
                parent, view, position, id ->

            var lstAlumnos = ArrayList<Alumno>()
            lifecycleScope.launch {
                try {
                    val materiaSeleccionada = globalMaterias[position]
                    lstAlumnos = ArrayList(
                        SupabaseManager.client
                            .from("alumnos")
                            .select {
                                order("nombres", Order.ASCENDING)
                            }
                            .decodeList<Alumno>()
                    )
                    android.util.Log.d("SUPABASE_DEBUG", "Alumnos encontrados: ${lstAlumnos.size}")
                    runOnUiThread {
                        Toast.makeText(this@mainContenedores, "Se encontraron ${lstAlumnos.size} alumnos", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    android.util.Log.e("SUPABASE_DEBUG", "Error cargando alumnos: ${e.message}", e)
                    runOnUiThread {
                        SupabaseErrorHandler.show(this@mainContenedores, e)
                    }
                }finally {
                    val adapter = AlumnoAdapter(this@mainContenedores, lstAlumnos)
                    lvAlumnos.adapter = adapter
                }
            }

        }



    }
}