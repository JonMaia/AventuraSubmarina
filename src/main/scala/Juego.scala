class Juego() {



  var tablero = new Tablero()
  var casillerosPorJugadores: Map[Jugador,List[CasilleroConReliquia]] = Map()
  var nivelOxigeno: Integer = _
  var indexSiguienteJugador:Integer = 0
  var direccionJugadorActual:Direccion = _

  def agregarJugador(jugador: Jugador): Unit = {
    casillerosPorJugadores = casillerosPorJugadores.updated(jugador, List[CasilleroConReliquia]())
  }

  def iniciar(jugadores:List[Jugador]): Unit = {
    jugadores.foreach(j => this.agregarJugador(j))
  }

  def iniciarRonda(): Unit = {
    nivelOxigeno = 25
  }

  def siguienteTurno():Integer = { // solo cuando cambia el turno
    //(indexSiguienteJugador == casillerosPorJugadores.size -1) ? 0 | indexSiguienteJugador + 1
    if(indexSiguienteJugador == casillerosPorJugadores.size -1) 0 else indexSiguienteJugador + 1
  }

  def consumirOxigeno():Unit = {

    // TODO tener en cuenta que el oxigeno puede llegar a cero y perderian
    nivelOxigeno = nivelOxigeno - totalCasillerosJugador(jugadorActual())
  }


  def jugadores():List[Jugador] = casillerosPorJugadores.keys.toList
  def jugadorActual(): Jugador = jugadores()(indexSiguienteJugador)

  def totalCasillerosJugador(jugador: Jugador): Integer = casillerosPorJugadores.get(jugador).size

  def jugadorSeMueveA(direccion: Direccion):Unit = direccionJugadorActual = direccion

  def mover(unidadesAMover: Int,direccion: Direccion): Unit = {
    if (unidadesAMover > 0 ){

    }
  }

  def nadar(direccion: Direccion): Unit = {

    // tiramos los dados se va a mover dados menos cantiadad de reliquias con <= 0 se queda donde esta
    var valorDado =  Randoms.lanzarDado()

    var unidadesAMover = valorDado - totalCasillerosJugador(jugadorActual())

    mover(unidadesAMover,direccion)

  }

  def iniciarTurno(acciones: List[Accion]):Unit= {

    indexSiguienteJugador

    //TODO hacer pattern matching segun las acciones

    var jugadorSeVaAMoverPara: Direccion = null

    acciones.foreach {
      case ConsumirOxigeno() => consumirOxigeno()
      case Subo() => jugadorSeVaAMoverPara = Arriba
      case Bajo() => jugadorSeVaAMoverPara = Abajo
      case Nadar() => nadar(jugadorSeVaAMoverPara)
      //case RecongerReliquia() => recogerReliquia()
      //case AbandonarReliquia() => abandonarReliquia()
      //case NoHacerNada() => noHacerNada()
    }

    actualizarSiguienteJugador()
  }



  def actualizarSiguienteJugador():Unit = indexSiguienteJugador = siguienteTurno()

  def nivelDeOxigeno():Integer = nivelOxigeno

}
