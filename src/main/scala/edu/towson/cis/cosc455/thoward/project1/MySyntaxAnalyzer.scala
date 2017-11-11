package edu.towson.cis.cosc455.thoward.project1
import scala.collection.mutable.Stack

class MySyntaxAnalyzer extends SyntaxAnalyzer{
  var errorFound : Boolean = false
  var errorCount : Int = 0
  var parseStack = Stack[String]()
  var currentToken: String = ""
  def resetError() = errorFound = false
  def getError : Boolean = errorFound

  override def gittex(): Unit = {
    resetError()
    if (Compiler.currentToken.equalsIgnoreCase(Constants.DOCB)){
      parseStack.push(Compiler.currentToken)
      currentToken = Compiler.Scanner.getNextToken()
    } else setError(Constants.DOCB)
    //variableDefine() //while loop??
    title()
    body()
    makeParse(Constants.DOCE)
    println(parseStack)
    if (errorCount > 0) System.exit(1)
  }

  override def paragraph(): Unit = {
    makeParse(Constants.PARAB)
    //variableDefine()
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
    variableDefine()
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
    if(currentToken.equalsIgnoreCase(Constants.PARAB)){
      paragraph()
      body()
    }
    else if(currentToken.equalsIgnoreCase(Constants.NEWLINE)){
      newline()
      body()
    }
    else if(currentToken.equalsIgnoreCase(Constants.DOCE)){
    }
    else if(currentToken.equalsIgnoreCase(Constants.PARAE)){

    }
    else{
      innerText()
      body()
    }
    //NEED EMPTY CASE
  }

  override def innerItem(): Unit = {
    if (currentToken.equalsIgnoreCase(Constants.USEB)) {
      variableUse()
      innerItem()
    }
    else if (currentToken.equalsIgnoreCase(Constants.BOLD)) {
      bold()
      innerItem()
    }
    else if (currentToken.equalsIgnoreCase(Constants.LINKB)) {
      link()
      innerItem()
    }


    else parseText()

  }

  override def innerText(): Unit = {
    if (currentToken.equalsIgnoreCase(Constants.PARAB)) {
      paragraph()
    }
    if (currentToken.equalsIgnoreCase(Constants.HEADING)) {
      heading()
      innerText()
    }
    else if (currentToken.equalsIgnoreCase(Constants.BOLD)) {
      bold()
      innerText()
    }
    else if (currentToken.equalsIgnoreCase(Constants.LISTITEM)) {
      listItem()
      innerText()
    }
    else if (currentToken.equalsIgnoreCase(Constants.IMAGEB)) {
      image()
      innerText()
    }
    else if (currentToken.equalsIgnoreCase(Constants.USEB)) {
      variableUse()
      innerText()
    }
    else if (currentToken.equalsIgnoreCase(Constants.LINKB)) {
      link()
      innerText()
    }
    else if (currentToken.equalsIgnoreCase(Constants.NEWLINE)) {
      newline()
      innerText()
    }
    else if(currentToken.equalsIgnoreCase(Constants.DOCE)){
      //println(parseStack)
      //
    }
    else
      parseText()
    }

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
    if (!Constants.tokens.contains(currentToken)){ //Need better verification that this is text
      parseStack.push(currentToken)
      currentToken = Compiler.Scanner.getNextToken()
    } else setError("text")
  }
  def setError(expect: String): Unit ={
    errorCount = errorCount + 1
    errorFound = true
    println("Syntax error at " + currentToken + " Expected: " + expect)
  }

}
