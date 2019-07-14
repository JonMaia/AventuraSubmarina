
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
      case Arriba => subir(jugador:Jugador,unidades:Integer)
      case Arriba if estaCasilleroInicial(jugador) => subirAlSubmarino(jugador:Jugador,unidades:Integer)
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

    casilleros.foreach( tup => if(tup._2 == jugador) nuevosCasilleros :+ (tup._1,null,tup._3))
    casilleros = nuevosCasilleros
  }

  def posicionarJugadorEnCasillero(jugador: Jugador,casillero:Integer): Unit ={
    var nuevosCasilleros: List[(Casillero,Jugador,Integer)] = List()

    casilleros.foreach( tup => if(tup._3 == casillero) nuevosCasilleros :+ (tup._1,jugador,tup._3))
    casilleros = nuevosCasilleros
  }

  def obtenerPosicionJugador(jugador: Jugador): Integer ={
    if (submarino.tieneJugador(jugador))-1
    else casilleros.filter(tup => tup._2 == jugador).head._3
  }



  def subir(jugador: Jugador, unidades: Integer): Unit ={
    var posicionActual:Integer = obtenerPosicionJugador(jugador)
    var desplazamiento:Integer = 0

    (0 to unidades).foreach( _ =>
      desplazamiento = desplazamiento + (if(hayOtroJugadorEnCasillero(posicionActual,jugador)) 2 else 1)
    )

    posicionarJugadorEnCasillero(jugador,posicionActual - desplazamiento)
  }

  def bajar(jugador: Jugador, unidades: Integer): Unit ={
    var posicionActual:Integer = obtenerPosicionJugador(jugador)
    var desplazamiento:Integer = 0

    (0 to unidades).foreach( _ =>
      desplazamiento = desplazamiento + (if(hayOtroJugadorEnCasillero(posicionActual,jugador)) 2 else 1)
    )

    posicionarJugadorEnCasillero(jugador,posicionActual + desplazamiento)
  }

  def jugadorSubioASubmarino(jugador: Jugador): Unit = {

  }

  def subirAlSubmarino(jugador: Jugador, integer: Integer): Unit = {
      submarino.subirJugador(jugador)
      jugadorSubioASubmarino(jugador)
  }

  def bajarDeSubmarino(jugador:Jugador,unidades:Integer):Unit = {
    submarino.bajarJugador(jugador)
    posicionarJugadorEnCasillero(jugador,0)
  }

  def estaCasilleroInicial(jugador: Jugador):Boolean = obtenerPosicionJugador(jugador) == 0


}











