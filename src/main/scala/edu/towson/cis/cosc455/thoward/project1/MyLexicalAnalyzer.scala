package edu.towson.cis.cosc455.thoward.project1

import edu.towson.cis.cosc455.thoward.project1.helpers.Constants
import edu.towson.cis.cosc455.thoward.project1.traits.LexicalAnalyzer


class MyLexicalAnalyzer extends LexicalAnalyzer{

  private var file : String= ""
  private var currentToken: List[Char] = List()
  var found: List[String] = List()
  private var nextChar:Char = 0
  private var position = 0
  private var validText : List[String] = List()
  private var lexemes : List[String] = List()

  def start(f: String): Unit = {
      initializeLexemes()
      file = f
      position = 0
      //getChar()
      //getNextToken()
  }

  override def getNextToken(): String = {
    getChar()
    currentToken = List()
    //Ignore white space
    while(isSpace(nextChar)){
      getChar()
    }
    //If potential start of token, process token
    if(isStartSymbol()){
      nextChar match {
        case '=' => addChar()
        case '(' => addChar()
        case '+' => addChar()
        case '*' => addChar()
        case '#' => addChar()
        case ')' => addChar()
        case ']' => addChar()
        case '[' => addChar()
        case '!' => processToken()
        case '\\' => processToken()
        case  _ =>  System.exit(1)    //ERRORLOG
      }
      //check if collected is valid token
      checkIfValid()
    }
    // If the char is not a token start, process if it valid text
    else if (validText.contains(nextChar.toString())){
      processText()
    } else{
      println("Lexical Error at \n\t" + nextChar + "\nThis is not valid text")
      System.exit(1)
    }
    //return collected token / text
    found :+= currentToken.mkString
    currentToken.mkString
  }

  def checkIfValid(): Unit ={
    if(!lookup(currentToken.mkString)){
      println("Lexical Error at \n\t" + currentToken.mkString + "\nThis is an invalid token")
      System.exit(1)
    }
    else {
      currentToken = currentToken.map(_.toTitleCase)
    }

  }

  def processText(): Unit ={
    var shouldContinue: Boolean = true

    while(shouldContinue){
      if(isStartSymbol()){
        shouldContinue = false
      }
      //do not process white space other than spaces
      else if (!Constants.whiteEscapes.contains(file.charAt(position)) && !Constants.leadSymbols.contains(file.charAt(position))) {
        addChar()
        getChar()
      }
      else{
        addChar()
        shouldContinue = false
      }
    }
  }

  def processToken(): Unit = {
    var shouldContinue:Boolean = true
    if (nextChar.equals('!')) {
      addChar()
      getChar()
      if (nextChar.equals('[')) {
        addChar()
      }
    }
    else if (nextChar.equals('\\')) {
      addChar()
      getChar()
      if (nextChar.equals('\\')) {
        addChar()
      }
      else {
        while (shouldContinue) {
          if (nextChar.equals('[')) {
            addChar()
            shouldContinue = false
          }
          else if(isSpace(nextChar) || lookup(currentToken.mkString)){
            shouldContinue = false
          }
          else {
            addChar()
            getChar()
          }
        }
      }
    }
  }

  def isStartSymbol(): Boolean ={
      Constants.leadSymbols.contains(nextChar)
  }

  override def lookup(candidateToken: String): Boolean = {
      lexemes.contains(candidateToken.toUpperCase)
  }

  override def getChar(): Unit = {
    if (position < file.length()) {
      nextChar = file.charAt(position)
      position = position + 1
    }
  }

  override def addChar(): Unit = {
    currentToken :+= nextChar
  }

  private def isSpace(c: Char):Boolean = {
    Constants.whiteSpace.contains(c.toString())
  }

  private def initializeLexemes() = {
    validText = Constants.letters ::: Constants.numbersEtc
    lexemes = Constants.tokens

  }

}

