import org.scalatest.{FunSpec, Matchers}
import scala.util.Random



//juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar(),RecongerReliquia()))

class JuegoTest extends  FunSpec with Matchers{

  describe("Juego") {
    Random.setSeed(1)
    /*
      Orden de los numeros aleatorios:
            0 - 3 - 2 - 1 - 2 - 1 - 3 - 3 - 0 - 0 - 0 - 4
    */


    it("nivel de oxigeno no se modifica si no tiene reliquias") {
      var juego = new Juego()
      var jugadorRojo = Jugador(Rojo)
      var jugadorAmarillo = Jugador(Amarillo)
      var jugadores: List[Jugador] = List[Jugador](jugadorAmarillo,jugadorRojo)

      juego.iniciar(jugadores)
      juego.iniciarRonda()
      juego.iniciarTurno(List[Accion](ConsumirOxigeno()))

      juego.nivelDeOxigeno shouldBe 25

    }

    it("un jugador declara que baja, tira los dados y se desplaza 3 unidades hacia abajo") {
      var juego = new Juego()
      var jugadorRojo = Jugador(Rojo)
      var jugadorAmarillo = Jugador(Amarillo)
      var jugadores: List[Jugador] = List[Jugador](jugadorAmarillo,jugadorRojo)

      juego.iniciar(jugadores)
      juego.iniciarRonda()
      juego.jugadorActual() shouldBe jugadorAmarillo

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 3

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe -1
    }

    it("dos jugadores tiran dados y bajan salteandose los casilleros ocupados por otro jugador") {
      var juego = new Juego()
      var jugadorRojo = Jugador(Rojo)
      var jugadorAmarillo = Jugador(Amarillo)
      var jugadores: List[Jugador] = List[Jugador](jugadorAmarillo,jugadorRojo)

      juego.iniciar(jugadores)
      juego.iniciarRonda()
      juego.jugadorActual() shouldBe jugadorAmarillo

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 3

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe 1                       // NO ESTOY SEGURO QUE ESTO ESTE BIEN

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) should not equal 3            

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorRojo) should not equal -1              // SI NO SALE DEL SUBMARINO LA POSICION ES -1
    }

    /*
    it("") {

    }
    */

  }

}
