package com.example.cvprofile.model

data class Portafolio(
    val titulo: String,
    val descripcion: String,
    val habilidades: List<String>,
    val experiencias: List<Experiencia>,
    val proyectos: List<Proyecto>,
    val videos: List<Video>,
    val enlaces: List<EnlaceProfesional>
)
