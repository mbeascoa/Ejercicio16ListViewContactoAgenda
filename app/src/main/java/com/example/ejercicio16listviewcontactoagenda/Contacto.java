package com.example.ejercicio16listviewcontactoagenda;


import android.os.Build;

import java.util.Objects;

public class Contacto {


        private String nombre;

        private int llamadas;



        public Contacto(){}


        public Contacto(String nombre, int llamadas) {
            this.nombre = nombre;
            this.llamadas = llamadas;
        }

        public String getNombre() {
            return this.nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getLlamadas() {
            return this.llamadas;
        }

        public void setLlamadas(int llamadas) {
            this.llamadas = llamadas;
        }

        @Override
        public String toString() {
            return nombre ;
        }







    }
