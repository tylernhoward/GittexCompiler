package edu.towson.cis.cosc455.thoward.project1

import java.util

import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters


class MyLexicalAnalyzer extends LexicalAnalyzer{
  import java.util

  private var file : String= ""
  private var currentToken = new Array[Char](100)
  private var nextChar:Char = 0
  private var position = 0
  private var lexemes = new util.ArrayList[String]

  def start(f: String) :Unit = {

      initializeLexemes()
      file = f
      position = 0;

      getChar();
      getNextToken();
  }
  override def getNextToken(): Unit = {

    var isNextToken: Boolean = false

    while

  }

  override def lookup(candidateToken: String): Boolean = {
    if(Constants.tokens.contains(candidateToken)){
      return true;
    }
    else return false
  }

  override def getChar(): Unit = {
    if (position < file.length()) {
      nextChar = file.charAt(position);
      position = position + 1
    }
    else return

  }
  override def addChar(): Unit = {
    currentToken :+= nextChar
  }

  private def isSpace(c: Char):Boolean = {
      c == ' ' || c == '\r' || c == '\n' || c == '\t'
  }

  private def initializeLexemes() = {
    lexemes = Constants.tokens
    lexemes.add(('a' to 'z').toString())
    lexemes.add(('A' to 'Z').toString())
    lexemes.add(('1' to '9').toString())

  }


}

