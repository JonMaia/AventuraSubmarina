
class Juego() {


  var tablero = new Tablero()
  var ronda:Ronda = _
  var numeroDeRonda = 1
  var reliquiasPorJugador: Map[Jugador,Integer] = Map()


  def agregarJugador(jugador: Jugador): Unit = {
    reliquiasPorJugador = reliquiasPorJugador.updated(jugador, 0)
  }

  def iniciar(jugadores:List[Jugador]): Unit = {
    jugadores.foreach(j => this.agregarJugador(j))
    jugadores.foreach(j => tablero.colocarJugadorEnSubmarino(j))
  }

  def iniciarRonda(): Unit = {
    if (numeroDeRonda == 4) throw new ExceptionFinDeJuego
    ronda = new Ronda(this)
  }

  def consumirOxigeno():Unit = ronda.consumirOxigeno()

  def jugadores():List[Jugador] = reliquiasPorJugador.keys.toList

  //def jugadorSeMueveA(direccion: Direccion):Unit = direccionJugadorActual = direccion

  def mover(unidadesAMover: Int,direccion: Direccion): Unit = {
    tablero.moverJugador(ronda.jugadorActual(),unidadesAMover,direccion)
  }

  def nadar(direccion: Direccion): Unit = {
    var valorDado =  Randoms.lanzarDado()
    //print("Valor del dado " + valorDado + "\n")
    var unidadesAMover = valorDado - ronda.totalCasillerosJugador(ronda.jugadorActual())

    mover(unidadesAMover,direccion)

  }

  def iniciarTurno(acciones: List[Accion]):Unit= {

    var jugadorSeVaAMoverPara: Direccion = Abajo

    try {
      acciones.foreach {
        case ConsumirOxigeno() => ronda.consumirOxigeno()
        case Subo() if ronda.hayOxigeno() => jugadorSeVaAMoverPara = Arriba
        case Bajo() if ronda.hayOxigeno()=> jugadorSeVaAMoverPara = Abajo
        case Nadar() if ronda.hayOxigeno() => nadar(jugadorSeVaAMoverPara)
        case RecongerReliquia() => recogerReliquia()
        //case AbandonarReliquia() => abandonarReliquia()
        //case NoHacerNada() => noHacerNada()
      }
      ronda.actualizarSiguienteJugador()
    }
    catch {
      case oxigeno: ExceptionFinDeOxigeno => seTerminoRondaPorFaltaDeOxigeno()
      case abordaje: ExceptionSubieronTodos => seTerminoRondaSubieronTodos()
    }

  }

  def posicionJugador(jugador: Jugador): Integer = {
    tablero.obtenerPosicionJugador(jugador)
  }

  def nivelDeOxigeno(): Integer = ronda.nivelOxigeno
  def vaciarOxigeno():Unit = {
    ronda.vaciarOxigeno()
    if (numeroDeRonda == 3) throw new ExceptionFinDeJuego
  }
  def jugadorActual():Jugador = ronda.jugadorActual()

  def subirJugadoresASubmarino(jugadores: List[Jugador]):Unit = {
    jugadores.foreach(j=> tablero.subirAlSubmarino(j))
  }

  def seTerminoRonda():Unit = {
    numeroDeRonda += 1
    iniciarRonda()
    subirJugadoresASubmarino(jugadores())
    tablero.reiniciar()
  }

  def seTerminoRondaPorFaltaDeOxigeno():Unit = {
    contabilizarReliquiasPorJugador()
    seTerminoRonda()
  }

  def contabilizarReliquiasPorJugador():Unit = {
    ronda.totalReliquiasPorJugadores().foreach(
      tup => reliquiasPorJugador.updated(tup._1,
        reliquiasPorJugador.getOrElse(tup._1,null) + tup._2)
    )
  }

  def seTerminoRondaSubieronTodos():Unit = {
    contabilizarReliquiasPorJugador()
    seTerminoRonda()
  }

  def recogerReliquia(): Unit = {
    if (tablero.posicionJugadorTieneReliquia(jugadorActual())){
      ronda.recongerReliquia(tablero)
    }
  }

  def posicionJugadorActual():Integer = {
    posicionJugador(jugadorActual())
  }

  def totalReliquiasJugador(jugador: Jugador):Integer = {
    ronda.totalReliquiasJugador(jugador)
  }


}
