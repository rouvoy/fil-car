package fil.car
import java.io.{ BufferedReader, DataOutputStream, InputStreamReader }
import java.net.InetAddress

class AnnuaireStub(ip: String, port: Int) extends IAnnuaire {
  lazy val socket = new java.net.Socket(InetAddress.getByName(ip), port)
  lazy val input = new BufferedReader(new InputStreamReader(socket.getInputStream))
  lazy val output = new DataOutputStream(socket.getOutputStream)

  def send(opcode: String)(msg: String*) = {
    output writeBytes opcode + ";" + msg.mkString(";") + "\n"
    input.readLine
  }

  def ajouter(adLogique: String, adPhysique: Adresse) =
    send("AJOUTER")(adLogique, adPhysique.encode).toBoolean

  def retirer(adLogique: String) = send("RETIRER")(adLogique).toBoolean

  def modifier(adLogique: String, adPhysique: Adresse) =
    send("MODIFIER")(adLogique, adPhysique.encode).toBoolean

  def lister = {
    val n = send("LISTER")().toInt
    for (i <- 0 until n) yield input.readLine
  }

  def chercher(adLogique: String) =
    if (send("CHERCHER")(adLogique).toBoolean)
      Some(Adresse decode input.readLine)
    else
      None
}