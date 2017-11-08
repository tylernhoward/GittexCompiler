package edu.towson.cis.cosc455.thoward.project1

import java.util

import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters


class MyLexicalAnalyzer extends LexicalAnalyzer{

  private var file : String= ""
  private var currentToken: List[Char] = List()
  private var nextChar:Char = 0
  private var position = 0
  private var lexemes : List[String] = List()


  def start(f: String) :Unit = {
      initializeLexemes()
      file = f
      position = 0
      getChar()
      getNextToken()
  }

  override def getNextToken(): String = {

    var tokenDiscovered: Boolean = false

    while(!tokenDiscovered){
      var c = getChar()
      if(Constants.leadSymbols.contains(c)){ //potential start of a token
        tokenDiscovered = true
        if(c == '\\' || c == '!'){
          addChar()
          c = getChar()
          if(c == '\\'){
            return currentToken.toString() //New line
          }
          else{
            while(!isSpace(c) && c == '['){
              addChar()
              c = getChar()
            }
          }
          if(lookup(currentToken.toString())){
            return currentToken.toString()
          }
          else{
            //ERROR
          }
        }
        else return currentToken.toString()
      }
    }
    return "" //THIS NEEDS TO BE ALTERED
  }

  override def lookup(candidateToken: String): Boolean = {
    Constants.tokens.contains(candidateToken)
  }

  override def getChar(): Char = {
    if (position < file.length()) {
      position = position + 1
      nextChar = file.charAt(position - 1)
    }
    nextChar
  }

  override def addChar(): Unit = {
    currentToken :+= nextChar
  }

  private def isSpace(c: Char):Boolean = {
     Constants.whiteSpace.contains(c)
  }

  private def initializeLexemes() = {
    lexemes = Constants.tokens ::: Constants.letters ::: Constants.numbersEtc

  }

}

