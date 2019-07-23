import org.scalatest.{FunSpec, Matchers}
import scala.util.Random

// crear funcion desplazarRandomN(Integer)
// crear variable con lista de acciones
//


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
       juego.posicionJugador(jugadorAmarillo) shouldBe  8

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
      juego.posicionJugador(jugadorAmarillo) shouldBe  8


      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe 1

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
      juego.posicionJugador(jugadorAmarillo) shouldBe  8


      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.posicionJugador(jugadorRojo) shouldBe 1

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

      juego.posicionJugador(jugadorAmarillo) shouldBe -1
      juego.posicionJugador(jugadorRojo) shouldBe -1


      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 2

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
      //val thrown = the [ExceptionFinDeJuego] thrownBy juego.vaciarOxigeno()
      //thrown.getMessage should equal ("El ganador es: "+juego.calcularJugadorGanador())
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

      juego.obtenerReliquiaEnPosicion(4) shouldBe 1
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))

      juego.posicionJugador(jugadorAmarillo) shouldBe 4
      juego.totalReliquiasJugador(jugadorAmarillo) shouldBe 1
      juego.esCasilleroLibre(juego.posicionJugador(jugadorAmarillo)) shouldBe true

    }


    it("un jugador abandona una reliquia en un casillero libre") {
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

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 4

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))

      juego.esCasilleroLibre(7) shouldBe false
      juego.ponerCasilleroLibreEnPosicion(7)
      juego.esCasilleroLibre(7) shouldBe true

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),AbandonarReliquia()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 7
      juego.esCasilleroLibre(7) shouldBe false
    }




    it("un jugador abandona una reliquia en un casillero ocupado") {
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

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))
      juego.posicionJugador(jugadorAmarillo) shouldBe 4

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))

      juego.esCasilleroLibre(6) shouldBe false
      intercept[ExceptionCasilleroOcupado] {
        juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),AbandonarReliquia()))
      }

    }



    it("un jugador sin reliquias abandona una en un casillero libre") {
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

      juego.esCasilleroLibre(4) shouldBe false
      juego.ponerCasilleroLibreEnPosicion(4)
      juego.esCasilleroLibre(4) shouldBe true

      intercept[ExceptionJugadorSinReliquias] {
        juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),AbandonarReliquia()))
      }

      juego.posicionJugador(jugadorAmarillo) shouldBe 4
    }


    it("al iniciar nueva ronda los jugadores estan sin reliquias") {
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

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.vaciarOxigeno()

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.numeroDeRonda shouldBe 2
      juego.jugadoresEstanSinReliquias() shouldBe true

    }


    it("al iniciar nueva ronda se eliminan del tablero los casilleros libres ") {
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

      var tamanioTableroRonda1:Integer = juego.tamanioTablero()

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))
      Random.nextInt(6)
      Random.nextInt(6)

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar(),RecongerReliquia()))
      Random.nextInt(6)
      Random.nextInt(6)
      Random.nextInt(6)
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar(),RecongerReliquia()))

      juego.vaciarOxigeno()

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.numeroDeRonda shouldBe 2
      var tamanioTableroRonda2:Integer = juego.tamanioTablero()

      tamanioTableroRonda2 shouldBe tamanioTableroRonda1 - 5
    }


    it("gana el jugardor con mas dinero en reliquias acumulado") {
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
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))
      juego.nivelDeOxigeno() shouldBe 25
      juego.posicionJugador(jugadorAmarillo) shouldBe 2


      Random.nextInt(6)
      Random.nextInt(6)
      Random.nextInt(6)
      Random.nextInt(6)
      //juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar(),RecongerReliquia()))
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.nivelDeOxigeno() shouldBe 25
      juego.posicionJugador(jugadorRojo) shouldBe 1

      Random.nextInt(6)
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.nivelDeOxigeno() shouldBe 24
      juego.posicionJugador(jugadorAmarillo) shouldBe -1

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.nivelDeOxigeno() shouldBe 24
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.nivelDeOxigeno() shouldBe 23
      juego.posicionJugador(jugadorAmarillo) shouldBe -1

      Random.nextInt(6)
      Random.nextInt(6)
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.nivelDeOxigeno() shouldBe 23
      juego.posicionJugador(jugadorRojo) shouldBe 0

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.nivelDeOxigeno() shouldBe 22
      juego.posicionJugador(jugadorAmarillo) shouldBe -1

      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.nivelDeOxigeno() shouldBe 25
      juego.posicionJugador(jugadorRojo) shouldBe -1

      //juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Bajo(),Nadar()))
      juego.numeroDeRonda shouldBe 2

      juego.vaciarOxigeno()
      juego.iniciarTurno(List[Accion](ConsumirOxigeno(),Subo(),Nadar()))
      juego.numeroDeRonda shouldBe 3

      val execpt = the [ExceptionFinDeJuego] thrownBy juego.vaciarOxigeno()
      execpt.getMessage should equal ("El ganador es: "+juego.calcularJugadorGanador())
      print(execpt.getMessage)


    }





    /*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    *//*
    it("") {

    }
    */
    /*
    it("") {

    }
    */



  }

}
