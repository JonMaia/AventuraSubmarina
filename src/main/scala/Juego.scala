
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


  def calcularJugadorGanador():Color = {
    reliquiasPorJugador.toList.maxBy(_._2)._1.color
  }

  def iniciarRonda(): Unit = {

    if (numeroDeRonda == 4) throw new ExceptionFinDeJuego("El ganador es: "+calcularJugadorGanador())
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
        case AbandonarReliquia() => abandonarReliquia()
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
    if (numeroDeRonda == 3) throw new ExceptionFinDeJuego("El ganador es: "+calcularJugadorGanador())
  }

  def jugadorActual():Jugador = {
    if(tablero.submarino.tieneJugador(ronda.jugadorActual()) && tablero.jugadores() == null){
      ronda.actualizarSiguienteJugador()
      ronda.jugadorActual()}
    else
      ronda.jugadorActual()
  }

  def subirJugadoresASubmarino(jugadores: List[Jugador]):Unit = {
    jugadores.foreach(j=> tablero.subirAlSubmarinoDesdeTablero(j))
  }

  def contabilizarReliquiasPorJugador():Unit = {
    ronda.totalReliquiasPorJugadores().foreach(tup =>
      if(tablero.submarino.tieneJugador(tup._1))
        reliquiasPorJugador = reliquiasPorJugador.updated(tup._1,reliquiasPorJugador.getOrElse(tup._1,null) + tup._2)
    )
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

  def seTerminoRondaSubieronTodos():Unit = {
    contabilizarReliquiasPorJugador()
    seTerminoRonda()
  }

  def recogerReliquia(): Unit = {
    if (tablero.posicionJugadorTieneReliquia(jugadorActual()))
      ronda.recongerReliquia(tablero)
    else
      throw new ExceptionCasilleroSinReliquia()
  }

  def posicionJugadorActual():Integer = {
    posicionJugador(jugadorActual())
  }

  def totalReliquiasJugador(jugador: Jugador):Integer = {
    ronda.totalReliquiasJugador(jugador)
  }

  def obtenerReliquiaEnPosicion(posicion:Integer):Integer = {
    tablero.cantidadReliquiaEnPosicion(posicion)
  }


  def abandonarReliquia(): Unit = {
    // Se asume que el jugador abandona la primer reliquia que tenga
    if (esCasilleroLibre(posicionJugadorActual()))
      if (ronda.jugadorTieneReliquia(jugadorActual())){
        tablero.colocarCasilleroEnPosicion(ronda.obtenerPrimerReliquiaDeJugadorActual(),
          posicionJugadorActual())
      }
      else throw new ExceptionJugadorSinReliquias()
    else throw new ExceptionCasilleroOcupado()
  }


  def esCasilleroLibre(posicion:Integer):Boolean = {
    !tablero.hayReliquiaEnPosicion(posicion)
  }

  def ponerCasilleroLibreEnPosicion(posicion:Integer): Unit = {
    tablero.colocarCasilleroEnPosicion(CasilleroLibre(),posicion)
  }

  def jugadoresEstanSinReliquias():Boolean ={
    ronda.jugadoresEstanSinReliquias()
  }


  def tamanioTablero():Integer = tablero.size()
}
