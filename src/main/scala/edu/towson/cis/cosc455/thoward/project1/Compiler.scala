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
    //This is the one that does not work for me, reverted back to version that worked with the most test cases, BLEGH
    //fileContents = "\\BEGIN\n\t\\DEF[lastname = Simpson]\n\t\\DEF[hname = Homer]\n\t\\DEF[bname = Bart]\n\t\\TITLE[Variables]\n\n\t\\PARAB\n\t\tThe members of the \\USE[lastname] family are:\n\t\\PARAE\n\n\t+ \\USE[hname]  \\USE[lastname]\n\t+ Marge \\USE[lastname]\n\t+ \\USE[bname]  \\USE[lastname]\n\t+ Lisa \\USE[lastname]\n\t+ Maggie \\USE[lastname]\n\\END"
    checkFile(args)
    readFile(args(0))
    Scanner.start(fileContents)
    currentToken = Scanner.getNextToken()
    Parser.gittex()
    convertedFile = SemanticAnalyzer.process()
    //testOutputs()
    produceFile(args(0))
  }

  def readFile(file : String) = {
    val source = scala.io.Source.fromFile(file)
    fileContents = try source.mkString finally source.close()
  }

  def checkFile(args : Array[String]) = {
    if (args.length != 1) {
      println("USAGE ERROR: Wrong number of arguments (see -help)")
      System.exit(1)
    }
    else if (args(0).equals("-help")){
      println("---HELP---")
      println("This is a compiler for a language called 'Gittex' (Git/Latex).")
      println("See documentation at https://github.com/tylernhoward/GittexCompiler")
      println("\nCompiler takes in .gtx files as arguments.\nProper use:\n\t'java -jar compiler.jar file_name.gtx'")
      println("You may specify a folder path like this:\n\t'java -jar compiler.jar folder_name/file_name.gtx'")
      println("\nEnjoy! -Tyler")
      System.exit(1)

    }
    else if (! args(0).endsWith(".gtx")) {
      println("USAGE ERROR: Wrong extension, needs '.gtx' (see -help)")
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
    println("File saved at:\n\t" +file.getAbsolutePath)
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
