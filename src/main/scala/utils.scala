import scala.util.Random

class utils {

  def desplazarRandomSeedN(n:Integer,umbral:Integer = 6):Unit ={
    (0 until n).foreach(_ =>
      Random.nextInt(umbral)
      //print(i + "\n")
    )
  }
}