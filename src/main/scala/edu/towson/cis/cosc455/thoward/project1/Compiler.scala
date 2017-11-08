package edu.towson.cis.cosc455.thoward.project1

object Compiler{

  var currentToken : String = ""
  var fileContents : String = ""

  val Scanner = new MyLexicalAnalyzer
  val Parser = new MySyntaxAnalyzer
  val SemanticAnalyzer = new MySyntaxAnalyzer

  def main(args: Array[String]): Unit = {
    //checkFile(args)
    //readFile(args(0))
    fileContents = "\\BEGIN\n\t\\TITLE[The Simpsons]\n\t# The Simpsons\n\t\\PARAB\n\t\tThe members of the [The Simpsons]" +
      "(https://en.wikipedia.org/wiki/The_Simpsons) are:\n\t\\PARAE\n\t+ Homer Simpson\n\t+ Marge Simpson\n\t+ Bart Simpson" +
      "\n\t+ Lisa Simpson\n\t+ Maggie Simpson\n\tHere is a picture:\n\t\\\\\n\t![The Simpsons] " +
      "(https://upload.wikimedia.org/wikipedia/en/0/0d/Simpsons_FamilyPicture.png)\n\\END\t"
    println(fileContents)
    Scanner.start(fileContents)
    currentToken = Scanner.getNextToken()

    Parser.gittex()

//    produceFile(args(0))
  }

  def readFile(file : String) = {
    val source = scala.io.Source.fromFile(file)
    fileContents = try source.mkString finally source.close()
  }

  def checkFile(args : Array[String]) = {
    if (args.length != 1) {
      println("USAGE ERROR: wrong number of args fool!")
      System.exit(1)
    }
    else if (! args(0).endsWith(".gtx")) {
      println("USAGE ERROR: wrong extension fool!")
      System.exit(1)
    }
  }
  def produceFile(file: String) = {
      //FileWriter
  }
}
