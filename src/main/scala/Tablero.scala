import scala.None

class Tablero() {

  var casilleros: Array[(Casillero,Jugador)] = Array()
  var posicionesPorJugadores: Map[Jugador,Integer] = Map()
  crearCasilleros()

  def crearCasilleros(): Unit = {
    for (i <- 0 to 31){
      if(i <= 7)
        casilleros = casilleros :+ (CasilleroConReliquia(Baja, Randoms.valorDeReliquia(Baja)),null)

      if(i >= 8 && i <= 15)
        casilleros = casilleros :+ (CasilleroConReliquia(Media, Randoms.valorDeReliquia(Media)),null)

      if(i >= 16 && i <= 23)
        casilleros = casilleros :+ (CasilleroConReliquia(Alta, Randoms.valorDeReliquia(Alta)),null)

      if(i >= 24 && i <= 31)
        casilleros = casilleros :+ (CasilleroConReliquia(Maxima, Randoms.valorDeReliquia(Maxima)),null)
    }
  }

  def moverJugador(jugador:Jugador,unidades:Integer, direccion: Direccion):Unit = {


    //direccion match{
    //  case Arriba => subir(jugador:Jugador,unidades:Integer)
    //  case Abajo =>  bajar(jugador:Jugador,unidades:Integer)
    //}
  }

  //def posicionesContrincantesSubiendo(jugador:Jugador,posicionActual:Integer): List[Integer] = {

    //var posiciones: List[Integer] = List[Integer]()

    //posicionesPorJugadores.keys.filter(k => k != jugador).map(k => posicionesPorJugadores.get(k)).foreach(
    //  p => posiciones = posiciones :+ p
    //)

//  posicionesPorJugadores.toList.foreach(
//    (k,v)  => if (k != jugador) {
//        posiciones = posiciones :+ p
//      }

   // posicionesPorJugadores.values.fold(p => (0 to casilleros.size-1). )
   // (posicionActual to casilleros.size-1).


    //)


  //}

  def subir(jugador: Jugador, unidades: Integer): Unit ={
    var posicionActual = posicionesPorJugadores.get(jugador)
    /*
    (0 to unidades).foreach(
      if (posicionesContrincantesSubiendo(jugador,posicionActual).contains(posicionActual)){

      }
      else{

      }
    )
    */
  }

  def bajar(jugador: Jugador, integer: Integer): Unit ={

  }


}
