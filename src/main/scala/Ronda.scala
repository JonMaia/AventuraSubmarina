class Ronda(juego:Juego){

  var nivelOxigeno: Integer = 25
  var indexSiguienteJugador:Integer = 0
  var direccionJugadorActual:Direccion = _
  var casillerosPorJugadores: Map[Jugador,List[CasilleroConReliquia]] = Map()


  juego.jugadores().foreach(
    j => casillerosPorJugadores = casillerosPorJugadores.updated(j,List[CasilleroConReliquia]())
  )

  def seTerminariaElOxigeno(): Boolean = nivelOxigeno - totalCasillerosJugador(jugadorActual()) <= 0
  def hayOxigeno():Boolean = nivelOxigeno > 0

  def consumirOxigeno():Unit = {
    nivelOxigeno = nivelOxigeno - totalCasillerosJugador(jugadorActual())
    if (seTerminariaElOxigeno())
      throw new ExceptionFinDeOxigeno
  }

  def vaciarOxigeno():Unit = nivelOxigeno = 0


  def siguienteTurno():Integer = { // solo cuando cambia el turno
    if(indexSiguienteJugador == casillerosPorJugadores.size -1) 0 else indexSiguienteJugador + 1
  }

  def jugadorActual(): Jugador = juego.jugadores()(indexSiguienteJugador)

  def actualizarSiguienteJugador():Unit = indexSiguienteJugador = siguienteTurno()

  def nivelDeOxigeno():Integer = nivelOxigeno

  def totalCasillerosJugador(jugador: Jugador): Integer = casillerosPorJugadores.getOrElse(jugador,null).size

  def totalReliquiasJugador(jugador: Jugador):Integer ={
    casillerosPorJugadores.getOrElse(jugador,null).map(r => r.reliquia).sum
  }

  def totalReliquiasPorJugadores(): Map[Jugador,Integer] = {
    var reliquiasPorJugador: Map[Jugador,Integer] = Map()

    casillerosPorJugadores.foreach(
      tup =>
        reliquiasPorJugador = reliquiasPorJugador.updated(tup._1,casillerosPorJugadores.getOrElse(tup._1,null).size))

    reliquiasPorJugador
  }

  def recongerReliquia(tablero: Tablero): Unit = {
    var casillero: Casillero = tablero.recogerReliquia(juego.posicionJugadorActual())

    casillerosPorJugadores = casillerosPorJugadores.updated(juego.jugadorActual(),
      casillerosPorJugadores.getOrElse(juego.jugadorActual(),null):+ casillero.asInstanceOf[CasilleroConReliquia])
    tablero.colocarCasilleroEnPosicion(CasilleroLibre(),juego.posicionJugadorActual())
  }

  def jugadorTieneReliquia(jugador: Jugador):Boolean={
    casillerosPorJugadores.getOrElse(jugador,null).nonEmpty
  }

  def obtenerPrimerReliquiaDeJugadorActual():CasilleroConReliquia={
    var reliquiasDeJugador:List[CasilleroConReliquia] = casillerosPorJugadores.getOrElse(jugadorActual(),null)
    var primerReliquia:CasilleroConReliquia = reliquiasDeJugador.head
    reliquiasDeJugador = reliquiasDeJugador.filter(r => r != primerReliquia)

    casillerosPorJugadores.updated(jugadorActual(),reliquiasDeJugador)
    primerReliquia
  }

}
