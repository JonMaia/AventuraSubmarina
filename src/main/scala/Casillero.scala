class Casillero

case class CasilleroConReliquia(profundidad: NivelDeProfundidad, reliquia: Int) extends Casillero
case class CasilleroLibre() extends Casillero
case class CasilleroSubmarino() extends Casillero