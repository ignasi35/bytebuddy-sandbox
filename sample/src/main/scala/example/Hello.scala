package example

object Hello extends App {


  private val time = System.currentTimeMillis()
  lazy val greeting: String = s"FAILED! (${time%100}) hello"
  val jp = Person("JP", "bmpl", 43)
  val jp2 = jp.copy(givenName = "JCJP_before")

  val jp3 = jp.copy(givenName = "JCJP_after")

  println(greeting + " " + jp2 )
  println(greeting + " " + jp3 )

  new ThingyTimed().hello()
}

@WithTracking
case class Person(givenName:String, familyName: String, age: Int)

class ThingyTimed(){
  def hello(): Unit = {
    println(" Timed hello")
  }
}
