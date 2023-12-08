package com.example.psm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatImageButton
import android.content.Intent

class CitasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_citas, container, false)
    }

    // Aquí podrías, por ejemplo, inicializar tus vistas, configurar escuchadores de eventos, etc.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonAgregarCita: AppCompatImageButton = view.findViewById(R.id.buttonAgregarCita)

        // Configura el oyente de clics para buttonAgregarCita
        buttonAgregarCita.setOnClickListener {
            // Crea una nueva Intent para abrir AddCita
            val intent = Intent(activity, AddCita::class.java)

            // Inicia la actividad
            startActivity(intent)
        }

    }
}