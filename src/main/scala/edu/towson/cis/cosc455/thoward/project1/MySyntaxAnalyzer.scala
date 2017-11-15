package edu.towson.cis.cosc455.thoward.project1
import edu.towson.cis.cosc455.thoward.project1.helpers.Constants
import edu.towson.cis.cosc455.thoward.project1.traits.SyntaxAnalyzer

import scala.collection.mutable.Stack

class MySyntaxAnalyzer extends SyntaxAnalyzer{
  var errorFound : Boolean = false
  //var errorCount : Int = 0
  var parseStack = Stack[String]()
  var currentToken: String = ""
  def resetError() = errorFound = false
  def getError : Boolean = errorFound

  override def gittex(): Unit = {
    resetError()
    //get the first token from the compiler object instead of specialty method
    if (Compiler.currentToken.equalsIgnoreCase(Constants.DOCB)){
      parseStack.push(Compiler.currentToken)
      currentToken = Compiler.Scanner.getNextToken()
    } else setError(Constants.DOCB)
    while(currentToken.equalsIgnoreCase(Constants.DEFB)){variableDefine()} // multiple variables can be defined
    title()
    body()
    makeParse(Constants.DOCE)
    checkForStragglers() //check if anything existed past \END
  }

  override def paragraph(): Unit = {
    makeParse(Constants.PARAB)
    while(currentToken.equalsIgnoreCase(Constants.DEFB)){variableDefine()}
    innerText()
    innerItem()
    makeParse(Constants.PARAE)
  }

  override def link(): Unit = {
    makeParse(Constants.LINKB)
    parseText()
    makeParse(Constants.BRACKETE)
    makeParse(Constants.ADDRESSB)
    parseText()
    makeParse(Constants.ADDRESSE)
  }

  override def bold(): Unit = {
    makeParse(Constants.BOLD)
    parseText()
    makeParse(Constants.BOLD)
  }

  override def newline(): Unit = {
    makeParse(Constants.NEWLINE)
  }

  override def title(): Unit = {
    makeParse(Constants.TITLEB)
    parseText()
    makeParse(Constants.BRACKETE)
  }

  override def variableDefine(): Unit = {
    makeParse(Constants.DEFB)
    parseText()
    makeParse(Constants.EQSIGN)
    parseText()
    makeParse(Constants.BRACKETE)
  }

  override def image(): Unit = {
    makeParse(Constants.IMAGEB)
    parseText()
    makeParse(Constants.BRACKETE)
    makeParse(Constants.ADDRESSB)
    parseText()
    makeParse(Constants.ADDRESSE)
  }

  override def variableUse(): Unit = {
    makeParse(Constants.USEB)
    parseText()
    makeParse(Constants.BRACKETE)
  }

  override def heading(): Unit = {
    makeParse(Constants.HEADING)
    parseText()
  }

  override def listItem(): Unit = {
    makeParse(Constants.LISTITEM)
    innerItem()
  }
  override def body(): Unit = {
    currentToken match {
      case Constants.PARAB => {
        paragraph()
        body()
      }
      case Constants.NEWLINE =>{
        newline()
        body()
      }
      case Constants.DOCE => //do nothing
      case Constants.PARAE => //do nothing
      case _ =>{
        innerText()
        body()
      }
    }
  }

  override def innerItem(): Unit = {
    currentToken match {
      case Constants.USEB => {
        variableUse()
        innerItem()
    }
      case Constants.BOLD => {
        bold()
        innerItem()
      }
      case Constants.LINKB => {
        link()
        innerItem()
      }
      case _ => parseText()
    }
  }

  override def innerText(): Unit = {
    currentToken match {
      case Constants.PARAB => paragraph()
      case Constants.HEADING => {
        heading()
        innerText()
      }
      case Constants.LISTITEM => {
        listItem()
        innerText()
      }
      case Constants.IMAGEB => {
        image()
        innerText()
      }
      case Constants.BOLD => {
        bold()
        innerText()
      }
      case Constants.USEB => {
        variableUse()
        innerText()
      }
      case Constants.LINKB => {
        link()
        innerText()
      }
      case Constants.NEWLINE => {
        newline()
        innerText()
      }
      case Constants.DOCE => //do nothing
      case _ => parseText()
    }
  }

  /* * Methods to add to Stack given a specific token constant * */
  def makeParse(cToken:String): Unit = {
    resetError()
    if (currentToken.equalsIgnoreCase(cToken)){
      parseStack.push(currentToken)
      if(!cToken.equalsIgnoreCase(Constants.DOCE)){
        currentToken = Compiler.Scanner.getNextToken()
      }
    } else setError(cToken)
  }
  def parseText(): Unit ={
    resetError()
    if (!Constants.tokens.contains(currentToken)){ //Need better verification this is text
      parseStack.push(currentToken)
      currentToken = Compiler.Scanner.getNextToken()
    } else setError("text")
  }

  /* * If method was called, print syntax error, what was expected and exit * */
  def setError(expect: String): Unit ={
    //errorCount = errorCount + 1
    errorFound = true
    println("Syntax error at \n\t" + currentToken + "\nExpected: " + expect)
    System.exit(1)
  }

  /* Checks if anything was residing beyond our 'syntactical' walls*/
  def checkForStragglers(): Unit ={
    var file = Compiler.fileContents.toUpperCase()
    //get all text from \END to the end of the file (ignore spaces)
    file = file.substring(file.indexOf(Constants.DOCE),file.length()).trim()
    if (!file.endsWith(Constants.DOCE)){
      println("Syntax error at \n\t" + file + "\nNo tokens may exist after \\END")
      System.exit(1)
    }
  }

}
