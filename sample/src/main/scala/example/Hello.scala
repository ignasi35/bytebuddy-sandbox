package example

object Hello extends App {

  private val time = System.currentTimeMillis()
  val greeting: String = s"(${time%100}) hello"
  val ims = Person("Ignasi", "Marimon", 45)
  val ims2 = ims.copy(givenName = "Ignasi2")
  val ims3 = ims2.copy(givenName = "Ignasi3")
  val ims4 = ims3.copy(age = 27)

  println(greeting + " " + ims2 )
  println(greeting + " " + ims3 )
  println(greeting + " " + ims4 )

}
