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
    //fileContents = "\\BEGIN\n\t\\DEF[lastname = Simpson]\n\t\\TITLE[Variables] \n\t\n\t\\PARAB\n\t\tThe members of the \\USE[lastname] family are:\n\t\\PARAE\n\t\n\t+ Homer \\USE[lastname] \n\t+ Marge \\USE[lastname]\n\t+ Bart \\USE[lastname]\n\t+ Lisa \\USE[lastname]\t\n\t+ Maggie \\USE[lastname]\n\\END "
    fileContents = "\\begin\n\t\\DeF[name = Josh]\n\t\\TitlE[Scoped Variables] \n\n\tMy name is \\use[name]. \n\t\n\t\\PARAB\n\t\t\\DEF[name = Jon]\n\t\tInside the paragraph block, my name is \\USE[name].\n\t\\PARAE\n\n\tNow, my name is \\USE[name] again.\n\\END\t "
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
