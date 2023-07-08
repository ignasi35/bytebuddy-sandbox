package example

object Hello extends App {

  private val time = System.currentTimeMillis()
  val greeting: String = s"(${time%100}) hello"
  val ims = Person("Ignasi", "Marimon", 45)
  val ims2 = ims.copy(givenName = "Ignasi2")
  val ims3 = ims.copy(givenName = "Ignasi3")

  println(greeting + " " + ims2 )
  println(greeting + " " + ims3 )

//  new ThingyTimed().hello()
}

//class ThingyTimed(){
//  def hello(): Unit = {
//    println(" Timed hello")
//  }
//}
//
