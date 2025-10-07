package com.example.cvprofile.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.IOException

object FileUtils {

    /**
     * Copia el contenido de un Uri a un archivo en almacenamiento interno.
     *
     * @param uri Uri del archivo de origen.
     * @param context Context de la app.
     * @param filename Nombre del archivo destino.
     * @return La ruta absoluta del archivo copiado, o null si hubo error.
     */
    fun copyUriToInternalStorage(uri: Uri, context: Context, filename: String): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: return null

            val file = File(context.filesDir, filename)
            inputStream.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Elimina un archivo en almacenamiento interno.
     *
     * @param context Context de la app.
     * @param filename Nombre del archivo a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    fun deleteInternalFile(context: Context, filename: String): Boolean {
        val file = File(context.filesDir, filename)
        return file.exists() && file.delete()
    }

    fun saveImage(uri: Uri, context: Context, filename: String): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File(context.filesDir, filename)
            inputStream.use { input -> file.outputStream().use { output -> input.copyTo(output) } }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}