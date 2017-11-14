package edu.towson.cis.cosc455.thoward.project1

import java.awt.Desktop
import java.io.{BufferedWriter, File, FileWriter, IOException}

object Compiler{

  var currentToken : String = ""
  var fileContents : String = ""
  var convertedFile: String = ""

  val Scanner = new MyLexicalAnalyzer
  val Parser = new MySyntaxAnalyzer
  val SemanticAnalyzer = new MySemanticAnalyzer

  def main(args: Array[String]): Unit = {
    //checkFile(args)
    //readFile(args(0))
    fileContents = "\\begin\n\t\\TITLE[The Simpsons]\n\t# The Simpsons\n\t\\PARAB\n\t\tThe members of the [The Simpsons](https://en.wikipedia.org/wiki/The_Simpsons) are:\n\t\\PARAE\n\t+ Homer Simpson\n\t+ Marge Simpson\n\t+ Bart Simpson\n\t+ Lisa Simpson\n\t+ Maggie Simpson\n\tHere is a * picture * :\n\t\\\\\n\t![The Simpsons] (https://upload.wikimedia.org/wikipedia/en/0/0d/Simpsons_FamilyPicture.png)\n\\end"

    Scanner.start(fileContents)
    currentToken = Scanner.getNextToken()
    Parser.gittex()
    convertedFile = SemanticAnalyzer.process()

    testOutputs()

    //produceFile(args(0))
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
  def produceFile(fileName: String) = {
      val name = fileName.substring(0,fileName.indexOf('.')) + ".html"
      val file = new File(name)
      val writer = new BufferedWriter(new FileWriter(file))
      writer.write(convertedFile)
      writer.flush();
      writer.close();
      openHTMLFileInBrowser(name)
  }

  /* * Hack Scala/Java function to take a String filename and open in default web browser. */
  def openHTMLFileInBrowser(htmlFileStr : String) = {
    val file : File = new File(htmlFileStr.trim)
    println(file.getAbsolutePath)
    if (!file.exists())
      sys.error("File " + htmlFileStr + " does not exist.")

    try {
      Desktop.getDesktop.browse(file.toURI)
    }
    catch {
      case ioe: IOException => sys.error("Failed to open file:  " + htmlFileStr)
      case e: Exception => sys.error("He's dead, Jim!")
    }
  }

  def testOutputs(): Unit ={
    println("File Contents\n" + fileContents + "\n")
    println("Lexemes Found\n" + Scanner.found + "\n")
    println("Syntax Stack\n" + Parser.parseStack + "\n" )
    println("Converted HTML\n" + convertedFile)
  }
}
