import scala.util.Random


object Randoms {

  var numero: Int = -1

  def lanzarControlable(valorControlable: Int): Unit = {
    this.numero = valorControlable
  }

  def desactivarControlable(): Unit = {
    this.numero = -1
  }

  //Cuando se usa sin parametro es random
  def lanzarDado(): Int = {
    this.numero match {
      case -1 => Random.nextInt(6)
      case n => n
     }
  }

  //Cuando se usa sin parametro es random
  def valorDeReliquia(profundidad: NivelDeProfundidad, numero: Int = -1): Int = {
    if(numero == -1)
      profundidad match {
        case Baja => Random.nextInt(4)
        case Media => Random.nextInt(8 - 4) + 4
        case Alta => Random.nextInt(12 - 8) + 8
        case Maxima => Random.nextInt(16 - 12) + 12
      } else {
      numero
    }

  }

}
