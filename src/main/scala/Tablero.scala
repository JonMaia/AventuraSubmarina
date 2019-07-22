
class Tablero() {

  var submarino: Submarino = Submarino()
  var casilleros: List[(Casillero,Jugador,Integer)] = List()
  crearCasilleros()

  def crearCasilleros(): Unit = {

    for (i <- 0 to 31){
      if(i <= 7)
        casilleros = casilleros :+ (CasilleroConReliquia(Baja, Randoms.valorDeReliquia(Baja)),null,i.asInstanceOf[Integer])

      if(i >= 8 && i <= 15)
        casilleros = casilleros :+ (CasilleroConReliquia(Media, Randoms.valorDeReliquia(Media)),null,i.asInstanceOf[Integer])

      if(i >= 16 && i <= 23)
        casilleros = casilleros :+ (CasilleroConReliquia(Alta, Randoms.valorDeReliquia(Alta)),null,i.asInstanceOf[Integer])

      if(i >= 24 && i <= 31)
        casilleros = casilleros :+ (CasilleroConReliquia(Maxima, Randoms.valorDeReliquia(Maxima)),null,i.asInstanceOf[Integer])
    }
  }

  def moverJugador(jugador:Jugador, unidades:Integer, direccion: Direccion):Unit = {

    direccion match{
      //case Arriba if estaCasilleroInicial(jugador) => subirAlSubmarino(jugador:Jugador)
      case Arriba => subir(jugador:Jugador,unidades:Integer)

      case Abajo if submarino.tieneJugador(jugador) => bajarDeSubmarino(jugador:Jugador,unidades:Integer)
      case Abajo => bajar(jugador:Jugador,unidades:Integer)
    }
  }

  def hayOtroJugadorEnCasillero(nuevoCasillero:Integer, jugador: Jugador): Boolean ={

    var hayOtroJugador:Boolean = false
    casilleros.foreach(tup => hayOtroJugador = hayOtroJugador ||
                                         (tup._3 == nuevoCasillero && tup._2 != null && tup._2 != jugador))
    hayOtroJugador
  }


  def sacarJugadorDeCasillero(jugador: Jugador,casillero: Integer): Unit = {
    var nuevosCasilleros: List[(Casillero,Jugador,Integer)] = List()

    casilleros.foreach( tup => if(tup._2 == jugador) nuevosCasilleros = nuevosCasilleros :+ (tup._1,null,tup._3)
                                else nuevosCasilleros = nuevosCasilleros :+ tup
    )
    casilleros = nuevosCasilleros
  }

  def posicionarJugadorEnCasillero(jugador: Jugador,casillero:Integer): Unit ={
    var nuevosCasilleros: List[(Casillero,Jugador,Integer)] = List()

    casilleros.foreach(
      tup =>  if(tup._3 == casillero)
                nuevosCasilleros = nuevosCasilleros :+ (tup._1,jugador,tup._3)
              else
                nuevosCasilleros = nuevosCasilleros :+ tup)
    casilleros = nuevosCasilleros
  }

  def obtenerPosicionJugador(jugador: Jugador): Integer ={
    if (submarino.tieneJugador(jugador))-1
    else casilleros.filter(tup => tup._2 == jugador).head._3
  }


  def subir(jugador: Jugador, unidades: Integer): Unit ={
    var posicionActual:Integer = obtenerPosicionJugador(jugador)
    var desplazamiento:Integer = 0

    // TODO : REVISAR CONDICION DE HAYOTROJUGADORENCASILLERO
    (0 to unidades).foreach( _ =>
      desplazamiento = desplazamiento + (if(hayOtroJugadorEnCasillero(posicionActual-desplazamiento,jugador)) 2 else 1)
    )
    if (unidades != 0) {
      sacarJugadorDeCasillero(jugador, posicionActual)
      if (posicionActual - desplazamiento < 0 )
        subirAlSubmarino(jugador,esFinDeRonda = true)
      else
        posicionarJugadorEnCasillero(jugador,posicionActual - desplazamiento)
    }
    //if (posicionActual - desplazamiento < 0 )
    //  subirAlSubmarino(jugador,esFinDeRonda = true)
    //else
    //  posicionarJugadorEnCasillero(jugador,posicionActual - desplazamiento)
  }

  def bajar(jugador: Jugador, unidades: Integer): Unit ={
    var posicionActual:Integer = if (submarino.tieneJugador(jugador))0 else obtenerPosicionJugador(jugador)
    var desplazamiento:Integer = 0

    (0 to (unidades)).foreach( _ =>
      desplazamiento = desplazamiento + (if(hayOtroJugadorEnCasillero(posicionActual+desplazamiento,jugador))2 else 1)
    )
    sacarJugadorDeCasillero(jugador,posicionActual)
    posicionarJugadorEnCasillero(jugador,posicionActual + desplazamiento)
  }

