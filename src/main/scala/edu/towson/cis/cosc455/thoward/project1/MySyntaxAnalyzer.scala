package edu.towson.cis.cosc455.thoward.project1
import scala.collection.mutable.Stack

class MySyntaxAnalyzer extends SyntaxAnalyzer{
  var errorFound : Boolean = false
  var parseStack = Stack[String]()
  var currentToken: String = ""
  def setError() = errorFound = true
  def resetError() = errorFound = false
  def getError : Boolean = errorFound

  override def gittex(): Unit = {
    if (Compiler.currentToken.equalsIgnoreCase(Constants.DOCB)){
      parseStack.push(Compiler.currentToken)
      currentToken = Compiler.Scanner.getNextToken()
    } else setError()
    //variableDefine() //while loop??
    title()
    body()
    makeParse(Constants.DOCE)
  }

  override def paragraph(): Unit = {
    makeParse(Constants.PARAB)
    //variableDefine()
//    innerText()
    makeParse(Constants.PARAE)
  }

  override def link(): Unit = {
    makeParse(Constants.LINKB)
    //text
    makeParse(Constants.BRACKETE)
    makeParse(Constants.ADDRESSB)
    //text
    makeParse(Constants.ADDRESSE)
  }

  override def bold(): Unit = {
    makeParse(Constants.BOLD)
    //text
    makeParse(Constants.BOLD)
  }

  override def newline(): Unit = {
    makeParse(Constants.NEWLINE)
  }

  override def title(): Unit = {
    makeParse(Constants.TITLEB)
    //check for text
    makeParse(Constants.BRACKETE)
  }

  override def variableDefine(): Unit = {
    makeParse(Constants.DEFB)
    //text
    makeParse(Constants.EQSIGN)
    //text
    makeParse(Constants.BRACKETE)
    variableDefine()
  }

  override def image(): Unit = {
    makeParse(Constants.IMAGEB)
    //text
    makeParse(Constants.BRACKETE)
    makeParse(Constants.ADDRESSB)
    //text
    makeParse(Constants.ADDRESSE)
  }

  override def variableUse(): Unit = {
    makeParse(Constants.USEB)
    //text
    makeParse(Constants.BRACKETE)
  }

  override def heading(): Unit = {
    makeParse(Constants.HEADING)
    //checkfortext
  }

  override def listItem(): Unit = {
    makeParse(Constants.LISTITEM)
    innerItem()
//    listItem()
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
      return
    }
    else {
      innerText()
//      body()
    }
    //need empty
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
    else if (currentToken.equalsIgnoreCase(Constants.DOCE)) {
      return
    }
    else return //text

  }

  override def innerText(): Unit = {
    if (currentToken.equalsIgnoreCase(Constants.PARAE)) {
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
    else if (currentToken.equalsIgnoreCase(Constants.DOCE)) {
      return
    }
    else return
      //text
  }

  def makeParse(cToken:String): Unit = {
    if (currentToken.equalsIgnoreCase(cToken)){
      parseStack.push(Compiler.currentToken)
      currentToken = Compiler.Scanner.getNextToken()
    } else setError()
  }
}
