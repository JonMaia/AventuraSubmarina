import org.scalatest.{FunSpec, Matchers}

class RandomsSpec extends  FunSpec with Matchers {
  describe("Randoms") {
    it("lanzarDadoControlable") {
      Randoms.lanzarDado(5) shouldEqual 5
      Randoms.lanzarDado(3) shouldEqual 3
      0 to 5 contains Randoms.lanzarDado() shouldBe true
    }

    it("nivelDeProfundidadControlable") {
      Randoms.valorDeReliquia(Baja, 3) shouldEqual 3
      Randoms.valorDeReliquia(Maxima, 14) shouldEqual 14
      0 to 3 contains Randoms.valorDeReliquia(Baja) shouldBe true
      12 to 15 contains Randoms.valorDeReliquia(Maxima) shouldBe true
    }

    it("creartablero") {
      var tablero: Array[Casillero] = Array()


      for (i <- 0 to 31){
        if(i <= 7) {
          tablero = tablero :+ Casillero(Baja, Randoms.valorDeReliquia(Baja))
        }

        if(i >= 8 && i <= 15) {
          tablero = tablero :+ Casillero(Media, Randoms.valorDeReliquia(Media))
        }

        if(i >= 16 && i <= 23) {
          tablero = tablero :+ Casillero(Alta, Randoms.valorDeReliquia(Alta))
        }

        if(i >= 24 && i <= 31) {
          tablero = tablero :+ Casillero(Maxima, Randoms.valorDeReliquia(Maxima))
        }
      }

      tablero.length shouldEqual 32
      
    }
  }

}