  def bajarDesdeElSubmarino(jugador: Jugador, unidades: Integer): Unit ={
    var posicionActual:Integer = if (submarino.tieneJugador(jugador))0 else obtenerPosicionJugador(jugador)
    var desplazamiento:Integer = 0

    (0 to (unidades-1)).foreach( _ =>
      desplazamiento = desplazamiento + (if(hayOtroJugadorEnCasillero(posicionActual+desplazamiento,jugador))2 else 1)
    )
    posicionarJugadorEnCasillero(jugador,posicionActual + desplazamiento)
  }

  def colocarJugadorEnSubmarino(jugador: Jugador): Unit = {
    subirAlSubmarino(jugador)
  }

  def jugadores(): List[Jugador] = {
    var _jugadores:List[Jugador] = casilleros.filter(tup => tup._2 != null).map(tup => tup._2)
    if (_jugadores.nonEmpty)
      _jugadores
    else
      submarino.jugadores
  }

  def subirAlSubmarino(jugador: Jugador,esFinDeRonda:Boolean = false): Unit = {
      //sacarJugadorDeCasillero(jugador,obtenerPosicionJugador(jugador))
      submarino.subirJugador(jugador)
      if (submarino.tieneJugadores(jugadores()) && esFinDeRonda)
        throw new ExceptionSubieronTodos
  }

  def bajarDeSubmarino(jugador:Jugador,unidades:Integer):Unit = {
    if (unidades > 0 ){
      bajarDesdeElSubmarino(jugador,unidades-1)
      submarino.bajarJugador(jugador)
    }
  }

  def subirAlSubmarinoDesdeTablero(jugador: Jugador,esFinDeRonda:Boolean = false): Unit = {
    sacarJugadorDeCasillero(jugador,obtenerPosicionJugador(jugador))
    submarino.subirJugador(jugador)
    if (submarino.tieneJugadores(jugadores()) && esFinDeRonda)
      throw new ExceptionSubieronTodos
    //sacarJugadorDeCasillero(jugador,obtenerPosicionJugador(jugador))
  }

  def estaCasilleroInicial(jugador: Jugador):Boolean = obtenerPosicionJugador(jugador) == 0


  def eliminarCasillerosLibres():Unit = {
    casilleros = casilleros.filter(tup => !tup._1.isInstanceOf[CasilleroLibre])
  }

  def reasignarCoordenasDeCasilleros():Unit = {

    var nuevosCasilleros:List[(Casillero,Jugador,Integer)] = List()

    for (i <- casilleros.indices) {
      var cTemp = casilleros(i)
      nuevosCasilleros = nuevosCasilleros :+ (cTemp._1,cTemp._2,i.asInstanceOf[Integer])
    }
    casilleros = nuevosCasilleros
  }

  def reiniciar(): Unit = {
    eliminarCasillerosLibres()
    reasignarCoordenasDeCasilleros()
  }

  def hayReliquiaEnPosicion(posicion: Integer): Boolean = {
    casilleros.exists(tup => tup._3 == posicion && tup._1.isInstanceOf[CasilleroConReliquia])
  }

  def posicionJugadorTieneReliquia(jugador: Jugador): Boolean = {
    hayReliquiaEnPosicion(obtenerPosicionJugador(jugador))
  }


  def obtenerReliquiaEnPosicion(posicion:Integer): Casillero = {
    casilleros.filter(tup => tup._3 == posicion).head._1
  }

  def colocarCasilleroEnPosicion(casillero: Casillero, posicion: Integer):Unit = {

    var nuevosCasilleros: List[(Casillero,Jugador,Integer)] = List()

    casilleros.foreach(
      tup =>  if(tup._3 == posicion)
        nuevosCasilleros = nuevosCasilleros :+ (casillero,tup._2,tup._3)
      else
        nuevosCasilleros = nuevosCasilleros :+ tup)
    casilleros = nuevosCasilleros
  }


  def recogerReliquia(posicion: Integer): Casillero = {
    var reliquia:Casillero = obtenerReliquiaEnPosicion(posicion)
    colocarCasilleroEnPosicion(CasilleroLibre(),posicion)
    reliquia
  }

  def cantidadReliquiaEnPosicion(posicion:Integer):Integer = {
    casilleros.filter(tup => tup._3 == posicion).head._1.asInstanceOf[CasilleroConReliquia].reliquia
  }

  def size():Integer = casilleros.size


}











