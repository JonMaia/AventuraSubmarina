import org.scalatest.{FunSpec, Matchers}
import scala.util.Random



class JuegoTest extends  FunSpec with Matchers{

  describe("Juego") {

    /*
      Orden de los numeros aleatorios:
            0 - 3 - 2 - 1 - 2 - 1 - 3 - 3 - 0 - 0 - 0 - 4
    */


    it("nivel de oxigeno no se modifica si no tiene reliquias") {
      Random.setSeed(1)
      var juego = new Juego()
      var jugadorRojo = Jugador(Rojo)
      var jugadorAmarillo = Jugador(Amarillo)
      var jugadores: List[Jugador] = List[Jugador](jugadorAmarillo,jugadorRojo)

      juego.iniciar(jugadores)
      juego.iniciarRonda()
      juego.iniciarTurno(List[Accion](ConsumirOxigeno()))

      juego.nivelDeOxigeno shouldBe 25

    }

    it("un jugador que esta en el submarino declara que baja, tira los dados (4) y queda en el casillero 3") {
      Random.setSeed(1)
      Random.nextInt(6)
      var juego = new Juego()
      var jugadorRojo = Jugador(Rojo)
      var jugadorAmarillo = Jugador(Amarillo)
      var jugadores: List[Jugador] = List[Jugador](jugadorAmarillo,jugadorRojo)

      juego.iniciar(jugadores)
      juego.iniciarRonda()
      juego.jugadorActual() shouldBe jugadorAmarillo

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 3                   // Dado: 4

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe 5                       // Dado: 5
    }

     it("dos jugadores tiran dados y bajan salteandose los casilleros ocupados por otro jugador") {
       Random.setSeed(1)
       Random.nextInt(6)
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
       juego.posicionJugador(jugadorRojo) shouldBe 5

       juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
       juego.posicionJugador(jugadorAmarillo) should not equal 3
       juego.posicionJugador(jugadorAmarillo) shouldBe  7

     }


    it("un jugador que tiene un contrincante atras dice que sube y lo saltea") {

      Random.setSeed(1)
      Random.nextInt(6)
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
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) should not equal 3
      juego.posicionJugador(jugadorAmarillo) shouldBe  7


      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe 2

      Random.nextInt(6)
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 4

    }


    it("un jugador sube al submarino desde el tablero") {

      Random.setSeed(1)
      Random.nextInt(6)
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
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) should not equal 3
      juego.posicionJugador(jugadorAmarillo) shouldBe  7


      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe 2

      Random.nextInt(6)
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 4

      Random.nextInt(6)
      Random.nextInt(6)
      Random.nextInt(6)
      Random.nextInt(6)
      Random.nextInt(6)
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe -1

    }


    it("jugador termina la ronda por falta de oxigeno ") {

      Random.setSeed(1)
      Random.nextInt(6)
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
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.vaciarOxigeno()

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe -1

      juego.numeroDeRonda shouldBe 2

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 3

      juego.vaciarOxigeno()

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe -1
      juego.posicionJugador(jugadorRojo) shouldBe -1

      juego.numeroDeRonda shouldBe 3

    }


    it("jugador termina la ronda porque subieron todos los jugadores al submarino") {

      Random.setSeed(1)
      Random.nextInt(6)
      var juego = new Juego()
      var jugadorRojo = Jugador(Rojo)
      var jugadorAmarillo = Jugador(Amarillo)
      var jugadores: List[Jugador] = List[Jugador](jugadorAmarillo,jugadorRojo)

      juego.iniciar(jugadores)
      juego.iniciarRonda()
      juego.jugadorActual() shouldBe jugadorAmarillo

      Random.nextInt(6)
      Random.nextInt(6)
      Random.nextInt(6)
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 2

      Random.nextInt(6)
      Random.nextInt(6)
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe 1

      Random.nextInt(6)
      Random.nextInt(6)
      Random.nextInt(6)
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe -1

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe -1

      juego.numeroDeRonda shouldBe 2
    }



    it("Al terminar la tercer ronda se termina el juego lanzando una exception") {
      Random.setSeed(1)
      Random.nextInt(6)
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
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.vaciarOxigeno()
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.numeroDeRonda shouldBe 2

      juego.vaciarOxigeno()
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.numeroDeRonda shouldBe 3

      intercept[ExceptionFinDeJuego] { juego.vaciarOxigeno() }
    }




    it("Al terminar el juego si ningun jugador recogio reliquias ") {
      Random.setSeed(1)
      Random.nextInt(6)
      Random.nextInt(6)
      var juego = new Juego()
      var jugadorRojo = Jugador(Rojo)
      var jugadorAmarillo = Jugador(Amarillo)
      var jugadores: List[Jugador] = List[Jugador](jugadorAmarillo,jugadorRojo)

      juego.iniciar(jugadores)
      juego.iniciarRonda()
      juego.jugadorActual() shouldBe jugadorAmarillo

      // Todavia no esta listo recoger reliquia
      //juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))
      //juego.posicionJugador(jugadorAmarillo) shouldBe 4

      //juego.totalReliquiasJugador(jugadorRojo) shouldBe 456

      /*
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.vaciarOxigeno()
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.numeroDeRonda shouldBe 2

      juego.vaciarOxigeno()
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.numeroDeRonda shouldBe 3

      intercept[ExceptionFinDeJuego] { juego.vaciarOxigeno() }

       */
    }



    /*
    it("") {

    }
    */


    /*
    it("") {

    }
    */

    /*
    it("") {

    }
    */
    /*
    it("") {

    }
    */



  }

}
