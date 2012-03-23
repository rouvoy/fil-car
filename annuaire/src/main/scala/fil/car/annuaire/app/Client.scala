package fil.car.annuaire.app {
  import fil.car.annuaire.tcp.AnnuaireFactory
  import fil.car.annuaire.Adresse

  object Client extends Application {
    println("Client starting...")
    val annuaire = AnnuaireFactory.souche("localhost", 9999)

    annuaire.ajouter("Google Search", Adresse("www.google.com", 80))
    annuaire.ajouter("FTP Lille!", Adresse("ftp.fil.univ-lille1.fr", 21))

    annuaire.lister foreach { adr =>
      println("=> " + adr + " = " + annuaire.chercher(adr).get)
    }

    annuaire retirer "Google Search"
    annuaire retirer "FTP Lille!"
  }
}