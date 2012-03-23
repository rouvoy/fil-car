package fil.car
import java.io.{ BufferedReader, DataOutputStream, InputStreamReader }
import java.net.InetAddress

class AnnuaireStub(host: String, port: Int) extends IAnnuaire {
  lazy val socket = new java.net.Socket(InetAddress.getByName(host), port)
  lazy val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
  lazy val out = new DataOutputStream(socket.getOutputStream)

  def envoyer(opcode: String)(msg: String*) = {
    out writeBytes opcode + ";" + msg.mkString(";") + "\n"
    in readLine
  }

  def ajouter(adLogique: String, adPhysique: Adresse) =
    envoyer("AJOUTER")(adLogique, adPhysique.encode).toBoolean

  def retirer(adLogique: String) =
    envoyer("RETIRER")(adLogique).toBoolean

  def modifier(adLogique: String, adPhysique: Adresse) =
    envoyer("MODIFIER")(adLogique, adPhysique.encode).toBoolean

  def lister = {
    val n = envoyer("LISTER")().toInt
    for (i <- 0 until n) yield in readLine
  }

  def chercher(adLogique: String) =
    if (envoyer("CHERCHER")(adLogique).toBoolean)
      Some(Adresse decode in.readLine)
    else
      None
}