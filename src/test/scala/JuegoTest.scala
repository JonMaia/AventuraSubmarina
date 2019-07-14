import org.scalatest.{FunSpec, Matchers}
import scala.util.Random



//juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar(),RecongerReliquia()))

class JuegoTest extends  FunSpec with Matchers{

  describe("Juego") {
    Random.setSeed(1)

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

      juego.posicionJugador(juego.jugadorActual()) shouldBe 3

    }



    /*it("") {

    }

    it("") {

    }
    */

  }

}
