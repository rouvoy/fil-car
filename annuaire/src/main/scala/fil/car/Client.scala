package fil.car

object Client extends Application {
  println("Client starting...")
  val annuaire = Annuaire.stub("localhost", 9999)

  annuaire.ajouter("Google Search", Adresse("www.google.com", 80))
  annuaire.ajouter("FTP Lille!", Adresse("ftp.fil.univ-lille1.fr", 21))

  annuaire.lister foreach { adr =>
    println("=> " + adr + " = " + annuaire.chercher(adr).get)
  }
}
