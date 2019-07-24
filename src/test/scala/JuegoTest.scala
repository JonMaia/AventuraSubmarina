//import org.scalatest.{FunSpec, Matchers}
import org.scalatest._
import scala.util.Random


class JuegoTest extends  FunSpec with Matchers with BeforeAndAfter {

  var juego:Juego = _
  var jugadorRojo:Jugador = _
  var jugadorAmarillo:Jugador = _
  var jugadores: List[Jugador] = _

  describe("Juego") {

    val acciones_ConsumirOxigenoBajoNadar: List[Accion] = List[Accion](ConsumirOxigeno(),Bajo(),Nadar())
    val acciones_ConsumirOxigenoSuboNadar: List[Accion] = List[Accion](ConsumirOxigeno(),Subo(),Nadar())
    val acciones_ConsumirOxigenoBajoNadarRecoger: List[Accion] = acciones_ConsumirOxigenoBajoNadar :+ RecongerReliquia()
    val acciones_ConsumirOxigenoBajoNadarAbandonar: List[Accion] = acciones_ConsumirOxigenoBajoNadar :+ AbandonarReliquia()
    val acciones_ConsumirOxigenoSuboNadarRecoger: List[Accion] = acciones_ConsumirOxigenoSuboNadar :+ RecongerReliquia()
    val utils = new utils()

    before {
      Random.setSeed(1)
      utils.desplazarRandomSeedN(1)
      juego = new Juego()
      jugadorRojo = Jugador(Rojo)
      jugadorAmarillo = Jugador(Amarillo)
      jugadores = List[Jugador](jugadorAmarillo,jugadorRojo)
      juego.iniciar(jugadores)
      juego.iniciarRonda()
    }


    it("nivel de oxigeno no se modifica si no tiene reliquias") {
      juego.iniciarTurno(List[Accion](ConsumirOxigeno()))
      juego.nivelDeOxigeno shouldBe 25
    }


    it("un jugador que esta en el submarino declara que baja, tira los dados (4) y queda en el casillero 3") {
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 3
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorRojo) shouldBe 5
    }


    it("dos jugadores tiran dados y bajan salteandose los casilleros ocupados por otro jugador") {
       juego.jugadorActual() shouldBe jugadorAmarillo

       juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
       juego.posicionJugador(jugadorAmarillo) shouldBe 3
       juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
       juego.posicionJugador(jugadorRojo) shouldBe 5

       juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
       juego.posicionJugador(jugadorAmarillo) should not equal 3
       juego.posicionJugador(jugadorAmarillo) shouldBe  8
    }


    it("un jugador que tiene un contrincante atras dice que sube y lo saltea") {
      juego.jugadorActual() shouldBe jugadorAmarillo

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 3
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) should not equal 3
      juego.posicionJugador(jugadorAmarillo) shouldBe  8

      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.posicionJugador(jugadorRojo) shouldBe 1

