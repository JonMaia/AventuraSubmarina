case class Submarino() {

  var jugadores: List[Jugador] = List()

  def subirJugador(jugador: Jugador):Unit = {
    jugadores = jugadores :+ jugador
  }
  def bajarJugador(jugador: Jugador):Unit ={
    jugadores = jugadores.filter(j=> j!= jugador)
  }

  def tieneJugador(jugador: Jugador): Boolean = jugadores.size > 0 && jugadores.contains(jugador)

  def tieneJugadores(jugadores: List[Jugador]):Boolean = {
    jugadores.count(j => tieneJugador(j)) == jugadores.size
  }


}
