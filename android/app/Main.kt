import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Hola, Tabnine en Kotlin!")

    val job = launch {
        delay(1000L)
        println("Coroutine completa después de 1 segundo")
    }

    println("Esperando a la coroutine...")
    job.join()
    println("Programa finalizado")
}
