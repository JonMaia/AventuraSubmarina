import org.scalatest.{FunSpec, Matchers}
import scala.util.Random

class JuegoTest extends  FunSpec with Matchers{

  describe("Juego") {
    Random.setSeed(1)

    it("") {
      var juego = new Juego()
      var jugadorRojo = Jugador(Rojo)
      var jugadorAmarillo = Jugador(Amarillo)
      var jugadores: List[Jugador] = List[Jugador](jugadorAmarillo,jugadorRojo)

      juego.iniciar(jugadores)
      juego.iniciarRonda()

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar(),RecongerReliquia()))
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))

      juego.nivelDeOxigeno shouldBe 25
      juego.jugadorActual() shouldBe jugadorRojo


    }

    /*it("") {

    }

    it("") {

    }
    */

  }

}
