import org.scalatest.{FunSpec, Matchers}

class CalculadoraTest extends  FunSpec with Matchers {

  describe("Calculadora.sumar") {
    it("Una calculadora suma dos numeros positivos") {
      val calc = new Calculadora
      calc.sumar(1,2) shouldBe 3
    }
  }

}