      utils.desplazarRandomSeedN(1)
      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 4
    }


    it("un jugador sube al submarino desde el tablero") {
      juego.jugadorActual() shouldBe jugadorAmarillo

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 3

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) should not equal 3
      juego.posicionJugador(jugadorAmarillo) shouldBe  8

      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.posicionJugador(jugadorRojo) shouldBe 1

      utils.desplazarRandomSeedN(1)
      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 4

      utils.desplazarRandomSeedN(5)
      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.posicionJugador(jugadorRojo) shouldBe -1
    }


    it("jugador termina la ronda por falta de oxigeno ") {
      juego.jugadorActual() shouldBe jugadorAmarillo

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 3

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.vaciarOxigeno()
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe -1
      juego.numeroDeRonda shouldBe 2
      juego.posicionJugador(jugadorAmarillo) shouldBe -1
      juego.posicionJugador(jugadorRojo) shouldBe -1

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 2

      juego.vaciarOxigeno()
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe -1
      juego.posicionJugador(jugadorRojo) shouldBe -1
      juego.numeroDeRonda shouldBe 3
    }


    it("jugador termina la ronda porque subieron todos los jugadores al submarino") {
      juego.jugadorActual() shouldBe jugadorAmarillo

      utils.desplazarRandomSeedN(3)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 2

      utils.desplazarRandomSeedN(2)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorRojo) shouldBe 1

      utils.desplazarRandomSeedN(3)
      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe -1

      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe -1
      juego.numeroDeRonda shouldBe 2
    }


    it("Al terminar la tercer ronda se termina el juego lanzando una exception") {
      juego.jugadorActual() shouldBe jugadorAmarillo

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 3

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.vaciarOxigeno()
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.numeroDeRonda shouldBe 2

      juego.vaciarOxigeno()
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.numeroDeRonda shouldBe 3
      intercept[ExceptionFinDeJuego] { juego.vaciarOxigeno() }
    }


    it("Al terminar el juego si ningun jugador recogio reliquias ") {
      juego.jugadorActual() shouldBe jugadorAmarillo

      utils.desplazarRandomSeedN(1)
      juego.obtenerReliquiaEnPosicion(4) shouldBe 0
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarRecoger)

      juego.posicionJugador(jugadorAmarillo) shouldBe 4
      juego.totalReliquiasJugador(jugadorAmarillo) shouldBe 0
      juego.esCasilleroLibre(juego.posicionJugador(jugadorAmarillo)) shouldBe true
    }


    it("un jugador abandona una reliquia en un casillero libre") {
      juego.jugadorActual() shouldBe jugadorAmarillo

      utils.desplazarRandomSeedN(1)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarRecoger)
      juego.posicionJugador(jugadorAmarillo) shouldBe 4

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.esCasilleroLibre(7) shouldBe false
      juego.ponerCasilleroLibreEnPosicion(7)
      juego.esCasilleroLibre(7) shouldBe true

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarAbandonar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 7
      juego.esCasilleroLibre(7) shouldBe false
    }


    it("un jugador abandona una reliquia en un casillero ocupado") {
      juego.jugadorActual() shouldBe jugadorAmarillo

      utils.desplazarRandomSeedN(1)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarRecoger)
      juego.posicionJugador(jugadorAmarillo) shouldBe 4

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.esCasilleroLibre(6) shouldBe false
      intercept[ExceptionCasilleroOcupado] {juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarAbandonar)}
    }


    it("un jugador sin reliquias abandona una en un casillero libre") {

      juego.jugadorActual() shouldBe jugadorAmarillo

      utils.desplazarRandomSeedN(1)
      juego.esCasilleroLibre(4) shouldBe false
      juego.ponerCasilleroLibreEnPosicion(4)
      juego.esCasilleroLibre(4) shouldBe true

      intercept[ExceptionJugadorSinReliquias] {
        juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarAbandonar)
      }
      juego.posicionJugador(jugadorAmarillo) shouldBe 4
    }


    it("al iniciar nueva ronda los jugadores estan sin reliquias") {
      juego.jugadorActual() shouldBe jugadorAmarillo
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarRecoger)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.vaciarOxigeno()

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.numeroDeRonda shouldBe 2
      juego.jugadoresEstanSinReliquias() shouldBe true
    }


    it("al iniciar nueva ronda se eliminan del tablero los casilleros libres ") {
      juego.jugadorActual() shouldBe jugadorAmarillo

      var tamanioTableroRonda1:Integer = juego.tamanioTablero()
      utils.desplazarRandomSeedN(1)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarRecoger)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarRecoger)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarRecoger)
      
      utils.desplazarRandomSeedN(2)
      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadarRecoger)
      
      utils.desplazarRandomSeedN(3)
      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadarRecoger)
      juego.vaciarOxigeno()
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.numeroDeRonda shouldBe 2

      var tamanioTableroRonda2:Integer = juego.tamanioTablero()
      tamanioTableroRonda2 shouldBe tamanioTableroRonda1 - 5
    }


    it("gana el jugardor con mas dinero en reliquias acumulado") {

      juego.jugadorActual() shouldBe jugadorAmarillo
      utils.desplazarRandomSeedN(3)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadarRecoger)
      juego.nivelDeOxigeno() shouldBe 25
      juego.posicionJugador(jugadorAmarillo) shouldBe 2

      utils.desplazarRandomSeedN(4)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.nivelDeOxigeno() shouldBe 25
      juego.posicionJugador(jugadorRojo) shouldBe 1

      utils.desplazarRandomSeedN(1)
      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.nivelDeOxigeno() shouldBe 24
      juego.posicionJugador(jugadorAmarillo) shouldBe -1

      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.nivelDeOxigeno() shouldBe 24
      juego.posicionJugador(jugadorRojo) shouldBe 5

      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.nivelDeOxigeno() shouldBe 23
      juego.posicionJugador(jugadorAmarillo) shouldBe -1
      
      utils.desplazarRandomSeedN(2)
      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.nivelDeOxigeno() shouldBe 23
      juego.posicionJugador(jugadorRojo) shouldBe 0

      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.nivelDeOxigeno() shouldBe 22
      juego.posicionJugador(jugadorAmarillo) shouldBe -1

      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.nivelDeOxigeno() shouldBe 25
      juego.posicionJugador(jugadorRojo) shouldBe -1
      juego.numeroDeRonda shouldBe 2

      juego.vaciarOxigeno()
      juego.iniciarTurno(acciones_ConsumirOxigenoSuboNadar)
      juego.numeroDeRonda shouldBe 3

      val execpt = the [ExceptionFinDeJuego] thrownBy juego.vaciarOxigeno()
      execpt.getMessage should equal ("El ganador es: "+juego.calcularJugadorGanador())
      print(execpt.getMessage)
    }

    it("Test lanzar dado controlable") {
      juego.jugadorActual() shouldBe jugadorAmarillo
      Randoms.lanzarControlable(5)
      juego.iniciarTurno(acciones_ConsumirOxigenoBajoNadar)
      juego.posicionJugador(jugadorAmarillo) shouldBe 4
      juego.posicionJugador(jugadorRojo) shouldBe -1
    }
  }
}
